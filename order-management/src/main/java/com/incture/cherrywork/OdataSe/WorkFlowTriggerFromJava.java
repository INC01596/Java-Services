package com.incture.cherrywork.OdataSe;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.util.DestinationReaderUtil;


public class WorkFlowTriggerFromJava {

	public String triggerWorkFlowAfterSavingToHana(ContextDto contextDto) {
		try {
			System.err.println("STEP 17 triggering workflow for " + contextDto.getSalesOrderNo());

			String xcsrfToken = null;
			List<String> cookies = null;
			String url = null;
			String auth = null;
			//Map<String, Object> destinationInfo = DestinationReaderUtil.getDestination(DkshConstants.WORKFLOW_TRIGGER);
			// Map<String, String> destinationInfo =
			// DestinationReaderUtil.getDestination("DKSHWorkflowInstance");
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			/*if (!destinationInfo.isEmpty()) {
				if (destinationInfo.get("URL") != null) {
					url = (String)destinationInfo.get("URL");
				}
			}*/
			// URL urlObj = new URL(url);
			// changes according to arun
                    url = Constants.WORKFLOW_REST_BASE_URL+"/v1/workflow-instances";
			//HttpContext httpContext = new BasicHttpContext();
			//httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
			HttpPost httpPost = null;
			CloseableHttpResponse responseClient = null;
			CloseableHttpClient httpClient = null;
			httpClient = getHTTPClient();

			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", "application/json");
			//String auth64bit = getAuth(destinationInfo);
			//String xsrfToken = getXSRFToken(url, auth64bit, httpClient, httpContext);
			System.err.println("STEP 18  xsrfToken  = " + jwToken);
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();

			String idb = gson.toJson(contextDto.getIdb());
			if (jwToken != null) {
				//httpPost.addHeader("X-CSRF-Token", xsrfToken); // header
				httpPost.addHeader("Authorization", "Bearer "+jwToken);
				String payload = "{\"definitionId\":\"" + contextDto.getDefinitionId()
						+ "\",\"context\":{\"salesOrderNo\":\"" + contextDto.getSalesOrderNo() + "\",\"requestId\":\""
						+ contextDto.getRequestId() + "\",\"distributionChannel\":\""
						+ contextDto.getDistributionChannel() + "\",\"country\":\"" + contextDto.getCountry()
						+ "\",\"salesDocType\":\"" + contextDto.getSalesOrderType() + "\",\"salesOrg\":\""
						+ contextDto.getSalesOrg() + "\",\"soCreatedECC\":\"" + contextDto.getSoCreatedECC()
						+ "\",\"customerPo\":\"" + contextDto.getCustomerPo() + "\",\"requestCategory\":\""
						+ contextDto.getRequestCategory() + "\",\"soldToParty\":\"" + contextDto.getSoldToParty()
						+ "\",\"shipToParty\":\"" + contextDto.getShipToParty() + "\",\"returnReason\":\""
						+ contextDto.getReturnReason() + "\",\"division\":\"" + contextDto.getDivision()
						+ "\",\"requestType\":\"" + contextDto.getRequestType() + "\",\"dataBtd\":\""+contextDto.getData()+
						"\",\"idb\":"+idb+",\"hdb\":\""+contextDto.getHdb().toString()+"\"}}";

				System.err.println("Workflow Payload :" + payload);

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
				System.err.println("STEP 19 Respose from workflow" + respBody);

				if (httpPost != null) {
					httpPost.releaseConnection();
				}
				if (httpClient != null) {
					httpClient.close();
				}
				return respBody;
			} else {
				System.err.println("trigger failure due to no XSCRF-Token not gernerated ");
				return "trigger failure due to no XSCRF-Token not gernerated ";
			}
			/*
			 * HttpURLConnection connection = (HttpURLConnection)
			 * urlObj.openConnection(); if(destinationInfo.get("User")!=null &&
			 * destinationInfo.get("Password")!=null){ String userpass =
			 * destinationInfo.get("User") + ":" +
			 * destinationInfo.get("Password"); auth = "Basic " +
			 * javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.
			 * getBytes());
			 * 
			 * } connection.setRequestMethod("GET");
			 * connection.setRequestProperty("Authorization", auth);
			 * connection.setRequestProperty("Content-Type",
			 * "application/json; charset=UTF-8");
			 * connection.setRequestProperty("X-CSRF-Token", "Fetch");
			 * connection.connect(); System.err.println("Response Code0:" +
			 * connection.getResponseCode()); if (HttpURLConnection.HTTP_OK ==
			 * connection.getResponseCode()) { HttpURLConnection connection1 =
			 * (HttpURLConnection) urlObj.openConnection();
			 * connection1.setRequestMethod("POST"); xcsrfToken =
			 * connection.getHeaderField("X-CSRF-Token"); cookies =
			 * connection.getHeaderFields().get("Set-Cookie");
			 * System.err.println("XSRF Token" + xcsrfToken); // SET COOKIES for
			 * (String cookie : cookies) { String tmp = cookie.split(";", 2)[0];
			 * connection1.addRequestProperty("Cookie", tmp); }
			 * 
			 * connection1.setRequestProperty("Authorization", auth);
			 * connection1.setRequestProperty("x-csrf-token", xcsrfToken);
			 * connection1.setRequestProperty("Content-Type",
			 * "application/json; charset=utf-8");
			 * connection1.setRequestProperty("Accept", "application/json");
			 * connection1.setRequestProperty("DataServiceVersion", "2.0");
			 * connection1.setRequestProperty("X-Requested-With",
			 * "XMLHttpRequest");
			 * connection1.setRequestProperty("Accept-Encoding",
			 * "gzip, deflate");
			 * connection1.setRequestProperty("Accept-Charset", "UTF-8");
			 * connection1.setDoInput(true); connection1.setDoOutput(true);
			 * connection1.setUseCaches(false);
			 * 
			 * String payload = "{\"definitionId\":\"" +
			 * contextDto.getDefinitionId() +
			 * "\",\"context\":{\"salesOrderNo\":\"" +
			 * contextDto.getSalesOrderNo() + "\",\"requestId\":\"" +
			 * contextDto.getRequestId() + "\",\"distributionChannel\":\"" +
			 * contextDto.getDistributionChannel() + "\",\"country\":\"" +
			 * contextDto.getCountry() + "\",\"salesDocType\":\"" +
			 * contextDto.getSalesOrderType() + "\"}}";
			 * 
			 * System.err.println("Workflow Payload :" + payload);
			 * 
			 * DataOutputStream dataOutputStream = new
			 * DataOutputStream(connection1.getOutputStream());
			 * dataOutputStream.write(payload.getBytes());
			 * dataOutputStream.flush(); dataOutputStream.close();
			 * connection1.connect();
			 * System.err.println("Workflow Response Code :" +
			 * connection1.getResponseCode());
			 * System.err.println("Workflow Response :" +
			 * getDataFromStream(connection1.getInputStream()));
			 * 
			 * } else { System.err.println("Else Trigger FAILURE "); }
			 */
		} catch (Exception e) {
			System.err.println("Trigger FAILURE " + e.getMessage());
			return "Trigger FAILURE" + e.getMessage();
		}

	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}

	@SuppressWarnings("unused")
	private static String getXSRFToken(String requestURL, String auth64bit, CloseableHttpClient client,
			HttpContext httpContext) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			httpGet = new HttpGet(requestURL);

			String auth = auth64bit;
			httpGet.addHeader("Authorization", auth);
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

	@SuppressWarnings("unused")
	private String getAuth(Map<String, Object> destinationInfo) {
		if (destinationInfo.get("User") != null && destinationInfo.get("Password") != null) {
			String userpass = destinationInfo.get("User") + ":" + destinationInfo.get("Password");
			return "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

		}
		return null;
	}

	@SuppressWarnings("unused")
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

}

