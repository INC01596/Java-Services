package com.incture.cherrywork.services;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.incture.cherrywork.util.AbbyApplicationConstants;
import com.incture.cherrywork.util.AbbyServiceUtil;
import com.incture.cherrywork.util.TokenGeneration;




@Service
public class AbbyOcrServiceImpl implements AbbyOcrService {

	private static final Logger logger = LoggerFactory.getLogger(AbbyOcrServiceImpl.class);
	@SuppressWarnings("unused")
	@Autowired
	private TokenGeneration tokenGeneration;

	@SuppressWarnings("deprecation")
	public String uploadDocToTransOld(File fileToUpload, String model) {
		// TODO Auto-generated method stub
		String finalResponse = "Error";
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			TokenGeneration t = new TokenGeneration();
			String token = t.generateAbbyyOCRToken(client);
			if (!token.toUpperCase().startsWith("error".toUpperCase())) {
				HttpPost httpPost = new HttpPost(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL);
				httpPost.setHeader("Authorization", "Bearer " + token);
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addPart("Files", new FileBody(fileToUpload, AbbyApplicationConstants.TYPE_PDF));
				builder.addPart("Model", new StringBody(model.toString(), ContentType.DEFAULT_TEXT));
				HttpEntity multipart = builder.build();
				httpPost.setEntity(multipart);
				CloseableHttpResponse response = client.execute(httpPost);
				System.out.println("response:"+response);

				String responseApiCall = AbbyServiceUtil.getDataFromStream(response.getEntity().getContent());
				System.out.println("responseApiCall:"+responseApiCall);
				JSONObject jsonObj = new JSONObject(responseApiCall);
				// transactionId
				logger.error("---------->" + jsonObj);
				String transactionId = jsonObj.getString("transactionId");
				String fileName = fileToUpload.getName();
				String fileId = null;
				logger.error("---------->" + fileName.toUpperCase().contains("MANUAL"));
				if (!fileName.isEmpty() && fileName.toUpperCase().contains("MANUAL")) {
					TimeUnit.SECONDS.sleep(10);
					fileId = getTransactionDetails(client, token, transactionId);
					return fileId;
				} else {
					TimeUnit.SECONDS.sleep(20);
					fileId = getTransactionDetails(client, token, transactionId);
					if (!fileId.toUpperCase().startsWith("OCR".toUpperCase()) && !fileId.startsWith("http")) {
						finalResponse = getDocumentDetails(client, token, transactionId, fileId);
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return finalResponse;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public String uploadDocToTrans(File fileToUpload, String model) {
		// TODO Auto-generated method stub
		String finalResponse = "Error";
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			TokenGeneration t = new TokenGeneration();
			String token = t.generateAbbyyOCRToken(client);
			logger.error("token "+token);
			if (!token.toUpperCase().startsWith("error".toUpperCase())) {
				HttpPost httpPost1 = new HttpPost(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL);
				httpPost1.setHeader("Authorization", "Bearer " + token);
				httpPost1.setHeader("Content-Type", "application/json");
				String payload = "{\"skillId\":\""+model.toString()+"\"}";
				logger.error("payload: "+payload);
		        StringEntity entity = new StringEntity(payload,
		                ContentType.APPLICATION_JSON);
		        logger.error("entity:"+entity);

		        httpPost1.setEntity(entity);
				CloseableHttpResponse response1 = client.execute(httpPost1);
				logger.error("response1:"+response1);

				String responseApiCall1 = AbbyServiceUtil.getDataFromStream(response1.getEntity().getContent());
				logger.error("responseApiCall1:"+responseApiCall1);
				JSONObject jsonObj1 = new JSONObject(responseApiCall1);
				// transactionId
				logger.error("jsonObj1---------->" + jsonObj1);
				String transactionId = jsonObj1.getString("transactionId");
				
				HttpPost httpPost2 = new HttpPost(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL+ "/" + transactionId+ "/files");
				httpPost2.setHeader("Authorization", "Bearer " + token);
				List<NameValuePair> nameValuePairs2=new ArrayList<NameValuePair>();

				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addPart("Files", new FileBody(fileToUpload, AbbyApplicationConstants.TYPE_PDF));
				builder.addPart("Model", new StringBody(model.toString(), ContentType.DEFAULT_TEXT));
				HttpEntity multipart = builder.build();
				httpPost2.setEntity(multipart);
				CloseableHttpResponse response2 = client.execute(httpPost2);
				logger.error("response2:"+response2);

				HttpPost httpPost3 = new HttpPost(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL+ "/" + transactionId+ "/start");
				httpPost3.setHeader("Authorization", "Bearer " + token);
				CloseableHttpResponse response3 = client.execute(httpPost3);
				logger.error("response3:"+response3);

				/*String responseApiCall2 = ServiceUtil.getDataFromStream(response2.getEntity().getContent());
				System.out.println("responseApiCall2......................:"+responseApiCall2);
				JSONObject jsonObj2 = new JSONObject(responseApiCall2);
				logger.error("jsonObj2---------->" + jsonObj2);
*/
				
				String fileName = fileToUpload.getName();
				logger.error("fileName......................:"+fileName);

				String fileId = null;
				logger.error("---------->" + fileName.toUpperCase().contains("MANUAL"));
				if (!fileName.isEmpty() && fileName.toUpperCase().contains("MANUAL")) {
					TimeUnit.SECONDS.sleep(10);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId if......................:"+fileId);

					//return fileId;
				} else {
					TimeUnit.SECONDS.sleep(20);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId else......................:"+fileId);

					//if (fileId.toUpperCase().startsWith("OCR".toUpperCase())) {
						finalResponse = getDocumentDetails(client, token, transactionId, fileId);
						logger.error("finalResponse ......................:"+finalResponse);

					//}
						
				}
				
			}
			/*Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        JsonParser jp = new JsonParser();
	        JsonElement je = jp.parse(finalResponse);
	        String prettyJsonString = gson.toJson(je);


	        PDDocument document = new PDDocument();
	        PDPage page = new PDPage();
	        document.addPage(page);

	        PDPageContentStream contentStream = new PDPageContentStream(document, page);

	        contentStream.setFont(PDType1Font.COURIER, 12);
	        contentStream.beginText();
	        contentStream.showText(finalResponse);
	        contentStream.endText();
	        contentStream.close();

	        document.save("InvoiceFromJsonToPDF.pdf");
	        document.close();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .body(document);*/
			return finalResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	@SuppressWarnings("deprecation")
	public String getDocumentDetails(CloseableHttpClient client, String token, String transactionId, String fileId) {
		try {
					// Trim the given string       
				String	fileIdNew = fileId.trim();           
					// Replace All space (unicode is \\s) to %20    
				fileIdNew = fileIdNew.replaceAll("\\s", "%20");         
					// Display the result      
					System.out.println(fileIdNew);    
			HttpGet httpGet = new HttpGet(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId + "/files/"
					+ fileIdNew + "/download");
			httpGet.setHeader("Authorization", "Bearer " + token);
			logger.error("ExecuteGetApi.executeGetApi()->httpGet::" + httpGet.getParams());
			logger.error("ExecuteGetApi.executeGetApi()->httpGet url to be called::" + httpGet.getURI());
			HttpResponse response = client.execute(httpGet);
			String responseApiCall = AbbyServiceUtil.getDataFromStream(response.getEntity().getContent());
			logger.error("ExecuteGetApi.executeGetApi()->responseApiCall::" + responseApiCall);
			JSONObject json = new JSONObject(responseApiCall);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	public String getTransactionDetails(CloseableHttpClient client, String token, String transactionId) {
		String fileId = null;
		@SuppressWarnings("unused")
		String manualReviewLink = null;
		try {
			HttpGet httpGet = new HttpGet(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId);
			httpGet.setHeader("Authorization", "Bearer " + token);
			logger.error("ExecuteGetApi.executeGetApi()->httpGet::" + httpGet.getParams());
			logger.error("ExecuteGetApi.executeGetApi()->httpGet url to be called::" + httpGet.getURI());
			HttpResponse response = client.execute(httpGet);
			String responseApiCall = AbbyServiceUtil.getDataFromStream(response.getEntity().getContent());
			logger.error("ExecuteGetApi.executeGetApi()->responseApiCall::" + responseApiCall);
			JSONObject json = new JSONObject(responseApiCall);
			if (json.getString("status").equalsIgnoreCase("Processed")) {//Processed
				JSONArray documanetArray = json.getJSONArray("documents");
				
				JSONObject documentObject = documanetArray.getJSONObject(0);
				fileId = documentObject.getJSONArray("resultFiles").getJSONObject(0).getString("fileId");
				return fileId;
			} else if (json.has("manualReviewLink")) {
				manualReviewLink = json.getString("manualReviewLink");
				return json.toString();
			}else if (json.getString("status").equalsIgnoreCase("Processing")){
				return getTransactionDetails(client, token, transactionId);
			}
				else {
				return "OCR Pending";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fileId;
	}
	

	@Override
	public String getTranDocumentDetails(String transactionId) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			TokenGeneration t = new TokenGeneration();
			String token = t.generateAbbyyOCRToken(client);
			String fileId = getTransactionDetails(client, token, transactionId);
			logger.error("fileid ::::"+fileId);
			String mainResponse = null;
			if(!fileId.isEmpty() && !fileId.startsWith("{")){
				logger.error("fileid ::::"+fileId);
				mainResponse = getDocumentDetails(client, token, transactionId, fileId);
				logger.error("mainResponse ::::"+mainResponse);
			}else{
				TimeUnit.SECONDS.sleep(20);
				mainResponse = getTranDocumentDetails(transactionId);
				logger.error("mainResponse ::::"+mainResponse);
			}
			return mainResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

}
