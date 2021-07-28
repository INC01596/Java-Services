package com.incture.cherrywork.workflow.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DestinationReaderUtil;

@Service
@Transactional
public class TriggerImeDestinationServiceImpl implements TriggerImeDestinationService {

	public static final String XCSRF_TOKEN = "X-CSRF-Token";
	public static final String DESTINATION_RULES_TOKEN = "rulestoken";
	public static final String DESTINATION_RULES = "Rules_V2";
	public static final String USER = "User";
	public static final String PASSWORD = "Password";
	public static final String URL = "URL";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String FETCH = "Fetch";
	public static final String IME_TOKEN = "IME_Token";
	public static final String TRIGGER_IME = "Trigger_IME";
	public static final String USER_NAME = "User";

	// public static final String

	@Override
	public ResponseEntity triggerImeForBlockWorkflow(String salesOrderNumber) {

		// System.out.println("Trigger list Size :" + dtoList.size());
		int i = 1;

		try {
			String xcsrfToken = null;
			List<String> cookies = null;

			Map<String, Object> map = DestinationReaderUtil.getDestination(IME_TOKEN);
			System.err.println("map for IME_TOKEN : " + map.toString());

			String url = (String) map.get(URL);
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			String userpass = map.get(USER_NAME) + ":" + map.get(PASSWORD);
			String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", auth);
			System.err.print("connection authorization " + connection.getRequestProperty("Authorization"));

			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("X-CSRF-Token", "Fetch");
			connection.connect();
			if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {

				Map<String, Object> messageMap = DestinationReaderUtil.getDestination(TRIGGER_IME);
				System.err.println("map for TRIGGER_IME : " + messageMap.toString());

				String mainUrl = (String) messageMap.get(URL);
				URL mainObj = new URL(mainUrl);
				HttpURLConnection connection1 = (HttpURLConnection) mainObj.openConnection();
				xcsrfToken = connection.getHeaderField("X-CSRF-Token");

				System.err.println("xsrf-token" + xcsrfToken);
				cookies = connection.getHeaderFields().get("Set-Cookie");
				System.err.println("XSRF Token" + xcsrfToken);
				// SET COOKIES
				for (String cookie : cookies) {
					String tmp = cookie.split(";", 2)[0];
					connection1.addRequestProperty("Cookie", tmp);
				}
				connection1.setRequestProperty("x-csrf-token", xcsrfToken);
				System.err.println("connection1 x-csrf-token " + connection1.getRequestProperty("x-csrf-token"));
				String userpass1 = messageMap.get(USER_NAME) + ":" + messageMap.get(PASSWORD);
				String auth1 = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass1.getBytes());
				connection1.setRequestMethod("POST");
				connection1.setRequestProperty("Authorization", auth1);
				System.err.println("connection1 authorization " + connection1.getRequestProperty("Authorization"));
				connection1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				connection1.setRequestProperty("Accept", "application/json");
				connection1.setRequestProperty("DataServiceVersion", "2.0");
				connection1.setRequestProperty("X-Requested-With", "XMLHttpRequest");
				connection1.setRequestProperty("Accept-Encoding", "gzip, deflate");
				connection1.setRequestProperty("Accept-Charset", "UTF-8");
				connection1.setDoInput(true);
				connection1.setDoOutput(true);
				connection1.setUseCaches(false);

				System.err.println("test");
				String withdraw = "Automated";

				// String payload=
				// "{\"workflowDefinitionId\":\"dkshworkflowtrial\",\"businessKey\":\"123\",\"definitionId\":\"intermediatemessageevent2\",\"context\":{\"message\":\"true\"}}";
				String payload = "{\"context\":{}, \"definitionId\":\"triggerPostDecisionSetsIME\", \"workflowDefinitionId\":\"blocktypedeterminationworkflow\",\"businessKey\":\""
						+ salesOrderNumber + "\"}";

				System.err.println("Workflow " + i + " Payload :" + payload);
				DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
				dataOutputStream.write(payload.getBytes());
				dataOutputStream.flush();
				dataOutputStream.close();
				connection1.connect();
				System.err.println("Workflow " + i + " Response Code :" + connection1.getResponseCode());
				System.err.println("Workflow " + i + " Response :" + getDataFromStream(connection1.getInputStream()));
			} else {
				System.err.println("Else Trigger FAILURE ");
				return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			System.err.println("Trigger FAILURE " + e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity("salesOrderNumber", HttpStatus.OK, salesOrderNumber, ResponseStatus.SUCCESS);
	}

	@Override
	public String triggerIme(String decisionSetId) {
		// System.out.println("Trigger list Size :" + dtoList.size());
		System.err.println("[triggerIme] decisionSetId: " + decisionSetId);
		int i = 1;

		HttpURLConnection connection1 = null;
		try {
			String xcsrfToken = null;
			List<String> cookies = null;

			// Map<String, Object> map =
			// com.incture.utils.DestinationReaderUtil.getDestination(IME_TOKEN);
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			System.err.println("map for IME_TOKEN : " + jwToken);

			String url = ComConstants.WORKFLOW_REST_BASE_URL + "/v1/messages";
			URL mainObj = new URL(url);
			if (jwToken != null) {

				connection1 = (HttpURLConnection) mainObj.openConnection();

				connection1.setRequestMethod("POST");
				connection1.setRequestProperty("Authorization", "Bearer " + jwToken);
				System.err.println("connection1 authorization " + connection1.getRequestProperty("Authorization"));
				connection1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				connection1.setRequestProperty("Accept", "application/json");
				connection1.setRequestProperty("DataServiceVersion", "2.0");
				connection1.setRequestProperty("X-Requested-With", "XMLHttpRequest");
				// connection1.setRequestProperty("Accept-Encoding", "gzip,
				// deflate");
				connection1.setRequestProperty("Accept-Charset", "UTF-8");
				connection1.setDoInput(true);
				connection1.setDoOutput(true);
				connection1.setUseCaches(false);

				System.err.println("test");

				// String payload=
				// "{\"workflowDefinitionId\":\"dkshworkflowtrial\",\"businessKey\":\"123\",\"definitionId\":\"intermediatemessageevent2\",\"context\":{\"message\":\"true\"}}";
				String payload = "{\"context\":{}, \"definitionId\":\"triggerPostLevelIME\", \"workflowDefinitionId\":\"decisionsetdetermination\",\"businessKey\":\""
						+ decisionSetId + "\"}";

				System.err.println("Workflow " + i + " Payload :" + payload);
				DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
				dataOutputStream.write(payload.getBytes());
				dataOutputStream.flush();
				connection1.connect();
				dataOutputStream.close();
				System.err.println("Workflow " + i + " Response Code :" + connection1.getResponseCode());
				System.err.println("Workflow " + i + " Response :" + getDataFromStream(connection1.getInputStream()));
			} else {
				System.err.println("Else Trigger FAILURE ");
				return "Trigger FAILURE";
			}

		} catch (Exception e) {
			System.err.println("Trigger FAILURE " + e.getMessage());
			return "Trigger FAILURE ";
		}
		i++;
		// }
		return "Workflow triggered successfully";
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

	public ResponseEntity triggerImePostDS(String salesOrderNumber) throws URISyntaxException, IOException {

		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();

		Map<String, Object> map = DestinationReaderUtil.getDestination(TRIGGER_IME);
		System.err.println("map for rulesToken : " + map.toString());

		httpPost = new HttpPost((String) map.get(URL));
		httpPost.addHeader(CONTENT_TYPE, "application/json; charset=utf-8");

		String xsrfToken = getXSRFToken(httpClient);

		System.err.println("xsrfToken : " + xsrfToken);
		String payload = new String();
		if (xsrfToken != null) {
			httpPost.addHeader(XCSRF_TOKEN, xsrfToken); // header
			payload = "{\"context\":{}, \"definitionId\":\"triggerPostDecisionSetsIME\", \"workflowDefinitionId\":\"blocktypedeterminationworkflow\",\"businessKey\":\""
					+ salesOrderNumber + "\"}";
		}

		String auth = map.get(USER) + ":" + map.get(PASSWORD);
		String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
		httpPost.addHeader(AUTHORIZATION, "Basic " + encoding); // header

		StringEntity stringEntity;
		String respBody;
		try {
			stringEntity = new StringEntity(payload);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			System.err.println("response : " + response);

			// process your response here
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			InputStream inputStream = response.getEntity().getContent();
			byte[] data = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(data)) > 0) {
				bytes.write(data, 0, length);
			}
			respBody = new String(bytes.toByteArray(), "UTF-8");
			// clean-up sessions
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			if (response != null) {
				response.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (UnsupportedEncodingException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		} catch (UnsupportedOperationException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		} catch (IOException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity("salesOrderNumber", HttpStatus.OK, salesOrderNumber + " : " + respBody,
				ResponseStatus.SUCCESS);
	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}
	
	private String getXSRFToken(CloseableHttpClient client) throws URISyntaxException {

		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination(IME_TOKEN);
			System.err.println("map for Rules_V2 : " + map.toString());

			// setting
			httpGet = new HttpGet((String) map.get(URL));
			String auth = map.get(USER) + ":" + map.get(PASSWORD);
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());

			System.err.println(httpGet);

			httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);
			httpGet.addHeader(XCSRF_TOKEN, FETCH);

			response = client.execute(httpGet);

			System.err.println("response token: " + response);

			Header xsrfTokenheader = response.getFirstHeader(XCSRF_TOKEN);
			System.err.println("xsrfTokenheader : " + xsrfTokenheader);

			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
				System.err.println("xsrfToken value : " + xsrfToken);
			}
		} catch (ClientProtocolException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
		} catch (IOException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					//logger.error(e.getMessage());
					System.err.println(e.getMessage());
				}
			}
		}
		return xsrfToken;
	}


	
	// private static CloseableHttpClient getHTTPClient() {
	// HttpClientBuilder clientBuilder = HttpClientBuilder.create();
	// CloseableHttpClient httpClient = clientBuilder.build();
	// return httpClient;
	// }

}
