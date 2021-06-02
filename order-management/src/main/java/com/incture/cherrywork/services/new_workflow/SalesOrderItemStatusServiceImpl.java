package com.incture.cherrywork.services.new_workflow;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dto.new_workflow.ListOfChangedItemData;
import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.new_workflow.dao.SalesOrderItemStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;

import static com.incture.cherrywork.WConstants.Constants.DATA_FOUND;
import static com.incture.cherrywork.WConstants.Constants.EMPTY_LIST;
import static com.incture.cherrywork.WConstants.Constants.EXCEPTION_FAILED;
import static com.incture.cherrywork.WConstants.Constants.INVALID_INPUT;




@Service
@Transactional
public class SalesOrderItemStatusServiceImpl implements SalesOrderItemStatusService {

	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

	@Autowired
	private SalesOrderTaskStatusDao soTaskStatusRepo;

	@Autowired
	private SalesOrderLevelStatusDao soLevelStatusRepo;

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public ResponseEntity saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto) {
		try {
			if (salesOrderItemStatusDto != null) {
				if (!HelperClass.checkString(salesOrderItemStatusDto.getTaskStatusSerialId())) {

					String msg = soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);

					if (msg == null) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST,
								INVALID_INPUT + ", Task status serial id field is : "
										+ salesOrderItemStatusDto.getTaskStatusSerialId() + " not registered Yet!!",
								ResponseStatus.FAILED);
					} else {
						return new ResponseEntity(salesOrderItemStatusDto, HttpStatus.CREATED, msg,
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST,
							INVALID_INPUT + ", Task status serial id field is mandatory!!", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, INVALID_INPUT, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrderItemStatuses() {
		try {
			List<SalesOrderItemStatusDto> list = soItemStatusRepo.listAllSalesOrderItemStatuses();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteSalesOrderItemStatusById(String salesOrderItemStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderItemStatusId)) {
				String msg = soItemStatusRepo.deleteSalesOrderItemStatusById(salesOrderItemStatusId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Item Status Id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesOrderItemStatusById(String salesOrderItemStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderItemStatusId)) {
				SalesOrderItemStatusDto salesOrderItemStatusDto = soItemStatusRepo
						.getSalesOrderItemStatusById(salesOrderItemStatusId);
				if (salesOrderItemStatusDto != null) {
					return new ResponseEntity(salesOrderItemStatusDto, HttpStatus.ACCEPTED,
							"Sales Order Item Status is found for Sales Order Item Status id : "
									+ salesOrderItemStatusId,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Item Status is not available for Sales Order Item Status id : "
									+ salesOrderItemStatusId,
							ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Item Status Id field is mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getItemStatusListByTaskSerIdItemId(List<TaskItemDto> taskItemList) {

		try {
			if (taskItemList != null) {

				Map<TaskItemDto, SalesOrderItemStatusDto> map = soItemStatusRepo
						.batchFetchByTaskSerIdItemId(taskItemList);

				if (map != null && !map.isEmpty()) {
					return new ResponseEntity(map, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, INVALID_INPUT, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity onSubmitCheckAndUpdateItemStatus(OnSubmitTaskDto submitTaskDto) {

		try {
			if (submitTaskDto != null && !HelperClass.checkString(submitTaskDto.getTaskId())
					&& !HelperClass.checkString(submitTaskDto.getLevelNum()) && !HelperClass.checkString(submitTaskDto.getDecisionSetId())) {

				if (!submitTaskDto.getListOfChangedItemData().isEmpty()) {

					String taskId = submitTaskDto.getTaskId();
					// String decisionSetId = submitTaskDto.getDecisionSetId();
					// String levelNum = submitTaskDto.getLevelNum();
					List<String> itemNum = new ArrayList<>();

					// Creating the list of item num from effected items
					for (ListOfChangedItemData item : submitTaskDto.getListOfChangedItemData()) {

						itemNum.add(item.getSalesItemOrderNo());
					}

					List<SalesOrderTaskStatusDto> soTaskStatusDtoForTaskIdList = soTaskStatusRepo
							.getAllTasksFromSapTaskId(taskId);
					List<SalesOrderItemStatusDto> soItemStatusEffectedDtoList = new ArrayList<>();
					for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : soTaskStatusDtoForTaskIdList) {

						for (ListOfChangedItemData effectedChangedItemData : submitTaskDto.getListOfChangedItemData()) {

							SalesOrderItemStatusDto soItemStatusDto = soItemStatusRepo
									.getItemStatusDataUsingTaskSerialIdAndItemNum(
											salesOrderTaskStatusDto.getTaskStatusSerialId(),
											effectedChangedItemData.getSalesItemOrderNo());

							soItemStatusEffectedDtoList.add(soItemStatusDto);

						}
					}

					for (SalesOrderItemStatusDto salesOrderItemStatusDto : soItemStatusEffectedDtoList) {

						for (ListOfChangedItemData effectedChangedItemData : submitTaskDto.getListOfChangedItemData()) {

							if (effectedChangedItemData.getSalesItemOrderNo()
									.equals(salesOrderItemStatusDto.getSalesOrderItemNum())) {
								if (effectedChangedItemData.getAcceptOrReject().equals("A")) {
									salesOrderItemStatusDto.setItemStatus(StatusConstants.ITEM_APPROVE);
									salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_INACTIVE);

								} else if (effectedChangedItemData.getAcceptOrReject().equals("R")) {
									salesOrderItemStatusDto.setItemStatus(StatusConstants.ITEM_REJECT);
									salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_INACTIVE);

									// Get inside other possiblities also

								}

							}

						}

						// Save the updated Status
						soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);

					}

					return new ResponseEntity(itemNum, HttpStatus.OK, "Item statuses is successfully updated",
							ResponseStatus.SUCCESS);

				} else {
					return new ResponseEntity(submitTaskDto, HttpStatus.BAD_REQUEST,
							"List of effected items are not found", ResponseStatus.FAILED);
				}

			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, INVALID_INPUT, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity getItemListBySapTaskId(String sapTaskId) {
		try {
			/*
			 * if (!HelperClass.checkString(sapTaskId)) { // List<SalesOrderItemStatusDto>
			 * list = soItemStatusRepo.getVisiblityOfItems(sapTaskId); if (list
			 * != null && !list.isEmpty()) { return new ResponseEntity(list,
			 * HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS); } else {
			 * return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST,
			 * ResponseStatus.FAILED); } } else { return new ResponseEntity("",
			 * HttpStatus.BAD_REQUEST, "sap task id not found",
			 * ResponseStatus.FAILED); }
			 */ } catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
		return null;
	}

	@Override
	public ResponseEntity getItemStatusFromDecisionSetAndLevel(String decisionSetId, String levelNum) {
		try {
			List<SalesOrderItemStatusDto> list = soItemStatusRepo.getItemStatusFromDecisionSetAndLevel(decisionSetId,
					levelNum);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String workflowTaskId, String itemNum) {
		try {
			List<SalesOrderItemStatusDto> list = soItemStatusRepo
					.getItemsStatusFromDecisionSetAndItemNumForAllLevels(decisionSetId, workflowTaskId, itemNum);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(String decisionSetId) {
		try {
			// CHECK ITEM STATUS OF ALL TASKS FROM ALL UPCOMING
			// LEVELS AND IF NO ITEM IS IN 'B', TRIGGER IME
			List<SalesOrderItemStatusDto> list = soItemStatusRepo
					.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(decisionSetId);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, Constants.DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, Constants.EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault {
		try {

			SalesOrderItemStatusDo salesOrderItemStatusDo = new SalesOrderItemStatusDo();

			SalesOrderTaskStatusDo salesOrderTaskStatusDo = soTaskStatusRepo
					.getSalesOrderTaskStatusDoById(salesOrderItemStatusDto.getTaskStatusSerialId());

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = soLevelStatusRepo.getSalesOrderLevelStatusDoById(
					salesOrderTaskStatusDo.getSalesOrderLevelStatus().getLevelStatusSerialId());

			salesOrderTaskStatusDo.setSalesOrderLevelStatus(salesOrderLevelStatusDo);
			salesOrderItemStatusDo.setSalesOrderTaskStatus(salesOrderTaskStatusDo);
			salesOrderItemStatusDo.setSalesOrderItemNum(salesOrderItemStatusDto.getSalesOrderItemNum());
			salesOrderItemStatusDo.setItemStatus(salesOrderItemStatusDto.getItemStatus());
			salesOrderItemStatusDo.setVisiblity(salesOrderItemStatusDto.getVisiblity());

			Session session = sessionfactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(salesOrderItemStatusDo);
			tx1.commit();
			session.clear();
			session.close();
			return salesOrderItemStatusDo.getItemStatusSerialId();

		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId, String itemNum) {
		try {
			List<SalesOrderItemStatusDto> list = soItemStatusRepo
					.getItemsStatusFromDecisionSetAndItemNumForAllLevels(decisionSetId, itemNum);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

}
