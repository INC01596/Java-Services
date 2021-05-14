
package com.incture.cherrywork.workflow;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.workflow.DlvBlockReleaseMapDto;
import com.incture.cherrywork.dto.workflow.SalesDocItemDto;
//import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.new_workflow.IWorkflowServices;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.workflow.repo.ISalesDocItemRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderItemStatusRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderLevelStatusCustomRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderLevelStatusRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderTaskStatusCustomRepository;
import com.incture.cherrywork.workflow.repo.ISalesOrderTaskStatusRepository;
import com.incture.cherrywork.WConstants.DkshStatusConstants;

import io.swagger.models.HttpMethod;


@SuppressWarnings("deprecation")
@Service
@Transactional
public class ApprovalworkflowTrigger {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String XCSRFTOKEN = "X-CSRF-Token";

	private static final String TRIGGERFAILUER = "Trigger FAILURE no poper dataset and level ";

	private static final String CONTENTTYPE = "Content-Type";

	private static final String BASIC = "Basic";
	private static final String AUTHORIZATION = "Authorization";

//	@Autowired
//	private SalesDocItemDao salesDocItemDao;
//
//	@Autowired
//	private DlvBlockReleaseMapService dlvBlockreleasemapservice;

	@Autowired
	private IWorkflowServices salesordertaskstatusdao;

//	@Autowired
//	private SalesOrderItemStatusService salesOrderItemStatusService;
//
//	@Autowired
//	private SalesOrderTaskStatusService salesOrderTaskStatus;

	@Autowired
	private ISalesOrderLevelStatusRepository salesorderlevelstausdao;
	
	@Autowired
	private ISalesOrderTaskStatusRepository salesOrderTaskStatusRepository;
	
	@Autowired
	private ISalesDocItemRepository salesDocItemRepository;

//	@Autowired
//	private SalesOrderItemStatusDao salesOrderItemStatusDao;
	
	@Autowired
	private ISalesOrderItemStatusRepository salesOrderItemStatusRepository;

	// triggers the approval workflow and create the reccord in the
	// SO_TASK_STATUS_table.
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public WorkflowResponseEntity approvalworkflowTriggeringAndUpdateTable(String salesOrderNo, String requestId,
			String approver, String level, String dataSet, String threshold, String decisionSetAmount,
			String HeaderBlocReas, String soCreatedECC, String country, String customerPo, String requestType,
			String requestCategory, String salesOrderType, String soldToParty, String shipToParty, String division,
			String distributionChannel, String salesOrg, String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");

		if (level != null && dataSet != null) {

			String[] userList = null;
			String group = null;
			WorkflowResponseEntity WorkflowResponseEntity = null;
			// create a new task in SO_TASK_STATUS Table
			String taskSerialId = createNewTaskOr(dataSet, level, approver);
			if (!HelperClass.checkString(taskSerialId)) {
				// changes to determine - groupname or single user/list
				String regex = "^[pP]\\d{6}$";
				if (!(approver.matches(regex))) {
					userList = approver.split(",");
					if (userList != null) {
						for (String s : userList) {
							if (!s.trim().matches(regex)) {
								List<String> approverList = (List<String>) HelperClass
										.findUsersFromGroupInIdp(approver);
								approver = String.join(",", approverList);
								break;
							}
						}
					} else {
						group = approver;
						approver = null;
					}
				}

				// Trigger workflow if the task is created
				WorkflowResponseEntity = approvalWorkflowTrigger(salesOrderNo, requestId, approver, level, dataSet,
						threshold, decisionSetAmount, group, HeaderBlocReas, soCreatedECC, country, customerPo,
						requestType, requestCategory, salesOrderType, soldToParty, shipToParty, division,
						distributionChannel, salesOrg, returnReason);
				// System.err.println("approvalWorkflowTrigger
				// WorkflowResponseEntity =" + WorkflowResponseEntity);
				if (WorkflowResponseEntity.getResponseStatusCode() == 200) {
					String taskId = WorkflowResponseEntity.getId();
					taskId = taskId.substring(1, taskId.length() - 1);

					if (!HelperClass.checkString(taskId)) {
						// update the taskId into taskstatusorder table
						String taskstatusid = updateLevelStatusAndTaskStatus(taskId, dataSet, level, taskSerialId);
						System.err.println("taskstatusid " + taskstatusid);
						if (!HelperClass.checkString(taskstatusid)) {
							// create item status in ItemTable
							ResponseEntity<Object> responseEntity = createNewItemAndUpdataItem(dataSet, taskstatusid, level);
							response.setData(responseEntity);
							response.setMessage("Success " + WorkflowResponseEntity.toString());
							response.setStatus(ResponseStatus.SUCCESS);

						} else {

							return new WorkflowResponseEntity("", 200,
									"failed Due to No task id at creation of task in item table ",
									ResponseStatus.FAILED, null, null, "");
						}
					}

					else {
						return new WorkflowResponseEntity("", 200, "failed Due to No  Workflow taskId",
								ResponseStatus.FAILED, null, null, "");
					}

				} else {

					return new WorkflowResponseEntity("", 200,
							"failed Due to No task id at creation of taskserial  in SO_TASK table ",
							ResponseStatus.FAILED, null, null, "");
				}

			} else {

				return new WorkflowResponseEntity("", 200, "failed Due to Incorrect DataSet and Level ",
						ResponseStatus.FAILED, null, null, "");

			}
		} else {

			return new WorkflowResponseEntity("", 200, "failed Due to trigger workflow", ResponseStatus.FAILED, null,
					null, "");

		}

		return response;

	}

