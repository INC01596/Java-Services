package com.incture.cherrywork.services.new_workflow;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.DkshConstants;
import com.incture.cherrywork.WConstants.DkshStatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.workflow.repo.ISalesOrderLevelStatusRepository;

@SuppressWarnings("deprecation")
@Transactional
@Service
public class WorkflowTriggerImpl implements WorkflowTrigger {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	private SalesOrderTaskStatusService salesOrderTaskStatusService;
//
//	@Autowired
//	private SalesDocItemDao salesDocItemDao;

	@Autowired
	private IWorkflowServices salesordertaskstatusdao;

	@Autowired
	private ISalesOrderLevelStatusRepository salesorderlevelstausdao;

//	@Autowired
//	private SalesOrderItemStatusDao salesOrderItemStatusDao;

	public WorkflowResponseEntity AprrovalWorkflowTrigger(String salesOrderNo, String requestId, String approver,
			String level, String dataSet) {

		SalesOrderTaskStatusDto salesOrderTaskStatusDto = new SalesOrderTaskStatusDto();

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200,
				"Trigger FAILURE no poper dataset and level ", ResponseStatus.FAILED, null, null, "");
		HttpURLConnection connection1 = null;

		if (level != null && dataSet != null) {

			SalesOrderLevelStatusDto salesOrderLevelStatusDto = salesorderlevelstausdao
					.getSalesOrderLevelStatusByDecisionSetAndLevel(dataSet, level);

			try {
				salesOrderTaskStatusDto.setLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
				System.err.println(" levelserialid" + salesOrderTaskStatusDto.getLevelStatusSerialId());
				salesOrderTaskStatusDto.setApprover(approver);

				salesOrderTaskStatusDto.setTaskStatus(DkshStatusConstants.TASK_NEW);

				salesordertaskstatusdao.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				// getting XSRF TOKEN
				String xcsrfToken = null;
				List<String> cookies = null;

				Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
				String url = "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
				URL urlObj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
				String userpass = map.get("User") + ":" + map.get("Password");
				String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Authorization", auth);
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				connection.setRequestProperty("X-CSRF-Token", "Fetch");
				connection.connect();
				System.err.println("Response Code0:" + connection.getResponseCode());
				if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
					// Triggering the Wrofklow by settign payload and XSRF
					// token.

					connection1 = (HttpURLConnection) urlObj.openConnection();
					connection1.setRequestMethod("POST");
					xcsrfToken = connection.getHeaderField("X-CSRF-Token");
					cookies = connection.getHeaderFields().get("Set-Cookie");
					System.err.println("XSRF Token" + xcsrfToken);
					// SET COOKIES
					for (String cookie : cookies) {
						String tmp = cookie.split(";", 2)[0];
						connection1.addRequestProperty("Cookie", tmp);
					}

					connection1.setRequestProperty("Authorization", auth);
					connection1.setRequestProperty("x-csrf-token", xcsrfToken);
					connection1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
					connection1.setRequestProperty("Accept", "application/json");
					connection1.setRequestProperty("DataServiceVersion", "2.0");
					connection1.setRequestProperty("X-Requested-With", "XMLHttpRequest");
					connection1.setRequestProperty("Accept-Encoding", "gzip, deflate");
					connection1.setRequestProperty("Accept-Charset", "UTF-8");
					connection1.setDoInput(true);
					connection1.setDoOutput(true);
					connection1.setUseCaches(false);
					/*
					 * String payload = "{  \"context\": {\"obligationId\": \""+
					 * contextDto.getObligationId() + "\",\"entityId\": \""+
					 * contextDto.getEntityId() + "\",\"entityName\": \""+
					 * contextDto.getEntityName() + "\",\"obligationType\": \""+
					 * contextDto.getObligationType() +
					 * "\",\"obligationYear\": \""+
					 * contextDto.getObligationYear() +
					 * "\",\"obligationMetadata\": \""+
					 * contextDto.getObligationMetadata() +
					 * "\",\"dueDate\": \""+ contextDto.getDueDate() +
					 * "\",\"accounts\": \""+ contextDto.getAccounts() +
					 * "\",\"exceptions\": \""+ contextDto.getExceptions() +
					 * "\",\"businessUnit\": \""+ contextDto.getBusinessUnit()+
					 * "\",\"filingRequired\": \""+
					 * contextDto.getFilingRequirement() +
					 * "\" },  \"definitionId\": \"deloitte_wf\"}"; //String
					 * payload=
					 * "{\"definitionId\":\"dkshworkflowtrial\",\"context\":{\"entityId\":\"1010\",\"obligationId\":\"2017.C.01.1010\",\"entityName\":\"ABC Global Asset Management\",\"obligationType\":\"CRS\",\"obligationYear\":\"2017\",\"obligationMetadata\":\"Canada\",\"dueDate\":\"2018-05-01\",\"accounts\":\"811\",\"exceptions\":\"20\",\"businessUnit\":\"Asset Management\",\"filingRequired\":\"Pending\"}}"
					 * ;
					 */

					String payload = "{\"definitionId\":\"approval\",\"context\":{\"salesOrderNo\":\"" + salesOrderNo
							+ "\",\"requestId\":\"" + requestId + "\",\"decisionSetId\":\"" + dataSet
							+ "\",\"approver\":\"" + approver + "\",\"level\":\"" + level + "\"}}";
					System.err.println("Workflow  Payload :" + payload);
					DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
					dataOutputStream.write(payload.getBytes());
					dataOutputStream.flush();
					connection1.connect();
					dataOutputStream.close();
					int responseCode = connection1.getResponseCode();
					response.setResponseStatusCode(responseCode);
					JSONObject triggerResponsMessage = new JSONObject(getDataFromStream(connection1.getInputStream()));
					response.setResponseMessage(triggerResponsMessage.toMap());
					// calling a method to get a taskid from the approval
					// workflow
					WorkflowResponseEntity responseId = getInstanceIdByWorkflowInstanceId(response);
					System.err.print("Get response id =" + responseId.getId());

					// updating the table with the taskId in Task status and
					// item status table
					String taskserializedId = salesordertaskstatusdao
							.updateLevelStatusAndTaskStatus(responseId.getId(), dataSet, level);

					// get salesordertaskstatusdto from salesordertable based on
					// the updated taskid change the name to approval task id
					List<SalesOrderTaskStatusDto> listdto = salesordertaskstatusdao
							.getAllTasksFromSapTaskId(responseId.getId());
					if (listdto != null) {
						List<String> listItemId = salesordertaskstatusdao.getItemListByDataSet(dataSet);
						if (level.equals("L1")) {
							if (listItemId != null) {

								for (int itemid = 0; itemid < listItemId.size(); itemid++) {
									SalesOrderItemStatusDto salesorderitemstatusdto = new SalesOrderItemStatusDto();
									salesorderitemstatusdto.setSalesOrderItemNum(listItemId.get(itemid));
									salesorderitemstatusdto.setItemStatus(DkshStatusConstants.BLOCKED);
									salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
									salesorderitemstatusdto.setTaskStatusSerialId(taskserializedId);
									String serialitemid = salesordertaskstatusdao
											.saveOrUpdateSalesOrderItemStatus(salesorderitemstatusdto);
									System.err.println("serialitemid" + serialitemid);
								}

							}
						} else {

							if (listItemId != null) {

								List<SalesOrderItemStatusDto> salesOrderItemStatusDtoList = null;
								SalesOrderItemStatusDto salesorderitemstatusdto = new SalesOrderItemStatusDto();
								salesOrderItemStatusDtoList = salesordertaskstatusdao
										.getItemStatusDataUsingTaskSerialId(listdto.get(0).toString());
								for (int itemid = 0; itemid < listItemId.size(); itemid++) {
									salesorderitemstatusdto = salesOrderItemStatusDtoList.get(itemid);
									salesorderitemstatusdto.setSalesOrderItemNum(listItemId.get(itemid));
									salesorderitemstatusdto.setItemStatus(DkshStatusConstants.BLOCKED);
									if (salesorderitemstatusdto.getItemStatus() == DkshStatusConstants.BLOCKED) {
										salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_INACTIVE);
									} else if (salesorderitemstatusdto
											.getItemStatus() == DkshStatusConstants.ITEM_APPROVE) {
										salesorderitemstatusdto.setVisiblity(DkshStatusConstants.VISIBLITY_ACTIVE);
									} else if (salesorderitemstatusdto
											.getItemStatus() == DkshStatusConstants.ITEM_REJECT
											|| salesorderitemstatusdto
													.getItemStatus() == DkshStatusConstants.ITEM_INDIRECT_REJECT) {
										salesorderitemstatusdto
												.setVisiblity(DkshStatusConstants.VISIBLITY_INACTIVE_INDIRECT_REJECT);
									}
									// seting the foreing key .
									salesorderitemstatusdto.setTaskStatusSerialId(listdto.get(1).toString());
									String serialitemid = salesordertaskstatusdao
											.saveOrUpdateSalesOrderItemStatus(salesorderitemstatusdto);
									System.err.println("serialitemid" + serialitemid);
								}
							}
						}

					}
					response.setMessage("Success");
					response.setStatus(ResponseStatus.SUCCESS);
					response.setResponseJson((null));

				} else {
					return response;
				}
			} catch (Exception e) {
				System.err.println("Trigger FAILURE " + e.getMessage());
				response.setMessage("Trigger FAILURE EXCEPTION	" + e.getMessage());
				response.setStatus(ResponseStatus.FAILED);
				return response;
			}
			return response;

		}

		return response;
	}

	private String getDataFromStream(InputStream stream) throws IOException {
		StringBuffer dataBuffer = new StringBuffer();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";
		while ((data = inStream.readLine()) != null) {
			dataBuffer.append(data);
		}
		inStream.close();
		return dataBuffer.toString();
	}

	@SuppressWarnings({ "resource" })
	public WorkflowResponseEntity getInstanceIdByWorkflowInstanceId(WorkflowResponseEntity workflowresponseentity) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "FAILED TO GET ID  ",
				ResponseStatus.FAILED, null, null, "");

		if (workflowresponseentity.getResponseStatusCode() == 201) {
			try {
				Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);

				Map<String, Object> responsemessage = workflowresponseentity.getResponseMessage();
				JSONObject respo = getJsonFromMap(responsemessage);
				System.err.print("response of trigger workflow" + workflowresponseentity.getResponseMessage());
				String id = respo.getString("id");
				System.err.println("id" + id);

				HttpClient client = new DefaultHttpClient();

				String userpass = map.get("User") + ":" + map.get("Password");

				String encoding = javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

				URIBuilder builder = new URIBuilder();
				builder.setScheme("https")
						.setHost("bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest")
						.setPath("/v1/task-instances")
						// .setParameter("workflowDefinitionId","approvalworkflow");
						// .setParameter("id","id");
						.setParameter("workflowInstanceId", id);
				URI uri = builder.build();

				HttpGet request = new HttpGet(uri);

				request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
				HttpResponse httpresponse;
				String result;

				try {
					httpresponse = client.execute(request);
					HttpEntity entity = httpresponse.getEntity();

					if (entity != null) {
						InputStream instream = entity.getContent();
						result = convertStreamToString(instream);
						instream.close();
						JSONArray responseResult = new JSONArray(result);

						if (httpresponse.getStatusLine().getStatusCode() == 200) {
							System.err.println("RESPONSE: " + result);

							response.setResponseStatusCode(httpresponse.getStatusLine().getStatusCode());
							response.setStatus(ResponseStatus.SUCCESS);
							response.setResponseJson(responseResult);

							JSONObject responsejson = (JSONObject) response.getResponseJson().get(0);

							response.setId((responsejson.getString("id")));
							System.err.print(" get id from responsejson " + responsejson.getString("id"));

						}

					}
					// Headers
					org.apache.http.Header[] headers = httpresponse.getAllHeaders();
					for (int i = 0; i < headers.length; i++) {
						System.err.println(headers[i]);
					}
				} catch (ClientProtocolException e1) {
					logger.error("WorkflowTrigger " + e1);
				} catch (IOException e1) {
					logger.error("WorkflowTrigger " + e1);
				}

			}

			catch (Exception e) {
				System.err.println("Trigger FAILURE " + e.getMessage());
				response.setMessage("Trigger FAILURE EXCEPTION	" + e.getMessage());
				response.setStatus(ResponseStatus.FAILED);
				return response;

			}

		}
		return response;

	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonFromMap(Map<String, Object> map) throws JSONException {
		JSONObject jsonData = new JSONObject();
		for (String key : map.keySet()) {
			Object value = map.get(key);
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
			logger.error("IOException " + e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("IOException " + e);
			}
		}
		return sb.toString();
	}

	@Override
	public WorkflowResponseEntity DecisionSetWorkflowTrigger(String salesOrderNo, String requestId, String keydataSet,
			String approverDtoList, double threshold, double decisionSetAmount, String headerBlocReas,
			String soCreatedECC, String country, String customerPo, String requestType, String requestCategory,
			String salesOrderType, String soldToParty, String shipToParty, String division, String distributionChannel,
			String salesOrg, String returnReason) {

		WorkflowResponseEntity response = new WorkflowResponseEntity("", 200, "Trigger FAILURE", ResponseStatus.FAILED,
				null, null, "");
		HttpURLConnection connection1 = null;
		try {
			System.err.print("decisionSet 401");

			// getting XSRF TOKEN
			String xcsrfToken = null;
			List<String> cookies = null;

			// String url =
			// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";

			// Map<String, Object> map =
			// DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

			String url = DkshConstants.WORKFLOW_REST_BASE_URL + "/v1/workflow-instances";
			System.err.print("decisionSet 416" + url);

			URL urlObj = new URL(url);
			if (jwToken != null) {
				// Triggering the Wrofklow by settign payload and XSRF token.
				System.err.print("decisionSet 430");
				connection1 = (HttpURLConnection) urlObj.openConnection();
				connection1.setRequestMethod("POST");
				System.err.println("decisionSet XSRF Token" + jwToken);
				// SET COOKIES
				connection1.setRequestProperty("Authorization", "Bearer " + jwToken);
				connection1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				connection1.setRequestProperty("Accept", "application/json");
				connection1.setRequestProperty("DataServiceVersion", "2.0");
				connection1.setRequestProperty("X-Requested-With", "XMLHttpRequest");
				connection1.setRequestProperty("Accept-Encoding", "gzip, deflate");
				connection1.setRequestProperty("Accept-Charset", "UTF-8");
				connection1.setDoInput(true);
				connection1.setDoOutput(true);
				connection1.setUseCaches(false);
				String payload = "{\"definitionId\":\"decisionsetdetermination\",\"context\":{\"salesOrderNo\":\""
						+ salesOrderNo + "\",\"requestId\":\"" + requestId + "\",\"decisionSetId\":\"" + keydataSet
						+ "\",\"approverDtoList\":" + approverDtoList + "" + ",\"threshold\":\"" + threshold
						+ "\",\"decisionSetAmount\":\"" + decisionSetAmount + "\",\"headerBlocReas\":\""
						+ headerBlocReas + "\",\"soCreatedECC\":\"" + soCreatedECC + "\",\"country\":\"" + country
						+ "\",\"customerPo\":\"" + customerPo + "\",\"requestType\":\"" + requestType
						+ "\",\"salesOrderType\":\"" + salesOrderType + "\",\"shipToParty\":\"" + shipToParty
						+ "\",\"soldToParty\":\"" + soldToParty + "\",\"division\":\"" + division
						+ "\",\"distributionChannel\":\"" + distributionChannel + "\",\"salesOrg\":\"" + salesOrg
						+ "\",\"returnReason\":\"" + returnReason + "\"}}";
				System.err.println("decisionSet Workflow  Payload :" + payload);
				System.err.print("decisionSet 458");
				DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
				dataOutputStream.write(payload.getBytes());
				dataOutputStream.flush();
				connection1.connect();
				dataOutputStream.close();
				int responseCode = connection1.getResponseCode();
				response.setResponseStatusCode(responseCode);
				System.err.print("decisionSet 466");
				JSONObject triggerResponsMessage = new JSONObject(getDataFromStream(connection1.getInputStream()));
				response.setResponseMessage(triggerResponsMessage.toMap());

				System.err.print("decisionSet 470");
				response.setMessage("Success");
				response.setStatus(ResponseStatus.SUCCESS);
				response.setResponseJson((null));
			} else {
				System.err.print("decisionSet 476 else response");
				return response;
			}

		} catch (Exception e) {

			System.err.println("Trigger FAILURE " + e.getMessage());
			System.err.print("decisionSet 483" + e.getMessage());
			response.setMessage("Trigger FAILURE EXCEPTION	" + e.getMessage());
			response.setStatus(ResponseStatus.FAILED);
			return response;

		}
		System.err.print("decisionSet 488 end response");
		return response;

	}

}
