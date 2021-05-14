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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.WConstants.Constants;

import com.incture.cherrywork.sales.constants.ResponseStatus;

 

@SuppressWarnings("unused")
public class HelperClass {
	
	
	public static ResponseEntity<?> consumingOdataService(String url, String entity, String method,
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
		credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
			    new UsernamePasswordCredentials( (String) destinationInfo.get("User"), (String) destinationInfo.get("Password"))); 
		
		HttpClientBuilder clientBuilder =  HttpClientBuilder.create();
		
		clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort))
		   .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
		   .setDefaultCredentialsProvider(credsProvider) 
		   .disableCookieManagement();
		
		HttpClient httpClient = clientBuilder.build();
		HttpRequestBase httpRequestBase = null;
		HttpResponse httpResponse = null;
		StringEntity input = null;
		Header [] json = null;
		JSONObject obj = null;
		 String jwToken = DestinationReaderUtil.getConectivityProxy();
		if (url != null) {
			if (method.equalsIgnoreCase("GET")) {
				httpRequestBase = new HttpGet(url);
			} else if (method.equalsIgnoreCase("POST")) {
				httpRequestBase = new HttpPost(url);
				try {
					
					System.err.println("entity "+entity);
					input = new StringEntity(entity);
					input.setContentType("application/json");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				System.err.println("inputEntity "+ input);
				((HttpPost) httpRequestBase).setEntity(input);
			}
			if (destinationInfo.get("sap-client") != null) {
				httpRequestBase.addHeader("sap-client", (String) destinationInfo.get("sap-client"));
			}
			httpRequestBase.addHeader("accept", "application/json");
			
			Header[] headers = getAccessToken((String) destinationInfo.get("URL") + "/sap/opu/odata/sap/Z_SALESORDER_STATUS_SRV/likpSet(Vbeln='80000329')", (String) destinationInfo.get("User"),
					(String) destinationInfo.get("Password"),httpClient,  proxyHost, proxyPort,
					(String) destinationInfo.get("sap-client"),jwToken);
			String token = null;
			List<String> cookies = new ArrayList<>();
			if(headers.length != 0){
			
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
				httpRequestBase.setHeader("Proxy-Authorization","Bearer " +jwToken);
				httpRequestBase.addHeader("SAP-Connectivity-SCC-Location_ID","incture");
				
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
//			if (tenantctx != null) {
//				httpRequestBase.addHeader("SAP-Connectivity-ConsumerAccount",
//						tenantctx.getTenant().getAccount().getId());
//			}
			try {
				
				
				System.err.println("this is requestBase ============" + Arrays.asList(httpRequestBase));
				httpResponse = httpClient.execute(httpRequestBase);
				System.err.println(
						"com.incture.utils.HelperClass ============" + Arrays.asList(httpResponse.getAllHeaders()));
				System.err.println(
						"com.incture.utils.HelperClass ============" + httpResponse);
				System.err.println("STEP 4 com.incture.utils.HelperClass ============StatusCode from odata hit="
						+ httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 201) {
					json = httpResponse.getAllHeaders();
					jsonResponse =  httpResponse.getHeaders("sap-message");
				} else {
					String responseFromECC = httpResponse.getEntity().toString();
					
					System.err.println("responseFromEcc"+responseFromECC);
					
					
					return new ResponseEntity<String>("Failed to create , OutBound already created",HttpStatus.BAD_REQUEST);
				}

				System.err.println("STEP 5 Result from odata hit ============" + json);

			} catch (IOException e) {
				System.err.print("IOException : " + e);
				throw new IOException(
						"Please Check VPN Connection ......." + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

			try {
				
				System.err.println("jsonOutput"+json);

				System.err.println("jsonHeaderResponse"+jsonResponse);
				obj = new JSONObject(jsonResponse);
				 objresult  = obj.toString();
				
				
			} catch (JSONException e) {
				System.err.print("JSONException : check " + e + "JSON Object : " + json);
				
				throw new JSONException(
						"Exception occured during json conversion" + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

		}

		System.err.print(" object returned from odata " + obj);
		return new ResponseEntity<JSONObject>(obj,HttpStatus.OK);

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
		StringBuilder dataBuffer = new StringBuilder();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";

		while ((data = inStream.readLine()) != null) {
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
			String proxyHost, int proxyPort, String sapClient,String token)
			throws ClientProtocolException, IOException {

		
  
		HttpGet httpGet = new HttpGet(url);
		
       String userpass = username + ":" + password;
       
       httpGet.setHeader("Proxy-Authorization","Bearer " +token);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION,
                                        "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
		// Encoding username and password
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);
		httpGet.addHeader("SAP-Connectivity-SCC-Location_ID","incture");


		HttpResponse response = client.execute(httpGet);
		//HttpResponse response = client.execute( httpGet);
		
		System.err.println("313 response "+ response);

		// HttpResponse response = client.execute(httpGet);

		return response.getAllHeaders();

	}
	public ResponseEntity cancellingWorkflowUsingOauthClient(String workflowId) {

		try {
			String token = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

			HttpClient client = HttpClientBuilder.create().build();

			/*Map<String, Object> map = DestinationReaderUtil
					.getDestination(DkshConstants.WORKFLOW_CLOSE_TASK_DESTINATION);*/
			
			String url = Constants.WORKFLOW_REST_BASE_URL + "/v1/workflow-instances/" + workflowId;
			String payload = "{\"context\": {},\"status\":\"CANCELED\"}";

			HttpPatch httpPatch = new HttpPatch(url);
			httpPatch.addHeader("Authorization", "Bearer " + token);
			httpPatch.addHeader("Content-Type", "application/json");

			try {
				StringEntity entity = new StringEntity(payload);
				entity.setContentType("application/json");
				httpPatch.setEntity(entity);
				HttpResponse response = client.execute(httpPatch);

				if (response.getStatusLine().getStatusCode() == HttpStatus.NO_CONTENT.value()) {
					return new ResponseEntity(HttpStatus.NO_CONTENT);
				} else {
					return new ResponseEntity(HttpStatus.BAD_REQUEST);
				}

			} catch (IOException e) {
				//logger.error(e.getMessage());
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			//logger.error(e.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity getWorkflowInstanceUsingOauthClient(String bussinessKey) {

		try {
			HttpClient client = HttpClientBuilder.create().build();
			//Map<String, Object> map = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER_ID);
			
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			String url = Constants.WORKFLOW_REST_BASE_URL;
			System.err.println("url " + url);
			String trimmed = url.substring(8);
			//String userpass = map.get("User") + ":" + map.get("Password");
			//String encoding = javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

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
		JSONArray jsonArray = new JSONArray( response.getBody().toString());
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

			//Map<String, Object> map = DestinationReaderUtil
				//	.getDestination(DkshConstants.WORKFLOW_CLOSE_TASK_DESTINATION);
			
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

}

 
