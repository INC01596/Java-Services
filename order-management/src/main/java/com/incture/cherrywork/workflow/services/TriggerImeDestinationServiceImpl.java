package com.incture.cherrywork.workflow.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.DestinationReaderUtil;




@Service
@Transactional
public class TriggerImeDestinationServiceImpl implements TriggerImeDestinationService{
	
	
	public static final String IME_TOKEN = "IME_Token";
	public static final String TRIGGER_IME = "Trigger_IME";
	
	public static final String USER_NAME = "User";
	public static final String PASSWORD = "Password";
	public static final String URL = "URL";
	
	
	//public static final String 

	
	@Override
	public ResponseEntity triggerImeForBlockWorkflow(String salesOrderNumber){

		//	System.out.println("Trigger list Size :" + dtoList.size());
			int i=1;

				try {
					String xcsrfToken = null;
					List<String> cookies = null;
					
					Map<String, Object> map = DestinationReaderUtil.getDestination(IME_TOKEN);
					System.err.println("map for IME_TOKEN : " +map.toString());
					
					String url = (String) map.get(URL);
					URL urlObj = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
					String userpass = map.get(USER_NAME) + ":" + map.get(PASSWORD);
					String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
					connection.setRequestMethod("GET");
					connection.setRequestProperty("Authorization", auth);
					System.err.print("connection authorization "+connection.getRequestProperty("Authorization"));
					
					connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
					connection.setRequestProperty("X-CSRF-Token", "Fetch");
					connection.connect();
					if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
						
						Map<String, Object> messageMap = DestinationReaderUtil.getDestination(TRIGGER_IME);
						System.err.println("map for TRIGGER_IME : " +messageMap.toString());
						
						String mainUrl = (String) messageMap.get(URL);
					  URL mainObj = new URL(mainUrl);
						HttpURLConnection connection1 = (HttpURLConnection) mainObj.openConnection();
						xcsrfToken = connection.getHeaderField("X-CSRF-Token");
						
						System.err.println("xsrf-token" +xcsrfToken );
						cookies = connection.getHeaderFields().get("Set-Cookie");
						System.err.println("XSRF Token"+xcsrfToken);
						// SET COOKIES
						for (String cookie : cookies) {
							String tmp = cookie.split(";", 2)[0];
							connection1.addRequestProperty("Cookie", tmp);
						}
					    connection1.setRequestProperty("x-csrf-token", xcsrfToken);
						System.err.println("connection1 x-csrf-token "+connection1.getRequestProperty("x-csrf-token"));
						String userpass1 = messageMap.get(USER_NAME) + ":" + messageMap.get(PASSWORD);
						String auth1 = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass1.getBytes());
						connection1.setRequestMethod("POST");
						connection1.setRequestProperty("Authorization", auth1);
						System.err.println("connection1 authorization "+connection1.getRequestProperty("Authorization"));
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
						String withdraw="Automated";

		            //String payload= "{\"workflowDefinitionId\":\"dkshworkflowtrial\",\"businessKey\":\"123\",\"definitionId\":\"intermediatemessageevent2\",\"context\":{\"message\":\"true\"}}";
		            String payload= "{\"context\":{}, \"definitionId\":\"triggerPostDecisionSetsIME\", \"workflowDefinitionId\":\"blocktypedeterminationworkflow\",\"businessKey\":\"" + salesOrderNumber +"\"}";
		            
					System.err.println("Workflow "+i+" Payload :"+payload);
						DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
					 	dataOutputStream.write(payload.getBytes());
					 	dataOutputStream.flush();
						dataOutputStream.close();
						connection1.connect();
						System.err.println("Workflow "+i+" Response Code :"+connection1.getResponseCode());
						System.err.println("Workflow "+i+" Response :"+getDataFromStream(connection1.getInputStream()));
					}else {
						System.err.println("Else Trigger FAILURE ");
						return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber, ResponseStatus.FAILED);
					}
				} catch (Exception e) {
					System.err.println("Trigger FAILURE "+e.getMessage());
					return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber, ResponseStatus.FAILED);
				}
				
				return new ResponseEntity("salesOrderNumber", HttpStatus.OK, salesOrderNumber, ResponseStatus.SUCCESS);		
	}
	

	public String triggerIme(String decisionSetId) {
	//	System.out.println("Trigger list Size :" + dtoList.size());
		int i=1;

			try {
				String xcsrfToken = null;
				List<String> cookies = null;
				
				Map<String, Object> map = DestinationReaderUtil.getDestination(IME_TOKEN);
				System.err.println("map for IME_TOKEN : " +map.toString());
				
				String url = (String) map.get(URL);
				URL urlObj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
				String userpass = map.get(USER_NAME) + ":" + map.get(PASSWORD);
				String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Authorization", auth);
				System.err.println("connection authorization "+connection.getRequestProperty("Authorization"));
				
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				connection.setRequestProperty("X-CSRF-Token", "Fetch");
				connection.connect();
				if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
					
					Map<String, Object> messageMap = DestinationReaderUtil.getDestination(TRIGGER_IME);
					System.err.println("map for IME_TOKEN : " +map.toString());
					
					String mainUrl = (String) messageMap.get(URL);
				    URL mainObj = new URL(mainUrl);
					HttpURLConnection connection1 = (HttpURLConnection) mainObj.openConnection();
					xcsrfToken = connection.getHeaderField("X-CSRF-Token");
					
					System.err.print("xsrf-token" +xcsrfToken );
					cookies = connection.getHeaderFields().get("Set-Cookie");
					System.err.println("XSRF Token"+xcsrfToken);
					// SET COOKIES
					for (String cookie : cookies) {
						String tmp = cookie.split(";", 2)[0];
						connection1.addRequestProperty("Cookie", tmp);
					}
				    connection1.setRequestProperty("x-csrf-token", xcsrfToken);
					System.err.println("connection1 x-csrf-token "+connection1.getRequestProperty("x-csrf-token"));
					String userpass1 = messageMap.get(USER_NAME) + ":" + messageMap.get(PASSWORD);
					String auth1 = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass1.getBytes());
					connection1.setRequestMethod("POST");
					connection1.setRequestProperty("Authorization", auth1);
					System.err.println("connection1 authorization "+connection1.getRequestProperty("Authorization"));
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
					String withdraw="Automated";

	            //String payload= "{\"workflowDefinitionId\":\"dkshworkflowtrial\",\"businessKey\":\"123\",\"definitionId\":\"intermediatemessageevent2\",\"context\":{\"message\":\"true\"}}";
	            String payload= "{\"context\":{}, \"definitionId\":\"triggerPostLevelIME\", \"workflowDefinitionId\":\"decisionsetdetermination\",\"businessKey\":\"" + decisionSetId +"\"}";
	            
				System.err.println("Workflow "+i+" Payload :"+payload);
					DataOutputStream dataOutputStream = new DataOutputStream(connection1.getOutputStream());
				 	dataOutputStream.write(payload.getBytes());
					dataOutputStream.close();
					connection1.connect();
					System.err.println("Workflow "+i+" Response Code :"+connection1.getResponseCode());
					System.err.println("Workflow "+i+" Response :"+getDataFromStream(connection1.getInputStream()));
				}else {
					System.err.println("Else Trigger FAILURE ");
					return "Trigger FAILURE";
				}
				
			} catch (Exception e) {
				System.err.println("Trigger FAILURE "+e.getMessage());
				return "Trigger FAILURE ";
			}
			i++;
	//	}
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


	

//	private static String getXSRFToken(String requestURL, CloseableHttpClient client, HttpContext httpContext) {
//		HttpGet httpGet = null;
//		CloseableHttpResponse response = null;
//		String xsrfToken = null;
//		try { 
//			httpGet = new HttpGet(requestURL);
//			
//			String userpass = "P2000982477" + ":" + "3Pg13ec022";
//			String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
//
//			//String auth = "Basic UDIwMDA5ODI0Nzc6M1BnMTNlYzAyMg==";
//			httpGet.addHeader("Authorization", auth);
//			httpGet.addHeader("X-CSRF-Token", "Fetch");
//			response = client.execute(httpGet);
//			Header xsrfTokenheader = response.getFirstHeader("X-CSRF-Token");
//			if (xsrfTokenheader != null) {
//				xsrfToken = xsrfTokenheader.getValue();
//				
//				System.err.print("xsrfToken");
//			}
//			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (httpGet != null) {
//				httpGet.releaseConnection();
//			}
//			if (response != null) {
//				try {
//					response.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return xsrfToken;
//	}
//
//	private static CloseableHttpClient getHTTPClient() {
//		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
//		CloseableHttpClient httpClient = clientBuilder.build();
//		return httpClient;
//	}

}
