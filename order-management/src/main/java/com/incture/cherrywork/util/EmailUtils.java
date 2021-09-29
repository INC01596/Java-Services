package com.incture.cherrywork.util;



import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.incture.cherrywork.WConstants.MailNotificationAppConstants;
import com.incture.cherrywork.dtos.DestinationDto;
import com.incture.cherrywork.dtos.EmailUiDto;
import com.incture.cherrywork.dtos.ResponseDto;

@Component
public class EmailUtils {

	@Autowired
	private DestinationUtility destinationUtility;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseDto triggerMail(EmailUiDto emailUiDto) {

		logger.info("[EmailSender][triggerMail] Execution start");

		ResponseDto responseDto = new ResponseDto();
		try {
			JSONObject inputBody = setRequestData(emailUiDto);
			if (!ServicesUtil.isEmpty(inputBody)) {
				DestinationDto destination = destinationUtility.getDestinationByName("Work_rules_mail");
				HttpRequestBase httpRequestBase = null;
				HttpResponse httpResponse = null;
				String jsonString = null;
				StringEntity data = null;
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				httpRequestBase = new HttpPost(destination.getUrl());

				data = new StringEntity(inputBody.toString());
				data.setContentType("application/json");
				((HttpPost) httpRequestBase).setEntity(data);

				httpRequestBase.addHeader(MailNotificationAppConstants.AUTHORIZATION,
						ServicesUtil.getBasicAuth(destination.getUserName(), destination.getPassword()));
				httpResponse = httpClient.execute(httpRequestBase);
				jsonString = EntityUtils.toString(httpResponse.getEntity());
				httpClient.close();
				if (ServicesUtil.isEmpty(jsonString)) {
					responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
					responseDto.setStatus(false);
					responseDto.setMessage("Error occured in triggering mail");
					responseDto.setData("");
					logger.error("[EmailSenderUtil][triggerMail] - exception{} Error occure in triggering mail");
				} else {
					responseDto.setStatusCode(HttpStatus.SC_OK);
					responseDto.setStatus(true);
					responseDto.setMessage("Successfully triggered Mail");
					responseDto.setData(jsonString);
				}

			} else {
				responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
				responseDto.setStatus(false);
				responseDto.setMessage("Please send valid input");
				responseDto.setData(inputBody);
				logger.error("[EmailSender][triggerMail] - exception{} invalid input");

			}

		} catch (IOException | JSONException e) {
			responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseDto.setStatus(false);
			responseDto.setMessage(e.getMessage());
			logger.error("[EmailSender][triggerMail] - exception :" + e.getMessage());

		}

		logger.info("[EmailSender][triggerMail] Execution end");

		return responseDto;

	}

	private JSONObject setRequestData(EmailUiDto emailUiDto) throws JSONException {
		JSONObject inputBody = new JSONObject();
		if (!ServicesUtil.isEmpty(emailUiDto)) {
			JSONObject inputParameters = new JSONObject();
			inputBody.put("mail_details", inputParameters);
			inputParameters.put("from", emailUiDto.getFromAddress());
			inputParameters.put("subject", emailUiDto.getSubject());
			inputParameters.put("body", emailUiDto.getContent());

			inputParameters.put("to", String.join(",", emailUiDto.getToList()));

			if (ServicesUtil.isEmpty(emailUiDto.getCcList())) {
				inputParameters.put("cc", "");
			} else {
				inputParameters.put("cc", String.join(",", emailUiDto.getCcList()));

			}
			if (ServicesUtil.isEmpty(emailUiDto.getBccList())) {
				inputParameters.put("bcc", "");

			} else {
				inputParameters.put("bcc", String.join(",", emailUiDto.getBccList()));
			}
			if (!ServicesUtil.isEmpty(emailUiDto.getAttachment())) {

				inputParameters.put("attachment", emailUiDto.getAttachment());
				inputParameters.put("fileName", "ErrorFile");
			}

		}
		return inputBody;
	}

	public List<Map<String, Object>> getRecordsInWorkrules(String query, Jwt jwt)
			throws JsonMappingException, JsonProcessingException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = null;
		HttpHeaders requestHeaders;
		HttpEntity<String> entity;

		uri = new URI("https://workrules.cfapps.eu10.hana.ondemand.com/rest/v1/decision-tables/records");