	// triiger the apporval workflow for the END condition.
	@SuppressWarnings("unchecked")
	public WorkflowResponseEntity ApprovalWorkflowTriggerAndUpdateTableOnAnd(String salesOrderNo, String requestId,
			String approver, String level, String dataSet, String thresold, String decisionSetAmount,
			String headerBlocReas, String soCreatedECC, String country, String customerPo, String requestType,
			String requestCategory, String salesOrderType, String soldToParty, String shipToParty, String division,
			String distributionChannel, String salesOrg, String returnReason) throws IOException {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");
		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ", Level status serial id registered ").body(null);
		if (level != null && dataSet != null) {
			List<String> approverList = null;
			List<String> taskSerialIdList = null;

			List<String> workflowInstaceId = null;
			String[] userList = null;
			String group = null;

			// if pid or , seperated pid
			String regex = "^[pP]\\d{6}$";

			if ((approver.matches(regex))) {
				userList = approver.split(",");
				approverList = new ArrayList<String>(Arrays.asList(userList));
				// System.err.println("approverList if matches or if pid " +
				// approverList);
			} else {
				userList = approver.split(",");
				if (userList.length > 1) {
					approverList = new ArrayList<String>(Arrays.asList(userList));

					// System.err.println("approverList if matches or if pid is
					// multiple " + approverList);
				} else {

					approverList = (List<String>) HelperClass.findUsersFromGroupInIdp(approver);

					// System.err.println("approverList is a group " +
					// approverList);
				}
			}
			// call worfklow API to get number of users from the group.......
			if (!approverList.isEmpty()) {

				// create new task in Task_SO_Table
				taskSerialIdList = createNewTaskAnd(dataSet, level, approverList);

				if (!taskSerialIdList.isEmpty()) {

					// call approval workflow to trigger all the users in the
					// group

					// System.err.println("approverList " + approverList);
					workflowInstaceId = approvalWorkflowTriggerAnd(salesOrderNo, requestId, approverList, level,
							dataSet, thresold, decisionSetAmount, group, headerBlocReas, soCreatedECC, country,
							customerPo, requestType, requestCategory, salesOrderType, soldToParty, shipToParty,
							division, distributionChannel, salesOrg, returnReason);
					if (!workflowInstaceId.isEmpty()) {

						taskSerialIdList = updateSalesOrderTaskStatusAnd(workflowInstaceId, dataSet, level,
								taskSerialIdList);

						if (!taskSerialIdList.isEmpty()) {
							// create reccord in itemStatus table
							responseEntity = createAndUpdateItemForAnd(dataSet, taskSerialIdList, level);

							response.setMessage("Success Approval And workflow " + responseEntity);
							response.setResponseStatusCode(200);
							response.setStatus(ResponseStatus.SUCCESS);
							response.setResponseMessage(null);
							response.setResponseJson(null);

						}

						else {

							return new WorkflowResponseEntity("", 200,
									" due to taskSerialIdList null  And approvla workflow" + TRIGGERFAILUER,
									ResponseStatus.FAILED, null, null, "");
						}

					} else {

						return new WorkflowResponseEntity("", 200,
								" due Workflow Instance Id is null  And approvla workflow" + TRIGGERFAILUER,
								ResponseStatus.FAILED, null, null, "");
					}

				} else {

					response.setMessage("failed due to taskserialIdList And approval workflow");
					return response;
				}
			} else {

				return new WorkflowResponseEntity("", 200,
						" due to null approver group And approvla workflow" + TRIGGERFAILUER, ResponseStatus.FAILED,
						null, null, "");

			}

		} else {
			return new WorkflowResponseEntity("", 200,
					" due no proper dataset and level And approvla workflow" + TRIGGERFAILUER, ResponseStatus.FAILED,
					null, null, "");
		}
		return response;
	}

	// create new task for the trigger appoval workflow for OR condition.
	public String createNewTaskOr(String dataSet, String level, String approver) {

		String taskSerialId = "";

		SalesOrderTaskStatusDto salesOrderTaskStatusDto = new SalesOrderTaskStatusDto();

		SalesOrderLevelStatusDo salesOrderLevelStatusDo = getLevelStatusBasedOnLevelAndDecisionSet(dataSet, level);

		try {
			salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDo.getLevelStatusSerialId());
			salesOrderTaskStatusDto.setSalesOrderLevelStatus(salesOrderLevelStatusDo);
			// logger.error(" levelserialid" +
			// salesOrderTaskStatusDto.getLevelStatusSerialId());
			salesOrderTaskStatusDto.setApprover(approver);
			salesOrderTaskStatusDto.setTaskStatus(StatusConstants.TASK_NEW);

			taskSerialId = salesordertaskstatusdao
					.saveOrUpdateSalesOrderTaskStatusSynchronized(salesOrderTaskStatusDto);
		} catch (Exception e1) {
			// logger.error("ApprovalWorkflowTrigger " + e1);
		}
		return taskSerialId;

	}

	// create a task in SO_TASK_STATUS table for AND condition.
	public List<String> createNewTaskAnd(String dataSet, String level, List<String> approverList) {

		// SalesOrderTaskStatusDto salesOrderTaskStatusDto = new
		// SalesOrderTaskStatusDto();

		List<SalesOrderTaskStatusDto> salesOrderTaskStatusDtoList = new ArrayList<>();

		SalesOrderLevelStatusDo salesOrderLevelStatusDo = getLevelStatusBasedOnLevelAndDecisionSet(dataSet, level);

		for (int iterateList = 0; iterateList < approverList.size(); iterateList++) {
			SalesOrderTaskStatusDto salesOrderTaskStatusDto = new SalesOrderTaskStatusDto();
			salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDo.getLevelStatusSerialId());

			salesOrderTaskStatusDto.setSalesOrderLevelStatus(salesOrderLevelStatusDo);

			// logger.error(" levelserialid" +
			// salesOrderTaskStatusDto.getLevelStatusSerialId());

			salesOrderTaskStatusDto.setApprover(approverList.get(iterateList));
			salesOrderTaskStatusDto.setTaskStatus(StatusConstants.TASK_NEW);
			// logger.error("SalesOrderTaskStatusDto
			// "+salesOrderTaskStatusDto.toString());
			salesOrderTaskStatusDtoList.add(salesOrderTaskStatusDto);
		}

		// logger.error("SalesORderTaskStatusDto "+
		// salesOrderTaskStatusDtoList.toString());
		return saveListOfSoTaskOrderTable(salesOrderTaskStatusDtoList);

	}

	// saves list of taskOrderStaus table with a task status new
	public List<String> saveListOfSoTaskOrderTable(List<SalesOrderTaskStatusDto> listSalesOrderTaskStatusDto) {

		SalesOrderTaskStatusDto salesOrderTaskStatusdto = null;
		List<String> taskSerialId = new ArrayList<>();
		for (int iterateOverList = 0; iterateOverList < listSalesOrderTaskStatusDto.size(); iterateOverList++) {
			salesOrderTaskStatusdto = listSalesOrderTaskStatusDto.get(iterateOverList);
			try {
				taskSerialId.add(
						salesordertaskstatusdao.saveOrUpdateSalesOrderTaskStatusSynchronized(salesOrderTaskStatusdto));
			} catch (Exception e) {
				// logger.error("Approvalworkflow failed to create task in
				// SO_TASK_TABLE" + e);

				return Collections.emptyList();
			}
		}
		return taskSerialId;
	}

	// get salesOrderLevelStatus based on DataSet and Level

	public SalesOrderLevelStatusDo getLevelStatusBasedOnLevelAndDecisionSet(String dataSet, String level) {

		return salesorderlevelstausdao.getSalesOrderLevelStatusByDecisionSetAndLevelDo(dataSet, level);

	}

	// update level status with a level ready and task ready
	public String updateLevelStatusAndTaskStatus(String taskid, String decisionSetId, String level,
			String salesOrderTaskStatusId) {

		// System.err.print(" inourdto" + taskid + decisionSetId + level);
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;
		SalesOrderTaskStatusDto salesOrderTaskStatusDto = null;

		String taskstatusid = null;
		try {
			if (!HelperClass.checkString(taskid) && decisionSetId != null && level != null) {
				salesOrderLevelStatusDto = salesorderlevelstausdao
						.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSetId, level);
				if (salesOrderLevelStatusDto != null) {
					salesOrderLevelStatusDto.setLevelStatus(StatusConstants.LEVEL_READY);
					String msgid = updateSalesOrderLevelStatus(salesOrderLevelStatusDto);

					// System.err.println(" check the update 365 " + msgid);
					salesOrderTaskStatusDto = salesordertaskstatusdao
							.getSalesOrderTaskStatusById(salesOrderTaskStatusId);
					salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
					salesOrderTaskStatusDto.setTaskId(taskid);
					salesOrderTaskStatusDto.setTaskStatus(StatusConstants.TASK_READY);
					taskstatusid = updateSalesOrderTaskStatus(salesOrderTaskStatusDto);

					// System.err.println(" check the update 371 " + msgid);
				}

			} else {
				return "failed to get taskid or level or dataSet";
			}
		}

		catch (Exception e) {

			System.err.println("Exception  in update method" + e);
			
			return e.toString();
		}

		return taskstatusid;

	}

	// Update salesOrderTaskStatus table for AND condition
	public List<String> updateSalesOrderTaskStatusAnd(List<String> taskid, String decisionSetId, String level,
			List<String> salesOrderTaskStatusId) {

		// logger.error(" inourdto" + taskid.toString() + decisionSetId +
		// level);
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;
		SalesOrderTaskStatusDto salesOrderTaskStatusDto = null;
		List<SalesOrderTaskStatusDto> salesOrderTaskStatusDtoList = null;

		List<String> updatedtaskstatusid = new ArrayList<>();
		try {
			if (!taskid.isEmpty() && decisionSetId != null && level != null) {

				salesOrderLevelStatusDto = salesorderlevelstausdao
						.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSetId, level);
				if (salesOrderLevelStatusDto != null) {
					salesOrderLevelStatusDto.setLevelStatus(StatusConstants.LEVEL_READY);
					updateSalesOrderLevelStatus(salesOrderLevelStatusDto);
					// get all the task from the SoTaskby taskSerialId
					salesOrderTaskStatusDtoList = salesOrderTaskStatusRepository
							.getAllTaskByTaskSerialId(salesOrderTaskStatusId);

					if (!salesOrderTaskStatusDtoList.isEmpty()) {

						for (int iterateSoTaskList = 0; iterateSoTaskList < salesOrderTaskStatusDtoList
								.size(); iterateSoTaskList++) {

							salesOrderTaskStatusDto = salesOrderTaskStatusDtoList.get(iterateSoTaskList);
							salesOrderTaskStatusDto
									.setLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
							salesOrderTaskStatusDto.setTaskId(taskid.get(iterateSoTaskList));
							salesOrderTaskStatusDto.setTaskStatus(StatusConstants.TASK_READY);
							updatedtaskstatusid.add(updateSalesOrderTaskStatus(salesOrderTaskStatusDto));
						}
					} else {
						System.err.println("task status didnt update due to salesordertasklist is empty"
								+ salesOrderTaskStatusDtoList);
						return null;
					}
				}

			} else {

				logger.error("task status didnt update due to saleordertasklist " + salesOrderLevelStatusDto);
				return null;

			}
		}

		catch (Exception e) {
			//ApprovalworkflowTriggeHelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			// logger.error("error in update of taskStatus" + e);
			return Collections.emptyList();
		}

		return updatedtaskstatusid;

	}

	public String updateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto) {

		String msgid = "";
		try {
			msgid = salesordertaskstatusdao.saveOrUpdateSalesOrderLevelStatusSynchronized(salesOrderLevelStatusDto);
		} catch (Exception e) {
			// logger.error("saveOrUpdateSalesOrderLevelStatusSynchronized
			// failed " + e);
		}

		return msgid;

	}

	public String updateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto) {

		try {
			return salesordertaskstatusdao.saveOrUpdateSalesOrderTaskStatusSynchronized(salesOrderTaskStatusDto);
		} catch (Exception e) {
			// logger.error("updateSalesOrderTaskStatus failed " + e);
		}
		return null;

	}

	// creates a new item record after the approval workflow is triggered and
	// status is updated

	public ResponseEntity<Object> createNewItemAndUpdataItem(String dataSet, String taskserializedId, String level) {

		ResponseEntity<Object> responseentity =ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", ", Level status serial id not registered ").body(null);
		String serialitemid = "";
		List<SalesDocItemDto> listItem = salesDocItemRepository.getSalesDocItemsByDecisionSetId(dataSet);

		// List<String> listItem =
		// salesDocItemDao.getItemListByDataSet(dataSet);

		if (level.equals("L1")) {
			if (listItem != null) {

				for (int itemid = 0; itemid < listItem.size(); itemid++) {

					ResponseEntity responseentity1DispayOnly = salesordertaskstatusdao
							.getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(listItem.get(itemid).getItemDlvBlock());

					DlvBlockReleaseMapDto releaseMapDto = (DlvBlockReleaseMapDto) responseentity1DispayOnly.getBody();

					SalesOrderItemStatusDto salesorderitemstatusdto = new SalesOrderItemStatusDto();

					if (responseentity1DispayOnly.getStatusCode() == HttpStatus.ACCEPTED && releaseMapDto != null
							&& releaseMapDto.getItemLevel() == true && releaseMapDto.getEdit() == false
							&& releaseMapDto.getSpecialClient() == false) {

						salesorderitemstatusdto.setSalesOrderItemNum(listItem.get(itemid).getSalesItemOrderNo());
						salesorderitemstatusdto.setItemStatus(DkshStatusConstants.DISPLAY_ONLY_ITEM);
						salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
						salesorderitemstatusdto.setTaskStatusSerialId(taskserializedId);

					} else {

						salesorderitemstatusdto.setSalesOrderItemNum(listItem.get(itemid).getSalesItemOrderNo());
						salesorderitemstatusdto.setItemStatus(DkshStatusConstants.BLOCKED);
						salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
						salesorderitemstatusdto.setTaskStatusSerialId(taskserializedId);
					}

					try {
						serialitemid = salesordertaskstatusdao
								.saveOrUpdateSalesOrderItemStatusSynchronised(salesorderitemstatusdto);
					} catch (Exception e) {
						// logger.error(" Level status serial id Failed" + e);
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", e.getMessage()).body(null);
						

					}
					// logger.error("serialitemid" + serialitemid);
				}
			}
		} else

		{

			if (listItem != null) {
				SalesOrderItemStatusDto salesOrderItemStatusDto = null;
				String previousLevel = "L" + (Integer.parseInt(String.valueOf(level.charAt(1))) - 1);
				// checking if the prevous level is AND/OR
				List<SalesOrderTaskStatusDto> salesOrderTaskStatusDtoList = salesOrderTaskStatusRepository
						.getAllTasksFromDecisionSetAndLevel(dataSet, previousLevel);
				// if the previous level is OR
				if (salesOrderTaskStatusDtoList.size() == 1) {

					for (int itemid = 0; itemid < listItem.size(); itemid++) {

						salesOrderItemStatusDto = salesOrderItemStatusRepository.getItemStatusFromDecisionSetAndLevel(dataSet,
								previousLevel, listItem.get(itemid).getSalesItemOrderNo());

						// check for null here
						salesOrderItemStatusDto.setSalesOrderItemNum(listItem.get(itemid).getSalesItemOrderNo());
						// salesOrderItemStatusDto.setSalesOrderItemNum(listItem.get(itemid));
						if (salesOrderItemStatusDto.getItemStatus().equals(DkshStatusConstants.BLOCKED)) {
							salesOrderItemStatusDto.setItemStatus(DkshStatusConstants.BLOCKED);
							salesOrderItemStatusDto.setVisiblity(DkshStatusConstants.VISIBLITY_INACTIVE);
						} else if (salesOrderItemStatusDto.getItemStatus().equals(DkshStatusConstants.ITEM_APPROVE)) {
							salesOrderItemStatusDto.setItemStatus(DkshStatusConstants.BLOCKED);
							salesOrderItemStatusDto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
						} else if (salesOrderItemStatusDto.getItemStatus().equals(DkshStatusConstants.ITEM_REJECT)
								|| salesOrderItemStatusDto.getItemStatus()
										.equals(DkshStatusConstants.ITEM_INDIRECT_REJECT)) {
							salesOrderItemStatusDto.setItemStatus(DkshStatusConstants.ITEM_INDIRECT_REJECT);
							salesOrderItemStatusDto
									.setVisiblity(DkshStatusConstants.VISIBLITY_INACTIVE_INDIRECT_REJECT);
						}
						// check for Rejected in ECCC for OR case
						else if (salesOrderItemStatusDto.getItemStatus()
								.equals(DkshStatusConstants.REJECTED_FROM_ECC)) {
							salesOrderItemStatusDto.setItemStatus(DkshStatusConstants.REJECTED_FROM_ECC);
							// check the visibility
							salesOrderItemStatusDto.setVisiblity(DkshStatusConstants.REJECTED_FROM_ECC);
						} else if (salesOrderItemStatusDto.getItemStatus()
								.equals(DkshStatusConstants.DISPLAY_ONLY_ITEM)) {
							salesOrderItemStatusDto.setItemStatus(DkshStatusConstants.DISPLAY_ONLY_ITEM);
							salesOrderItemStatusDto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
						}

						// setting the foreing key .
						salesOrderItemStatusDto.setTaskStatusSerialId(taskserializedId);
						try {
							serialitemid = salesordertaskstatusdao
									.saveOrUpdateSalesOrderItemStatusSynchronised(salesOrderItemStatusDto);
							// System.err.println("serialitemid" +
							// serialitemid);
						} catch (Exception e) {
							ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",", Level status serial id not registered ").body(null);
							// logger.error("saveOrUpdateSalesOrderItemStatusSynchronised
							// failed " + e);
						}
					}
				}
				// if the previous level is AND
				else {
					CreateReccordForcumilativeStatusOfALLTaskANDType(taskserializedId, dataSet, level);
				}
			}
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).header("message", ", Level status serial id registered ").body(null);
				

	}

	public ResponseEntity<Object> createAndUpdateItemForAnd(String dataSet, List<String> taskserializedIdList, String level) {
		ResponseEntity<Object> responseentity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",
				", Level status serial id not registered ").body(null);

		String serialitemid = "";
		// List<String> listItemId =
		// salesDocItemDao.getItemListByDataSet(dataSet);
		List<SalesDocItemDto> listItem = salesDocItemRepository.getSalesDocItemsByDecisionSetId(dataSet);
		for (int taskid = 0; taskid < taskserializedIdList.size(); taskid++) {

			String taskserializedId = taskserializedIdList.get(taskid);

			if (level.equals("L1")) {
				if (listItem != null) {

					for (int itemid = 0; itemid < listItem.size(); itemid++) {

						ResponseEntity<Object> responseentity1DispayOnly = salesordertaskstatusdao
								.getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(
										listItem.get(itemid).getItemDlvBlock());

						DlvBlockReleaseMapDto releaseMapDto = (DlvBlockReleaseMapDto) responseentity1DispayOnly
								.getBody();

						SalesOrderItemStatusDto salesorderitemstatusdto = new SalesOrderItemStatusDto();

						if (responseentity1DispayOnly.getStatusCode() == HttpStatus.ACCEPTED && releaseMapDto != null
								&& releaseMapDto.getItemLevel() == true && releaseMapDto.getEdit() == false
								&& releaseMapDto.getSpecialClient() == false) {

							salesorderitemstatusdto.setSalesOrderItemNum(listItem.get(itemid).getSalesItemOrderNo());
							salesorderitemstatusdto.setItemStatus(DkshStatusConstants.DISPLAY_ONLY_ITEM);
							salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
							salesorderitemstatusdto.setTaskStatusSerialId(taskserializedId);

						} else {

							salesorderitemstatusdto.setSalesOrderItemNum(listItem.get(itemid).getSalesItemOrderNo());
							salesorderitemstatusdto.setItemStatus(DkshStatusConstants.BLOCKED);
							salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
							salesorderitemstatusdto.setTaskStatusSerialId(taskserializedId);
						}
						try {
							serialitemid = salesordertaskstatusdao
									.saveOrUpdateSalesOrderItemStatusSynchronised(salesorderitemstatusdto);
						} catch (Exception e) {
							// logger.error(" Level status serial id Failed" +
							// e);
							responseentity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",
									e.getMessage()).body(null);
							return responseentity;

						}
						// logger.error("serialitemid" + serialitemid);
					}

				}
			} else {

				if (listItem != null) {

					CreateReccordForcumilativeStatusOfALLTaskANDType(taskserializedId, dataSet, level);

				}
			}
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).header("message" ,", Level status serial id registered ").body(null);
				

	}

	// trigger the approval workflow

	public WorkflowResponseEntity approvalWorkflowTrigger(String salesOrderNo, String requestId, String approver,
			String level, String dataSet, String threshold, String decisionSetAmount, String group,
			String HeaderBlocReas, String soCreatedECC, String country, String customerPo, String requestType,
			String requestCategory, String salesOrderType, String soldToParty, String shipToParty, String division,
			String distributionChannel, String salesOrg, String returnReason) {
		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");
		HttpURLConnection connection1 = null;
		// logger.error("ApprovalWorkflowTriggerInput = " + salesOrderNo +
		// requestId + approver + level + dataSet);
		try {

			// getting XSRF TOKEN
			String xcsrfToken = null;
			List<String> cookies = null;
			HttpContext httpContext = new BasicHttpContext();
			httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
			HttpPost httpPost = null;
			CloseableHttpResponse responseClient = null;
			CloseableHttpClient httpClient = null;
			httpClient = getHTTPClient();

			RequestConfig config = RequestConfig.custom().setConnectTimeout(50 * 1000)
					.setConnectionRequestTimeout(50 * 1000).setSocketTimeout(50 * 1000).build();
			// String url =
			// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
			//Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
			
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			
			String url =Constants.WORKFLOW_REST_BASE_URL+"/v1/workflow-instances";
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", "application/json");
			//String xsrfToken = getXSRFToken(url, httpClient, httpContext);
			if (jwToken != null) {
				//httpPost.addHeader("X-CSRF-Token", xsrfToken); // header

				//String auth = map.get("User") + ":" + map.get("Password");
				//String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
				httpPost.addHeader(AUTHORIZATION, "Bearer " + jwToken);

				String returnType = "";
				if (dataSet.contains("RO")) {
					returnType = "05";
				}

				String payload = "{\"definitionId\":\"approval\",\"context\":{\"salesOrderNo\":\"" + salesOrderNo
						+ "\",\"requestId\":\"" + requestId + "\",\"decisionSetId\":\"" + dataSet + "\",\"approver\":\""
						+ approver + "\",\"level\":\"" + level + "\",\"threshold\":\"" + threshold
						+ "\",\"decisionSetAmount\":\"" + decisionSetAmount + "\",\"group\":\"" + group
						+ "\",\"headerBlocReas\":\"" + HeaderBlocReas + "\",\"soCreatedECC\":\"" + soCreatedECC
						+ "\",\"country\":\"" + country + "\",\"customerPo\":\"" + customerPo + "\",\"requestType\":\""
						+ requestType + "\",\"requestCategory\":\"" + requestCategory + "\",\"salesOrderType\":\""
						+ salesOrderType + "\",\"soldToParty\":\"" + soldToParty + "\",\"shipToParty\":\"" + shipToParty
						+ "\",\"division\":\"" + division + "\",\"distributionChannel\":\"" + distributionChannel
						+ "\",\"salesOrg\":\"" + salesOrg + "\",\"returnReason\":\"" + returnReason
						+ "\",\"returnType\":\"" + returnType + "\"}}";
				// System.err.println("Workflow Payload :" + payload);
				StringEntity workflowPayload = new StringEntity(payload);
				httpPost.setEntity(workflowPayload);
				httpPost.setConfig(config);
				responseClient = httpClient.execute(httpPost);
				// process response here
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				InputStream inputStream = responseClient.getEntity().getContent();
				byte[] data = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(data)) > 0) {
					bytes.write(data, 0, length);
				}
				String respBody = new String(bytes.toByteArray(), "UTF-8");
				System.out.println(respBody);

				JsonObject jsonObject = new JsonParser().parse(respBody).getAsJsonObject();
				response.setId("" + (jsonObject.get("id")));
				response.setMessage("Trigger Succes");
				response.setResponseStatusCode(200);
				response.setStatus(ResponseStatus.SUCCESS);
				// clean-up sessions
				if (httpPost != null) {
					httpPost.releaseConnection();
				}
				if (response != null) {
					responseClient.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} else {
				response.setMessage("trigger failure due to no XSCRF-Token not gernerated ");
			}
		} catch (Exception e) {
			// System.err.println("Trigger FAILURE Approval Workflow " +
			// e.getMessage());
			response.setMessage("Trigger FAILURE EXCEPTION Approval Worklow" + e.getMessage());
			response.setResponseStatusCode(500);
			response.setStatus(ResponseStatus.FAILED);
			return response;
		}
		return response;
	}

	private String getDataFromStream(InputStream stream) throws IOException {
		StringBuilder dataBuffer = new StringBuilder();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";
		while ((data = inStream.readLine()) != null) {
			dataBuffer.append(data);
		}
		inStream.close();
		return dataBuffer.toString();
	}

	// get the WOrkflow taskId for the trigger approval workflow
	@SuppressWarnings({ "resource" })

	public WorkflowResponseEntity getInstanceIdByWorkflowInstanceId(WorkflowResponseEntity workflowresponseentity)
			throws IOException {

		// System.err.println("workflowresponseentity input = " +
		// workflowresponseentity);

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "FAILED TO GET ID  ",
				ResponseStatus.FAILED, null, null, "");

		HttpClient client = null;
		InputStream instream = null;
		// System.err.println("line 542 inside get id");
		if (workflowresponseentity.getResponseStatusCode() == 200) {
			try {
				client = new DefaultHttpClient();
				Map<String, Object> responsemessage = workflowresponseentity.getResponseMessage();

				JSONObject respo = getJsonFromMap(responsemessage);

				String id = respo.getString("id");
				// logger.error("line 552 inside get id");

				//Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER_ID);
				String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
				String url = Constants.WORKFLOW_REST_BASE_URL;

				String trimmed = url.substring(8);

				// System.err.println("URL Trimmed" + trimmed);

				//String userpass = map.get("User") + ":" + map.get("Password");
				//String encoding = javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				// add the destination url wth a common name .......
				URIBuilder builder = new URIBuilder();
				builder.setScheme("https").setHost(trimmed).setPath("/v1/task-instances")
						.setParameter("workflowInstanceId", id);
				URI uri = builder.build();
				HttpGet request = new HttpGet(uri);

				request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwToken);
				HttpResponse httpresponse = null;
				String result = "";

				// logger.error("line 578 inside get id request " + request);
				httpresponse = client.execute(request);
				HttpEntity entity = httpresponse.getEntity();
				if (entity != null) {
					instream = entity.getContent();
					result = convertStreamToString(instream);
					JSONArray responseResult = new JSONArray(result);
					// logger.error("line 592 inside get id" + responseResult);

					// logger.error("StatusCode " +
					// httpresponse.getStatusLine().getStatusCode());
					if (httpresponse.getStatusLine().getStatusCode() != 500) {
						if (httpresponse.getStatusLine().getStatusCode() != 429) {
							if (httpresponse.getStatusLine().getStatusCode() == 200) {
								// logger.error("RESPONSE: " + result);
								response.setMessage("Succes" + responseResult.toString());
								response.setResponseStatusCode(httpresponse.getStatusLine().getStatusCode());
								response.setStatus(ResponseStatus.SUCCESS);
								JSONObject responsejson = (JSONObject) responseResult.get(0);
								response.setId((responsejson.getString("id")));
								// logger.error(" get id from responsejson " +
								// responsejson.getString("id"));
							}

						} else {
							return new WorkflowResponseEntity("", 200,
									"Too many requests. You’ve reached the usage limits that are configured for your tenant. Reduce the number of requests. ",
									ResponseStatus.FAILED, null, null, "");

						}
					} else {
						return new WorkflowResponseEntity("", 200,
								"Internal server error. The operation you requested led to an error during execution. ",
								ResponseStatus.FAILED, null, null, "");
					}
				}
				// Headers
				org.apache.http.Header[] headers = httpresponse.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					// System.err.println(headers[i]);
				}
			} catch (Exception e) {
				// System.err.println("Trigger FAILURE For get Id " +
				// e.getMessage());
				response.setMessage("Trigger FAILURE EXCEPTION for get id	" + e.getMessage());
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} finally {
				try {
					if (instream != null) {
						instream.close();
					}
				}

				catch (Exception e) {
					// System.err.print("Closing HttpClient Exception : " + e);
				}

			}
		}
		// System.err.println("workflowresponseentity output = " + response);
		return response;

	}

	@SuppressWarnings({ "resource" })
	public WorkflowResponseEntity getInstanceIdByWorkflowInstanceId(List<String> workflowInstaceId) throws IOException {
		// System.err.println("line 538 inside get id");
		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "FAILED TO GET ID  ",
				ResponseStatus.FAILED, null, null, "");
		Map<String, String> mapOfTaskId = new HashMap<String, String>();
		HttpClient client = null;
		InputStream instream = null;
		// System.err.println("line 542 inside get id");
		if (!workflowInstaceId.isEmpty()) {
			try {
				for (String id : workflowInstaceId) {
					client = new DefaultHttpClient();
					//Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER_ID);
			String jwToken		 = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
					//String userpass = map.get("User") + ":" + map.get("Password");
					//String encoding = javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

					// iteratign over the workflowInstanceI
					String url = Constants.WORKFLOW_REST_BASE_URL;
					String trimmed = url.substring(8);
					// add the destination url wth a common name .......
					URIBuilder builder = new URIBuilder();
					builder.setScheme("https").setHost(trimmed).setPath("/v1/task-instances")
							.setParameter("workflowInstanceId", id);
					URI uri = builder.build();
					HttpGet request = new HttpGet(uri);
					request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwToken);
					HttpResponse httpresponse = null;
					String result = "";

					// logger.error("line 578 inside get id request " +
					// request);
					httpresponse = client.execute(request);
					HttpEntity entity = httpresponse.getEntity();
					if (entity != null) {
						instream = entity.getContent();
						result = convertStreamToString(instream);
						JSONArray responseResult = new JSONArray(result);
						// logger.error("line 592 inside get id" +
						// responseResult);

						// logger.error("StatusCode " +
						// httpresponse.getStatusLine().getStatusCode());
						if (httpresponse.getStatusLine().getStatusCode() != 500) {
							if (httpresponse.getStatusLine().getStatusCode() != 429) {
								if (httpresponse.getStatusLine().getStatusCode() == 200) {
									// logger.error("RESPONSE: " + result);
									response.setMessage("Succes" + responseResult.toString());
									response.setResponseStatusCode(httpresponse.getStatusLine().getStatusCode());
									response.setStatus(ResponseStatus.SUCCESS);
									JSONObject responsejson = (JSONObject) responseResult.get(0);
									response.setId((responsejson.getString("id")));
									if (response.getId() != null) {

										mapOfTaskId.put(id, response.getId());

									}
									// logger.error(" get id from responsejson "
									// + responsejson.getString("id"));
								}

							} else {

								

							}
						} else {

							throw new RuntimeException(
									"Internal server error. The operation you requested led to an error during execution. ");
							/*
							 * return new WorkflowResponseEntity("", 200,
							 * "Internal server error. The operation you requested led to an error during execution. "
							 * , ResponseStatus.FAILED, null, null, "");
							 */
						}
					}
					// Headers
					org.apache.http.Header[] headers = httpresponse.getAllHeaders();
					for (int i = 0; i < headers.length; i++) {
						// System.err.println(headers[i]);
					}
				}
			} catch (Exception e) {
				// System.err.println("Trigger FAILURE For get Id " +
				// e.getMessage());
				response.setMessage("Trigger FAILURE EXCEPTION for get id	" + e.getMessage());
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} finally {
				try {
					if (instream != null) {
						instream.close();
					}
				}

				catch (Exception e) {
					// System.err.print("Closing HttpClient Exception : " + e);
				}

			}
		}

		response.setData(mapOfTaskId);
		// System.err.println("workflowresponseentity output map response = " +
		// response);
		return response;

	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonFromMap(Map<String, Object> map) throws JSONException {
		JSONObject jsonData = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				value = getJsonFromMap((Map<String, Object>) value);
			}
			jsonData.put(key, value);
		}
		return jsonData;
	}

	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// logger.error("contverStreamToString Output " + e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// logger.error("contverStreamToString Output " + e +
				// sb.toString());
			}
		}

		return sb.toString();
	}

	// triggers approval workflow for the ANd condtion
	@SuppressWarnings("null")
	public List<String> approvalWorkflowTriggerAnd(String salesOrderNo, String requestId, List<String> approvers,
			String level, String dataSet, String threshold, String decisionSetAmount, String group,
			String headerBlocReas, String soCreatedECC, String country, String customerPo, String requestType,
			String requestCategory, String salesOrderType, String soldToParty, String shipToParty, String division,
			String distributionChannel, String salesOrg, String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");
		HttpURLConnection connection1 = null;
		System.err.println("ApprovalWorkflowTriggerInput = " + salesOrderNo + requestId + approvers + level + dataSet
				+ headerBlocReas);

		List<String> WorkflowTaskIds = new ArrayList<>();

		if (!approvers.isEmpty()) {
			try {
				// getting XSRF TOKEN
				for (int iterateAprroverList = 0; iterateAprroverList < approvers.size(); iterateAprroverList++) {

					String approver = approvers.get(iterateAprroverList);

					WorkflowTaskIds.add(httpPostClient(salesOrderNo, requestId, approver, level, dataSet, threshold,
							decisionSetAmount, group, headerBlocReas, soCreatedECC, country, customerPo, requestType,
							requestCategory, salesOrderType, soldToParty, shipToParty, division, distributionChannel,
							salesOrg, returnReason));
				}

				return WorkflowTaskIds;
			} catch (Exception e) {
				// System.err.println("workflowResponseEntity ApprovalWorkflow
				// output Exception" + e);

				@SuppressWarnings("unchecked")
				List<String> failedException = (List<String>) e;

				return failedException;
			}
		}
		// System.err.println("workflowResponseEntity ApprovalWorkflow output "
		// + WorkflowTaskIds);
		return WorkflowTaskIds;

	}

	// checks if the next level need to be triggered or not based on the
	// previous level item status

	private static String getXSRFToken(String requestURL, CloseableHttpClient client, HttpContext httpContext)
			throws URISyntaxException {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination("rulestokenWorflowTrigger");
			// System.err.println("map for Rules_V2 : " + map.toString());

			httpGet = new HttpGet((String) map.get("URL"));
			// setting
			String auth = map.get("User") + ":" + map.get("Password");
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
			httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);

			httpGet.addHeader("X-CSRF-Token", "Fetch");
			response = client.execute(httpGet);
			Header xsrfTokenheader = response.getFirstHeader("X-CSRF-Token");
			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xsrfToken;
	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();

