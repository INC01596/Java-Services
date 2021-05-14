package com.incture.cherrywork.services.new_workflow;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.SalesOrderItemStatusDao;
import com.incture.cherrywork.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;


@Service
@Transactional
public class SalesOrderTaskStatusServiceImpl implements SalesOrderTaskStatusService {

	@Autowired
	private SalesOrderTaskStatusDao soTaskStatusRepo;

	@Autowired
	private SalesOrderLevelStatusDao salesorderlevelstatusdao;

	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

	@Override
	public ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto) {
		try {
			if (salesOrderTaskStatusDto != null) {
				if (!HelperClass.checkString(salesOrderTaskStatusDto.getLevelStatusSerialId())) {
					String msg = soTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);

					if (msg == null) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST,
								"INVALID_INPUT + , Level status serial id : "
										+ salesOrderTaskStatusDto.getLevelStatusSerialId() + " is not registered yet!!",
								ResponseStatus.FAILED);
					} else {
						return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.CREATED, msg,
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST,
							"INVALID_INPUT + , Level status serial id field is mandatory!!", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.BAD_REQUEST, "INVALID_INPUT",
						ResponseStatus.FAILED);
			}
		} catch (

		Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrderTaskStatuses() {
		try {
			List<SalesOrderTaskStatusDto> list = soTaskStatusRepo.listAllSalesOrderTaskStatuses();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, " ",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteSalesOrderTaskStatusById(String salesOrderTaskStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderTaskStatusId)) {
				String msg = soTaskStatusRepo.deleteSalesOrderTaskStatusById(salesOrderTaskStatusId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, " ",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Task Status Id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusById(String salesOrderTaskStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderTaskStatusId)) {
				SalesOrderTaskStatusDto salesOrderTaskStatusDto = soTaskStatusRepo
						.getSalesOrderTaskStatusById(salesOrderTaskStatusId);
				if (salesOrderTaskStatusDto != null) {
					return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.ACCEPTED,
							"Sales Order Task Status is found for Sales Order Task Status id : "
									+ salesOrderTaskStatusId,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Task Status is not available for Sales Order Task Status id : "
									+ salesOrderTaskStatusId,
							ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Task Status Id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public String updateLevelStatusAndTaskStatus(String taskid, String decisionSetId, String level) {

		System.err.print(" inourdto" + taskid + decisionSetId + level);
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;
		SalesOrderTaskStatusDto salesOrderTaskStatusDto = null;

		String taskstatusid = null;
		try {
			if (taskid != null && decisionSetId != null && level != null) {

				salesOrderLevelStatusDto = salesorderlevelstatusdao
						.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSetId, level);
				if (salesOrderLevelStatusDto != null) {
					salesOrderLevelStatusDto.setLevelStatus(StatusConstants.LEVEL_READY);
					salesOrderTaskStatusDto = soTaskStatusRepo
							.getAllTasksFromLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
					salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
					salesOrderTaskStatusDto.setTaskId(taskid);
					salesOrderTaskStatusDto.setTaskStatus(StatusConstants.TASK_READY);
					taskstatusid = soTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);

					String msgid = salesorderlevelstatusdao.saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);

					System.err.println(msgid);
				}

			}
		}

		catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return e.toString();
		}

		return taskstatusid;

	}

	@Override
	public ResponseEntity getAllTasksFromDecisionSetAndLevelAndItemNum(String decisionSetId, String level,
			String itemNum) {
		try {
			List<SalesOrderTaskStatusDto> list = soTaskStatusRepo
					.getAllTasksFromDecisionSetAndLevelAndItemNum(decisionSetId, level, itemNum);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum) {
		try {
			if (!HelperClass.checkString(decisionSetId) && !HelperClass.checkString(levelNum)) {
				List<SalesOrderTaskStatusDto> list = soTaskStatusRepo.getAllTasksFromDecisionSetAndLevel(decisionSetId,
						levelNum);

				if (list != null && !list.isEmpty()) {
					for (SalesOrderTaskStatusDto taskDto : list) {

						List<SalesOrderItemStatusDto> itemDtoList = soItemStatusRepo
								.getItemStatusDataUsingTaskSerialId(taskDto.getTaskStatusSerialId());
						taskDto.setItemStatusList(itemDtoList);

					}

					Map<String, List<Integer>> mapToCalculate = new HashMap<>();

					for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : list) {

						for (SalesOrderItemStatusDto salesOrderItemStatusDto : salesOrderTaskStatusDto
								.getItemStatusList()) {

							if (mapToCalculate.containsKey(salesOrderItemStatusDto.getSalesOrderItemNum())) {

								List<Integer> itemStatusNumList = mapToCalculate
										.get(salesOrderItemStatusDto.getSalesOrderItemNum());
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());
								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);
							} else {

								List<Integer> itemStatusNumList = new ArrayList<>();
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());

								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);

							}

						}

					}

					Map<String, Integer> mapToCalculateCumulativeStatus = new HashMap<>();

					for (String itemNum : mapToCalculate.keySet()) {
						List<Integer> itemStatusNumList = mapToCalculate.get(itemNum);
						int flagToCountApproveCase = 0, flagToCountDisplayOnlyCase = 0;
						for (Integer itemStatus : itemStatusNumList) {

							if (itemStatus == StatusConstants.ITEM_REJECT
									|| itemStatus == StatusConstants.ITEM_INDIRECT_REJECT) {
								mapToCalculateCumulativeStatus.put(itemNum, StatusConstants.ITEM_REJECT);
								break;
							} else if (itemStatus == StatusConstants.ITEM_APPROVE) {
								flagToCountApproveCase++;
								if (flagToCountApproveCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, StatusConstants.ITEM_APPROVE);
								}
							} else if (itemStatus == StatusConstants.DISPLAY_ONLY_ITEM) {
								flagToCountDisplayOnlyCase++;
								if (flagToCountDisplayOnlyCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, StatusConstants.DISPLAY_ONLY_ITEM);
								}
 
							} else if (itemStatus == StatusConstants.REJECTED_FROM_ECC) {
								mapToCalculateCumulativeStatus.put(itemNum, StatusConstants.REJECTED_FROM_ECC);
								break;
							} else {
								mapToCalculateCumulativeStatus.put(itemNum, StatusConstants.BLOCKED);
							}

						}

					}

					return new ResponseEntity(mapToCalculateCumulativeStatus, HttpStatus.OK, mapToCalculate.toString(),
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Decision set and Level Num fields are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, " ",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getAllTasksFromDecisionSetAndLevelWithItems(String decisionSetId, String levelNum) {
		try {
			List<SalesOrderTaskStatusDto> taskWithItemList = soTaskStatusRepo
					.getAllTasksFromDecisionSetAndLevel(decisionSetId, levelNum);

			if (taskWithItemList != null && !taskWithItemList.isEmpty()) {
				for (SalesOrderTaskStatusDto taskDto : taskWithItemList) {

					List<SalesOrderItemStatusDto> itemDtoList = soItemStatusRepo
							.getItemStatusDataUsingTaskSerialId(taskDto.getTaskStatusSerialId());
					taskDto.setItemStatusList(itemDtoList);

				}
			}
			if (taskWithItemList != null && !taskWithItemList.isEmpty()) {
				return new ResponseEntity(taskWithItemList, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
