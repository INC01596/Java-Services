package com.incture.cherrywork.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.util.AbbyApplicationConstants;
import com.incture.cherrywork.util.AbbyServiceUtil;
import com.incture.cherrywork.util.OCRConfigurationConstants;
import com.incture.cherrywork.util.TokenGeneration;

@Service
@Transactional
public class AbbyOcrServiceImpl implements AbbyOcrService {

	private static final Logger logger = LoggerFactory.getLogger(AbbyOcrServiceImpl.class);
	
	@Autowired
	private TokenGeneration tokenGeneration;

	
	
	
	@Autowired
	private SalesOrderHeaderService services;
	
	
	
	
	@Autowired
	private AttachmentService dbFileStorageService;

	
	
public ResponseEntity<Object> uploadDocToTrans(byte[] array) {
		logger.error("[AbbyOcrServiceImpl][uploadDocToTrans] Started:");
		HashMap<String, String> finalResponse = new HashMap<>();
		String model = "236b51f2-c613-4aa0-b8a4-055693bd3bc0";
		
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			TokenGeneration t = new TokenGeneration();
			String token = t.generateAbbyyOCRToken(client);
			logger.error("token " + token);
			if (!token.toUpperCase().startsWith("error".toUpperCase())) {
				logger.error("createTranscrionId");
				HttpPost httpPost1 = new HttpPost(OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL);
				httpPost1.setHeader("Authorization", "Bearer " + token);
				httpPost1.setHeader("Content-Type", "application/json");

				String payload = "{\"skillId\": \"" + model + "\"}";
				logger.error("payload:" + payload);

				StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
				logger.error("entity:" + entity);

				httpPost1.setEntity(entity);
				CloseableHttpResponse response1 = client.execute(httpPost1);
				logger.error("response:" + response1);

				String responseApiCall = AbbyServiceUtil.getDataFromStream(response1.getEntity().getContent());
				logger.error("responseApiCall:" + responseApiCall);

				JSONObject jsonObj = new JSONObject(responseApiCall);
				logger.error("Transcation Id" + jsonObj);

				String transactionId = jsonObj.getString("transactionId");

				
					
				HttpPost httpPost2 = new HttpPost(OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId + "/files");

	                httpPost2.setHeader("Authorization", "Bearer " + token);
	               String FILEPATH = "po1.pdf";
	      	     File file = new File(FILEPATH);
	      	     try {
	      	    	logger.error("[Creating File from byte[]");
	      	            // Initialize a pointer
	      	            // in file using OutputStream
	      	            OutputStream
	      	                os
	      	                = new FileOutputStream(file);
	      	  
	      	            // Starts writing the bytes in it
	      	            os.write(array);
	      	            System.out.println("Successfully"
	      	                               + " byte inserted");
	      	  
	      	            // Close the file
	      	            os.close();
	      	        }
	      	  
	      	        catch (Exception e) {
	      	        	
	      	        	
	      	        }

					MultipartEntityBuilder builder = MultipartEntityBuilder.create();
					builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
					builder.addPart("Files", new FileBody(file));
					
					String payload1 = "{ \"files\": [{\"imageProcessingOptions\": {\"autoCrop\": \"Default\",\"autoOrientation\": \"Default\"}}]}";
					logger.error("payload:" + payload1);
					
					builder.addPart("Model", new StringBody(payload1, ContentType.APPLICATION_JSON));

					HttpEntity multipart = builder.build();
					httpPost2.setEntity(multipart);
					CloseableHttpResponse response2 = client.execute(httpPost2);
					if (response2.getStatusLine().getStatusCode() == 200){
						logger.error("result.getStatusLine:" + response2.getStatusLine());
						
					} else{
						logger.error("[uploadFileToOCR] : file upload failed");
						
					}
				
			
			
				
				
				
				
				//Starting the transaction
					HttpPost httpPost3 = new HttpPost(
							OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId + "/start");
					httpPost3.setHeader("Authorization", "Bearer " + token);
				System.err.println("httPost3 "+httpPost3);
				CloseableHttpResponse response3 = client.execute(httpPost3);
				logger.error("response3:"+response3);
				//client2.close();

				String responseApiCall2 = AbbyServiceUtil.getDataFromStream(response2.getEntity().getContent());
				System.out.println("responseApiCall2......................:" + responseApiCall2);
			
				String fileName =file.getName();
			   String fileId = null;
				
				if (!fileName.isEmpty() && fileName.toUpperCase().contains("MANUAL")) {
					TimeUnit.SECONDS.sleep(10);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId if......................:" + fileId);

					
				} else {
					TimeUnit.SECONDS.sleep(20);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId else......................:" + fileId);
                   return getDocumentDetails(client, token, transactionId, fileId);
					//logger.error("finalResponse ......................:" + finalResponse);
					
					

				}

			}
			

		client.close();
			return ResponseEntity.ok().body(finalResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public ResponseEntity<Object> uploadDocToTrans(File file) {
		
		HashMap<String, String> finalResponse = new HashMap<>();
		String model = "236b51f2-c613-4aa0-b8a4-055693bd3bc0";
		
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			TokenGeneration t = new TokenGeneration();
			String token = t.generateAbbyyOCRToken(client);
			logger.error("token " + token);
			if (!token.toUpperCase().startsWith("error".toUpperCase())) {
				logger.error("createTranscrionId");
				HttpPost httpPost1 = new HttpPost(OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL);
				httpPost1.setHeader("Authorization", "Bearer " + token);
				httpPost1.setHeader("Content-Type", "application/json");

				String payload = "{\"skillId\": \"" + model + "\"}";
				logger.error("payload:" + payload);

				StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
				logger.error("entity:" + entity);

				httpPost1.setEntity(entity);
				CloseableHttpResponse response1 = client.execute(httpPost1);
				logger.error("response:" + response1);

				String responseApiCall = AbbyServiceUtil.getDataFromStream(response1.getEntity().getContent());
				logger.error("responseApiCall:" + responseApiCall);

				JSONObject jsonObj = new JSONObject(responseApiCall);
				logger.error("Transcation Id" + jsonObj);

				String transactionId = jsonObj.getString("transactionId");

				
					HttpPost httpPost2 = new HttpPost(OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId + "/files");

	               httpPost2.setHeader("Authorization", "Bearer " + token);
	               System.err.println("byteArray "+dbFileStorageService.getFileByReturnReqNum1("234").toString());
	              


					MultipartEntityBuilder builder = MultipartEntityBuilder.create();
					builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
					builder.addPart("Files", new FileBody(file));
					
					String payload1 = "{ \"files\": [{\"imageProcessingOptions\": {\"autoCrop\": \"Default\",\"autoOrientation\": \"Default\"}}]}";
					logger.error("payload:" + payload1);
					
					builder.addPart("Model", new StringBody(payload1, ContentType.APPLICATION_JSON));

					HttpEntity multipart = builder.build();
					httpPost2.setEntity(multipart);
					CloseableHttpResponse response2 = client.execute(httpPost2);
					if (response2.getStatusLine().getStatusCode() == 200){
						logger.error("result.getStatusLine:" + response2.getStatusLine());
						
					} else{
						logger.error("[uploadFileToOCR] : file upload failed");
						
					}
				
			
			
				
				
				
				
				//Starting the transaction
					HttpPost httpPost3 = new HttpPost(
							OCRConfigurationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId + "/start");
					httpPost3.setHeader("Authorization", "Bearer " + token);
				System.err.println("httPost3 "+httpPost3);
				CloseableHttpResponse response3 = client.execute(httpPost3);
				logger.error("response3:"+response3);
				//client2.close();

				String responseApiCall2 = AbbyServiceUtil.getDataFromStream(response2.getEntity().getContent());
				System.out.println("responseApiCall2......................:" + responseApiCall2);
			
				String fileName =file.getName();
			   String fileId = null;
				
				if (!fileName.isEmpty() && fileName.toUpperCase().contains("MANUAL")) {
					TimeUnit.SECONDS.sleep(10);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId if......................:" + fileId);

					
				} else {
					TimeUnit.SECONDS.sleep(20);
					fileId = getTransactionDetails(client, token, transactionId);
					logger.error("fileId else......................:" + fileId);
                   return getDocumentDetails(client, token, transactionId, fileId);
					//logger.error("finalResponse ......................:" + finalResponse);
					
					

				}

			}
			

		client.close();
			return ResponseEntity.ok().body(finalResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@SuppressWarnings("deprecation")
	public ResponseEntity<Object> getDocumentDetails(CloseableHttpClient client, String token, String transactionId,
			String fileId) {
		try {
			// Trim the given string
			String fileIdNew = fileId.trim();
			SalesOrderHeaderItemDto dto = new SalesOrderHeaderItemDto();
			SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
			List<SalesOrderItemDto> lineItemList = new ArrayList<>();
			SalesOrderItemDto linedto = new SalesOrderItemDto();
			SalesOrderItemDto linedto1 = new SalesOrderItemDto();

			// Replace All space (unicode is \\s) to %20
			fileIdNew = fileIdNew.replaceAll("\\s", "%20");
			// Display the result
			// System.out.println(fileIdNew);
			HttpGet httpGet = new HttpGet(AbbyApplicationConstants.ABBYY_TRANSACTIONS_URL + "/" + transactionId
					+ "/files/" + fileIdNew + "/download");
			httpGet.setHeader("Authorization", "Bearer " + token);
			logger.error("ExecuteGetApi.executeGetApi()->httpGet::" + httpGet.getParams());
			logger.error("[AbbyOcrServiceImpl][getDocumentDetails]->httpGet url to be called::" + httpGet.getURI());
			HttpResponse response = client.execute(httpGet);
			logger.info("[AbbyOcrServiceImpl][getDocumentDetails] HttpResponse: " + response);
			String responseApiCall = AbbyServiceUtil.getDataFromStream(response.getEntity().getContent());
			logger.info("[AbbyOcrServiceImpl][getDocumentDetails]->responseApiCall::" + responseApiCall);
			JSONObject json = new JSONObject(responseApiCall);
			JSONObject jsonArray = json.getJSONObject("Transaction").getJSONArray("Documents").getJSONObject(0);
			JSONArray jsonArray1 = jsonArray.getJSONObject("ExtractedData").getJSONObject("RootObject")
					.getJSONArray("Fields");
			// System.err.println(jsonObj);
			HashMap<String, String> map = new HashMap<>();
			// HashMap<String,String> map1=new HashMap<>();
			for (int i = 0; i < jsonArray1.length(); i++) {
				String value = null;
				JSONObject obj = jsonArray1.getJSONObject(i);
				String name = obj.getString("Name");
				// System.err.println("name "+name);
				if (name.equalsIgnoreCase("Buyer"))// ||name.equalsIgnoreCase("Buyer")||name.equalsIgnoreCase("Line
													// Items")||name.equalsIgnoreCase("Ship
													// to")||name.equalsIgnoreCase("Supplier"))
				{

					JSONArray Jarray = obj.getJSONArray("List").getJSONObject(0).getJSONObject("Value")
							.getJSONArray("Fields");
					String buyer = "";
					for (int j = 0; j < Jarray.length(); j++) {
						buyer = buyer + " "
								+ Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0).getString("Value");
					}

					value = buyer;

				} else if (name.equalsIgnoreCase("Supplier"))// ||name.equalsIgnoreCase("Buyer")||name.equalsIgnoreCase("Line
																// Items")||name.equalsIgnoreCase("Ship
																// to")||name.equalsIgnoreCase("Supplier"))
				{
					continue;
				}

				else if (name.equalsIgnoreCase("Ship to")) {

					JSONArray Jarray = obj.getJSONArray("List").getJSONObject(0).getJSONObject("Value")
							.getJSONArray("Fields");
					String shipTo = "";
					for (int j = 0; j < Jarray.length(); j++) {
						shipTo = shipTo + " "
								+ Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0).getString("Value");
					}

					value = shipTo;

				} else if (name.equalsIgnoreCase("Bill To")) {
					continue;
				} else if (name.equalsIgnoreCase("Line Items")) {
					JSONArray Jarray = obj.getJSONArray("List").getJSONObject(0).getJSONObject("Value")
							.getJSONArray("Fields");
					JSONArray Jarray1 = obj.getJSONArray("List").getJSONObject(1).getJSONObject("Value")
							.getJSONArray("Fields");

					for (int j = 0; j < Jarray.length(); j++) {
						if (Jarray.getJSONObject(j) != null
								&& Jarray.getJSONObject(j).getString("Name").equalsIgnoreCase("Article Number Buyer")) {

							String material = Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getString("Value");
							String material1 = Jarray1.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getString("Value");
							map.put("material", material);
							map.put("material1", material1);

						}
						if (Jarray.getJSONObject(j) != null
								&& Jarray.getJSONObject(j).getString("Name").equalsIgnoreCase("Unit Price")) {

							String unitPrice = Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getJSONArray("Annotations").getJSONObject(0).getString("RawValue");
							String unitPrice1 = Jarray1.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getJSONArray("Annotations").getJSONObject(0).getString("RawValue");
							System.err.println(" unitPrice "+ unitPrice+" "+ "unitPrice1 "+ unitPrice1);
							map.put("unitPrice",
									((unitPrice.substring(1, 3)).concat(unitPrice.substring(4))).concat(".00"));

							map.put("unitPrice1",
									((unitPrice1.substring(1, 3)).concat(unitPrice1.substring(4)).concat(".00")));
							System.err.println("unit" + unitPrice.length());

						}
						if (Jarray.getJSONObject(j) != null
								&& Jarray.getJSONObject(j).getString("Name").equalsIgnoreCase("Quantity")) {

							String quantity = Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getString("Value");
							String quantity1 = Jarray1.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getString("Value");
							map.put("quantity", quantity);
							map.put("quantity1", quantity1);

						}
						if (Jarray.getJSONObject(j) != null
								&& Jarray.getJSONObject(j).getString("Name").equalsIgnoreCase("Total Price")) {

							String totalPrice = Jarray.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getJSONArray("Annotations").getJSONObject(0).getString("RawValue");
							String totalPrice1 = Jarray1.getJSONObject(j).getJSONArray("List").getJSONObject(0)
									.getJSONArray("Annotations").getJSONObject(0).getString("RawValue");
							map.put("totalPrice", (totalPrice.substring(1, 3)).concat(totalPrice.substring(4)));
							map.put("totalPrice1", (totalPrice1.substring(1, 3)).concat(totalPrice1.substring(4)));

						}
					}

				} else {
					value = obj.getJSONArray("List").getJSONObject(0).getString("Value");

				}
				map.put(name, value);

			}
			System.err.println("map "+map);
			linedto.setMaterial(map.get("material"));
			linedto.setEnteredOrdQuantity(new BigDecimal(map.get("quantity")));
			System.err.println(new BigDecimal(map.get("quantity")));
			linedto.setNetValue((map.get("totalPrice")));
			linedto.setBasePrice(new BigDecimal(map.get("unitPrice")));
			linedto.setPlant("CODD");
			linedto.setLineItemNumber("000001");

			linedto1.setMaterial(map.get("material1"));
			linedto1.setEnteredOrdQuantity(new BigDecimal(map.get("quantity1")));
			System.err.println(new BigDecimal(map.get("quantity1")));
			linedto1.setNetValue((map.get("totalPrice1")));
			linedto1.setBasePrice(new BigDecimal(map.get("unitPrice1")));
			linedto1.setPlant("CODD");
			System.err.println(linedto);
			lineItemList.add(linedto);
			lineItemList.add(linedto1);
			System.err.println(linedto1);
			headerDto.setDocumentType("OR");
			headerDto.setIncoTerms1("CIF");
			headerDto.setIncoTerms2("CIF");
			headerDto.setUnderDeliveryTolerance("5");
			headerDto.setOverDeliveryTolerance("5");
			headerDto.setPlant("CODD");
			headerDto.setEmailId("Prabinkumar.sahu@incture.com");
			headerDto.setWeight("T");
			headerDto.setNetValue(map.get("Total"));
			headerDto.setNetValueSA(map.get("Total"));
			headerDto.setCreatedBy("Sandeep.k@incture.com"); // headerDto.getNet
			headerDto.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").parse(map.get("Order Date")));
			headerDto.setCustomerPODate(new SimpleDateFormat("yyyy-MM-dd").parse(map.get("Order Date")));
			headerDto.setRequestDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-20"));
			headerDto.setAmount(map.get("Total"));
			headerDto.setPaymentTerms("0001");
			headerDto.setSoldToParty("0000000046");
			headerDto.setShipToParty("0000000046");
			headerDto.setShippingType("01");
			
			dto.setHeaderDto(headerDto);
			dto.setLineItemList(lineItemList);
			// System.err.println(headerDto);
			return services.submitSalesOrder(dto);
			//return map;// m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
			if (json.getString("status").equalsIgnoreCase("Processed")) {// Processed
				JSONArray documanetArray = json.getJSONArray("documents");
				JSONObject documentObject = documanetArray.getJSONObject(0);
				fileId = documentObject.getJSONArray("resultFiles").getJSONObject(0).getString("fileId");
				System.err.println("documents " + documanetArray);
				return fileId;
			} else if (json.has("manualReviewLink")) {
				manualReviewLink = json.getString("manualReviewLink");
				return json.toString();
			} else if (json.getString("status").equalsIgnoreCase("Processing")) {
				return getTransactionDetails(client, token, transactionId);
			} else {
				return "OCR Pending";
			}
		} catch (Exception e) {
			
		}
		return fileId;
	}

	

}
