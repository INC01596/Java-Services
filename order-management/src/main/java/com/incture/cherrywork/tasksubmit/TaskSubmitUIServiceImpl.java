package com.incture.cherrywork.tasksubmit;




import static com.incture.cherrywork.WConstants.Constants.INVALID_INPUT;
import static com.incture.cherrywork.WConstants.StatusConstants.LEVEL_COMPLETE;
import static com.incture.cherrywork.WConstants.StatusConstants.LEVEL_IN_PROGRESS;
import static com.incture.cherrywork.WConstants.StatusConstants.TASK_COMPLETE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.OdataS.OdataService;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.CommentDaoImpl;
import com.incture.cherrywork.dao.SalesDocHeaderDao;
import com.incture.cherrywork.dao.SalesDocItemDao;

import com.incture.cherrywork.dto.new_workflow.ListOfChangedItemData;
import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderValidatorDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.dto.workflow.OrderToItems;
import com.incture.cherrywork.dtos.CommentDto;
import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.dtos.OdataBatchOnsubmitItem;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.new_workflow.dao.SalesOrderItemStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.dlv_block.DlvBlockReleaseMapService;
import com.incture.cherrywork.services.new_workflow.SalesOrderTaskStatusService;
import com.incture.cherrywork.util.DestinationClient;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ODataBatchUtil;
import com.incture.cherrywork.workflow.services.ApprovalworkflowTrigger;
import com.incture.cherrywork.workflow.services.TriggerImeDestinationService;
import com.incture.cherrywork.tasksubmitdto.OdataBatchOnSubmitPayload;