//		
//		 * RequestConfig.Builder requestConfig = RequestConfig.custom();
//		 * requestConfig.setConnectTimeout(50 * 1000);
//		 * requestConfig.setConnectionRequestTimeout(50 * 1000);
//		 * requestConfig.setSocketTimeout(50 * 1000);
//		 */

		CloseableHttpClient httpClient = clientBuilder.disableAutomaticRetries().build();
		return httpClient;
	}

	public WorkflowResponseEntity approvalworkflowTriggeringAndUpdateTables(String salesOrderNo, String requestId,
			String approver, String level, String dataSet, String threshold, String decisionSetAmount,
			String approvalType, String headerBlocReas, String soCreatedECC, String country, String customerPo,
			String requestType, String requestCategory, String salesOrderType, String soldToParty, String shipToParty,
			String division, String distributionChannel, String salesOrg, String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "FAILED TO GET ID  ",
				ResponseStatus.FAILED, null, null, "");

		// calling approverMethod based on the approverType
		if (approvalType.equals("OR")) {
			System.err.println("inside approver or method");
			response = approvalworkflowTriggeringAndUpdateTable(salesOrderNo, requestId, approver, level, dataSet,
					threshold, decisionSetAmount, headerBlocReas, soCreatedECC, country, customerPo, requestType,
					requestCategory, salesOrderType, soldToParty, shipToParty, division, distributionChannel, salesOrg,
					returnReason);
			return response;

		} else if (approvalType.equals("AND"))

		{
			System.err.println("inside approver end method");
			try {
				response = ApprovalWorkflowTriggerAndUpdateTableOnAnd(salesOrderNo, requestId, approver, level, dataSet,
						threshold, decisionSetAmount, headerBlocReas, soCreatedECC, country, customerPo, requestType,
						requestCategory, salesOrderType, soldToParty, shipToParty, division, distributionChannel,
						salesOrg, returnReason);
				return response;
			} catch (IOException e) {

				System.err.println(" inside approver and method IOException =" + e);
			}

		} else {

			System.err.println("inside approver or2 method");
			response = approvalworkflowTriggeringAndUpdateTable(salesOrderNo, requestId, approver, level, dataSet,
					threshold, decisionSetAmount, headerBlocReas, soCreatedECC, country, customerPo, requestType,
					requestCategory, salesOrderType, soldToParty, shipToParty, division, distributionChannel, salesOrg,
					returnReason);
			return response;

		}

		return response;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<Object> checkForNextLevelTrigger(String dataSet, String level) {

		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header(
				"message", " Check Next level Triggere failed ").body(null);

		// System.err.println("dataSet and level " + dataSet + level);

		boolean checkIfApproved = false;

		if (!HelperClass.checkString(dataSet) && !HelperClass.checkString(level)) {
			// get the soLevelstatus by dataSet and Level
			SalesOrderLevelStatusDto salesOrderLevelStatusDto = salesorderlevelstausdao
					.getSalesOrderLevelStatusByDecisionSetAndLevelSession(dataSet, level);

			// System.err.println("salesOrderLevelStatusDto " +
			// salesOrderLevelStatusDto.toString());

			if (salesOrderLevelStatusDto != null) {

				SalesOrderTaskStatusDto salesOrderTaskStatusDto = salesOrderTaskStatusRepository
						.getAllTasksFromLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
				// System.err.println("salesOrderTaskStatusDto " +
				// salesOrderTaskStatusDto);

				if (salesOrderTaskStatusDto != null) {

					List<SalesOrderItemStatusDto> salesOrderItemStatusDtoList = salesOrderItemStatusRepository
							.getItemStatusDataUsingTaskSerialId(salesOrderTaskStatusDto.getTaskStatusSerialId());

					// System.err.println("salesOrderItemStatusDtoList " +
					// salesOrderItemStatusDtoList.toString());
					int itemReject = 0;

					if (!salesOrderItemStatusDtoList.isEmpty()) {

						SalesOrderItemStatusDto salesOrderItemstatusdto = null;

						for (int item = 0; item < salesOrderItemStatusDtoList.size(); item++) {
							salesOrderItemstatusdto = salesOrderItemStatusDtoList.get(item);
							// System.err.println("salesOrderItemStatusDtoinisde
							// if" + salesOrderItemstatusdto);
							if (salesOrderItemstatusdto.getItemStatus().equals(StatusConstants.ITEM_APPROVE)) {

								// System.err.println("Inside the if statemnt
								// ");
								checkIfApproved = true;
//								responseEntity.setMessage("Success");
//								responseEntity.setStatus(ResponseStatus.SUCCESS);
//								responseEntity.setStatusCode(HttpStatus.ACCEPTED);
								// if atleast one item is approved ,than return
								// true
								return ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "Success").body(null);
							} else {
								// Check while if remaining items are approvred
								// or not , if all rejected than return false

								// System.err.println("salesOrderItemStatusDtoList"
								// + salesOrderItemStatusDtoList.size()
								// + "item" + item);
								itemReject = item + 1;
								if (salesOrderItemStatusDtoList.size() == itemReject) {

									List<SalesOrderLevelStatusDto> salesOrderLevelStatusDtoList = salesorderlevelstausdao
											.getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(dataSet, level);

									if (!salesOrderLevelStatusDtoList.isEmpty()) {

										responseEntity = updateLevelStatusAbandond(salesOrderLevelStatusDtoList);
										return ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "All the item are rejected by user and further Level Abondened").body("All the item are rejected by user and further Level Abondened");
												

									} else {
										return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "salesOrderLevelStatusDtoList is null").body("salesOrderLevelStatusDtoList is null");
												
									}
								}

							}
						}
					} else {
						
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "salesOrderItemStatusDtoList is null").body("salesOrderItemStatusDtoList is null");
						
					}
				} else {
					
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "salesOrderTaskStatusDto is null").body("salesOrderTaskStatusDto is null");
				}
			} else {

				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "salesOrderLevelStatusDto is null").body("salesOrderLevelStatusDto is null");
			}
		} else {

			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "salesOrderLevelStatusDto is null").body("salesOrderLevelStatusDto is null");
		}

		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "at the end check next trigger fail").body("at the end check next trigger fail");
	}

	public ResponseEntity<Object> updateLevelStatusAbandond(List<SalesOrderLevelStatusDto> saleOrderLevelStatusDoList) {
		ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "Failed to update SalesOrderLevelStatus").body("Failed to update SalesOrderLevelStatus");
		
		if (!saleOrderLevelStatusDoList.isEmpty()) {
			SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;

			for (int iteratelevellist = 0; iteratelevellist < saleOrderLevelStatusDoList.size(); iteratelevellist++) {

				salesOrderLevelStatusDto = saleOrderLevelStatusDoList.get(iteratelevellist);

				salesOrderLevelStatusDto.setLevelStatus(DkshStatusConstants.LEVEL_ABANDON);

				try {
					salesordertaskstatusdao.saveOrUpdateSalesOrderLevelStatusSynchronized(salesOrderLevelStatusDto);

				} catch (Exception e) {

					
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "Failed to update SalesOrderLevelStatus").body("Failed to update SalesOrderLevelStatus");
				}
			}
		} else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message",  "SalesOrderLevelStatusList is null").body("SalesOrderLevelStatusList is null");
		}
		return ResponseEntity.status(HttpStatus.OK).header("message",  "Success").body("Success");

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<Object> checkForNextLevelTriggerAnd(String dataSet, String level) {

		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("message","Check Next level Triggere failed ").body("Check Next level Triggere failed ");
		
		
		// System.err.println("dataSet and level " + dataSet + level);
		boolean checkIfApproved = false;
		responseEntity = salesordertaskstatusdao.getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(dataSet,
				level);

		Map<String, Integer> mapToEvaluateApprove = (Map<String, Integer>) responseEntity.getBody();

		if (mapToEvaluateApprove.containsValue(StatusConstants.ITEM_APPROVE)) {
			checkIfApproved = true;
//			responseEntity.setMessage("Success");
//			responseEntity.setStatus(ResponseStatus.SUCCESS);
//			responseEntity.setStatusCode(HttpStatus.ACCEPTED);

			return ResponseEntity.status(HttpStatus.ACCEPTED).header("message","Success").body(checkIfApproved);
		} else {
			checkIfApproved = false;
			List<SalesOrderLevelStatusDto> salesOrderLevelStatusDtoList = salesorderlevelstausdao
					.getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(dataSet, level);

			if (!salesOrderLevelStatusDtoList.isEmpty()) {

				responseEntity = updateLevelStatusAbandond(salesOrderLevelStatusDtoList);
//				responseEntity.setMessage);
				return ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "All the item are rejected by user and further Level Abondened").body(responseEntity);		
				}
			
			return responseEntity;
		}

	}

	// if the Count of the salesOrderTaskStatusDto is more than one than , its a
	// AND typeApprover.
	public String checkIfPreviousLevelApproverType(String dataSet, String level) {

		String previousLevel = "L" + (Integer.parseInt(String.valueOf(level.charAt(1))) - 1);

		// get all the salesOrderLevelStatusDto by prevousLevel and DataSet to
		// check the AprroverType .

		List<SalesOrderTaskStatusDto> salesOrderTaskStatusDtoList = salesOrderTaskStatusRepository
				.getAllTasksFromDecisionSetAndLevel(dataSet, previousLevel);

		if (!salesOrderTaskStatusDtoList.isEmpty() && salesOrderTaskStatusDtoList.size() > 1) {
			return "AND";
		} else {

			return "OR";
		}

	}

	// to get a cumilative status of the AND type all the task
	public ResponseEntity CreateReccordForcumilativeStatusOfALLTaskANDType(String taskserializedId, String DataSet,
			String level) {

		ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", "Check Next level Triggere failed").body("Check Next level Triggere failed");

		String previousLevel = "L" + (Integer.parseInt(String.valueOf(level.charAt(1))) - 1);

		responseEntity = salesordertaskstatusdao.getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(DataSet,
				previousLevel);
		@SuppressWarnings("unchecked")
		Map<String, Integer> cumilativeItemStatus = (Map<String, Integer>) responseEntity.getBody();
		Set<String> itemIdSet = cumilativeItemStatus.keySet();
		for (String itemId : itemIdSet) {
			SalesOrderItemStatusDto salesOrderItemStatusDto = new SalesOrderItemStatusDto();
			salesOrderItemStatusDto.setSalesOrderItemNum(itemId);
			if (StatusConstants.ITEM_APPROVE.equals(cumilativeItemStatus.get(itemId))) {
				salesOrderItemStatusDto.setItemStatus(StatusConstants.BLOCKED);
				salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_ACTIVE);
			} else if (StatusConstants.BLOCKED.equals(cumilativeItemStatus.get(itemId))) {
				salesOrderItemStatusDto.setItemStatus(StatusConstants.BLOCKED);
				salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_INACTIVE);
			} else if (StatusConstants.ITEM_REJECT.equals(cumilativeItemStatus.get(itemId))
					|| StatusConstants.ITEM_INDIRECT_REJECT.equals(cumilativeItemStatus.get(itemId))) {
				salesOrderItemStatusDto.setItemStatus(StatusConstants.ITEM_INDIRECT_REJECT);
				salesOrderItemStatusDto.setVisiblity(DkshStatusConstants.VISIBLITY_INACTIVE_INDIRECT_REJECT);
			}
			// Rjected from ECCC get cumilative from or to and
			else if (StatusConstants.REJECTED_FROM_ECC.equals(cumilativeItemStatus.get(itemId))) {
				salesOrderItemStatusDto.setItemStatus(StatusConstants.REJECTED_FROM_ECC);
				salesOrderItemStatusDto.setVisiblity(StatusConstants.REJECTED_FROM_ECC);
			} else if (DkshStatusConstants.DISPLAY_ONLY_ITEM.equals(cumilativeItemStatus.get(itemId))) {
				salesOrderItemStatusDto.setItemStatus(StatusConstants.DISPLAY_ONLY_ITEM);
				salesOrderItemStatusDto.setVisiblity(StatusConstants.VISIBLITY_ACTIVE);
			}
			salesOrderItemStatusDto.setTaskStatusSerialId(taskserializedId);

			try {
				String serialitemid = salesordertaskstatusdao
						.saveOrUpdateSalesOrderItemStatusSynchronised(salesOrderItemStatusDto);
				// System.err.println("serialitemid" + serialitemid);
			} catch (Exception e) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", "Level status serial id not registered "+e).body("Level status serial id not registered "+e);
				// logger.error("saveOrUpdateSalesOrderItemStatusSynchronised
				// failed " + e);

			}

		}

		return responseEntity;

	}

	public WorkflowResponseEntity workflowTaskInstanceIdByDecisionSetAndLevel(String DataSet) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");

		List<String> workflowInstanceIdList = salesOrderTaskStatusRepository.getTasksIdFromDecisionSetAndLevel(DataSet);

		System.err.println(" workflowInstanceIdList :" + workflowInstanceIdList);
		if (!workflowInstanceIdList.isEmpty()) {

			try {
				response = getInstanceIdByWorkflowInstanceId(workflowInstanceIdList);
			} catch (IOException e) {

				return new WorkflowResponseEntity("", 200, TRIGGERFAILUER + " inside catch block " + e,
						ResponseStatus.FAILED, null, null, "");
			}
		} else {
			return new WorkflowResponseEntity("", 200, "no workflowInstanceId found to complete the approval workflow",
					ResponseStatus.SUCCESS, null, null, "");
		}

		return response;

	}

	public ResponseEntity checkNextLevelTriggerForAND_ORR(String dataSet, String level, String approvalType) {

		ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", "Check Next level Triggere failed").body("Check Next level Triggere failed");
			

		if (approvalType.equalsIgnoreCase("OR") || approvalType.equalsIgnoreCase("")) {
			responseEntity = checkForNextLevelTrigger(dataSet, level);

		}

		else {

			responseEntity = checkForNextLevelTriggerAnd(dataSet, level);
		}

		return responseEntity;

	}

	public String httpPostClient(String salesOrderNo, String requestId, String approver, String level, String dataSet,
			String threshold, String decisionSetAmount, String group, String headerBlocReas, String soCreatedECC,
			String country, String customerPo, String requestType, String requestCategory, String salesOrderType,
			String soldToParty, String shipToParty, String division, String distributionChannel, String salesOrg,
			String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, TRIGGERFAILUER, ResponseStatus.FAILED,
				null, null, "");

		// System.err.println("ApprovalWorkflowTriggerInput = " + salesOrderNo +
		// requestId + level + dataSet
		// + headerBlocReas);
		String Id = null;
		if (!approver.isEmpty()) {
			try {
				// getting XSRF TOKEN

				//String xcsrfToken = null;
				//List<String> cookies = null;
				//HttpContext httpContext = new BasicHttpContext();
				//httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
				HttpPost httpPost = null;
				CloseableHttpResponse responseClient = null;
				CloseableHttpClient httpClient = null;
				httpClient = getHTTPClient();
				// String url =
				// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
				//Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
				   String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
				approver = approver.replaceAll("[\\p{Ps}\\p{Pe}]", "");
				String url = Constants.WORKFLOW_REST_BASE_URL+"/v1/workflow-instances";
				httpPost = new HttpPost(url);
				httpPost.addHeader("Content-type", "application/json");
				//String xsrfToken = getXSRFToken(url, httpClient, httpContext);
				if (jwToken != null) {
					httpPost.addHeader("X-CSRF-Token", jwToken); // header
					//String auth = map.get("User") + ":" + map.get("Password");
					//String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
					httpPost.addHeader(AUTHORIZATION, "Bearer " + jwToken);

					String returnType = "";
					if (dataSet.contains("RO")) {
						returnType = "05";
					}

					String payload = "{\"definitionId\":\"approval\",\"context\":{\"salesOrderNo\":\"" + salesOrderNo
							+ "\",\"requestId\":\"" + requestId + "\",\"decisionSetId\":\"" + dataSet
							+ "\",\"approver\":\"" + approver + "\",\"level\":\"" + level + "\",\"threshold\":\""
							+ threshold + "\",\"decisionSetAmount\":\"" + decisionSetAmount + "\",\"group\":\"" + group
							+ "\",\"headerBlocReas\":\"" + headerBlocReas + "\",\"soCreatedECC\":\"" + soCreatedECC
							+ "\",\"country\":\"" + country + "\",\"customerPo\":\"" + customerPo
							+ "\",\"requestType\":\"" + requestType + "\",\"requestCategory\":\"" + requestCategory
							+ "\",\"salesOrderType\":\"" + salesOrderType + "\",\"soldToParty\":\"" + soldToParty
							+ "\",\"shipToParty\":\"" + shipToParty + "\",\"division\":\"" + division
							+ "\",\"distributionChannel\":\"" + distributionChannel + "\",\"salesOrg\":\"" + salesOrg
							+ "\",\"returnReason\":\"" + returnReason + "\",\"returnType\":\"" + returnType + "\"}}";
					
					// System.err.println("Workflow Payload :" + payload);
					StringEntity workflowPayload = new StringEntity(payload);
					httpPost.setEntity(workflowPayload);
					responseClient = httpClient.execute(httpPost);

					// process response here
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					InputStream inputStream = responseClient.getEntity().getContent();
					byte[] data = new byte[1024];
					int length = 0;
					while ((length = inputStream.read(data)) > 0) {
						bytes.write(data, 0, length);
					}
					String respBody = new String(bytes.toByteArray(), "UTF-8");
					// System.out.println(respBody);

					JsonObject jsonObject = new JsonParser().parse(respBody).getAsJsonObject();
					response.setId("" + (jsonObject.get("id")));
					Id = response.getId();
					Id = Id.replaceAll("^\"|\"$", "");

					response.setMessage("Trigger Succes");
					response.setResponseStatusCode(200);
					response.setStatus(ResponseStatus.SUCCESS);
					// clean-up sessions
					if (httpPost != null) {
						httpPost.releaseConnection();
					}
					if (response != null) {
						responseClient.close();
					}
					if (httpClient != null) {
						httpClient.close();
					}

					return Id;
				}

				else {
					response.setMessage("trigger failure due to no XSCRF-Token not gernerated ");
				}

			} catch (Exception e) {
				// System.err.println("Trigger FAILURE Approval Workflow " +
				// e.getMessage());

				return "no Id due to exception" + e.getMessage();
			}
		} else {

			// System.err.println("Trigger FAILURE Approval Workflow NOapprover
			// found in else ");
			return "no Id due to exception";

		}
		// System.err.println("workflowResponseEntity ApprovalWorkflow output "
		// + Id);
		return Id;

	}

	// Bussiness key will be SalesOrder Number and DecisionSet Number
	public ResponseEntity closeAllWorkflowsByBussinessKey(String bussinessKey) {
		try {
			ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).header("message", "Worklfow Failed To CloseCompeletly").body("Worklfow Failed To CloseCompeletly");
				
			List<String> dsNumber = salesDocItemRepository.getDSBySalesHeaderID(bussinessKey);

			System.err.println("dsNumbers " + dsNumber);
			List<String> listWorkflowResponse = null;

			dsNumber.add(bussinessKey);
			for (int i = 0; i < dsNumber.size(); i++) {
				System.err.println("dsNumberInList " + dsNumber.get(i));

				if (dsNumber.get(i) != null) {
					// get all the workflowInstanceId by bussinessKey.
					ResponseEntity response = new HelperClass().getWorkflowInstanceUsingOauthClient(dsNumber.get(i));
					System.err.println("responseEntity WokrlfowIds " + response);

					if (response.getStatusCode().value() == HttpStatus.OK.value()) {
						// get all the response and form the list of the
						// workflowInstanceId
						listWorkflowResponse = new HelperClass().convertResponseToDto(response);
						System.err.println("workflowResponseDto " + listWorkflowResponse);
						if (listWorkflowResponse != null && !listWorkflowResponse.isEmpty()) {
							// call the executor framework to update task status
							// to
							// cancel workfow api
							listWorkflowResponse.stream().forEach(e -> {
								System.err.println("Workflow Instance Id : " + e);
								ResponseEntity responses = new HelperClass().cancellingWorkflowUsingOauthClient(e);
								System.err.println("responseOfCancelling " + responses);
							});

						}
						responseEntity = ResponseEntity.status(HttpStatus.OK).header("message", "Worklfow closed Compeletly").body("Worklfow closed Compeletly");
								
					} else {
						responseEntity = ResponseEntity.status(HttpStatus.OK).header("message", "Worklfow Failed To Close Compeletly").body(response);
								
					}

				}
			}
			return responseEntity;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "failed").body(e);
					
		}

	}
	
	
	// a generic method to do httpRequest 
	// input parameter  - definition id , context, GET/POST 
	
	public ResponseEntity httpRequest(String definitionId,String payload,String requestType,String requestUrl) throws URISyntaxException, IOException{
		 ResponseEntity  response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Failed").body(null);
					
		
	
		HttpResponse httpResponse = null;
		HttpClient client = HttpClientBuilder.create().build();
		String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
		try {

		if(requestType.equals("GET")){
			HttpGet request = new HttpGet(Constants.WORKFLOW_REST_BASE_URL+requestUrl);
		httpResponse  = client.execute(request);
		response = ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "SUCCESSFUL").body(httpResponse);
					
		}
		if(requestType.equals("POST")){
			 HttpPost request = new HttpPost(Constants.WORKFLOW_REST_BASE_URL+requestUrl);
			request.addHeader("Content-type", "application/json");
			request.addHeader(AUTHORIZATION, "Bearer " + jwToken);
			StringEntity workflowPayload = new StringEntity(payload);
		      request.setEntity(workflowPayload);
		      httpResponse = client.execute(request);
		      response =   ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "SUCCESSFUL").body(httpResponse);
						
		}
		if(requestType.equals("PATCH")){
			HttpPatch request = new HttpPatch(Constants.WORKFLOW_REST_BASE_URL+requestUrl);
			request.addHeader("Content-type", "application/json");
			request.addHeader(AUTHORIZATION, "Bearer " + jwToken);
			StringEntity workflowPayload = new StringEntity(payload);
		      request.setEntity(workflowPayload);
		      httpResponse =  client.execute(request);
		      response = ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "SUCCESSFUL").body(httpResponse);
						
		}
		if(requestType.equals("DELETE")){
			HttpDelete request =  new HttpDelete(Constants.WORKFLOW_REST_BASE_URL+requestUrl);
			request.addHeader("Content-type", "application/json");
			request.addHeader(AUTHORIZATION, "Bearer " + jwToken);
			httpResponse = client.execute(request);
			response =	ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "SUCCESSFUL").body(httpResponse);
					
		}
		
		return response;
		} catch (Exception e ){
			
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Failed").body(e);
						
			 
		}
	}
	
	
	
	// get jwtToken  get method for authentication 
	
	
}

