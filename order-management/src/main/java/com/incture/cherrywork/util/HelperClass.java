package com.incture.cherrywork.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.sales.constants.ApplicationConstants;
import com.incture.cherrywork.sales.constants.ResponseStatus;

import com.incture.cherrywork.WConstants.DkshConstants;
import com.incture.cherrywork.dtos.FilterOnReturnHeaderDto;
import com.incture.cherrywork.dtos.UserAttributes;
import com.incture.cherrywork.dtos.UserCustom;
import com.incture.cherrywork.dtos.UserInfo;
import com.incture.cherrywork.dtos.UserList;
import com.incture.cherrywork.dtos.VisitPlannerWorkflowTaskOutputDto;
import com.incture.cherrywork.dtos.WorkflowTaskOutputDto;

@SuppressWarnings("unused")
public class HelperClass {

	public static final String FETCHING_FAILED = "Fetching failed";
	public static final String INVALID_INPUT_PLEASE_RETRY = "Invalid Input, Please retry";

	static Logger logger = LoggerFactory.getLogger(HelperClass.class);

	public static ResponseEntity consumingOdataService(String url, String entity, String method,
			Map<String, Object> destinationInfo) throws IOException, URISyntaxException {

		System.err.println("com.incture.utils.HelperClass  + Inside consumingOdataService==================");

		String proxyHost = "connectivityproxy.internal.cf.eu10.hana.ondemand.com";
		System.err.println("proxyHost-- " + proxyHost);
		int proxyPort = 20003;
		Header[] jsonResponse = null;
		String objresult = null;

		JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));

		System.err.println("116 - jsonObj =" + jsonObj);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(
				(String) destinationInfo.get("User"), (String) destinationInfo.get("Password")));

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();

		clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort))
				.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
				.setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

		HttpClient httpClient = clientBuilder.build();
		HttpRequestBase httpRequestBase = null;
		HttpResponse httpResponse = null;
		StringEntity input = null;
		Header[] json = null;
		JSONObject obj = null;
		String jwToken = DestinationReaderUtil.getConectivityProxy();
		if (url != null) {
			if (method.equalsIgnoreCase("GET")) {
				httpRequestBase = new HttpGet(url);
			} else if (method.equalsIgnoreCase("POST")) {
				httpRequestBase = new HttpPost(url);
				try {

					System.err.println("entity " + entity);
					input = new StringEntity(entity);
					input.setContentType("application/json");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				System.err.println("inputEntity " + input);
				((HttpPost) httpRequestBase).setEntity(input);
			}
			if (destinationInfo.get("sap-client") != null) {
				httpRequestBase.addHeader("sap-client", (String) destinationInfo.get("sap-client"));
			}
			httpRequestBase.addHeader("accept", "application/json");

			Header[] headers = getAccessToken(
					(String) destinationInfo.get("URL")
							+ "/sap/opu/odata/sap/Z_SALESORDER_STATUS_SRV/likpSet(Vbeln='80000329')",
					(String) destinationInfo.get("User"), (String) destinationInfo.get("Password"), httpClient,
					proxyHost, proxyPort, (String) destinationInfo.get("sap-client"), jwToken);
			String token = null;
			List<String> cookies = new ArrayList<>();
			if (headers.length != 0) {

				for (Header header : headers) {

					if (header.getName().equalsIgnoreCase("x-csrf-token")) {
						token = header.getValue();
						System.err.println("token --- " + token);
					}

					if (header.getName().equalsIgnoreCase("set-cookie")) {
						cookies.add(header.getValue());
					}

				}
			}

			if (destinationInfo.get("User") != null && destinationInfo.get("Password") != null) {
				String encoded = HelperClass.encodeUsernameAndPassword((String) destinationInfo.get("User"),
						(String) destinationInfo.get("Password"));
				httpRequestBase.addHeader("Authorization", encoded);
				httpRequestBase.setHeader("Proxy-Authorization", "Bearer " + jwToken);
				httpRequestBase.addHeader("SAP-Connectivity-SCC-Location_ID", "incture");

			}
			if (token != null) {
				httpRequestBase.addHeader("X-CSRF-Token", token);
			}
			if (!cookies.isEmpty()) {
				for (String cookie : cookies) {
					String tmp = cookie.split(";", 2)[0];
					httpRequestBase.addHeader("Cookie", tmp);
				}
			}
			// if (tenantctx != null) {
			// httpRequestBase.addHeader("SAP-Connectivity-ConsumerAccount",
			// tenantctx.getTenant().getAccount().getId());
			// }
			try {

				System.err.println("this is requestBase ============" + Arrays.asList(httpRequestBase));
				httpResponse = httpClient.execute(httpRequestBase);
				System.err.println(
						"com.incture.utils.HelperClass ============" + Arrays.asList(httpResponse.getAllHeaders()));
				System.err.println("com.incture.utils.HelperClass ============" + httpResponse);
				System.err.println("STEP 4 com.incture.utils.HelperClass ============StatusCode from odata hit="
						+ httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 201) {
					json = httpResponse.getAllHeaders();
					jsonResponse = httpResponse.getHeaders("sap-message");
				} else {
					String responseFromECC = httpResponse.getEntity().toString();

					System.err.println("responseFromEcc" + responseFromECC);

					return new ResponseEntity<String>("Failed to create , OutBound already created",
							HttpStatus.BAD_REQUEST);
				}

				System.err.println("STEP 5 Result from odata hit ============" + json);

			} catch (IOException e) {
				System.err.print("IOException : " + e);
				throw new IOException(
						"Please Check VPN Connection ......." + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

			try {

				System.err.println("jsonOutput" + json);

				System.err.println("jsonHeaderResponse" + jsonResponse);
				obj = new JSONObject(jsonResponse);
				objresult = obj.toString();

			} catch (JSONException e) {
				System.err.print("JSONException : check " + e + "JSON Object : " + json);

				throw new JSONException(
						"Exception occured during json conversion" + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

		}

		System.err.print(" object returned from odata " + obj);
		return new ResponseEntity<JSONObject>(obj, HttpStatus.OK);

	}

	public static String encodeUsernameAndPassword(String username, String password) {
		String encodeUsernamePassword = username + ":" + password;
		String auth = "Basic " + DatatypeConverter.printBase64Binary(encodeUsernamePassword.getBytes());
		return auth;
	}

	public static Object findUsersFromGroupInIdp(String groupName) {
		try {
			if (!HelperClass.checkString(groupName)) {

				Map<String, Object> map = DestinationReaderUtil
						.getDestination(Constants.USERS_FROM_GROUP_IN_IDP_DESTINATION_NAME);

				String groupListUrl = map.get("URL") + "service/scim/Groups";

				Map<String, String> groupsInIdp = getGroupsFromIdp(groupListUrl, (String) map.get("User"),
						(String) map.get("Password"));

				String groupId = groupsInIdp.get(groupName);

				if (!HelperClass.checkString(groupId)) {
					return getUsersFromGroupInIdp(groupId, groupListUrl, (String) map.get("User"),
							(String) map.get("Password"));
				} else {
					return "Group name is not found in idp";
				}

			} else {
				return "Group name is invalid";
			}
		} catch (Exception e) {
			return e;
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getGroupsFromIdp(String url, String username, String password)
			throws IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);

		httpGet.addHeader("Content-Type", "application/json");

		// Encoding username and password
		String auth = encodeUsernameAndPassword(username, password);
		httpGet.addHeader("Authorization", auth);

		HttpResponse response = client.execute(httpGet);
		String dataFromStream = getDataFromStream(response.getEntity().getContent());
		if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			JSONObject json = new JSONObject(dataFromStream);

			Map<String, Object> map = json.toMap();

			Map<String, String> mapForGroupsInIdp = new HashMap<>();

			List<Map<String, Object>> listOfGroups = (List<Map<String, Object>>) map.get("Resources");

			for (Map<String, Object> innerMap : listOfGroups) {
				mapForGroupsInIdp.put((String) innerMap.get("displayName"), (String) innerMap.get("id"));
			}

			System.err.println(mapForGroupsInIdp);
			return mapForGroupsInIdp;
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	private static Object getUsersFromGroupInIdp(String groupId, String url, String username, String password)
			throws IOException {
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet httpGet = new HttpGet(url + "/" + groupId);

		// Encoding username and password
		String auth = encodeUsernameAndPassword(username, password);
		httpGet.addHeader("Authorization", auth);

		HttpResponse response = client.execute(httpGet);
		String dataFromStream = getDataFromStream(response.getEntity().getContent());
		if (response.getStatusLine().getStatusCode() == 200) {

			JSONObject json = new JSONObject(dataFromStream);
			Map<String, Object> outputInMap = json.toMap();

			List<Map<String, String>> listOfUsersInMap = (List<Map<String, String>>) outputInMap.get("members");

			List<String> listOfUsers = new ArrayList<>();
			for (Map<String, String> usersMap : listOfUsersInMap) {
				listOfUsers.add(usersMap.get("value"));
			}
			return listOfUsers;

		} else {
			return dataFromStream;
		}
	}

	public static String getDataFromStream(InputStream stream) throws IOException {
		System.err.println("[Helper Class][getDataFromStream] started");
		System.err.println("[Helper Class][getDataFromStream] stream: " + stream);
		StringBuilder dataBuffer = new StringBuilder();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";
		while ((data = inStream.readLine()) != null) {
			System.err.println("[Helper Class][getDataFromStream] data: " + data);
			dataBuffer.append(data);
		}
		inStream.close();
		return dataBuffer.toString();
	}

	public static boolean checkString(String s) {
		if (s == null || s.equals("") || s.trim().isEmpty() || s.matches("") || s.equals(null)) {
			return true;
		}
		return false;
	}

	private static Header[] getAccessToken(String url, String username, String password, HttpClient client,
			String proxyHost, int proxyPort, String sapClient, String token)
			throws ClientProtocolException, IOException {

		HttpGet httpGet = new HttpGet(url);

		String userpass = username + ":" + password;

		httpGet.setHeader("Proxy-Authorization", "Bearer " + token);
		httpGet.setHeader(HttpHeaders.AUTHORIZATION,
				"Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
		// Encoding username and password
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);
		httpGet.addHeader("SAP-Connectivity-SCC-Location_ID", "incture");

		HttpResponse response = client.execute(httpGet);
		// HttpResponse response = client.execute( httpGet);

		System.err.println("313 response " + response);

		// HttpResponse response = client.execute(httpGet);

		return response.getAllHeaders();

	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity getWorkflowInstanceUsingOauthClient(String bussinessKey) {

		try {
			HttpClient client = HttpClientBuilder.create().build();
			// Map<String, Object> map =
			// DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER_ID);

			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			String url = Constants.WORKFLOW_REST_BASE_URL;
			System.err.println("url " + url);
			String trimmed = url.substring(8);
			// String userpass = map.get("User") + ":" + map.get("Password");
			// String encoding =
			// javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost(trimmed).setPath("/v1/workflow-instances")
					.setParameter("businessKey", bussinessKey).setParameter("status", "RUNNING");
			URI uri = builder.build();
			System.err.println("URI " + uri);

			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwToken);
			httpGet.addHeader("Content-Type", "application/json");

			try {

				HttpResponse response = client.execute(httpGet);

				if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
					return new ResponseEntity(HttpStatus.OK);
				} else {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}

			} catch (IOException e) {

				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {

			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public List<String> convertResponseToDto(@SuppressWarnings("rawtypes") ResponseEntity response) {
		System.err.println("response " + response);
		ArrayList<String> ids = new ArrayList<String>();
		JSONArray jsonArray = new JSONArray(response.getBody().toString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject explrObject = jsonArray.getJSONObject(i);
			ids.add(explrObject.getString("id").toString());
		}
		System.err.println("al1 " + ids);
		return ids;
	}

	public static List<?> convertObjectToList(Object obj) {
		List<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = Arrays.asList((Object[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}

	public ResponseEntity completeTaskInWorkflowUsingOauthClient(String salesOrderNum, String taskId) {

		try {
			String token = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

			HttpClient client = HttpClientBuilder.create().build();

			// Map<String, Object> map = DestinationReaderUtil
			// .getDestination(DkshConstants.WORKFLOW_CLOSE_TASK_DESTINATION);

			String url = Constants.WORKFLOW_REST_BASE_URL + "/v1/task-instances/" + taskId;
			String payload = "{\"context\": {},\"status\":\"COMPLETED\",\"subject\": \"" + salesOrderNum
					+ "\",\"priority\": \"MEDIUM\"}";

			HttpPatch httpPatch = new HttpPatch(url);
			httpPatch.addHeader("Authorization", "Bearer " + token);
			httpPatch.addHeader("Content-Type", "application/json");

			try {
				if (!HelperClass.checkString(salesOrderNum)) {
					StringEntity entity = new StringEntity(payload);
					entity.setContentType("application/json");
					httpPatch.setEntity(entity);
					HttpResponse response = client.execute(httpPatch);

					if (response.getStatusLine().getStatusCode() == HttpStatus.NO_CONTENT.value()) {
						return new ResponseEntity(HttpStatus.NO_CONTENT);
					} else {
						return new ResponseEntity(HttpStatus.BAD_REQUEST);
					}
				} else {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}

			} catch (IOException e) {

				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {

			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public static ResponseEntity<Object> fetchUserTasksForApprovalWorkflowOfReturnsForNewDac(String userId,
			FilterOnReturnHeaderDto filterDto, Map<String, String> mapOfAttributeValues, Boolean flagForAllRights,
			Boolean flagForEmptyInput) {
		try {
			if (!checkString(userId)) {

				// Map<String, Object> map = DestinationReaderUtil
				// .getDestination(WorkflowConstants.WORKFLOW_CLOSE_TASK_DESTINATION);

				String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

				HttpClient client = HttpClientBuilder.create().build();

				StringBuilder url = new StringBuilder();

				// default condition no need to check explicitly
				appendParamInUrl(url, WorkflowConstants.STATUS_OF_APPROVAL_TASKS_KEY,
						WorkflowConstants.STATUS_OF_APPROVAL_TASKS_VALUE);
				appendParamInUrl(url, WorkflowConstants.WORKFLOW_DEFINATION_ID_KEY,
						WorkflowConstants.WORKFLOW_DEFINATION_ID_VALUE);
				appendParamInUrl(url, WorkflowConstants.RECIPIENT_USER_KEY, userId);
				appendParamInUrl(url, WorkflowConstants.FIND_COUNT_OF_TASKS_KEY,
						WorkflowConstants.FIND_COUNT_OF_TASKS_VALUE);
				appendParamInUrl(url, WorkflowConstants.TOP_KEY, WorkflowConstants.TOP_VALUE);

				// When flag for empty input is true and rights is true means
				// initially loaded user screen with all rights
				if (flagForAllRights && flagForEmptyInput) {

					appendParamInUrl(url, WorkflowConstants.RETURN_TYPE, WorkflowConstants.RETURN_TYPE_VALUE);

				}
				// When flag for empty input is true and rights is false means
				// initially loaded user screen with some rights
				else if (!flagForAllRights && flagForEmptyInput) {

					// call some method for some rights
					generateURLForUserWithSomeRightsForNewDac(mapOfAttributeValues, url);

				}
				// User used filter data fields
				else {

					Map<String, Object> mapOfFilterDetailsData = new HashMap<>();

					populateMapWithDataForFilterDetails(filterDto, mapOfFilterDetailsData);

					System.err.println("mapOfFilterDetailsData : " + mapOfFilterDetailsData);

					// call some method for filtered data
					generateUrlForUserUsedFilteredParams(mapOfFilterDetailsData, url);

				}

				url.insert(0, ComConstants.WORKFLOW_REST_BASE_URL + "/v1/task-instances?");

				System.err.println("URL : " + url);

				HttpGet httpGet = new HttpGet(url.toString());

				httpGet.addHeader("Content-Type", "application/json");
				// Encoding username and password
				// String auth = encodeUsernameAndPassword((String)
				// map.get("User"), (String) map.get("Password"));

				System.err.println("jwToken in fetchReturnOrderList " + jwToken);

				httpGet.addHeader("Authorization", "Bearer " + jwToken);

				HttpResponse response = client.execute(httpGet);
				System.err.println(
						"[Helper Class][fetchUserTasksForApprovalWorkflowOfReturnsForNewDac]response: " + response);
				String dataFromStream = getDataFromStream(response.getEntity().getContent());
				System.err.println("[Helper Class][fetchUserTasksForApprovalWorkflowOfReturnsForNewDac]dataFromStream: "
						+ dataFromStream);
				if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

					JSONArray jsonArray = new JSONArray(dataFromStream);

					List<WorkflowTaskOutputDto> listOfWorkflowTasks = new ArrayList<>();

					jsonArray.forEach(jsonObject -> {
						WorkflowTaskOutputDto taskDto = new Gson().fromJson(jsonObject.toString(),
								WorkflowTaskOutputDto.class);
						taskDto.setCustomerPo(taskDto.getDescription().split("\\|")[7]);
						taskDto.setRoType(taskDto.getDescription().split("\\|")[8]);
						listOfWorkflowTasks.add(taskDto);

					});
					if (!listOfWorkflowTasks.isEmpty()) {
						return new ResponseEntity<>(listOfWorkflowTasks, HttpStatus.OK);
					} else {
						return new ResponseEntity<>("No tasks are available for user : " + userId,
								HttpStatus.NO_CONTENT);
					}
				} else {
					return new ResponseEntity<>(FETCHING_FAILED, HttpStatus.CONFLICT);

				}
			} else {
				return new ResponseEntity<>(INVALID_INPUT_PLEASE_RETRY + " with provide USER ID.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	public static ResponseEntity<Object> fetchUserTasksForVisitPlannerWorkflowOfReturnsForNewDac(String userId) {
		try {
			if (!checkString(userId)) {

				// Map<String, Object> map = DestinationReaderUtil
				// .getDestination(WorkflowConstants.WORKFLOW_CLOSE_TASK_DESTINATION);

				String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

				HttpClient client = HttpClientBuilder.create().build();

				StringBuilder url = new StringBuilder();

				// default condition no need to check explicitly
				appendParamInUrl(url, WorkflowConstants.STATUS_OF_APPROVAL_TASKS_KEY,
						WorkflowConstants.STATUS_OF_APPROVAL_TASKS_VALUE);
				appendParamInUrl(url, WorkflowConstants.WORKFLOW_DEFINATION_ID_KEY,
						WorkflowConstants.WORKFLOW_DEFINATION_ID_VALUE_VISIT_PLANNER);
				appendParamInUrl(url, WorkflowConstants.RECIPIENT_USER_KEY, userId);
				appendParamInUrl(url, WorkflowConstants.FIND_COUNT_OF_TASKS_KEY,
						WorkflowConstants.FIND_COUNT_OF_TASKS_VALUE);
				appendParamInUrl(url, WorkflowConstants.TOP_KEY, WorkflowConstants.TOP_VALUE);

				url.insert(0, ComConstants.WORKFLOW_REST_BASE_URL + "/v1/task-instances?");

				System.err.println("URL : " + url);

				HttpGet httpGet = new HttpGet(url.toString());

				httpGet.addHeader("Content-Type", "application/json");

				System.err.println("jwToken in fetchReturnOrderList " + jwToken);

				httpGet.addHeader("Authorization", "Bearer " + jwToken);

				HttpResponse response = client.execute(httpGet);
				System.err.println(
						"[Helper Class][fetchUserTasksForVisitPlannerWorkflowOfReturnsForNewDac]response: " + response);
				String dataFromStream = getDataFromStream(response.getEntity().getContent());
				System.err.println(
						"[Helper Class][fetchUserTasksForVisitPlannerWorkflowOfReturnsForNewDac]dataFromStream: "
								+ dataFromStream);
				if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

					JSONArray jsonArray = new JSONArray(dataFromStream);

					List<VisitPlannerWorkflowTaskOutputDto> listOfWorkflowTasks = new ArrayList<>();

					jsonArray.forEach(jsonObject -> {
						VisitPlannerWorkflowTaskOutputDto taskDto = new Gson().fromJson(jsonObject.toString(),
								VisitPlannerWorkflowTaskOutputDto.class);
						listOfWorkflowTasks.add(taskDto);
					});

					if (!listOfWorkflowTasks.isEmpty()) {
						return new ResponseEntity<>(listOfWorkflowTasks, HttpStatus.OK);
					} else {
						return new ResponseEntity<>("No tasks are available for user : " + userId,
								HttpStatus.NO_CONTENT);
					}
				} else {
					return new ResponseEntity<>(FETCHING_FAILED, HttpStatus.CONFLICT);

				}
			} else {
				return new ResponseEntity<>(INVALID_INPUT_PLEASE_RETRY + " with provide USER ID.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	public static void appendParamInUrl(StringBuilder url, String key, String value) {
		if (url.length() > 0) {
			url.append("&" + key + "=" + value);
		} else {
			url.append(key + "=" + value);
		}
	}

	private static void generateURLForUserWithSomeRightsForNewDac(Map<String, String> mapOfAttributeValues,
			StringBuilder url) {
		appendParamInUrl(url, WorkflowConstants.RETURN_TYPE, WorkflowConstants.RETURN_TYPE_VALUE);
		if (mapOfAttributeValues.containsKey(DacMappingConstants.DISTRIBUTION_CHANNEL)
				&& !mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).contains("*")) {
			addParamForListValues(url, WorkflowConstants.DISTRIBUTION_CHANNEL,
					Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).split("@"))
							.collect(Collectors.toList()));

		}
		// if
		// (mapOfAttributeValues.containsKey(DacMappingConstants.SOLD_TO_PARTY)
		// &&
		// !mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).contains("*"))
		// {
		// addParamForListValues(url, WorkflowConstants.SOLD_TO_PARTY,
		// Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).split("@"))
		// .collect(Collectors.toList()));
		//
		// }
		if (mapOfAttributeValues.containsKey(DacMappingConstants.DIVISION)
				&& !mapOfAttributeValues.get(DacMappingConstants.DIVISION).contains("*")) {
			addParamForListValues(url, WorkflowConstants.DIVISION,
					Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.DIVISION).split("@"))
							.collect(Collectors.toList()));

		}
		if (mapOfAttributeValues.containsKey(DacMappingConstants.ORDER_TYPE)
				&& !mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).contains("*")) {
			addParamForListValues(url, WorkflowConstants.ORDER_TYPE,
					Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).split("@"))
							.collect(Collectors.toList()));

		}
		if (mapOfAttributeValues.containsKey(DacMappingConstants.SALES_ORG)
				&& !mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).contains("*")) {
			addParamForListValues(url, WorkflowConstants.SALES_ORG,
					Arrays.stream(mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).split("@"))
							.collect(Collectors.toList()));

		}
	}

	private static void populateMapWithDataForFilterDetails(FilterOnReturnHeaderDto filterDto,
			Map<String, Object> mapOfFilterDetailsData) {

		if (!checkString(filterDto.getReturnType())) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.RETURN_TYPE, filterDto.getReturnType());
		}
		if (!checkString(filterDto.getCustomerPo())) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.CUSTOMER_PO, filterDto.getCustomerPo());
		}
		if (!checkString(filterDto.getOrderNumber())) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.ORDER_NUM, filterDto.getOrderNumber());
		}
		if (filterDto.getDistributionChannelList() != null && !filterDto.getDistributionChannelList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.DISTRIBUTION_CHANNEL,
					filterDto.getDistributionChannelList());
		}
		if (filterDto.getDivisionList() != null && !filterDto.getDivisionList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.DIVISION, filterDto.getDivisionList());
		}

		if (filterDto.getSalesOrgList() != null && !filterDto.getSalesOrgList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.SALES_ORG, filterDto.getSalesOrgList());
		}

		if (filterDto.getOrderTypeList() != null && !filterDto.getOrderTypeList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.ORDER_TYPE, filterDto.getOrderTypeList());
		}

		if (filterDto.getSoldToPartyList() != null && !filterDto.getSoldToPartyList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.SOLD_TO_PARTY,
					filterDto.getSoldToPartyList());
		}

		if (filterDto.getShipToPartyList() != null && !filterDto.getShipToPartyList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.SHIP_TO_PARTY,
					filterDto.getShipToPartyList());
		}

		if (filterDto.getReturnReasonList() != null && !filterDto.getReturnReasonList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.RETURN_REASON,
					filterDto.getReturnReasonList());
		}

		if (filterDto.getHeaderDeliveryBlockList() != null && !filterDto.getHeaderDeliveryBlockList().isEmpty()) {
			mapOfFilterDetailsData.put(FilterDetailsReturnConstantsForHana.HEADER_DELIVERY_BLOCK,
					filterDto.getHeaderDeliveryBlockList());
		}
	}

	@SuppressWarnings("unchecked")
	private static void generateUrlForUserUsedFilteredParams(Map<String, Object> mapOfFilterDetailsData,
			StringBuilder url) {
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.RETURN_TYPE)) {
			appendParamInUrl(url, WorkflowConstants.RETURN_TYPE,
					(String) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.RETURN_TYPE));
		} else {
			appendParamInUrl(url, WorkflowConstants.RETURN_TYPE, WorkflowConstants.RETURN_TYPE_VALUE);
		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.CUSTOMER_PO)) {
			appendParamInUrl(url, WorkflowConstants.CUSTOMER_PO,
					(String) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.CUSTOMER_PO));
		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.ORDER_NUM)) {
			appendParamInUrl(url, WorkflowConstants.ORDER_NUM,
					(String) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.ORDER_NUM));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.DISTRIBUTION_CHANNEL)) {

			addParamForListValues(url, WorkflowConstants.DISTRIBUTION_CHANNEL, (List<String>) mapOfFilterDetailsData
					.get(FilterDetailsReturnConstantsForHana.DISTRIBUTION_CHANNEL));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.DIVISION)) {

			addParamForListValues(url, WorkflowConstants.DIVISION,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.DIVISION));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.HEADER_DELIVERY_BLOCK)) {

			addParamForListValues(url, WorkflowConstants.HEADER_DELIVERY_BLOCK, (List<String>) mapOfFilterDetailsData
					.get(FilterDetailsReturnConstantsForHana.HEADER_DELIVERY_BLOCK));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.ORDER_TYPE)) {

			addParamForListValues(url, WorkflowConstants.ORDER_TYPE,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.ORDER_TYPE));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.RETURN_REASON)) {

			addParamForListValues(url, WorkflowConstants.RETURN_REASON,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.RETURN_REASON));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.SALES_ORG)) {

			addParamForListValues(url, WorkflowConstants.SALES_ORG,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.SALES_ORG));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.SHIP_TO_PARTY)) {

			addParamForListValues(url, WorkflowConstants.SHIP_TO_PARTY,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.SHIP_TO_PARTY));

		}
		if (mapOfFilterDetailsData.containsKey(FilterDetailsReturnConstantsForHana.SOLD_TO_PARTY)) {

			addParamForListValues(url, WorkflowConstants.SOLD_TO_PARTY,
					(List<String>) mapOfFilterDetailsData.get(FilterDetailsReturnConstantsForHana.SOLD_TO_PARTY));

		}
	}

	public static void addParamForListValues(StringBuilder url, String workflowKey, List<String> values) {
		values.stream().forEach(value -> appendParamInUrl(url, workflowKey, value));
	}

	public static ResponseEntity<?> completeTaskInWorkflowUsingOauthClient(String taskId) {

		try {
			TimeUnit.SECONDS.sleep(2);

			HttpClient client = HttpClientBuilder.create().build();

			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			String url = ComConstants.WORKFLOW_REST_BASE_URL + "/v1/task-instances/" + taskId;
			String payload = "{\"context\": {},\"status\":\"COMPLETED\",\"priority\": \"MEDIUM\"}";

			HttpPatch httpPatch = new HttpPatch(url);
			httpPatch.addHeader("Authorization", "Bearer " + jwToken);
			httpPatch.addHeader("Content-Type", "application/json");

			try {
				StringEntity entity = new StringEntity(payload);
				entity.setContentType("application/json");
				httpPatch.setEntity(entity);
				HttpResponse response = client.execute(httpPatch);

				if (response.getStatusLine().getStatusCode() == HttpStatus.NO_CONTENT.value()) {
					logger.info("[completeTaskInWorkflowUsingOauthClient] response: " + response);
					return new ResponseEntity<String>("Task completed", HttpStatus.NO_CONTENT);
				} else {
					return new ResponseEntity<String>(getDataFromStream(response.getEntity().getContent()),
							HttpStatus.BAD_REQUEST);
				}

			} catch (IOException e) {
				// logger.error(e.getMessage());
				System.err.println(e.getMessage());
				return new ResponseEntity<String>("Exception failed " + e.getCause().getCause().getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			// logger.error(e.getMessage());
			System.err.println(e.getMessage());
			return new ResponseEntity<String>("Exception due to " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public static ResponseEntity<?> findAllidpUsers(Integer startIndex, Integer count) {
		try {
			if (startIndex != null && count != null) {

				Map<String, Object> map = DestinationReaderUtil
						.getDestination(ApplicationConstants.IDP_SERVICES_DESTINATION_NAME);

				HttpClient client = HttpClientBuilder.create().build();
				HttpGet httpGet = new HttpGet(
						map.get("URL") + "service/scim/Users?startIndex=" + startIndex + "&count=" + count);

				httpGet.addHeader("Content-Type", "application/json");

				// Encoding username and password
				String auth = encodeUsernameAndPassword((String) map.get("User"), (String) map.get("Password"));
				httpGet.addHeader("Authorization", auth);

				HttpResponse response = client.execute(httpGet);
				String dataFromStream = getDataFromStream(response.getEntity().getContent());
				System.err.println("dataFromStream : " + dataFromStream);
				if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

					JSONObject json = new JSONObject(dataFromStream);

					UserList userDetails = new UserList();
					userDetails.setTotalResults(json.getNumber("totalResults").toString());
					userDetails.setStartIndex(json.getNumber("startIndex").toString());
					userDetails.setItemsPerPage(json.getNumber("itemsPerPage").toString());

					List<UserInfo> listOfUser = new ArrayList<>();

					JSONArray arr = json.getJSONArray("Resources");
					for (int i = 0; i < arr.length(); i++) {

						JSONObject d = arr.getJSONObject(i);

						UserInfo data = new Gson().fromJson(d.toString(), UserInfo.class);

						// add custom attributes
						if (!d.isNull("urn:sap:cloud:scim:schemas:extension:custom:2.0:User")) {
							JSONArray att = d.getJSONObject("urn:sap:cloud:scim:schemas:extension:custom:2.0:User")
									.getJSONArray("attributes");
							List<UserAttributes> listOfAttributes = new ArrayList<>();
							if (att.length() > 0) {
								for (int j = 0; j < att.length(); j++) {
									JSONObject attribute = att.getJSONObject(j);
									UserAttributes userAtt = new UserAttributes();
									userAtt.setName(attribute.getString("name"));
									userAtt.setValue(attribute.getString("value"));
									listOfAttributes.add(userAtt);
								}
								data.setUserCustomAttributes(new UserCustom(listOfAttributes));
							}

						}

						listOfUser.add(data);

					}
					userDetails.setResources(listOfUser);

					return new ResponseEntity<>(userDetails, HttpStatus.OK);

				} else {
					return new ResponseEntity<>(FETCHING_FAILED, HttpStatus.CONFLICT);

				}
			} else {
				return new ResponseEntity<>(INVALID_INPUT_PLEASE_RETRY, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	/*
	 * public static ResponseEntity<?> createExcelForAllIasUsers(String
	 * sheetName, String fileName, Integer startIndex, Integer count) { try { if
	 * (startIndex != null && count != null && !checkString(sheetName) &&
	 * !checkString(fileName)) { Double countD = new Double(count); Double
	 * startIndexD = new Double(startIndex); ResponseEntity<?> responseFromIas =
	 * findAllidpUsersForExcel(startIndexD.intValue(), countD.intValue());
	 * 
	 * if (responseFromIas.getStatusCodeValue() == HttpStatus.OK.value()) {
	 * 
	 * IasUserList ias = (IasUserList) responseFromIas.getBody();
	 * List<IasResourceDto> listOfUsers = ias.getListOfUsers();
	 * 
	 * Double totalResult = ias.getTotalResults().doubleValue();
	 * 
	 * if (totalResult.intValue() != 0) { if (totalResult > (startIndexD *
	 * countD)) { for (int i = 0; i < Math.ceil(totalResult / (startIndex *
	 * count) - 1); i++) { startIndexD = startIndexD + countD; IasUserList
	 * iasTemp = (IasUserList) findAllidpUsersForExcel(startIndexD.intValue(),
	 * countD.intValue()).getBody();
	 * listOfUsers.addAll(iasTemp.getListOfUsers()); }
	 * 
	 * }
	 * 
	 * File file = new File(fileName + ".csv");
	 * 
	 * FileUtils.writeByteArrayToFile(file,
	 * IOUtils.toByteArray(ExcelUtils.writeToExcel(sheetName, listOfUsers)));
	 * 
	 * return ResponseEntity.ok() .header("Content-Disposition",
	 * "attachment; filename=" + fileName + ".csv")
	 * .contentLength(file.length()).contentType(MediaType.parseMediaType(
	 * "text/csv")) .body(new FileSystemResource(file)); } else { return new
	 * ResponseEntity<>("No data found", HttpStatus.NO_CONTENT); }
	 * 
	 * } else { return responseFromIas; } } else { return new
	 * ResponseEntity<>(INVALID_INPUT_PLEASE_RETRY, HttpStatus.BAD_REQUEST); } }
	 * catch (Exception e) { return new ResponseEntity<>(e.getMessage(),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * public static ResponseEntity<?> findAllidpUsersForExcel(Integer
	 * startIndex, Integer count) { try { if (startIndex != null && count !=
	 * null) {
	 * 
	 * Map<String, Object> map = DestinationReaderUtil
	 * .getDestination(DkshApplicationConstants.IDP_SERVICES_DESTINATION_NAME);
	 * 
	 * HttpClient client = HttpClientBuilder.create().build(); HttpGet httpGet =
	 * new HttpGet( map.get("URL") + "service/scim/Users?startIndex=" +
	 * startIndex + "&count=" + count);
	 * 
	 * httpGet.addHeader("Content-Type", "application/json");
	 * 
	 * // Encoding username and password String auth =
	 * encodeUsernameAndPassword((String) map.get("User"), (String)
	 * map.get("Password"));
	 * 
	 * httpGet.addHeader("Authorization", auth);
	 * 
	 * HttpResponse response = client.execute(httpGet); String dataFromStream =
	 * getDataFromStream(response.getEntity().getContent()); if
	 * (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
	 * 
	 * JSONObject json = new JSONObject(dataFromStream);
	 * 
	 * IasUserList iasUserDetails = new IasUserList();
	 * iasUserDetails.setTotalResults(json.getInt("totalResults"));
	 * iasUserDetails.setStartIndex(json.getInt("startIndex"));
	 * iasUserDetails.setItemsPerPage(json.getInt("itemsPerPage"));
	 * 
	 * List<IasResourceDto> listOfUsers = new ArrayList<>();
	 * 
	 * JSONArray arr = json.getJSONArray("Resources"); for (int i = 0; i <
	 * arr.length(); i++) {
	 * 
	 * IasResourceDto userDetails = new IasResourceDto();
	 * 
	 * JSONObject d = arr.getJSONObject(i);
	 * 
	 * userDetails.setPid(d.getString("id")); if (!d.isNull("name")) { if
	 * (!d.getJSONObject("name").isNull("givenName")) {
	 * userDetails.setFirstName(d.getJSONObject("name").getString("givenName"));
	 * } if (!d.getJSONObject("name").isNull("familyName")) {
	 * userDetails.setFirstName(d.getJSONObject("name").getString("familyName"))
	 * ; } } // if (!d.isNull("addresses") && //
	 * !d.getJSONArray("addresses").isEmpty() // && //
	 * d.getJSONArray("addresses").getJSONObject(0).isNull("country")) // { //
	 * userDetails.setCountry(d.getJSONArray("addresses").getJSONObject(0).
	 * getString("country")); // } if (!d.isNull("emails")) {
	 * userDetails.setEmail(d.getJSONArray("emails").getJSONObject(0).getString(
	 * "value")); } if (!d.isNull("groups")) { StringJoiner sj = new
	 * StringJoiner(","); JSONArray groupArr = d.getJSONArray("groups"); for
	 * (int g = 0; g < groupArr.length(); g++) {
	 * sj.add(groupArr.getJSONObject(g).getString("display")); }
	 * userDetails.setUserGroup(sj.toString()); }
	 * 
	 * // add custom attributes if
	 * (!d.isNull("urn:sap:cloud:scim:schemas:extension:custom:2.0:User")) {
	 * JSONArray att =
	 * d.getJSONObject("urn:sap:cloud:scim:schemas:extension:custom:2.0:User")
	 * .getJSONArray("attributes"); if (att.length() > 0) { for (int j = 0; j <
	 * att.length(); j++) { JSONObject attribute = att.getJSONObject(j);
	 * 
	 * if (attribute.getString("name").equalsIgnoreCase("CustomAttribute1")) {
	 * userDetails.setCustomAttribute1(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute2")) {
	 * userDetails.setCustomAttribute2(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute3")) {
	 * userDetails.setCustomAttribute3(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute4")) {
	 * userDetails.setCustomAttribute4(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute5")) {
	 * userDetails.setCustomAttribute5(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute6")) {
	 * userDetails.setCustomAttribute6(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute7")) {
	 * userDetails.setCustomAttribute7(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute8")) {
	 * userDetails.setCustomAttribute8(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute9")) {
	 * userDetails.setCustomAttribute9(attribute.getString("value")); } else if
	 * (attribute.getString("name").equalsIgnoreCase("CustomAttribute10")) {
	 * userDetails.setCustomAttribute10(attribute.getString("value")); } } }
	 * 
	 * } listOfUsers.add(userDetails); }
	 * iasUserDetails.setListOfUsers(listOfUsers);
	 * 
	 * return new ResponseEntity<>(iasUserDetails, HttpStatus.OK);
	 * 
	 * } else { return new ResponseEntity<>(FETCHING_FAILED,
	 * HttpStatus.CONFLICT);
	 * 
	 * } } else { return new ResponseEntity<>(INVALID_INPUT_PLEASE_RETRY,
	 * HttpStatus.BAD_REQUEST); } } catch (Exception e) { return new
	 * ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * } }
	 */

	public static ResponseEntity<?> findAllCountryTexts() {
		try {
			Map<String, Object> map = DestinationReaderUtil
					.getDestination(ApplicationConstants.IDP_SERVICES_DESTINATION_NAME);

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet httpGetForFetchingCountryMap = new HttpGet(map.get("URL") + "md/countries");

			HttpResponse responseFromFetchingCountryMap = client.execute(httpGetForFetchingCountryMap);
			if (responseFromFetchingCountryMap.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				String responseFromFetchingCountryString = getDataFromStream(
						responseFromFetchingCountryMap.getEntity().getContent());
				return new ResponseEntity<>(new JSONObject(responseFromFetchingCountryString).toMap(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(getDataFromStream(responseFromFetchingCountryMap.getEntity().getContent()),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			return new ResponseEntity<>("Exception due to " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

// public static ResponseEntity<?> sendSms(SmsSendingDto smsSendingDto) {
// try {
// if ((!checkString(smsSendingDto.getMobileNum())
// || (smsSendingDto.getMobileNumList() != null &&
// !smsSendingDto.getMobileNumList().isEmpty()))
// && !checkString(smsSendingDto.getFrom()) &&
// !checkString(smsSendingDto.getMessage())
// && !checkString(smsSendingDto.getReport()) &&
// !checkString(smsSendingDto.getMessageType())) {
//
// Map<String, Object> destinationMap = DestinationReaderUtil
// .getDestination(DkshApplicationConstants.SMS_SENDING_DESTINATION_NAME);
//
// HttpClient client = MySSLSocketFactory.getNewHttpClient();
// HttpPost post = new HttpPost((String) destinationMap.get("URL"));
//
// HttpHeaders headers = new HttpHeaders();
// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
// // add request parameter, form parameters
// List<NameValuePair> map = new ArrayList<>();
//
// map.add(new BasicHeader(DkshApplicationConstants.REPORT_KEY,
// smsSendingDto.getReport()));
// map.add(new BasicHeader(DkshApplicationConstants.SENDER_GROUP_NAME_KEY,
// smsSendingDto.getFrom()));
// map.add(new BasicNameValuePair(DkshApplicationConstants.CHARGE_KEY,
// (String) destinationMap.get(DkshApplicationConstants.CHARGE_KEY)));
// map.add(new BasicNameValuePair(DkshApplicationConstants.CMD_KEY,
// (String) destinationMap.get(DkshApplicationConstants.CMD_KEY)));
// map.add(new BasicNameValuePair(DkshApplicationConstants.CODE_KEY,
// (String) destinationMap.get(DkshApplicationConstants.CODE_KEY)));
// map.add(new BasicHeader(DkshApplicationConstants.MESSAGE_TYPE_KEY,
// smsSendingDto.getMessageType()));
// map.add(new BasicHeader(DkshApplicationConstants.MESSAGE_KEY,
// smsSendingDto.getMessageType().equalsIgnoreCase("UNICODE")
// ? convertStringToUTF16BEHexaValue(smsSendingDto.getMessage())
// : smsSendingDto.getMessage()));
//
// HttpResponse response = null;
// StringBuilder sbResponse = new StringBuilder();
// if (smsSendingDto.getMobileNumList() != null &&
// !smsSendingDto.getMobileNumList().isEmpty()) {
//
// logger.error("Num list : " + smsSendingDto.getMobileNumList());
//
// for (String number : smsSendingDto.getMobileNumList()) {
// map.add(new
// BasicNameValuePair(DkshApplicationConstants.RECIPIENT_MOBILE_NUM_KEY,
// number));
// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(map);
// post.setEntity(entity);
// logger.error("Entity : " + entity);
// response = client.execute(post);
// String dataFromStream = EntityUtils.toString(response.getEntity());
// if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
// JSONObject json = XML.toJSONObject(dataFromStream);
// if
// (json.getJSONObject("XML").get("STATUS").toString().equalsIgnoreCase("OK")) {
// sbResponse.append("SMS send successfully for Number : " + number + "; ");
// } else {
// sbResponse.append("Failed to send SMS for Number : " + number + " due to, "
// + json.getJSONObject("XML").get("DETAIL") + "; ");
// }
// } else {
// sbResponse.append("Failed to connect for Number : " + number + "; ");
// }
// }
// } else {
// map.add(new BasicHeader(DkshApplicationConstants.RECIPIENT_MOBILE_NUM_KEY,
// smsSendingDto.getMobileNum()));
// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(map);
// post.setEntity(entity);
// logger.error("Entity : " + entity);
// response = client.execute(post);
// String dataFromStream = EntityUtils.toString(response.getEntity());
// if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
// JSONObject json = XML.toJSONObject(dataFromStream);
// if
// (json.getJSONObject("XML").get("STATUS").toString().equalsIgnoreCase("OK")) {
// sbResponse.append("SMS send successfully for Number : " +
// smsSendingDto.getMobileNum());
// } else {
// sbResponse.append("Failed to send SMS for Number : " +
// smsSendingDto.getMobileNum()
// + " due to, " + json.getJSONObject("XML").get("DETAIL"));
// }
// } else {
// sbResponse.append("Failed to connect for Number : " +
// smsSendingDto.getMobileNum());
// }
// }
//
// return new ResponseEntity<>(sbResponse.toString(), HttpStatus.OK);
// } else {
// return new ResponseEntity<>(
// INVALID_INPUT_PLEASE_RETRY
// + " with from, message, message type, report and recipient mobile num.",
// HttpStatus.BAD_REQUEST);
// }
// } catch (Exception e) {
// return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG +
// e.getMessage(),
// HttpStatus.INTERNAL_SERVER_ERROR);
//
// }
// }
//