@Service
@Transactional
public class TaskSubmitUIServiceImpl implements TaskSubmitUIService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TriggerImeDestinationService triggerImeService;

	@Autowired
	private SalesOrderTaskStatusDao soTaskStatusRepo;

	@Autowired
	private SalesOrderTaskStatusService soTaskStatusService;

	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

	@Autowired
	private SalesOrderLevelStatusDao soLevelStatusRepo;

	@Autowired
	private SalesDocHeaderDao salesDocHeaderRepo;

	@Autowired
	private SalesDocItemDao salesDocItemRepo;

	@Autowired
	private CommentDaoImpl commentRepo;

	@Autowired
	private OdataService odataServices;

	@Autowired
	private ApprovalworkflowTrigger approvalWorkflow;

	@Autowired
	private DlvBlockReleaseMapService dlvBlockReleaseMapService;

	private static boolean flagToCheckCompletedWorkflowTaskId;

	// private static boolean flagToCallTriggerIme;

	// private static boolean flagToCheckIsTheLastLevel;

	@Override
	public ResponseEntity mainMethod(OnSubmitTaskDto submitTaskDto) {
		try {
			flagToCheckCompletedWorkflowTaskId = false;
			// flagToCheckIsTheLastLevel = false;
			// flagToCallTriggerIme = false;

			// Creating the list of item num from effected items
			List<String> effecteditemNumberList = new ArrayList<>();

			if (submitTaskDto != null && !HelperClass.checkString(submitTaskDto.getTaskId())
					&& !HelperClass.checkString(submitTaskDto.getLevelNum()) && !HelperClass.checkString(submitTaskDto.getDecisionSetId())
					&& !HelperClass.checkString(submitTaskDto.getWorkflowId())) {

				if (submitTaskDto.getListOfChangedItemData() != null
						&& !submitTaskDto.getListOfChangedItemData().isEmpty()) {

					try {

						// Update Comments and updated by
						// This method needs to call first before checking the
						// items last level and populate the effected item list
						updateCommentsAndEffectiveItemInItems(submitTaskDto, effecteditemNumberList);
					} catch (ExecutionFault e) {
						return new ResponseEntity("Failed At saving comments", HttpStatus.BAD_REQUEST,
								Constants.EXCEPTION_FAILED + e, ResponseStatus.FAILED);
					}

					// updating effective item list
					if (!effecteditemNumberList.isEmpty()) {

						// List of tasks from taskId always one
						List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList = soTaskStatusRepo
								.getAllTasksFromSapTaskId(submitTaskDto.getWorkflowId());

						// Checking the existence of task id in our db
						if (!soTaskStatusDtoForTaskIdList.isEmpty()) {

							// Method 1 starts
							updatingItemStatus(submitTaskDto, soTaskStatusDtoForTaskIdList, effecteditemNumberList);

							// Method 2 now
							updatingTaskStatus(soTaskStatusDtoForTaskIdList, submitTaskDto.getSalesOrderNum(),
									submitTaskDto.getTaskId(), submitTaskDto.getLoggedInUserName());

							// Method 3 now
							SalesOrderLevelStatusDto soLevelStatusDto = updatingLevelStatus(
									submitTaskDto.getDecisionSetId(), submitTaskDto.getLevelNum());

							if (soLevelStatusDto != null) {

								if (flagToCheckCompletedWorkflowTaskId) {
									return new ResponseEntity("", HttpStatus.OK,
											"Task Submitted, Please refresh the inbox.", ResponseStatus.SUCCESS);
								} else {
									return new ResponseEntity(
											"Task not completed, need to take action on all the line items.",
											HttpStatus.OK, "Task Submitted, Please refresh the inbox.",
											ResponseStatus.SUCCESS);
								}
							} else {
								return new ResponseEntity(submitTaskDto, HttpStatus.NO_CONTENT,
										"Sales Order Task Status is not available for Decision Set Id : "
												+ submitTaskDto.getDecisionSetId() + " and level : "
												+ submitTaskDto.getLevelNum(),
										ResponseStatus.FAILED);
							}
						} else {
							return new ResponseEntity(submitTaskDto, HttpStatus.OK,
									"No Task ID is associated to any sales order items", ResponseStatus.FAILED);
						}
					} else {
						return new ResponseEntity(submitTaskDto, HttpStatus.OK,
								"Effective Item list is empty, due to no checked item send from UI",
								ResponseStatus.FAILED);
					}

				} else {
					return new ResponseEntity(submitTaskDto, HttpStatus.BAD_REQUEST,
							"List of effected items are not available", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, INVALID_INPUT, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			logger.error(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity checkStatusTriggerIME(OnSubmitTaskDto submitTaskDto) {

		// 1.effected itemIdList
		List<String> effecteditemList = new ArrayList<>();

		// 2.list of taskStatusDtos
		List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList = null;

		// 3.Current level status dto
		SalesOrderLevelStatusDto soLevelStatusDto = null;

		try {
			// 1. inserting data into item id list
			for (ListOfChangedItemData item : submitTaskDto.getListOfChangedItemData()) {
				if (!HelperClass.checkString(item.getAcceptOrReject())) {
					effecteditemList.add(item.getSalesItemOrderNo());
				}
			}

			System.err.println(effecteditemList.toString());

			// 2. fetch task status dto list from task status table
			soTaskStatusDtoForTaskIdList = soTaskStatusRepo
					.getAllTasksFromDecisionSetAndLevel(submitTaskDto.getDecisionSetId(), submitTaskDto.getLevelNum());

			// 3. fetch current level status dto from level status table
			soLevelStatusDto = soLevelStatusRepo.getSalesOrderLevelStatusByDecisionSetAndLevelSession(
					submitTaskDto.getDecisionSetId(), submitTaskDto.getLevelNum());

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity("", HttpStatus.BAD_REQUEST,
				Constants.DATA_NOT_FOUND + ", Failed at Trigger ime for fetching level status dto not found",
					ResponseStatus.FAILED);
		}
		if (soLevelStatusDto != null && soTaskStatusDtoForTaskIdList != null && !effecteditemList.isEmpty()) {
			try {
				return checkItemEligibity(effecteditemList, soTaskStatusDtoForTaskIdList, soLevelStatusDto,
						submitTaskDto.getDecisionSetId(), submitTaskDto.getEccUpdateRequired(), submitTaskDto);

			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
						ResponseStatus.FAILED);
			}
		} else {

			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Input is invalid", ResponseStatus.FAILED);

		}

	}

	private ResponseEntity checkItemEligibity(List<String> effecteditemNumberList,
			List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList, SalesOrderLevelStatusDto soLevelStatusDto,
			String decisionSetId, boolean eccUpdateFlag, OnSubmitTaskDto submitTaskDto) {

		// creating Map structure with TaskSerId and item id as key
		// 1.prepaing the input list
		List<TaskItemDto> taskItemList = new ArrayList<>();

		String nextLevel = "L" + (Integer.parseInt(String.valueOf(soLevelStatusDto.getLevel().charAt(1))) + 1);
		SalesOrderLevelStatusDto nextlevelStatusDto = null;

		try {
			// checking if there exists a next level, if yes then set the dto
			// value to nextLevelStatusDto
			nextlevelStatusDto = soLevelStatusRepo
					.getSalesOrderLevelStatusByDecisionSetAndLevelSession(submitTaskDto.getDecisionSetId(), nextLevel);
		} catch (NoResultException e) {
			logger.error(
					"TaskSubmitUIServiceImpl.checkItemEligibityForNextLevel - levelStatusDtoList :" + e.getMessage());
		}

		Map<String, SalesOrderTaskStatusDto> mapOfTasksInAParticularDecisionSetAndLevel = soTaskStatusDtoForTaskIdList
				.stream().collect(Collectors.toMap(SalesOrderTaskStatusDto::getTaskId, task -> task,
						(oldValue, newValue) -> newValue, LinkedHashMap::new));

		Map<String, ListOfChangedItemData> mapOfChangedItemDataFromUiForAandR = submitTaskDto.getListOfChangedItemData()
				.stream().collect(Collectors.toMap(ListOfChangedItemData::getAcceptOrReject, item -> item,
						(oldValue, newValue) -> newValue, LinkedHashMap::new));

		// In OR Condition, when there exists only one task for the respective
		// level - proceed with below steps

		// 1. check if the task dto size is 1 and if the task id is same as task
		// id in table
		if (mapOfTasksInAParticularDecisionSetAndLevel.containsKey(submitTaskDto.getWorkflowId()) && eccUpdateFlag) {
			boolean flagToCheckIfLastLevel = false;
			// 2. if there exists a next level, proceed with update (only
			// reject
			// case)
			if (nextlevelStatusDto != null) {

				boolean flagToUpdateInEcc = false;

				if (mapOfChangedItemDataFromUiForAandR.containsKey("R")) {
					flagToUpdateInEcc = true;
				}

				// Reject only
				if (flagToUpdateInEcc) {
					return odataServices.updateSalesOrderInEccUsingOdata(submitTaskDto, flagToCheckIfLastLevel);
				} else {
					return new ResponseEntity(submitTaskDto, HttpStatus.OK, "Didn't update in ECC",
							ResponseStatus.SUCCESS);
				}
			}
			// 3. if there exists NO next level, approve is also considered,
			// hence set the flag to true - check
			// updateSalesOrderInEccUsingOdata for more details on approve
			// case
			else if (nextlevelStatusDto == null) {

				// TRUE - it is last level (APPROVE added)
				flagToCheckIfLastLevel = true;

				// flag to check to update in ECC
				// default is true bcz its for OR case
				boolean flagToUpdateInEcc = true;

				// Reject + Approver for last level case
				// write here
				// Finding all the tasks for a particular item
				/* AND CASE */
				if (soTaskStatusDtoForTaskIdList.size() > 1) {

					// FALSE bcz its AND case and need to check cumulative
					// status for update in ECC
					flagToUpdateInEcc = false;

					// Calculating cumulative status now here
					ResponseEntity responseForItemToCalulateCumulativeStatus = soTaskStatusService
							.getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(decisionSetId,
									submitTaskDto.getLevelNum());

					if (responseForItemToCalulateCumulativeStatus.getStatus().equals(ResponseStatus.SUCCESS)) {
						@SuppressWarnings("unchecked")
						Map<String, Integer> mapToCalculate = (Map<String, Integer>) responseForItemToCalulateCumulativeStatus
								.getData();

						System.err.println("Cumulative status for each items : " + mapToCalculate);

						for (String salesOrderNumber : mapToCalculate.keySet()) {

							if (mapToCalculate.get(salesOrderNumber) == StatusConstants.ITEM_APPROVE) {
								flagToUpdateInEcc = true;
							}

							for (ListOfChangedItemData item : submitTaskDto.getListOfChangedItemData()) {
								if (item.getSalesItemOrderNo().equalsIgnoreCase(salesOrderNumber)
										&& "A".equalsIgnoreCase(item.getAcceptOrReject())
										&& mapToCalculate.get(salesOrderNumber) != StatusConstants.ITEM_APPROVE) {
									item.setAcceptOrReject("B");
								}
							}

						}

						if (flagToUpdateInEcc == false) {

							if (mapOfChangedItemDataFromUiForAandR.containsKey("R")) {
								flagToUpdateInEcc = true;
							}
						}

						System.err.println("On submit dto for cumulatively approve and reject case : " + submitTaskDto);

					} else {
						return responseForItemToCalulateCumulativeStatus;
					}
				}

				if (flagToUpdateInEcc) {

					// true means this is the last level and update in ECC
					return odataServices.updateSalesOrderInEccUsingOdata(submitTaskDto, flagToCheckIfLastLevel);
				} else {
					return new ResponseEntity(submitTaskDto, HttpStatus.OK, "Didn't update in ECC and its a last level",
							ResponseStatus.SUCCESS);
				}
			}
		}

		for (SalesOrderTaskStatusDto taskStatusDto : soTaskStatusDtoForTaskIdList) {
			for (String itemId : effecteditemNumberList) {
				// if update to ECC is called, skip the current task id, reason
				// explained in the following method call
				if (eccUpdateFlag) {
					if (!taskStatusDto.getTaskId().equals(submitTaskDto.getTaskId())) {
						TaskItemDto taskItemDto = new TaskItemDto();
						taskItemDto.setTaskSerId(taskStatusDto.getTaskStatusSerialId());
						taskItemDto.setItemId(itemId);
						taskItemList.add(taskItemDto);
					}
				}
				// if ime trigger, no check required as above
				else {
					TaskItemDto taskItemDto = new TaskItemDto();
					taskItemDto.setTaskSerId(taskStatusDto.getTaskStatusSerialId());
					taskItemDto.setItemId(itemId);
					taskItemList.add(taskItemDto);
				}
			}
		}

		System.err.println("taskItemList : " + taskItemList.toString());

		// 2.passing input list and fetching the results -
		// storing the key value pairs in a map
		Map<TaskItemDto, SalesOrderItemStatusDto> resultFromItemStatusTableMap = soItemStatusRepo
				.batchFetchByTaskSerIdItemId(taskItemList);

		// WRITE CODE FOR AND CONDITION

		// TaskItemDto taskItemDto = new TaskItemDto();
		//
		// if(eccUpdateFlag) {
		// taskItemDto.setTaskSerId(soTaskStatusDtoForTaskIdList.get(0).getTaskStatusSerialId());
		// for (String itemId : effecteditemNumberList) {
		//
		// }
		// }

		/*
		 * map that will store the item and it's status list( in 'AND' condition
		 * - each value in the list refers to corresponding status of item in
		 * each task
		 */

		Map<String, List<Integer>> itemStatus = new HashMap<>();

		// iterating the map to find the list of status for each
		// item
		for (Map.Entry<TaskItemDto, SalesOrderItemStatusDto> entry : resultFromItemStatusTableMap.entrySet()) {
			for (String itemId : effecteditemNumberList) {
				if (entry.getKey().getItemId().equals(itemId)) {
					if (itemStatus.containsKey(itemId))
						itemStatus.get(itemId).add(entry.getValue().getItemStatus());
					else {
						List<Integer> statusList = new ArrayList<>();
						statusList.add(entry.getValue().getItemStatus());
						itemStatus.put(itemId, statusList);
					}
				}
			}
		}

		System.err.println("itemStatus : " + itemStatus.toString());

		// contains the cummulative status of each item
		// 4. iterating through the above map and finding the cumulative status
		// of each item
		Map<String, Integer> cummulativeItemStatus = new HashMap<>();

		for (Map.Entry<String, List<Integer>> entry : itemStatus.entrySet()) {
			if (!entry.getValue().contains(StatusConstants.ITEM_INDIRECT_REJECT)
					&& !entry.getValue().contains(StatusConstants.ITEM_REJECT)) {
				if (entry.getValue().contains(StatusConstants.BLOCKED))
					cummulativeItemStatus.put(entry.getKey(), StatusConstants.BLOCKED);
				else
					cummulativeItemStatus.put(entry.getKey(), StatusConstants.ITEM_APPROVE);

			} else
				cummulativeItemStatus.put(entry.getKey(), StatusConstants.ITEM_REJECT);
		}

		System.err.println("cummulativeItemStatus : " + cummulativeItemStatus.toString());

		// 5.check if the items should move to next level, i.e. if next level
		// needs to be triggered
		// if there are items that have been approved,
		if (!eccUpdateFlag) {
			if (cummulativeItemStatus != null && !cummulativeItemStatus.isEmpty()
					&& cummulativeItemStatus.containsValue(StatusConstants.ITEM_APPROVE)) {

				if (nextlevelStatusDto != null
						&& nextlevelStatusDto.getLevelStatus().equals(StatusConstants.LEVEL_NEW)) {
					// change - from ready to new trigger IME of second decision
					// set
					System.err.println("triggering IME");
					try {
						System.err.println("Level triggered at APPROVED one item on " + new Date().toGMTString());
						triggerImeService.triggerIme(decisionSetId);
					} catch (Exception e) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.TRIGGER_FAILED,
								ResponseStatus.FAILED);
					}
					return new ResponseEntity("", HttpStatus.OK, Constants.TRIGGER_SUCCESS, ResponseStatus.SUCCESS);

				}
			}

			if (soLevelStatusDto.getLevelStatus().equals(StatusConstants.LEVEL_IN_PROGRESS)) {
				try {
					// CHECK ITEM STATUS OF ALL TASKS FROM ALL UPCOMING
					// LEVELS AND IF NO ITEM IS IN 'B', TRIGGER IME
					List<SalesOrderItemStatusDto> list = soItemStatusRepo
							.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
									submitTaskDto.getDecisionSetId());
					System.err.println("Upcoming item status for blocked items : " + list);

					if (list.isEmpty()) {
						// no item found for upcoming levels as items
						// status of Blocked
						System.err.println(
								"Level triggered at current level is on progress on " + new Date().toGMTString());
						triggerImeService.triggerIme(submitTaskDto.getDecisionSetId());
					}
				} catch (Exception e) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.TRIGGER_FAILED,
							ResponseStatus.FAILED);
				}

			}

			// if all items have been rejected and also if it's the last level

			if (soLevelStatusDto.getLevelStatus().equals(StatusConstants.LEVEL_COMPLETE)) {
				// if(nextlevelStatusDto == null) {
				System.err.println("current level completed, triggering IME");

				if (nextlevelStatusDto != null) {
					// condition check for cases where the current level is
					// complete and IME needs to be triggered when next level is
					// in NEW status
					if (nextlevelStatusDto.getLevelStatus().equals(StatusConstants.LEVEL_NEW)) {

						try {
							System.err.println(
									"Level triggered at next level default status on " + new Date().toGMTString());
							triggerImeService.triggerIme(decisionSetId);
						} catch (Exception e) {
							return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.TRIGGER_FAILED,
									ResponseStatus.FAILED);
						}

					} else if (nextlevelStatusDto.getLevelStatus().equals(StatusConstants.LEVEL_IN_PROGRESS)) {
						// CHECK ITEM STATUS OF ALL TASKS FROM ALL UPCOMING
						// LEVELS AND IF NO ITEM IS IN 'B', TRIGGER IME
						try {
							// CHECK ITEM STATUS OF ALL TASKS FROM ALL UPCOMING
							// LEVELS AND IF NO ITEM IS IN 'B', TRIGGER IME
							List<SalesOrderItemStatusDto> list = soItemStatusRepo
									.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
											submitTaskDto.getDecisionSetId());
							System.err.println("Upcoming item status for blocked items : " + list);

							if (list.isEmpty()) {
								// no item found for upcoming levels as items
								// status of Blocked
								System.err.println("Level triggered at current level is on progress on "
										+ new Date().toGMTString());
								triggerImeService.triggerIme(submitTaskDto.getDecisionSetId());
							}

						} catch (Exception e) {
							return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.TRIGGER_FAILED,
									ResponseStatus.FAILED);
						}

					}
				} else if (nextlevelStatusDto == null) {
					try {
						System.err.println("Level triggered at last level on " + new Date().toGMTString());
						triggerImeService.triggerIme(decisionSetId);
					} catch (Exception e) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.TRIGGER_FAILED,
								ResponseStatus.FAILED);
					}
				}
			}

		}
		return new ResponseEntity("", HttpStatus.OK, "Item/Items Submitted", ResponseStatus.SUCCESS);

	}

	@Override
	public ResponseEntity updateToECC(OnSubmitTaskDto submitTaskDto) {

		// 1.effected itemIdList
		List<String> effecteditemList = new ArrayList<>();

		// 2.list of taskStatusDtos
		List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList = null;

		// 3.Current level status dto
		SalesOrderLevelStatusDto soLevelStatusDto = null;

		try {

			// 2. fetch task status dto list from task status table
			soTaskStatusDtoForTaskIdList = soTaskStatusRepo
					.getAllTasksFromDecisionSetAndLevel(submitTaskDto.getDecisionSetId(), submitTaskDto.getLevelNum());

			// find the current task items
			for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : soTaskStatusDtoForTaskIdList) {
				if (submitTaskDto.getWorkflowId().equals(salesOrderTaskStatusDto.getTaskId())) {
					List<SalesOrderItemStatusDto> itemsInAPerticularTask = soItemStatusRepo
							.getItemStatusDataUsingTaskSerialId(salesOrderTaskStatusDto.getTaskStatusSerialId());

					Map<String, SalesOrderItemStatusDto> mapOfItemsInAParticularTask = itemsInAPerticularTask.stream()
							.collect(Collectors.toMap(SalesOrderItemStatusDto::getSalesOrderItemNum, item -> item,
									(oldValue, newValue) -> newValue, LinkedHashMap::new));

					// Update item status here for current task

					submitTaskDto.getListOfChangedItemData().forEach(itemOnSubmit -> {

						// inserting data into item id list
						if (!HelperClass.checkString(itemOnSubmit.getAcceptOrReject())) {
							effecteditemList.add(itemOnSubmit.getSalesItemOrderNo());
						}
						if (mapOfItemsInAParticularTask.containsKey(itemOnSubmit.getSalesItemOrderNo())
								&& !HelperClass.checkString(itemOnSubmit.getAcceptOrReject())
								&& "A".equalsIgnoreCase(itemOnSubmit.getAcceptOrReject())) {

							SalesOrderItemStatusDto itemInDb = mapOfItemsInAParticularTask
									.get(itemOnSubmit.getSalesItemOrderNo());
							itemInDb.setItemStatus(StatusConstants.ITEM_APPROVE);
							itemInDb.setVisiblity(StatusConstants.VISIBLITY_INACTIVE);
							try {
								soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(itemInDb);
							} catch (ExecutionFault e) {
								return;
							}
						}

					});

				}
			}

			System.err.println(effecteditemList.toString());

			// 3. fetch current level status dto from level status table
			soLevelStatusDto = soLevelStatusRepo.getSalesOrderLevelStatusByDecisionSetAndLevelSession(
					submitTaskDto.getDecisionSetId(), submitTaskDto.getLevelNum());

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.DATA_NOT_FOUND, ResponseStatus.FAILED);
		}

		if (soLevelStatusDto != null && soTaskStatusDtoForTaskIdList != null && !effecteditemList.isEmpty()) {
			try {
				return checkItemEligibity(effecteditemList, soTaskStatusDtoForTaskIdList, soLevelStatusDto,
						submitTaskDto.getDecisionSetId(), submitTaskDto.getEccUpdateRequired(), submitTaskDto);

			} catch (Exception e) {
				logger.error(e.getMessage());
				return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
						ResponseStatus.FAILED);
			}
		} else {

			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Input is invalid", ResponseStatus.FAILED);

		}

	}

	private void updateCommentsAndEffectiveItemInItems(OnSubmitTaskDto submitTaskDto,
			List<String> effecteditemNumberList) throws ExecutionFault {

		for (ListOfChangedItemData item : submitTaskDto.getListOfChangedItemData()) {

			if (!HelperClass.checkString(item.getAcceptOrReject())) {
				effecteditemNumberList.add(item.getSalesItemOrderNo());

			/*	if (!HelperClass.checkString(item.getComments())) {

					// Setting Comment List from comment table
					List<CommentDto> commentDtoList = new ArrayList<>();
					CommentDto commentDto = new CommentDto();
					commentDto.setComments(item.getComments());
					commentDto.setUpdatedBy(submitTaskDto.getLoggedInUserName());
					commentDtoList.add(commentDto);
					for(List<CommentDto>commentDtoList :commentDto){
					commentRepo.saveOrUpdateComment(commentDto
							submitTaskDto.getRequestId() + "," + submitTaskDto.getDecisionSetId() + ","
									+ submitTaskDto.getLevelNum() + "," + item.getSalesItemOrderNo());
					}

				}*/
			}
		}
	}

	private SalesOrderLevelStatusDto updatingLevelStatus(String decisionSetId, String levelId) throws ExecutionFault {

		// Fetch tasks from decision set id and level
		List<SalesOrderTaskStatusDto> soTaskStatusDtoList = soTaskStatusRepo
				.getAllTasksFromDecisionSetAndLevel(decisionSetId, levelId);

		if (!soTaskStatusDtoList.isEmpty()) {
			// Getting the level status serial id for first task
			String levelStatusSerialId = soTaskStatusDtoList.get(0).getLevelStatusSerialId();

			// Checking task status for each entity
			int countFlag = 0;
			for (SalesOrderTaskStatusDto taskDto : soTaskStatusDtoList) {
				// Checking all task for same level status serial id
				if (taskDto.getTaskStatus() == TASK_COMPLETE) {
					countFlag++;
				}
			}

			// Get the level entity from serial id of level
			SalesOrderLevelStatusDto soLevelStatusDto = soLevelStatusRepo
					.getSalesOrderLevelStatusById(levelStatusSerialId);

			// Checking each task status flag if all tasks have
			// completed then Level is completed
			if (countFlag == soTaskStatusDtoList.size()) {
				// Setting status of level here
				soLevelStatusDto.setLevelStatus(LEVEL_COMPLETE);
			} else {
				// Setting status of level here
				soLevelStatusDto.setLevelStatus(LEVEL_IN_PROGRESS);
			}

			// Updating the DB for level status according to task
			// status
			soLevelStatusRepo.saveOrUpdateSalesOrderLevelStatus(soLevelStatusDto);
			return soLevelStatusDto;

		} else {
			return null;
		}
	}

	private ResponseEntity updatingTaskStatus(List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList,
			String salesOrderNum, String sapTaskId, String loggedInUserName) throws ExecutionFault {
		if (soTaskStatusDtoForTaskIdList != null && !soTaskStatusDtoForTaskIdList.isEmpty()) {
			SalesOrderTaskStatusDto salesOrderTaskDetail = soTaskStatusDtoForTaskIdList.get(0);
			if (salesOrderTaskDetail != null) {
				String taskStatusSerialId = salesOrderTaskDetail.getTaskStatusSerialId();
				if (taskStatusSerialId != null) {

					// get data using foreign key from itemStatus table
					boolean taskStatus = false;

					// List of item status from task serial id
					List<SalesOrderItemStatusDto> itemList = soItemStatusRepo
							.getItemStatusDataUsingTaskSerialId(taskStatusSerialId);

					for (SalesOrderItemStatusDto salesOrderItemStatusDto : itemList) {
						// if the item is Blocked and visibility is Active or
						// Inactive then the complete task is always in progress
						Integer sum = salesOrderItemStatusDto.getItemStatus() + salesOrderItemStatusDto.getVisiblity();
						if (sum == 22 || sum == 23) {
							taskStatus = true;
							// the item will be always be in progress so
							// task will always be in progress
							break;
						}
					}

					// if all task are complete
					if (!taskStatus) {
						salesOrderTaskDetail.setTaskStatus(StatusConstants.TASK_COMPLETE);
						salesOrderTaskDetail.setCompletedBy(loggedInUserName);

						// UPDATING WORKFLOW TASK STATUS TO COMPLETED
						String response = null;
						try {
							response = updatingWorkflowTaskStatus(salesOrderNum, sapTaskId);
						} catch (IOException | NamingException e) {
							return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
									ResponseStatus.FAILED);
						} catch (Exception e) {
							return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
									ResponseStatus.FAILED);
						}
						if (response.equals(Constants.TASK_COMPLETED)) {
							flagToCheckCompletedWorkflowTaskId = true;

						}
					} else {
						salesOrderTaskDetail.setTaskStatus(StatusConstants.TASK_IN_PROGRESS);
					}
					soTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskDetail);

					return new ResponseEntity("", HttpStatus.OK, "Returning decisionsetId and levelId",
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "Task Serial Id is empty",
							ResponseStatus.FAILED);
				}

			} else {
				return new ResponseEntity(salesOrderTaskDetail, HttpStatus.NO_CONTENT,
						"No tasks available for a task id", ResponseStatus.FAILED);
			}
		} else {
			return new ResponseEntity(soTaskStatusDtoForTaskIdList, HttpStatus.BAD_REQUEST, "List of tasks is empty",
					ResponseStatus.FAILED);
		}
	}

	private String updatingWorkflowTaskStatus(String salesOrderNum, String sapTaskId)
			throws IOException, NamingException, URISyntaxException {

		Map<String, Object> map = DestinationReaderUtil.getDestination(Constants.WORKFLOW_CLOSE_TASK_DESTINATION);

		String payload = "{\"context\": {},\"status\":\"COMPLETED\",\"subject\": \"" + salesOrderNum
				+ "\",\"priority\": \"MEDIUM\"}";

		return new DestinationClient().invoke((String) map.get("URL"), payload, (String) map.get("User"),
				(String) map.get("Password"));

	}

	private ResponseEntity updatingTaskStatusForUpcomingLevelTasks(
			List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList, String salesOrderNum, String sapTaskId)
			throws ExecutionFault {
		if (soTaskStatusDtoForTaskIdList != null && !soTaskStatusDtoForTaskIdList.isEmpty()) {
			SalesOrderTaskStatusDto salesOrderTaskDetail = soTaskStatusDtoForTaskIdList.get(0);
			if (salesOrderTaskDetail != null) {
				String taskStatusSerialId = salesOrderTaskDetail.getTaskStatusSerialId();
				if (taskStatusSerialId != null) {

					// get data using foreign key from itemStatus table
					boolean taskStatus = false;

					// List of item status from task serial id
					List<SalesOrderItemStatusDto> itemList = soItemStatusRepo
							.getItemStatusDataUsingTaskSerialId(taskStatusSerialId);

					for (SalesOrderItemStatusDto salesOrderItemStatusDto : itemList) {
						// if the item is Blocked and visibility is Active or
						// Inactive then the complete task is always in progress
						Integer sum = salesOrderItemStatusDto.getItemStatus() + salesOrderItemStatusDto.getVisiblity();
						if (sum == 22 || sum == 23) {
							taskStatus = true;
							// the item will be always be in progress so
							// task will always be in progress
							break;
						}
					}

					org.springframework.http.ResponseEntity responseFromWorkflowApi = null;

					// if all task are complete
					if (!taskStatus) {
						salesOrderTaskDetail.setTaskStatus(StatusConstants.TASK_COMPLETE);

						// UPDATING WORKFLOW TASK STATUS TO COMPLETED

						//responseFromWorkflowApi = new HelperClass().completeTaskInWorkflowUsingOauthClient(salesOrderNum, sapTaskId);
					} else {
						salesOrderTaskDetail.setTaskStatus(StatusConstants.TASK_IN_PROGRESS);
					}
					soTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskDetail);

					return new ResponseEntity(responseFromWorkflowApi, HttpStatus.OK,
							"Returning decisionsetId and levelId", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "Task Serial Id is empty",
							ResponseStatus.FAILED);
				}

			} else {
				return new ResponseEntity(salesOrderTaskDetail, HttpStatus.NO_CONTENT,
						"No tasks available for a task id", ResponseStatus.FAILED);
			}
		} else {
			return new ResponseEntity(soTaskStatusDtoForTaskIdList, HttpStatus.BAD_REQUEST, "List of tasks is empty",
					ResponseStatus.FAILED);
		}
	}

	public ResponseEntity updateTaskAndLevelStatus(String decisionSet, String salesOrderNum) {
		try {
			List<SalesOrderLevelStatusDto> upcomingLevelStatusList = null;
			ResponseEntity taskStatusRes = null;

			// Map of workflow id (DB) and sap task id
			@SuppressWarnings("unchecked")
			Map<String, String> mapOfTaskId = (Map<String, String>) approvalWorkflow
					.workflowTaskInstanceIdByDecisionSetAndLevel(decisionSet).getData();

			// Upcoming levels with in progess status
			upcomingLevelStatusList = soLevelStatusRepo
					.getSalesOrderLevelStatusByDecisionSetWithLevelInProgress(decisionSet);

			if (upcomingLevelStatusList != null && !upcomingLevelStatusList.isEmpty()) {
				for (SalesOrderLevelStatusDto salesOrderLevelStatusDto : upcomingLevelStatusList) {
					if (salesOrderLevelStatusDto.getLevelStatus() != StatusConstants.LEVEL_NEW) {

						// Setting the dto for all tasks for level
						salesOrderLevelStatusDto
								.setTaskStatusList(soTaskStatusRepo.getAllTasksListFromLevelStatusSerialId(
										salesOrderLevelStatusDto.getLevelStatusSerialId()));
						System.err.println("List of tasks : " + salesOrderLevelStatusDto.getTaskStatusList());

						for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : salesOrderLevelStatusDto
								.getTaskStatusList()) {

							if (salesOrderTaskStatusDto.getTaskStatus() !=StatusConstants.TASK_NEW) {

								/*
								 * PARAM list of taskStatus, sales order num and
								 * sap task ID to close the task
								 */
								// Updating Task Status now
								taskStatusRes = updatingTaskStatusForUpcomingLevelTasks(
										Arrays.asList(salesOrderTaskStatusDto), salesOrderNum,
										mapOfTaskId.get(salesOrderTaskStatusDto.getTaskId()));

							}

						}
						if (taskStatusRes.getStatus().toString().equalsIgnoreCase(ResponseStatus.SUCCESS.toString())) {

							// Updating Level Status now
							SalesOrderLevelStatusDto levelStatusDto = updatingLevelStatus(
									salesOrderLevelStatusDto.getDecisionSetId(), salesOrderLevelStatusDto.getLevel());
							if (levelStatusDto == null) {
								return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Level status updating failed",
										ResponseStatus.FAILED);
							}
						} else {
							return taskStatusRes;
						}
					}
				}
				return new ResponseEntity(upcomingLevelStatusList, HttpStatus.OK, "Data is successfully updated",
						ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.OK, "No in progess upcoming levels", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			logger.error(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	private ResponseEntity updatingItemStatus(OnSubmitTaskDto submitTaskDto,
			List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList, List<String> effecteditemNumberList)
			throws ExecutionFault {

		// List to store effected item statuses
		// List<SalesOrderItemStatusDto> soItemStatusEffectedDtoList = new
		// ArrayList<>();

		Map<String, SalesOrderItemStatusDto> mapOfItemsInAParticularTask = new HashMap<>();

		fetchingItemStatusDataFromDb(soTaskStatusDtoForTaskIdList, effecteditemNumberList, mapOfItemsInAParticularTask);

		// Finding effected item list
		if (!mapOfItemsInAParticularTask.isEmpty()) {

			// Calculating cumulative status now here for
			// current level
			ResponseEntity responseForItemToCalulateCumulativeStatus = soTaskStatusService
					.getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(submitTaskDto.getDecisionSetId(),
							submitTaskDto.getLevelNum());

			// Checking effected and actioned item with all the items in a
			// changed item data list
			for (ListOfChangedItemData effectedChangedItemData : submitTaskDto.getListOfChangedItemData()) {
				if (mapOfItemsInAParticularTask.containsKey(effectedChangedItemData.getSalesItemOrderNo())) {

					// If any action occur in item then updating in item
					// status
					if (!HelperClass.checkString(effectedChangedItemData.getAcceptOrReject())) {
						if (effectedChangedItemData.getAcceptOrReject().equals("A")) {

							if (responseForItemToCalulateCumulativeStatus.getStatus().equals(ResponseStatus.SUCCESS)) {
								@SuppressWarnings("unchecked")
								Map<String, Integer> mapToCalculateForCurrentLevel = (Map<String, Integer>) responseForItemToCalulateCumulativeStatus
										.getData();

								System.err.println("Cumulative status to update the next level for each items : "
										+ mapToCalculateForCurrentLevel);

								// Updating upcoming level visibility for the
								// individual item
								updatingNextLevelItemVisiblity(submitTaskDto, effectedChangedItemData,
										mapToCalculateForCurrentLevel);
							} else {
								return responseForItemToCalulateCumulativeStatus;
							}

							// If item rejected then IIR is the status for
							// the other tasks
						} else if (effectedChangedItemData.getAcceptOrReject().equals("R")) {

							SalesOrderItemStatusDto salesOrderItemStatusDto = mapOfItemsInAParticularTask
									.get(effectedChangedItemData.getSalesItemOrderNo());

							salesOrderItemStatusDto.setItemStatus(StatusConstants.ITEM_REJECT);
							salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_INACTIVE);

							// Save the updated Item Status
							soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);

							// updating version here
							// ResponseEntity response =
							// updateSalesOrderVersionOnRejectCase(submitTaskDto,
							// salesOrderItemStatusDto,
							// effectedChangedItemData);
							// if (response.getStatus() ==
							// ResponseStatus.FAILED) {
							// return response;
							// }

							// Finding all the items for a particular item
							// decision set and not the current workflow id
							// also item statuses which are Blocked
							// Fetching only 9 status item status ones
							updateIIRInCaseOfReject(submitTaskDto, salesOrderItemStatusDto);

						}
					}
				}

			}

			return new ResponseEntity("", HttpStatus.OK, "Successfully updated the item status",
					ResponseStatus.SUCCESS);

		} else {
			return new ResponseEntity("", HttpStatus.NO_CONTENT,
					Constants.DATA_NOT_FOUND + ", for List to store affected item statuses", ResponseStatus.FAILED);
		}
	}

	private void fetchingItemStatusDataFromDb(List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList,
			List<String> effecteditemNumberList, Map<String, SalesOrderItemStatusDto> mapOfItemsInAParticularTask) {
		// Find Items status dto for only effected items (either A/R)
		for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : soTaskStatusDtoForTaskIdList) {
			for (String effectedChangedItemData : effecteditemNumberList) {

				SalesOrderItemStatusDto soItemStatusDto = soItemStatusRepo.getItemStatusDataUsingTaskSerialIdAndItemNum(
						salesOrderTaskStatusDto.getTaskStatusSerialId(), effectedChangedItemData);

				mapOfItemsInAParticularTask.put(effectedChangedItemData, soItemStatusDto);

			}
		}
	}

	private void updatingNextLevelItemVisiblity(OnSubmitTaskDto submitTaskDto,
			ListOfChangedItemData effectedChangedItemData, Map<String, Integer> mapToCalculateForCurrentLevel) {
		// Making next level task for particular
		// items visibility as ACTIVE
		Integer nextLevelNum = Integer.parseInt(submitTaskDto.getLevelNum().substring(1)) + 1;
		String nextLevel = "L" + nextLevelNum;
		List<SalesOrderTaskStatusDto> taskWithItemListForNextLevel = soTaskStatusRepo
				.getAllTasksFromDecisionSetAndLevel(submitTaskDto.getDecisionSetId(), nextLevel);

		if (taskWithItemListForNextLevel != null && !taskWithItemListForNextLevel.isEmpty()) {

			for (SalesOrderTaskStatusDto taskDto : taskWithItemListForNextLevel) {
				List<SalesOrderItemStatusDto> itemDtoList = soItemStatusRepo
						.getItemStatusDataUsingTaskSerialId(taskDto.getTaskStatusSerialId());
				taskDto.setItemStatusList(itemDtoList);

			}
		}

		if (taskWithItemListForNextLevel != null && !taskWithItemListForNextLevel.isEmpty()) {
			for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : taskWithItemListForNextLevel) {
				for (SalesOrderItemStatusDto itemStatusDto : salesOrderTaskStatusDto.getItemStatusList()) {
					mapToCalculateForCurrentLevel.forEach((k, v) -> {
						if (effectedChangedItemData.getSalesItemOrderNo().equals(itemStatusDto.getSalesOrderItemNum())
								&& effectedChangedItemData.getSalesItemOrderNo().equals(k)) {

							// Finding cumulative
							// Approve
							// then marking ACTIVE
							if (v == StatusConstants.ITEM_APPROVE) {

								itemStatusDto.setVisiblity(StatusConstants.VISIBLITY_ACTIVE);
								try {
									System.err.println(
											"Should update next level task items as ACTIVE data = " + itemStatusDto);

									soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(itemStatusDto);

								} catch (ExecutionFault e) {
									System.err.println(e);
								}
							}

						}

					});
				}
			}
		}
	}

	private void updateIIRInCaseOfReject(OnSubmitTaskDto submitTaskDto, SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault {
		List<SalesOrderItemStatusDto> listOfItemsFromDecisionSetAndItemNum = soItemStatusRepo
				.getItemsStatusFromDecisionSetAndItemNumForAllLevels(submitTaskDto.getDecisionSetId(),
						submitTaskDto.getWorkflowId(), salesOrderItemStatusDto.getSalesOrderItemNum());

		System.err.println("Other item data for different tasks : " + listOfItemsFromDecisionSetAndItemNum);

		// Making item status to IIR and
		// Visibility as Inactive
		if (!listOfItemsFromDecisionSetAndItemNum.isEmpty()) {
			for (SalesOrderItemStatusDto soItemStatusDto : listOfItemsFromDecisionSetAndItemNum) {
				soItemStatusDto.setItemStatus(StatusConstants.ITEM_INDIRECT_REJECT);
				soItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_INACTIVE_INDIRECT_REJECT);
				soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(soItemStatusDto);

			}

		}
	}

	private void updateSalesOrderVersionOnRejectCase(OnSubmitTaskDto submitTaskDto,
			SalesOrderItemStatusDto salesOrderItemStatusDto, ListOfChangedItemData effectedChangedItemData)
			throws ExecutionFault {
		// update reason of reject here
		SalesDocItemDto salesDocItemDto = salesDocItemRepo
				.getSalesDocItemById(salesOrderItemStatusDto.getSalesOrderItemNum(), submitTaskDto.getSalesOrderNum());

		// updating sales doc item now
		if (salesDocItemDto != null) {
			salesDocItemDto.setReasonForRejection(effectedChangedItemData.getReasonForRejection());
			salesDocItemRepo.saveOrUpdateSalesDocItem(salesDocItemDto);

			SalesOrderHistoryDto salesOrderHistoryDto = new SalesOrderHistoryDto();
			// updating version history here
			salesOrderHistoryDto.setBatchNum(effectedChangedItemData.getBatchNum());
			// salesOrderHistoryDto.setItemBillingBlock(item.getIte);
			salesOrderHistoryDto.setItemDlvBlock(effectedChangedItemData.getItemDeliveryBlock());
			salesOrderHistoryDto.setNetPrice(Double.parseDouble(effectedChangedItemData.getUnitPrice()));
			salesOrderHistoryDto.setNetWorth(effectedChangedItemData.getAmount());
			salesOrderHistoryDto.setOrderedQtySales(effectedChangedItemData.getSalesQty());
			// salesOrderHistoryDto.setPlant(item.get);
			salesOrderHistoryDto.setReasonOfRejection(effectedChangedItemData.getReasonForRejection());
			salesOrderHistoryDto.setSalesDocNum(submitTaskDto.getSalesOrderNum());
			salesOrderHistoryDto.setSalesItemNum(effectedChangedItemData.getSalesItemOrderNo());
			salesOrderHistoryDto.setSalesUnit(effectedChangedItemData.getSalesUnit());
			salesOrderHistoryDto.setStorageLoc(effectedChangedItemData.getStorageLoc());
			salesOrderHistoryDto.setUpdatedBy(submitTaskDto.getLoggedInUserName());
			salesOrderHistoryDto.setUpdatedOn(new Date());

			//return new SalesOrderHistoryServiceImpl().saveSalesOrderItem(salesOrderHistoryDto);

		} else {
			return;
		}
	}

	@Override
	public ResponseEntity validateSalesOrder(String salesOrderNum, String decisionSetId, String sapTaskId,
			String levelNum) {
		try {
			if (!HelperClass.checkString(salesOrderNum) && !HelperClass.checkString(decisionSetId) && !HelperClass.checkString(sapTaskId)
					&& !HelperClass.checkString(levelNum)) {
				SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderRepo
						.getSalesDocHeaderWithoutItemsById(salesOrderNum);
				if (salesDocHeaderDto != null) {
					List<SalesDocItemDto> salesDocItemDtoList = salesDocItemRepo
							.getSalesDocItemsByDecisionSetId(decisionSetId);

					DlvBlockReleaseMapDto dlvBlockReleaseMapDto = (DlvBlockReleaseMapDto) dlvBlockReleaseMapService
							.getDlvBlockReleaseMapBydlvBlockCodeWithSpecialClients(
									salesDocHeaderDto.getDeliveryBlockCode())
							.getData();
					if (dlvBlockReleaseMapDto != null) {
						if (dlvBlockReleaseMapDto.getHeaderLevel() == true) {
							salesDocHeaderDto
									.setSpecialClientListForHeader(dlvBlockReleaseMapDto.getClientMapDtoList());
						}
					}

					salesDocHeaderDto.setLevelNum(levelNum);
					salesDocHeaderDto.setDecisionSetId(decisionSetId);
					if (salesDocItemDtoList != null && !salesDocItemDtoList.isEmpty()) {
						salesDocHeaderDto.setSalesDocItemList(salesDocItemDtoList);

						List<SalesOrderItemStatusDto> salesOrderItemStatusList = soItemStatusRepo
								.getItemsBySapTaskId(sapTaskId);

						if (!salesOrderItemStatusList.isEmpty()) {

							for (SalesOrderItemStatusDto salesOrderItemStatusDto : salesOrderItemStatusList) {
								for (SalesDocItemDto salesDocItemDto : salesDocHeaderDto.getSalesDocItemList()) {

									if (salesOrderItemStatusDto.getSalesOrderItemNum()
											.equals(salesDocItemDto.getSalesItemOrderNo())) {
										salesDocItemDto.setNetPrice(String.format("%.2f",
												Double.parseDouble(salesDocItemDto.getNetPrice())));
										salesDocItemDto.setNetWorth(String.format("%.2f",
												Double.parseDouble(salesDocItemDto.getNetWorth())));
										salesDocItemDto.setVisiblity(salesOrderItemStatusDto.getVisiblity());
										salesDocItemDto.setTaskItemStatus(salesOrderItemStatusDto.getItemStatus());
										salesDocItemDto
												.setItemStagingStatus(StatusConstants.MAP_TO_PRINT_ITEM_STATUS
														.get(salesDocItemDto.getVisiblity()
																+ salesDocItemDto.getTaskItemStatus()));

										DlvBlockReleaseMapDto dlvBlockReleaseMapDtoForItem = (DlvBlockReleaseMapDto) dlvBlockReleaseMapService
												.getDlvBlockReleaseMapBydlvBlockCodeWithSpecialClients(
														salesDocItemDto.getItemDlvBlock())
												.getData();
										if (dlvBlockReleaseMapDtoForItem != null) {
											if (dlvBlockReleaseMapDtoForItem.getItemLevel() == true) {
												salesDocItemDto.setSpecialClientListForItem(
														dlvBlockReleaseMapDtoForItem.getClientMapDtoList());
											}
										}

									}
								}
							}

							return new ResponseEntity(salesDocHeaderDto, HttpStatus.OK,Constants.DATA_FOUND,
									ResponseStatus.SUCCESS);
						} else {
							return new ResponseEntity("", HttpStatus.NO_CONTENT, "Sap task id is not registered yet!!",
									ResponseStatus.FAILED);
						}

					} else {
						return new ResponseEntity("", HttpStatus.NO_CONTENT,
								Constants.DATA_NOT_FOUND + ", Invalid Decision Set id", ResponseStatus.FAILED);
					}

				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							Constants.DATA_NOT_FOUND + ", Invalid Sales Order num", ResponseStatus.FAILED);
				}
			} else {

				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						INVALID_INPUT
								+ ", Sales Order Number, Decision Set Id, Sap Task Id and Level Number are mandatory",
						ResponseStatus.FAILED);

			}
		} catch (Exception e) {
			logger.error(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity validateAllSalesOrders(List<SalesOrderValidatorDto> salesOrderValidatorList) {
		try {
			if (!salesOrderValidatorList.isEmpty()) {

				List<Object> salesDocHeaderDtoList = new ArrayList<>();
				Map<SalesOrderValidatorDto, String> salesOrderNumbers = new HashMap<>();

				for (SalesOrderValidatorDto salesOrderValidatorDto : salesOrderValidatorList) {

					ResponseEntity salesDocHeaderDto = validateSalesOrder(salesOrderValidatorDto.getSalesOrderNum(),
							salesOrderValidatorDto.getDecisionSetId(), salesOrderValidatorDto.getSapTaskId(),
							salesOrderValidatorDto.getLevelNum());

					if (salesDocHeaderDto.getData() != null && !salesDocHeaderDto.getData().equals("")) {
						salesDocHeaderDtoList.add(salesDocHeaderDto.getData());
					} else {
						salesOrderNumbers.put(salesOrderValidatorDto, salesDocHeaderDto.getMessage());
					}
				}

				if (!salesDocHeaderDtoList.isEmpty()) {
					return new ResponseEntity(salesDocHeaderDtoList, HttpStatus.OK,Constants.DATA_FOUND,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST,Constants.INVALID_INPUT,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						INVALID_INPUT
								+ ", Sales Order Number List, Decision Set Id List, Sap Task Id List and Level Number List are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			logger.error(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	// update the HDB checking the approval of the item
	@Override
	public ResponseEntity releaseHdbBlock(String salesOrderNumber) {

		String response = null;

		SalesDocItemDto salesDocItemDto = null;

		SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderRepo.getSalesDocHeaderWithoutItemsById(salesOrderNumber);
		// get all the item Status based on slaesOrderNum
		List<SalesDocItemDto> listOfItem = salesDocItemRepo.listOfItemsInSalesOrder(salesOrderNumber);
		int rejectItemCount = 0;
		int acceptedItemCount = 0;
		int sizeOfTotalItem = listOfItem.size();
		int statusResult = 0;
		String overalHeaderStatus = null;
		if (listOfItem != null && !listOfItem.isEmpty()) {
			for (int i = 0; i < listOfItem.size(); i++) {
				salesDocItemDto = new SalesDocItemDto();
				salesDocItemDto = listOfItem.get(i);
				System.err.println("reasonOfRejection = " + salesDocItemDto.getReasonForRejection());
				if (salesDocItemDto.getReasonForRejection().isEmpty()
						|| salesDocItemDto.getReasonForRejection() == null) {
					acceptedItemCount++;
				} else {
					rejectItemCount++;
				}
			}
			if (acceptedItemCount > 0) {
				statusResult = acceptedItemCount / sizeOfTotalItem;
			}
			if (rejectItemCount > 0) {
				statusResult = rejectItemCount / sizeOfTotalItem;
			}

			if (statusResult == 1) {
				if (acceptedItemCount == sizeOfTotalItem) {
					overalHeaderStatus = "A";
				}
				if (rejectItemCount == sizeOfTotalItem) {
					overalHeaderStatus = "R";
				}

			} else {
				overalHeaderStatus = "PA";
			}

		}

		// call ecc onsubmit method ....
		System.err.println("itemStatus " + overalHeaderStatus);

		OdataBatchOnSubmitPayload odataBatchOnSubmit = OdataBatchOnSubmitPayload(salesOrderNumber, overalHeaderStatus,
				listOfItem, salesDocHeaderDto);

		System.err.println("odataBatchPayload " + odataBatchOnSubmit);
		List<OdataBatchOnSubmitPayload> oDataPayloadList = new ArrayList<>();
		oDataPayloadList.add(odataBatchOnSubmit);
		try {
			response = ODataBatchUtil.BULK_INSERT_ON_SUBMIT_DATA(oDataPayloadList,
				StatusConstants.RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT,
					StatusConstants.RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT_TAG);
			System.err.println("HDB Response - " + response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR,Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity(response, HttpStatus.OK,Constants.DATA_FOUND, ResponseStatus.SUCCESS);

	}

	private OdataBatchOnSubmitPayload OdataBatchOnSubmitPayload(String salesOrderNum, String itemStatusApproval,
			List<SalesDocItemDto> listOfItem, SalesDocHeaderDto salesheader) {
		logger.error("> Start :: ODataBatchPayload method.");

		// batch Payload for the onsubmit for header
		// ODataBatchPayload batchItem = new ODataBatchPayload();
		OdataBatchOnSubmitPayload batchHeaderData = new OdataBatchOnSubmitPayload();
		batchHeaderData.setCreatedBy(salesheader.getCreatedBy());
		batchHeaderData.setDocNumber(salesOrderNum);
		if (itemStatusApproval.contains("A")) {
			if (salesheader.getCustomerPo().contains("/")) {
				batchHeaderData.setDlvBlock("H3");
			} else {
				batchHeaderData.setDlvBlock("R5");
			}
		} else if (itemStatusApproval.contains("PA")) {
			batchHeaderData.setDlvBlock("H3");
		} else {
			batchHeaderData.setDlvBlock("H8");
		}
		batchHeaderData.setPurchNo("SUBMIT");
		batchHeaderData.setApprove_Rej(itemStatusApproval);
		batchHeaderData.setHdrText("");
		List<OdataBatchOnsubmitItem> returnItemList = new ArrayList<>();
		OdataBatchOnsubmitItem item = new OdataBatchOnsubmitItem();
		item.setApprove_Rej("");
		item.setDocNumber(salesOrderNum);
		item.setItmNumber("000010");
		item.setMaterial("");
		item.setItemText("");
		item.setCreatedBy(salesheader.getCreatedBy());

		returnItemList.add(item);
		OrderToItems orderItem = new  OrderToItems(returnItemList);

		batchHeaderData.setOrderToItems(orderItem);

		return batchHeaderData;

	}

	

}