		requestHeaders = new HttpHeaders();
		requestHeaders.add(MailNotificationAppConstants.AUTHORIZATION,
				MailNotificationAppConstants.TOKEN +  jwt.getClaims());
		requestHeaders.add(MailNotificationAppConstants.CONTENT_TYPE, "application/json;charset=utf-8");

		entity = new HttpEntity<String>(query, requestHeaders);
		ResponseEntity<String> response = null;

		response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		logger.info("response from wr " + response.toString() + " " + response.getStatusCodeValue() + " "
				+ response.getBody());
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = mapper.readValue(response.getBody(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		return data;
	}

	public Map<String, List<String>> getRecipientsRecordsFromWr(List<String> emailDefinitionIdList, Jwt jwt)
			throws JsonMappingException, JsonProcessingException, URISyntaxException {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(" select * from ");

		selectQuery.append(" cw_wr_rule_records_" + MailNotificationAppConstants.APPLICATION_DEF_ACCESS_DT_NAME);

		selectQuery.append(" where COL_413DADA33ADD47FE9462AE3C35B6CB4C2C68F003AA0141CBA17FE0C4A0BEF2AD_C ");
		selectQuery.append(" in ( ");
		for (String id : emailDefinitionIdList) {
			if (!emailDefinitionIdList.get(0).equals(id)) {
				selectQuery.append(",");
			}
			selectQuery.append("'" + id + "'");
		}
		selectQuery.append(")");
		logger.info("select query for recipients from wr " + selectQuery.toString());
		List<Map<String, Object>> list = getRecordsInWorkrules(selectQuery.toString(), jwt);
		if (!ServicesUtil.isEmpty(list)) {
			logger.info(" recipient list fetched successfully");

			for (Map<String, Object> recipient : list) {
				String emailDefinitionId = recipient
						.get("COL_413DADA33ADD47FE9462AE3C35B6CB4C2C68F003AA0141CBA17FE0C4A0BEF2AD_C").toString();

				String email = recipient.get("COL_577520D0606C435297255CC4738B8325F760954457D546728F0889D4D17ECB6E_A")
						.toString();
				String recipientType = recipient
						.get("COL_577520D0606C435297255CC4738B83257566BF8B1B234CD3ADF89762A6491904_A") != null
								? recipient
										.get("COL_577520D0606C435297255CC4738B83257566BF8B1B234CD3ADF89762A6491904_A")
										.toString()
								: null;

				if (MailNotificationAppConstants.TO.equals(recipientType)) {
					if (map.get(emailDefinitionId + MailNotificationAppConstants.TO) != null
							&& !map.get(emailDefinitionId + MailNotificationAppConstants.TO).isEmpty()) {
						map.get(emailDefinitionId + MailNotificationAppConstants.TO).add(email);
					} else {
						List<String> toList = new ArrayList<String>();
						toList.add(email);
						map.put(emailDefinitionId + MailNotificationAppConstants.TO, toList);

					}
				} else if (MailNotificationAppConstants.CC.equals(recipientType)) {
					if (map.get(emailDefinitionId + MailNotificationAppConstants.CC) != null
							&& !map.get(emailDefinitionId + MailNotificationAppConstants.CC).isEmpty()) {
						map.get(emailDefinitionId + MailNotificationAppConstants.CC).add(email);
					} else {
						List<String> toList = new ArrayList<String>();
						toList.add(email);
						map.put(emailDefinitionId + MailNotificationAppConstants.CC, toList);

					}

				} else if (MailNotificationAppConstants.BCC.equals(recipientType)) {
					if (map.get(emailDefinitionId + MailNotificationAppConstants.BCC) != null
							&& !map.get(emailDefinitionId + MailNotificationAppConstants.CC).isEmpty()) {
						map.get(emailDefinitionId + MailNotificationAppConstants.BCC).add(email);
					} else {
						List<String> toList = new ArrayList<String>();
						toList.add(email);
						map.put(emailDefinitionId + MailNotificationAppConstants.BCC, toList);

					}

				}

			}

		}
		return map;
	}

	public Map<String, List<String>> getRecipientsRecordsFromWorkRules(List<String> emailDefinitionIdList)
			throws JsonMappingException, JsonProcessingException, URISyntaxException {

		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

		Object responseBody = this.invokeExecutionEngine(emailDefinitionIdList);
		JsonNode data = new ObjectMapper().convertValue(responseBody, JsonNode.class);

		JsonNode result = data.get("result");

		if (result != null && result.isArray()) {
			ArrayNode results = (ArrayNode) result;

			for (int i = 0; i < results.size(); i++) {
				String emailDefinitionId = emailDefinitionIdList.get(i);

				if (emailDefinitionId != null) {
					JsonNode emailActions = results.get(i);
					ArrayNode actions = (ArrayNode) emailActions.get("EMAIL_ACTION");
					if (actions != null) {
						for (JsonNode action : actions) {
							String recipientType = action.get("RECIPIENT_TYPE") != null
									? action.get("RECIPIENT_TYPE").asText()
									: null;
							String recipientMail = action.get("RECIPIENT_EMAIL") != null
									? action.get("RECIPIENT_EMAIL").asText()
									: null;

							if (MailNotificationAppConstants.TO.equals(recipientType)) {
								if (map.get(emailDefinitionId + MailNotificationAppConstants.TO) != null
										&& !map.get(emailDefinitionId + MailNotificationAppConstants.TO).isEmpty()) {
									map.get(emailDefinitionId + MailNotificationAppConstants.TO).add(recipientMail);
								} else {
									List<String> toList = new ArrayList<String>();
									toList.add(recipientMail);
									map.put(emailDefinitionId + MailNotificationAppConstants.TO, toList);

								}
							} else if (MailNotificationAppConstants.CC.equals(recipientType)) {
								if (map.get(emailDefinitionId + MailNotificationAppConstants.CC) != null
										&& !map.get(emailDefinitionId + MailNotificationAppConstants.CC).isEmpty()) {
									map.get(emailDefinitionId + MailNotificationAppConstants.CC).add(recipientMail);
								} else {
									List<String> toList = new ArrayList<String>();
									toList.add(recipientMail);
									map.put(emailDefinitionId + MailNotificationAppConstants.CC, toList);

								}

							} else if (MailNotificationAppConstants.BCC.equals(recipientType)) {
								if (map.get(emailDefinitionId + MailNotificationAppConstants.BCC) != null
										&& !map.get(emailDefinitionId + MailNotificationAppConstants.CC).isEmpty()) {
									map.get(emailDefinitionId + MailNotificationAppConstants.BCC).add(recipientMail);
								} else {
									List<String> toList = new ArrayList<String>();
									toList.add(recipientMail);
									map.put(emailDefinitionId + MailNotificationAppConstants.BCC, toList);

								}

							}
						}
					}
				}
			}

		}

		return map;
	}

	public Object invokeExecutionEngine(List<String> emailDefinitionIdList)
			throws URISyntaxException, JsonMappingException, JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		URI uri = null;
		HttpHeaders requestHeaders;
		HttpEntity<RuleEnginePayload> entity;

		uri = new URI(MailNotificationAppConstants.WR_EXECUTION_ENGINE_BASE_URL + "v1/invoke-rules");

		requestHeaders = new HttpHeaders();
		/*
		 * requestHeaders.add(MailNotificationAppConstants.AUTHORIZATION,
		 * MailNotificationAppConstants.TOKEN + jwt.getTokenValue());
		 */
		requestHeaders.add(MailNotificationAppConstants.CONTENT_TYPE, "application/json;charset=utf-8");

		RuleEnginePayload payload = new RuleEnginePayload();
		payload.setDecisionTableId(MailNotificationAppConstants.APPLICATION_DEF_ACCESS_DT_NAME);
		for (String id : emailDefinitionIdList) {
			if (id == null) {
				id = "";
			}
			Map<String, Object> map = new HashMap<>();
			map.put("CP_FIELD_CATALOG.EMAIL_TEMPLATE", id);
			payload.getConditions().add(map);
		}

		entity = new HttpEntity<RuleEnginePayload>(payload, requestHeaders);

		ResponseDto response = restTemplate.postForObject(uri, entity, ResponseDto.class);
		logger.info(
				"response from wr " + response.toString() + " " + response.getStatusCode() + " " + response.getData());

		return response.getData();
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException, URISyntaxException {
		EmailUtils email = new EmailUtils();
		List<String> emailDefinitionIdList = new ArrayList<>();
//		emailDefinitionIdList.add("4df2cf34-c23c-42d9-aa39-759c20fee39a");
//		emailDefinitionIdList.add("");
//		emailDefinitionIdList.add("822dbafe-aeb7-43e6-bcf9-8cae51910da8");

		System.out.println(email.getRecipientsRecordsFromWorkRules(emailDefinitionIdList));
	}

}
