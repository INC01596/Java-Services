package com.incture.cherrywork.workflow.services;






import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

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
import org.hibernate.result.Output;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Repository
@Component
public class ImeTriggerImpl implements ImeTrigger {
	

	public String triggerIme(String salesOrderNo) {
	//	System.out.println("Trigger list Size :" + dtoList.size());
		int i=1;

		
		
		
		
			try {
				String xcsrfToken = null;
				List<String> cookies = null;
				//https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com 
				 //https://bpmworkflowruntimea2d6007ea-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances
						
				String url = "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";
				URL urlObj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
				String userpass = "P2000982477" + ":" + "3Pg13ec022";
				String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Authorization", auth);
				System.err.print("connection authorization "+connection.getRequestProperty("Authorization"));
				
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				connection.setRequestProperty("X-CSRF-Token", "Fetch");
				connection.connect();
				System.err.println("Response Code0:"+connection.getResponseCode());
				if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
					String mainUrl ="https://bpmworkflowruntimea2d6007ea-af91a028a.hana.ondemand.com/workflow-service/rest/v1/messages";
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
					System.err.print("connection1 x-csrf-token "+connection1.getRequestProperty("x-csrf-token"));
					
					String userpass1 = "P2000982477" + ":" + "3Pg13ec022";
					String auth1 = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass1.getBytes());
					connection1.setRequestMethod("POST");
					connection1.setRequestProperty("Authorization", auth1);
					System.err.print("connection1 authorization "+connection1.getRequestProperty("Authorization"));
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

					/*String payload = "{  \"context\": {\"obligationId\": \""+ contextDto.getObligationId() + "\",\"entityId\": \""+ contextDto.getEntityId() +
							"\",\"entityName\": \""+ contextDto.getEntityName() + "\",\"obligationType\": \""+ contextDto.getObligationType() + "\",\"obligationYear\": \""+ contextDto.getObligationYear() + 
						"\",\"obligationMetadata\": \""+ contextDto.getObligationMetadata() + "\",\"dueDate\": \""+ contextDto.getDueDate() + "\",\"accounts\": \""+ contextDto.getAccounts() + "\",\"exceptions\": \""+ contextDto.getExceptions() + "\",\"businessUnit\": \""+ contextDto.getBusinessUnit()+ "\",\"filingRequired\": \""+ contextDto.getFilingRequirement() + "\" },  \"definitionId\": \"deloitte_wf\"}";
					//String payload= "{\"definitionId\":\"dkshworkflowtrial\",\"context\":{\"entityId\":\"1010\",\"obligationId\":\"2017.C.01.1010\",\"entityName\":\"ABC Global Asset Management\",\"obligationType\":\"CRS\",\"obligationYear\":\"2017\",\"obligationMetadata\":\"Canada\",\"dueDate\":\"2018-05-01\",\"accounts\":\"811\",\"exceptions\":\"20\",\"businessUnit\":\"Asset Management\",\"filingRequired\":\"Pending\"}}";
					*/
	    String payload= "{\"workflowDefinitionId\":\"blocktypedeterminationworkflow\",\"businessKey\":\"123\",\"definitionId\":\"intermediatemessageevent3\",\"context\":{\"message\":\"true\"}}";
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


	
	public String execute( String salesOrderNo) throws ClientProtocolException, IOException {/*
		
		
		System.err.println("Inside The execute method ");
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();
		
		
		String url = "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";
		URL urlObj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		String userpass = "P2000982477" + ":" + "3Pg13ec022";
		String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", auth);
		System.err.print("connection authorization "+connection.getRequestProperty("Authorization"));
		
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("X-CSRF-Token", "Fetch");
		connection.setDoOutput(true);
		
		connection.connect();

		String rulesRuntimeUrl = "https://bpmrulesruntimerules-uk81qreeol.ap1.hana.ondemand.com/"; //url of bpmruntimerules picked from DKSH dev tenant
		String xsrfTokenUrl = rulesRuntimeUrl + "workflow-service/rest/v1/xsrf-token"; // sap cloud business rules api - v2 version for x-csrf token

		String invokeUrl ="https://bpmrulesruntimerules-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/messages";//sap cloud business rules api - v2 version for rule service (hits the specified rule) 
		
		URL invokeUrlmessage = new URL(invokeUrl);
		HttpURLConnection connection1 = (HttpURLConnection) invokeUrlmessage.openConnection();
		
		httpPost = new HttpPost(invokeUrl);
		
		httpPost.addHeader("Content-type", "application/json");

	
		//String xsrfToken = getXSRFToken(xsrfTokenUrl, httpClient, httpContext);
		
		String xsrfToken1= connection.getHeaderField("X-CSRF-Token");
		
		String userpass1 = "P2000982477" + ":" + "3Pg13ec022";
		String auth1 = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		connection1.setRequestMethod("POST");
		connection1.setRequestProperty("Authorization", auth);
		System.err.print("connection authorization "+connection.getRequestProperty("Authorization"));
		
		connection1.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection1.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection1.setRequestProperty("X-CSRF-Token",xsrfToken1 );
		connection1.setDoInput(false);
		connection1.setDoOutput(true);
		connection1.connect();

		
	
		
     try{
		// String userpass = "P2000982477" + ":" + "3Pg13ec022";
		//String auth = "Basic UDIwMDA5ODI0Nzc6M1BnMTNlYzAyMg==";
		//httpPost.addHeader("Authorization",auth); // header

		//String ruleInputString = input.toRuleInputString(rulesServiceId);
		//StringEntity stringEntity = new StringEntity(ruleInputString);

		String payload= "{\"workflowDefinitionId\":\"decisionsetworkflow\",\"businessKey\":\"5555\",\"definitionId\":\"intermediatemessageevent1\",\"context\":{\"message\":\"true\"}}";
		System.err.println("Workflow  Payload :"+payload);
		
		StringEntity stringEntity = new StringEntity(payload);
		
		httpPost.setEntity(stringEntity);

		response = httpClient.execute(httpPost);

		// process your response here
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InputStream inputStream = response.getEntity().getContent();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) > 0) {
			bytes.write(data, 0, length);
		}
		String respBody = new String(bytes.toByteArray(), "UTF-8");
		System.out.println(respBody);
		
		
		
		   OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        
	        
	        
		
	
			
	        connection1.disconnect();
	       
	    
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
		
		if(jsonString.toString()!=null){
			System.out.print("failure");
			return "failure";
		}
		
		
		System.err.print("respBody ="+respBody);
		return respBody;
		
		
     } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
    }
     return "successs";
	
	*/
		
		try {
			
			String xcsrfToken = null;
			List<String> cookies = null;
			//https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com 
			 //https://bpmworkflowruntimea2d6007ea-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances
					
			String url = "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			String userpass = "P2000982477" + ":" + "3Pg13ec022";
			String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", auth);
			System.err.print("connection authorization "+connection.getRequestProperty("Authorization"));
			
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("X-CSRF-Token", "Fetch");
			connection.connect();
			System.err.println("Response Code0:"+connection.getResponseCode());
			if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){

	       /* URL url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet"
	                + "&key=AIzaSyAhONZJpMCBqCfQjFUj21cR2klf6JWbVSo"
	                + "&access_token=" + access_token);*/
				String  mainUrl ="https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/messages";
				URL urlmain = new URL(mainUrl);
	        HttpURLConnection conn = (HttpURLConnection) urlmain.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");

	      //  String input = "{ \"snippet\": {\"playlistId\": \"WL\",\"resourceId\": {\"videoId\": \""+videoId+"\",\"kind\": \"youtube#video\"},\"position\": 0}}";
	        String payload= "{\"workflowDefinitionId\":\"decisionsetworkflow\",\"businessKey\":\"5555\",\"definitionId\":\"intermediatemessageevent1\",\"context\":{\"message\":\"true\"}}";
	        OutputStream os = conn.getOutputStream();
	        os.write(payload.getBytes());
	        os.flush();

	        System.err.print("status_code "+conn.getResponseCode());
	        
	        
	        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                + conn.getResponseCode() + conn.getResponseMessage());
	        }
          
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String output;
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	        }

	        if(output ==null ){
				return "success"; 
				}
	        conn.disconnect();

	      } }catch (MalformedURLException e) {

	        e.printStackTrace();

	      } catch (IOException e) {

	        e.printStackTrace();

	     }
		return "failure";
	
	}

	private static String getXSRFToken(String requestURL, CloseableHttpClient client, HttpContext httpContext) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try { 
			httpGet = new HttpGet(requestURL);
			
			String userpass = "P2000982477" + ":" + "3Pg13ec022";
			String auth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			//String auth = "Basic UDIwMDA5ODI0Nzc6M1BnMTNlYzAyMg==";
			httpGet.addHeader("Authorization", auth);
			httpGet.addHeader("X-CSRF-Token", "Fetch");
			response = client.execute(httpGet);
			Header xsrfTokenheader = response.getFirstHeader("X-CSRF-Token");
			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
				
				System.err.print("xsrfToken");
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
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}

	

}





