package com.incture.cherrywork.services;



import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.SalesDocItemDao;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.entities.SalesDocItemDo;
import com.incture.cherrywork.entities.SalesDocItemPrimaryKeyDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.new_workflow.dao.SalesOrderItemStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.rules.ApproverDataOutputDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.tasksubmit.TriggerImeForWorkflowService;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.workflow.services.DecisionSetCreation;
import com.incture.cherrywork.workflow.services.TriggerImeDestinationService;
import com.incture.cherrywork.workflow.services.WorkflowTrigger;

@Service
@Transactional
public class SalesDocItemServiceImpl implements SalesDocItemService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DecisionSetCreation dataSet;

	@Autowired
	private SalesDocItemDao salesDocItemRepo;

	@Autowired
	private SessionFactory sessionfactory;

	@Autowired
	private SalesOrderLevelStatusDao salesOrderLevelStatusDao;

	@Autowired
	private SalesOrderTaskStatusDao salesOrderTaskStatusDao;

	@Autowired
	private SalesOrderItemStatusDao salesOrderItemStatusDao;

//	@Autowired
//	private WorkflowTrigger workflowtrigger;

//	@Autowired
//	 private TriggerImeDestinationService triggerImeService;

	@Autowired
	private TriggerImeForWorkflowService triggerImeService;

	@Override
	public ResponseEntity removeItemDeliveryBlockFromSalesDocItem(String salesHeaderId, String salesItemId) {
		try {
			if (!HelperClass.checkString(salesItemId) && !HelperClass.checkString(salesHeaderId)) {
				SalesDocItemDto salesDocItemDto = salesDocItemRepo.getSalesDocItemById(salesItemId, salesHeaderId);
				if (salesDocItemDto != null) {

					if (!salesDocItemDto.getScheduleLineList().isEmpty()) {

						for (ScheduleLineDto scheduleLine : salesDocItemDto.getScheduleLineList()) {
							scheduleLine.setSchlineDeliveryBlock("");
							scheduleLine.setRelfordelText("");
						}
						salesDocItemRepo.saveOrUpdateSalesDocItem(salesDocItemDto);

						return new ResponseEntity("", HttpStatus.ACCEPTED,
								"Delivery Blocks is successfully removed from Sales Doc Item", ResponseStatus.SUCCESS);
					} else {
						return new ResponseEntity(salesDocItemDto, HttpStatus.ACCEPTED, "No Schedule Lines Available",
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Invalid input, Sales Document Header is not available for Sales Order Item Id : "
									+ salesItemId + " and Sales Header Id : " + salesHeaderId,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.SALES_HEADER_ITEM_ID_MANDATORY,
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity saveOrUpdateSalesDocItem(SalesDocItemDto salesDocItemDto) {
		try {
			if (!HelperClass.checkString(salesDocItemDto.getSalesHeaderNo())
					&& !HelperClass.checkString(salesDocItemDto.getSalesItemOrderNo())) {
				String msg = salesDocItemRepo.saveOrUpdateSalesDocItem(salesDocItemDto);
				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.CREATION_FAILED,
							ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesDocItemDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity(salesDocItemDto, HttpStatus.BAD_REQUEST,
						Constants.SALES_HEADER_ITEM_ID_MANDATORY, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity saveOrUpdateSalesDocItemUsingMerge(SalesDocItemDto salesDocItemDto) {
		try {
			if (!HelperClass.checkString(salesDocItemDto.getSalesHeaderNo())
					&& !HelperClass.checkString(salesDocItemDto.getSalesItemOrderNo())) {

				String msg = salesDocItemRepo.saveOrUpdateSalesDocItemForDS(salesDocItemDto);
				// String msg =
				// salesDocItemRepo.saveOrUpdateSalesDocItem(salesDocItemDto);
				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.CREATION_FAILED,
							ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesDocItemDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity(salesDocItemDto, HttpStatus.BAD_REQUEST,
						Constants.SALES_HEADER_ITEM_ID_MANDATORY, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesDocItems() {
		try {
			List<SalesDocItemDto> list = salesDocItemRepo.listAllSalesDocItems();
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
	public ResponseEntity getSalesDocItemById(String salesItemId, String salesHeaderId) {
		try {
			if (!!HelperClass.checkString(salesItemId) && !HelperClass.checkString(salesHeaderId)) {
				SalesDocItemDto salesDocItemDto = salesDocItemRepo.getSalesDocItemById(salesItemId, salesHeaderId);
				if (salesDocItemDto != null) {
					return new ResponseEntity(salesDocItemDto, HttpStatus.ACCEPTED,
							"Sales Document Item is found for Sales Order Item Id : " + salesItemId
									+ " and Sales Header Id : " + salesHeaderId,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Invalid input, Sales Document Header is not available for Sales Order Item Id : "
									+ salesItemId + " and Sales Header Id : " + salesHeaderId,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.SALES_HEADER_ITEM_ID_MANDATORY,
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteSalesDocItemById(String salesItemId, String salesHeaderId) {
		try {
			if (!!HelperClass.checkString(salesItemId) && !HelperClass.checkString(salesHeaderId)) {
				String msg = salesDocItemRepo.deleteSalesDocItemById(salesItemId, salesHeaderId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.SALES_HEADER_ITEM_ID_MANDATORY,
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesDocItemsBySalesHeaderId(String salesHeaderId) {
		try {
			if (!!HelperClass.checkString(salesHeaderId)) {
				List<SalesDocItemDto> list = salesDocItemRepo.listOfItemsInSalesOrder(salesHeaderId);
				if (list != null && !list.isEmpty()) {
					return new ResponseEntity(list, HttpStatus.ACCEPTED, Constants.DATA_FOUND,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"No Item Found on sales order header id : " + salesHeaderId, ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales header id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesDocItemsFromSalesOrderInputs(SalesOrderHeaderInput soInput) {
		try {
			if (soInput == null || soInput.getSalesOrderItemIdList().isEmpty()) {
				return new ResponseEntity(soInput, HttpStatus.BAD_REQUEST,
						"Sales Order Item " + Constants.EMPTY_LIST, ResponseStatus.FAILED);
			} else {
				if (soInput.getSalesOrderHeaderId() != null) {
					List<SalesDocItemDto> salesDocItemDtoList = salesDocItemRepo.listOfItemsFromMultiItemId(soInput);
					if (salesDocItemDtoList != null && !salesDocItemDtoList.isEmpty()) {
						return new ResponseEntity(salesDocItemDtoList, HttpStatus.ACCEPTED, Constants.DATA_FOUND,
								ResponseStatus.SUCCESS);
					} else {
						return new ResponseEntity("", HttpStatus.NO_CONTENT, Constants.DATA_NOT_FOUND + soInput,
								ResponseStatus.FAILED);
					}
				} else {
					return new ResponseEntity(soInput, HttpStatus.BAD_REQUEST, "Sales Doc Header Id in Empty",
							ResponseStatus.FAILED);
				}
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	// called on blockTypeDetermination AccesRULES and trigger decisionsets
	// workflow

	@Override
	public ResponseEntity getInputDtoDataSet(SalesOrderHeaderInput soInput) {
		System.err.println("inside getInputDtoDataSet " + soInput.toString());
		ResponseEntity responsentity = new ResponseEntity(soInput, HttpStatus.BAD_REQUEST,
				"Sales Order Item List is empty", ResponseStatus.FAILED);

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "Trigger FAILURE", ResponseStatus.FAILED,
				null, null, "");
		try {
			if (soInput.getSalesOrderItemIdList().isEmpty()) {
				return new ResponseEntity(soInput, HttpStatus.BAD_REQUEST, "Sales Order Item List is empty",
						ResponseStatus.FAILED);
			} else {
				if (soInput.getSalesOrderHeaderId() != null) {
					List<SalesDocItemDto> salesDocItemDtoList = salesDocItemRepo.listOfItemsFromMultiItemId(soInput);
					System.err.println("salesDocItemDtoList " + salesDocItemDtoList.toString());
					Map<String, List<ApproverDataOutputDto>> listDto = dataSet.createAndReturnApprovalMap(
							soInput.getRequestId(), soInput.getSalesOrderHeaderId(), salesDocItemDtoList,
							soInput.getStrategy(), soInput.getDistributionChannel(), soInput.getSalesOrg(),
							soInput.getCountry(), soInput.getCustomerPo(), soInput.getRequestType(),
							soInput.getRequestCategory());
					System.err.println("decisionSet listDto  " + listDto.toString());

					List<ApproverDataOutputDto> listapproverDto = null;
					if (listDto.isEmpty()) {
						return new ResponseEntity(soInput, HttpStatus.NO_CONTENT, "listapproverDto is null " + soInput,
								ResponseStatus.FAILED);
					} else {
						SalesOrderLevelStatusDto salesorderlevelstatusdto = new SalesOrderLevelStatusDto();

						// Configure gson
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.create();

						String LevelStatusSerialId = "";

						int countDataSetKey = 0;
						for (Map.Entry<String, List<ApproverDataOutputDto>> entry : listDto.entrySet()) {
							System.err.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
							listapproverDto = entry.getValue();
							System.err.println("listapproverDto " + listapproverDto);

							System.err.println("decisionSet listapproverDto " + listapproverDto);

							for (int listIterate = 0; listIterate < listapproverDto.size(); listIterate++) {
								ApproverDataOutputDto approverDataOutput = listapproverDto.get(listIterate);

								// setting the salesorderlevelstatusdto
								salesorderlevelstatusdto.setDecisionSetId(entry.getKey());
								salesorderlevelstatusdto.setApproverType(approverDataOutput.getApprovalType());
								salesorderlevelstatusdto.setLevel(approverDataOutput.getLevel());
								salesorderlevelstatusdto.setUserGroup(approverDataOutput.getApprover());
								salesorderlevelstatusdto.setLevelStatus(StatusConstants.LEVEL_NEW);

								// create a record in the level status table
								LevelStatusSerialId = salesOrderLevelStatusDao
										.saveOrUpdateSalesOrderLevelStatusSynchronized(salesorderlevelstatusdto);

								System.err.print("decisionSet levelStatusSerailId inserted succefullly  ="
										+ LevelStatusSerialId);

							}

							String approverDtoList = gson.toJson(entry.getValue());

							System.err.print("decisionSet approverDtoList" + approverDtoList);
							// setting the decisionSetAmount
							List<SalesDocItemDto> itemStoListByDS = null;
							itemStoListByDS = salesDocItemRepo.getSalesDocItemsByDecisionSetId(entry.getKey());
							Double decisionSetAmount = 0.0;
							System.err.print("decisionSet 296");
							// calculating total amout and setting the final
							// value
							for (SalesDocItemDto itemDto : itemStoListByDS) {
								decisionSetAmount = decisionSetAmount + Double.parseDouble(itemDto.getNetWorth());
								System.err.print("decisionSet 301");
							}

							Double threshold = 100000.0;

							System.err.print("decisionSet 306");

//							response = workflowtrigger.DecisionSetWorkflowTrigger(soInput.getSalesOrderHeaderId(),
//									soInput.getRequestId(), entry.getKey(), approverDtoList, threshold,
//									decisionSetAmount, soInput.getHeaderBlocReas(), soInput.getSoCreatedECC(),
//									soInput.getCountry(), soInput.getCustomerPo(), soInput.getRequestType(),
//									soInput.getRequestCategory(), soInput.getSalesOrderType(), soInput.getSoldToParty(),
//									soInput.getShipToParty(), soInput.getDivision(), soInput.getDistributionChannel(),
//									soInput.getSalesOrg(), soInput.getReturnReason());

							System.err.print("workflowtrigger.DecisionSetWorkflowTrigger =" + response);

							countDataSetKey++;
						}

						responsentity.setMessage("" + countDataSetKey);
						responsentity.setData(response);
						responsentity.setStatusCode(HttpStatus.ACCEPTED);
						responsentity.setStatus(ResponseStatus.SUCCESS);
					}

				}
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
		return responsentity;

	}

	@Override
	public ResponseEntity updateSalesDocItemWithDecisionSet(String decisionSet, String salesItemId,
			String salesHeaderId) {
		try {
			if (!HelperClass.checkString(decisionSet) && !HelperClass.checkString(salesItemId) && !HelperClass.checkString(salesHeaderId)) {
				String msg = salesDocItemRepo.updateSalesDocItemWithDecisionSet(decisionSet, salesItemId,
						salesHeaderId);
				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.CREATION_FAILED,
							ResponseStatus.FAILED);
				}
				return new ResponseEntity(decisionSet, HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set id, Sales header id and Sales item id fields are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity triggerImePostSoDsCompletion(String salesOrderNumber, String decisionSetId)
			throws URISyntaxException, IOException {

		System.err.println("salesOrderNumber : " + salesOrderNumber);
		System.err.println("decisionSetId : " + decisionSetId);

		// List<SalesDocItemDto> salesDocItemList = new ArrayList<>();
		List<String> decisionSetIdList = new ArrayList<>();
		Set<Integer> levelStatus = new HashSet<>();

		ResponseEntity response = new ResponseEntity(decisionSetId, HttpStatus.ACCEPTED, decisionSetId,
				ResponseStatus.SUCCESS);
		//
		// try {
		// salesDocItemList =
		// salesDocItemRepo.listOfItemsInSalesOrder(salesOrderNumber);
		// } catch (Exception e) {
		// return new ResponseEntity(response, HttpStatus.BAD_REQUEST,
		// Constants.CREATION_FAILED,
		// ResponseStatus.FAILED);
		// }

		try {
			decisionSetIdList = salesDocItemRepo.getDSBySalesHeaderID(salesOrderNumber);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST, Constants.CREATION_FAILED,
					ResponseStatus.FAILED);
		}

		System.err.println("salesDocItemList : " + decisionSetIdList.toString());

		// if (null != salesDocItemList && !salesDocItemList.isEmpty()) {
		// for (SalesDocItemDto itemDto : salesDocItemList) {
		// if (null != itemDto.getDecisionSetId()) {
		// decisionSetIdSet.add(itemDto.getDecisionSetId());
		// }
		// }
		// }

		if (!decisionSetIdList.isEmpty()) {
			for (String dsId : decisionSetIdList) {

				List<SalesOrderLevelStatusDto> salesOrderLevelStatusDtoList = new ArrayList<>();
				try {
					salesOrderLevelStatusDtoList = salesOrderLevelStatusDao.getSalesOrderLevelStatusByDecisionSet(dsId);

					System.err.println("salesOrderLevelStatusDtoList" + salesOrderLevelStatusDtoList.toString());
				} catch (NoResultException e) {
					logger.error(e.getMessage());
					return new ResponseEntity(response, HttpStatus.BAD_REQUEST, Constants.CREATION_FAILED,
							ResponseStatus.FAILED);
				}

				if (null != salesOrderLevelStatusDtoList) {
					for (SalesOrderLevelStatusDto salesOrderLevelStatusDto : salesOrderLevelStatusDtoList) {
						levelStatus.add(salesOrderLevelStatusDto.getLevelStatus());
					}
				}
			}
		}

		if (((levelStatus.size() == 1) && levelStatus.contains(StatusConstants.LEVEL_COMPLETE))
				|| (levelStatus.size() == 2 && levelStatus.contains(StatusConstants.LEVEL_ABANDON))) {
			ResponseEntity triggerResponse = triggerImeService.triggerImePostDS(salesOrderNumber);
			if (triggerResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST))
				return new ResponseEntity(triggerResponse, HttpStatus.BAD_REQUEST, Constants.TRIGGER_FAILED,
						ResponseStatus.FAILED);
		} else {
			ResponseEntity triggerResponse = new ResponseEntity(decisionSetId, HttpStatus.BAD_REQUEST, decisionSetId,
					ResponseStatus.FAILED);
			return new ResponseEntity(triggerResponse, HttpStatus.BAD_REQUEST, Constants.PENDING_DS_LEVELS,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity(response, HttpStatus.ACCEPTED, decisionSetId, ResponseStatus.SUCCESS);
	}

	public List<SalesDocItemDo> listOfItemsFromMultiItemId(SalesOrderHeaderInput soInput) {
		List<String> salesDocItemIdList = soInput.getSalesOrderItemIdList();
		List<SalesDocItemDo> salesDocItemDoList = new ArrayList<>();
		for (String itemId : salesDocItemIdList) {
			SalesDocItemDo item = getSalesDocItemByIds(itemId, soInput.getSalesOrderHeaderId());
			if (item != null) {
				salesDocItemDoList.add(item);
			}
		}
		return salesDocItemDoList;
	}

	public SalesDocItemDo getSalesDocItemByIds(String salesItemId, String salesHeaderId) {
		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();

		// Creating Sales Header Entity For inserting in Composite PK
		SalesDocHeaderDo salesDocHeader = new SalesDocHeaderDo();
		// Setting Sales Header Primary Key
		salesDocHeader.setSalesOrderNum(salesHeaderId);

		SalesDocItemDo salesDocItemDo = session.get(SalesDocItemDo.class,
				new SalesDocItemPrimaryKeyDo(salesItemId, salesDocHeader));

		tx.commit();

		session.clear();
		session.close();

		return salesDocItemDo;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity checkForNextLevelTrigger(String dataSet, String level) {

		ResponseEntity responseEntity = new ResponseEntity("", HttpStatus.BAD_REQUEST,
				"INVALID_INPUT "+ ", Check Next level Triggere failed ", ResponseStatus.FAILED);

		System.err.println("dataSet and level " + dataSet + level);

		boolean checkIfApproved = false;

		if (!HelperClass.checkString(dataSet) && !HelperClass.checkString(level)) {
			// get the soLevelstatus by dataSet and Level
			SalesOrderLevelStatusDo salesOrderLevelStatusDo = salesOrderLevelStatusDao
					.getSalesOrderLevelStatusByDecisionSetAndLevelDo(dataSet, level);

			System.err.println("checkNext salesOrderLevelStatusDtos " + salesOrderLevelStatusDo.toString());

			if (salesOrderLevelStatusDo != null) {

				SalesOrderTaskStatusDto salesOrderTaskStatusDto = salesOrderTaskStatusDao
						.getAllTasksFromLevelStatusSerialId(salesOrderLevelStatusDo.getLevelStatusSerialId());
				System.err.println("checkNext salesOrderTaskStatusDtos " + salesOrderTaskStatusDto);

				if (salesOrderTaskStatusDto != null) {

					List<SalesOrderItemStatusDto> salesOrderItemStatusDtoList = salesOrderItemStatusDao
							.getItemStatusDataUsingTaskSerialId(salesOrderTaskStatusDto.getTaskStatusSerialId());

					System.err.println(
							"checkNext salesOrderItemStatusDtoLists " + salesOrderItemStatusDtoList.toString());

					if (!salesOrderItemStatusDtoList.isEmpty()) {

						SalesOrderItemStatusDto salesOrderItemstatusdto = null;
						System.err.println("checkNext 1024");

						for (int item = 0; item < salesOrderItemStatusDtoList.size(); item++) {
							salesOrderItemstatusdto = salesOrderItemStatusDtoList.get(item);
							System.err.println("salesOrderItemStatusDtoinisde if" + salesOrderItemstatusdto);
							if (salesOrderItemstatusdto.getItemStatus().equals(StatusConstants.ITEM_APPROVE)) {
								System.err.println("checkNext 1031");
								System.err.println("Inside the if statemnt ");
								checkIfApproved = true;
								responseEntity.setMessage("Success");
								responseEntity.setStatus(ResponseStatus.SUCCESS);
								responseEntity.setStatusCode(HttpStatus.ACCEPTED);
								// if atleast one item is approved ,than return
								// true
								System.err.println("checkNext 1039");
								return new ResponseEntity(responseEntity, HttpStatus.ACCEPTED, "" + checkIfApproved,
										ResponseStatus.SUCCESS);
							} else {
								// Check while if remaining items are approvred
								// or not , if all rejected than return false
								System.err.println("checkNext 1045");
								System.err.println("salesOrderItemStatusDtoList" + salesOrderItemStatusDtoList.size()
										+ "item" + item);
								item = item + 1;
								if (salesOrderItemStatusDtoList.size() == item) {
									List<SalesOrderLevelStatusDto> salesOrderLevelStatusDtoList = salesOrderLevelStatusDao
											.getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(dataSet, level);
									System.err.println("checkNext 1051");
									if (!salesOrderLevelStatusDtoList.isEmpty()) {
										System.err.println("checkNext 1053");
										responseEntity = updateLevelStatusAbandond(salesOrderLevelStatusDtoList);
										responseEntity.setMessage(
												"All the item are rejected by user and further Level Abondened");
										return new ResponseEntity(responseEntity, HttpStatus.ACCEPTED,
												"" + checkIfApproved, ResponseStatus.SUCCESS);

									} else {

										System.err.println("checkNext 1062");
										return new ResponseEntity("", HttpStatus.BAD_REQUEST,
												"INVALID_INPUT" + ",salesOrderLevelStatusDtoList is null",
												ResponseStatus.FAILED);
									}
								}

							}
						}
					} else {
						System.err.println("checkNext 1072");
						return new ResponseEntity("", HttpStatus.BAD_REQUEST,
								"INVALID_INPUT "+ ",salesOrderItemStatusDtoList is null", ResponseStatus.FAILED);
					}
				} else {
					System.err.println("checkNext 1077");
					return new ResponseEntity("", HttpStatus.BAD_REQUEST,
							"INVALID_INPUT "+ ",salesOrderTaskStatusDto is null", ResponseStatus.FAILED);
				}
			}
		} else {
			System.err.println("checkNext 1088");
			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "INVALID_INPUT" + ",salesOrderLevelStatusDto is null",
					ResponseStatus.FAILED);
		}

		System.err.println("checkNext 1093");
		responseEntity.setMessage("at the end check next trigger fail ");
		return responseEntity;
	}

	// upon rejection of all the items in a task ,updates all the method to
	// level abonded status

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity updateLevelStatusAbandond(List<SalesOrderLevelStatusDto> saleOrderLevelStatusDoList) {
		ResponseEntity responseEntity = new ResponseEntity("", HttpStatus.BAD_REQUEST,
				"INVALID_INPUT" + ",Failed to update SalesOrderLevelStatus", ResponseStatus.FAILED);
		if (!saleOrderLevelStatusDoList.isEmpty()) {
			SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;

			for (int iteratelevellist = 0; iteratelevellist < saleOrderLevelStatusDoList.size(); iteratelevellist++) {

				salesOrderLevelStatusDto = saleOrderLevelStatusDoList.get(iteratelevellist);

				salesOrderLevelStatusDto.setLevelStatus(StatusConstants.LEVEL_ABANDON);

				try {
					salesOrderLevelStatusDao.saveOrUpdateSalesOrderLevelStatusSynchronized(salesOrderLevelStatusDto);
					responseEntity.setMessage("Success");
					responseEntity.setStatus(ResponseStatus.SUCCESS);
					responseEntity.setStatusCode(HttpStatus.ACCEPTED);

				} catch (ExecutionFault e) {

					return new ResponseEntity("", HttpStatus.BAD_REQUEST,
							"INVALID_INPUT" + ",Failed to update SalesOrderLevelStatus" + e, ResponseStatus.FAILED);
				}
			}
		} else {
			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "INVALID_INPUT "+ ", SalesOrderLevelStatusList is null",
					ResponseStatus.FAILED);
		}
		return responseEntity;

	}

	@Override
	public ResponseEntity addReasonOfRejectionFromSalesDocItem(String salesHeaderId, String salesItemId,
			String reasonOfRejection) {
		try {
			if (!HelperClass.checkString(salesItemId) && !HelperClass.checkString(salesHeaderId)) {
				SalesDocItemDto salesDocItemDto = salesDocItemRepo.getSalesDocItemById(salesItemId, salesHeaderId);
				if (salesDocItemDto != null) {

					if (!salesDocItemDto.getScheduleLineList().isEmpty()) {

						salesDocItemDto.setReasonForRejection(reasonOfRejection);

						salesDocItemRepo.saveOrUpdateSalesDocItem(salesDocItemDto);

						return new ResponseEntity("", HttpStatus.ACCEPTED,
								"Delivery Blocks is successfully removed from Sales Doc Item", ResponseStatus.SUCCESS);
					} else {
						return new ResponseEntity(salesDocItemDto, HttpStatus.ACCEPTED, "No Schedule Lines Available",
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Invalid input, Sales Document Header is not available for Sales Order Item Id : "
									+ salesItemId + " and Sales Header Id : " + salesHeaderId,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, Constants.SALES_HEADER_ITEM_ID_MANDATORY,
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, Constants.EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

}

