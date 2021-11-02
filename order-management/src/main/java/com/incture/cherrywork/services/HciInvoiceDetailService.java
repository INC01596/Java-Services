package com.incture.cherrywork.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.InvoiceDetailsDto;
import com.incture.cherrywork.dtos.InvoiceHeaderDto;
import com.incture.cherrywork.dtos.InvoiceItemDto;
import com.incture.cherrywork.dtos.InvoiceLineItemDTO;
import com.incture.cherrywork.dtos.ParkingDto;
import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.dtos.PendingInvoiceItemDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.entities.PendingInvoiceDo;
import com.incture.cherrywork.entities.PendingInvoiceItemDo;
import com.incture.cherrywork.util.AuthCpi;
import com.incture.cherrywork.util.HciApiConstants;
import com.incture.cherrywork.util.RestInvoker;

@Service
@Transactional
public class HciInvoiceDetailService implements HciInvoiceDetailServiceLocal {

	private static final Logger logger = LoggerFactory.getLogger(HciInvoiceDetailService.class);

	@Autowired
	private RestInvoker restInvoker;

	 @Autowired
	 private CustomerDaoLocal customerDao;
	 
	 @Autowired
	 private AuthCpi cpi;

	ResponseDto responseDto;

	InvoiceDetailsDto invoiceDetailsDto;

	List<InvoiceLineItemDTO> invoiceLineItemDtoList;

	List<ParkingDto> parkingDtoList;

	InvoiceLineItemDTO invoiceLineItemDTO;

	ParkingDto parkingDto;

	@Override
	public ResponseDto getInvoices(String customerId) {

		responseDto = new ResponseDto();

		InvoiceDetailsDto invoiceDetailsDto = new InvoiceDetailsDto();

		List<InvoiceLineItemDTO> invoiceLineItemDtoList = new ArrayList<>();

		InvoiceLineItemDTO invoiceLineItemDTO;

		if (customerId != null && !customerId.equals("")) {

			try {

				JSONObject inputObject = new JSONObject();

				JSONObject getInvoiceDetails = new JSONObject();

				getInvoiceDetails.put("CustomerNumber", customerId);

				inputObject.put("GetInvoiceDetails", getInvoiceDetails);

				Map<String, Object> map = new HashMap<>();
				map.put("URL", "https://inccpidev.it-cpi001-rt.cfapps.eu10.hana.ondemand.com/");
				map.put("BP_CREATE", HciApiConstants.GET_INVOICE_DETAILS);

				String response = checkFunction(inputObject.toString(), map);

				if (response != null && !(response.equals(""))) {

					JSONObject responseObject = new JSONObject(response);

					JSONObject zsdGetInvoiceDetails = responseObject.getJSONObject("Z_SD_GET_INVOICE_DETAILS.Response");

					
					if (zsdGetInvoiceDetails.optJSONObject("GT_INVOICE") != null) {

						JSONObject gtInvoice = zsdGetInvoiceDetails.getJSONObject("GT_INVOICE");

						if (gtInvoice.optJSONArray("item") != null) {

							JSONArray invoiceItemArray = gtInvoice.getJSONArray("item");

							JSONObject invoiceItem;

							for (int i = 0; i < invoiceItemArray.length(); i++) {

								invoiceLineItemDTO = new InvoiceLineItemDTO();

								invoiceItem = invoiceItemArray.getJSONObject(i);
								/*
								 * String invoiceStatus =
								 * invoiceDao.getStatusByInvoiceNo(invoiceItem.
								 * optString ("VBELN"));
								 * 
								 * if
								 * (invoiceStatus.equalsIgnoreCase("Inprogress")
								 * ||
								 * invoiceStatus.equalsIgnoreCase("Acknowledged"
								 * )) continue;
								 */
								invoiceLineItemDTO.setCustId(invoiceItem.getString("KUNNR"));
								invoiceLineItemDTO.setAccountingDocument(invoiceItem.getString("BELNR"));
								invoiceLineItemDTO.setDocumentType(invoiceItem.getString("BLART"));
								invoiceLineItemDTO.setBillingDocumentNo(invoiceItem.getString("VBELN"));
								invoiceLineItemDTO.setCurrency(invoiceItem.getString("WAERS"));
								invoiceLineItemDTO.setAmountInLocalCurrency(invoiceItem.getString("DMBTR"));
								invoiceLineItemDTO.setAmount(invoiceItem.getString("WRBTR"));
								invoiceLineItemDTO.setPendingAmount(invoiceItem.getString("PENDING_AMOUNT"));
								invoiceLineItemDTO.setPostingDate(invoiceItem.getString("BUDAT"));
								invoiceLineItemDTO.setDocumentDate(invoiceItem.getString("BLDAT"));

								invoiceLineItemDtoList.add(invoiceLineItemDTO);

							}

						} else if (gtInvoice.optJSONObject("item") != null) {

							invoiceLineItemDTO = new InvoiceLineItemDTO();

							JSONObject invoiceItem = gtInvoice.optJSONObject("item");

							/*
							 * String invoiceStatus =
							 * invoiceDao.getStatusByInvoiceNo(invoiceItem.
							 * optString( "VBELN"));
							 * 
							 * if
							 * (!(invoiceStatus.equalsIgnoreCase("Inprogress")
							 * ||
							 * invoiceStatus.equalsIgnoreCase("Acknowledged")))
							 * {
							 */
							invoiceLineItemDTO.setCustId(invoiceItem.getString("KUNNR"));
							invoiceLineItemDTO.setAccountingDocument(invoiceItem.getString("BELNR"));
							invoiceLineItemDTO.setDocumentType(invoiceItem.getString("BLART"));
							invoiceLineItemDTO.setBillingDocumentNo(invoiceItem.getString("VBELN"));
							invoiceLineItemDTO.setCurrency(invoiceItem.getString("WAERS"));
							invoiceLineItemDTO.setAmountInLocalCurrency(invoiceItem.getString("DMBTR"));
							invoiceLineItemDTO.setAmount(invoiceItem.getString("WRBTR"));
							invoiceLineItemDTO.setPendingAmount(invoiceItem.getString("PENDING_AMOUNT"));
							invoiceLineItemDTO.setPostingDate(invoiceItem.getString("BUDAT"));
							invoiceLineItemDTO.setDocumentDate(invoiceItem.getString("BLDAT"));

							invoiceLineItemDtoList.add(invoiceLineItemDTO);

							// }

						}

					}

					/*
					 * if
					 * (zsdGetInvoiceDetails.optJSONObject("GT_PARKING_ITEMS")
					 * != null) {
					 * 
					 * JSONObject gtParkingItems =
					 * zsdGetInvoiceDetails.getJSONObject("GT_PARKING_ITEMS");
					 * 
					 * JSONObject parkingItem;
					 * 
					 * parkingDtoList = new ArrayList<>();
					 * 
					 * if (gtParkingItems.optJSONArray("item") != null) {
					 * 
					 * JSONArray parkingItemsArray =
					 * gtParkingItems.getJSONArray("item");
					 * 
					 * for (int i = 0; i < parkingItemsArray.length(); i++) {
					 * 
					 * parkingDto = new ParkingDto();
					 * 
					 * parkingItem = parkingItemsArray.getJSONObject(i);
					 * 
					 * parkingDto.setCustId(parkingItem.getString("CUSTOMER_KEY"
					 * )); parkingDto.setDocumentNo(parkingItem.getString(
					 * "DOCUMENT_NO"));
					 * parkingDto.setAmountInLocalCurrency(parkingItem.getString
					 * ("AMOUNT_IN_LC"));
					 * parkingDto.setAmount(parkingItem.getString("AMOUNT"));
					 * 
					 * parkingDtoList.add(parkingDto); }
					 * 
					 * } else if (gtParkingItems.optJSONObject("item") != null)
					 * {
					 * 
					 * parkingItem = gtParkingItems.getJSONObject("item");
					 * 
					 * parkingDto = new ParkingDto();
					 * 
					 * parkingDto.setCustId(parkingItem.getString("CUSTOMER_KEY"
					 * )); parkingDto.setDocumentNo(parkingItem.getString(
					 * "DOCUMENT_NO"));
					 * parkingDto.setAmountInLocalCurrency(parkingItem.getString
					 * ("AMOUNT_IN_LC"));
					 * parkingDto.setAmount(parkingItem.getString("AMOUNT"));
					 * 
					 * parkingDtoList.add(parkingDto);
					 * 
					 * }
					 * 
					 * }
					 */

				}

			} catch (Exception e) {
				logger.error("[HciInvoiceDetailService][getInvoices]" + e.getMessage());
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus(false);
				responseDto.setStatusCode(1);
			}

		}

		invoiceDetailsDto.setListOfInvoiceLineItems(invoiceLineItemDtoList);
		// invoiceDetailsDto.setListOfParkingDto(parkingDtoList);
		responseDto.setData(invoiceDetailsDto);
		responseDto.setMessage("success");
		responseDto.setStatus(true);
		responseDto.setStatusCode(0);

		return responseDto;
	}

	
	@Override
	public ResponseDto getInvoiceDetails(String invoiceNumber) {

		responseDto = new ResponseDto();

		InvoiceHeaderDto invoiceHeaderDto = new InvoiceHeaderDto();

		if (!invoiceNumber.trim().isEmpty()) {

			try {

				JSONObject inputObject = new JSONObject();

				JSONObject getInvoiceLines = new JSONObject();

				getInvoiceLines.put("InvoiceNumber", invoiceNumber);

				inputObject.put("GetInvoiceLines", getInvoiceLines);

				Map<String, Object> map = new HashMap<>();
				map.put("URL", "https://inccpidev.it-cpi001-rt.cfapps.eu10.hana.ondemand.com/");
				map.put("BP_CREATE", HciApiConstants.GET_INVOICE_LINES);

				String response = checkFunction(inputObject.toString(), map);
//				HttpEntity entity = response.getEntity();
//				String responseString = EntityUtils.toString(entity, "UTF-8");
//				System.err.println(responseString);
//				System.err.println(response.toString());

//				String response = restInvoker.postDataToServer(HciApiConstants.GET_INVOICE_LINES,
//						inputObject.toString());

				if (response != null && !(response.equals(""))) {

					JSONObject responseObject = new JSONObject(response);

					JSONObject zsdGetinvoiceLinesResponse = responseObject
							.optJSONObject("ZSD_GETINVOICE_LINES.Response");

					JSONObject invoiceHeader = zsdGetinvoiceLinesResponse.optJSONObject("E_INV_HEADER");

					JSONObject headerItem = invoiceHeader.optJSONObject("item");

					invoiceHeaderDto.setInvoiceNumber(headerItem.getString("INV_NUM"));
					invoiceHeaderDto.setBillDate(headerItem.getString("BILL_DATE"));
					invoiceHeaderDto.setSoldToParty(headerItem.getString("SOLD_TO_PARTY"));
					invoiceHeaderDto.setPayer(headerItem.getString("PAYER"));
					invoiceHeaderDto.setNetValue(headerItem.getBigDecimal("NET_VALUE"));
					invoiceHeaderDto.setTaxAmount(headerItem.getBigDecimal("TAX_AMOUNT"));
					invoiceHeaderDto.setCurrency(headerItem.getString("CURRENCY"));

					JSONObject invoiceItemsHeader = zsdGetinvoiceLinesResponse.optJSONObject("E_INV_ITEMS");

					JSONArray invoiceItemArray = invoiceItemsHeader.optJSONArray("item");

					List<InvoiceItemDto> invoiceItemDtoList = new ArrayList<>();
					InvoiceItemDto invoiceItemDto;

					if (invoiceItemArray != null) {

						for (int i = 0; i < invoiceItemArray.length(); i++) {

							JSONObject item = invoiceItemArray.getJSONObject(i);

							invoiceItemDto = new InvoiceItemDto();

							invoiceItemDto.setInvoiceNumber(item.getString("INV_NUM"));
							invoiceItemDto.setItemNumber(item.getString("ITEM_NUM"));
							invoiceItemDto.setMatID(item.getString("MATERIAL"));
							invoiceItemDto.setDescription(item.getString("DESCRIPTION"));
							invoiceItemDto.setHighItem(item.getBigDecimal("HIGH_ITEM"));
							invoiceItemDto.setBilledQty(item.getBigDecimal("BILLED_QTY"));
							invoiceItemDto.setSalesUnit(item.getString("SALES_UNIT"));
							invoiceItemDto.setNetPrice(item.getBigDecimal("NET_PRICE"));
							invoiceItemDto.setTaxAmount(item.getBigDecimal("TAX_AMOUNT"));
							invoiceItemDto.setSalesDoc(item.getString("SALES_DOC"));
							invoiceItemDto.setItemCateg(item.getString("ITEM_CATEG"));
							invoiceItemDto.setFreegoodInd(item.getString("FREEGOOD_IND"));

							invoiceItemDtoList.add(invoiceItemDto);
						}

					} else if (invoiceItemsHeader.optJSONObject("item") != null) {

						JSONObject item = invoiceItemsHeader.getJSONObject("item");

						invoiceItemDto = new InvoiceItemDto();

						invoiceItemDto.setInvoiceNumber(item.getString("INV_NUM"));
						invoiceItemDto.setItemNumber(item.getString("ITEM_NUM"));
						invoiceItemDto.setMatID(item.getString("MATERIAL"));
						invoiceItemDto.setDescription(item.getString("DESCRIPTION"));
						invoiceItemDto.setHighItem(item.getBigDecimal("HIGH_ITEM"));
						invoiceItemDto.setBilledQty(item.getBigDecimal("BILLED_QTY"));
						invoiceItemDto.setSalesUnit(item.getString("SALES_UNIT"));
						invoiceItemDto.setNetPrice(item.getBigDecimal("NET_PRICE"));
						invoiceItemDto.setTaxAmount(item.getBigDecimal("TAX_AMOUNT"));
						invoiceItemDto.setSalesDoc(item.getString("SALES_DOC"));
						invoiceItemDto.setItemCateg(item.getString("ITEM_CATEG"));
						invoiceItemDto.setFreegoodInd(item.getString("FREEGOOD_IND"));

						invoiceItemDtoList.add(invoiceItemDto);

					}

					invoiceHeaderDto.setInvoiceItemList(invoiceItemDtoList);

				}

			} catch (Exception e) {
				logger.error("[HciInvoiceDetailService][getInvoiceDetails]" + e.getMessage());
				responseDto.setMessage(e.getMessage());
				responseDto.setStatus(false);
				responseDto.setStatusCode(1);

			}

			responseDto.setData(invoiceHeaderDto);
			responseDto.setMessage("success");
			responseDto.setStatus(true);
			responseDto.setStatusCode(0);
		}

		return responseDto;
	}

	
	public String checkFunction(String dto, Map<String, Object> map) throws IOException {
	System.err.println("checkFunction : dto " + dto + " map " + map.toString());
	HttpResponse httpResponse = null;
	HttpRequestBase httpRequestBase = null;
	StringEntity data = null;
	String barerToken = null;
	String responseString = null;
	try {
	barerToken = cpi.getToken();
	} catch (OAuthSystemException e) {
	e.printStackTrace();
	} catch (OAuthProblemException e) {
	e.printStackTrace();
	}
	CloseableHttpClient httpClient = HttpClientBuilder.create().build(); if (!checkString(dto)) { if (map != null) {
	// String authTokenDuplicate = "eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vaW5jY3BpZGV2LmF1dGhlbnRpY2F0aW9uLmV1MTAuaGFuYS5vbmRlbWFuZC5jb20vdG9rZW5fa2V5cyIsImtpZCI6ImRlZmF1bHQtand0LWtleS04MjQ4OTI0NTIiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiJhMmY2MWIzZDdmYzk0YTc4OWI5YTA5NDE0YWY0NTI2OCIsImV4dF9hdHRyIjp7ImVuaGFuY2VyIjoiWFNVQUEiLCJzdWJhY2NvdW50aWQiOiJiNGQzMmI2Yy05MThiLTQ4YzYtOWIxZi1lODhiMzQ4YWFlNTMiLCJ6ZG4iOiJpbmNjcGlkZXYiLCJzZXJ2aWNlaW5zdGFuY2VpZCI6ImEzYzJmOTg5LWQ5Y2UtNDczOC04YWMzLTJlODkzODBkNjBiZCJ9LCJzdWIiOiJzYi1hM2MyZjk4OS1kOWNlLTQ3MzgtOGFjMy0yZTg5MzgwZDYwYmQhYjYzNjI2fGl0LXJ0LWluY2NwaWRldiFiMTYwNzciLCJhdXRob3JpdGllcyI6WyJ1YWEucmVzb3VyY2UiLCJpdC1ydC1pbmNjcGlkZXYhYjE2MDc3LkVTQk1lc3NhZ2luZy5zZW5kIl0sInNjb3BlIjpbIml0LXJ0LWluY2NwaWRldiFiMTYwNzcuRVNCTWVzc2FnaW5nLnNlbmQiLCJ1YWEucmVzb3VyY2UiXSwiY2xpZW50X2lkIjoic2ItYTNjMmY5ODktZDljZS00NzM4LThhYzMtMmU4OTM4MGQ2MGJkIWI2MzYyNnxpdC1ydC1pbmNjcGlkZXYhYjE2MDc3IiwiY2lkIjoic2ItYTNjMmY5ODktZDljZS00NzM4LThhYzMtMmU4OTM4MGQ2MGJkIWI2MzYyNnxpdC1ydC1pbmNjcGlkZXYhYjE2MDc3IiwiYXpwIjoic2ItYTNjMmY5ODktZDljZS00NzM4LThhYzMtMmU4OTM4MGQ2MGJkIWI2MzYyNnxpdC1ydC1pbmNjcGlkZXYhYjE2MDc3IiwiZ3JhbnRfdHlwZSI6ImNsaWVudF9jcmVkZW50aWFscyIsInJldl9zaWciOiI2MTAwYzM3MyIsImlhdCI6MTYzNTIzMTI0OSwiZXhwIjoxNjM1MjM1NDQ5LCJpc3MiOiJodHRwczovL2luY2NwaWRldi5hdXRoZW50aWNhdGlvbi5ldTEwLmhhbmEub25kZW1hbmQuY29tL29hdXRoL3Rva2VuIiwiemlkIjoiYjRkMzJiNmMtOTE4Yi00OGM2LTliMWYtZTg4YjM0OGFhZTUzIiwiYXVkIjpbIml0LXJ0LWluY2NwaWRldiFiMTYwNzcuRVNCTWVzc2FnaW5nIiwidWFhIiwic2ItYTNjMmY5ODktZDljZS00NzM4LThhYzMtMmU4OTM4MGQ2MGJkIWI2MzYyNnxpdC1ydC1pbmNjcGlkZXYhYjE2MDc3Il19.HecXeAajlA7U_hFFAejUoYhlHvcE_pbpkyIVkp4nofEx7PRl1AxspWn47b3EbAFDYrP-B8YgozgSkNvZlYJGJtuDqRyigv54_ARwRSl_nVPJTiA_FZD2awNyXq17Yb-vYJKhKze0E9AiHJxEHMdGorqYIGJi96w3no2b07DfnsEzGcHfkSmpH0EmGp3q6K_wk8ebYb16H0PdPX_vUoiao2XH-7ewdcrtvJ95pHgKCazUwIxcX57SF175l7XPpLqYrXg5BlXRq44aJSF8Uy_7qrNSbpxZwiqTzOM9Pbs2ky-SZ_IO8gZ8D71dHqVhdCKQ9YZAtRAVO60DcXxL-j134w";
	System.err.println("barerToken " + barerToken);
	if (!checkString(barerToken)) {
	String requestUrl = (String) map.get("URL") + (String) map.get("BP_CREATE");
	System.err.println("requestUrl" + requestUrl);
	httpRequestBase = new HttpPost(requestUrl);
	// ObjectMapper mapper = new ObjectMapper();
	// String json = mapper.writeValueAsString(dto);
	// logger.info("MDG request payload " + dto);
	// JSONObject jsonObject = new JSONObject(dto);
	// data = new StringEntity(dto, "utf-8");
	// data.setContentType("application/json");
	StringEntity requestEntity = new StringEntity(
	dto,
	ContentType.APPLICATION_JSON);
	((HttpPost) httpRequestBase).setEntity(requestEntity);
	httpRequestBase.addHeader("accept", "*/*");
	httpRequestBase.addHeader("Authorization", "Bearer "+barerToken);
	httpRequestBase.addHeader("Accept-Encoding", "gzip,deflate,br");
	httpRequestBase.addHeader("Connection", "keep-alive");
	httpResponse = httpClient.execute(httpRequestBase);
	System.err.println("httpResponse" + httpResponse);
	HttpEntity entity = httpResponse.getEntity();
	responseString = EntityUtils.toString(entity, "UTF-8");
	System.err.println(responseString);
	httpClient.close(); }
	}
	}
	return responseString; 
	}


	public boolean checkString(String s) {
		if (s == null || s.equals("") || s.trim().isEmpty() || s.matches(""))
			return true;
		else
			return false;
	}

	public String encodeUsernameAndPassword(String username, String password) {
		String encodeUsernamePassword = username + ":" + password;
		String auth = "Basic " + DatatypeConverter.printBase64Binary(encodeUsernamePassword.getBytes());
		return auth;
	}
	//
	// //
	// **********************************************************************************************************
	 @Override
	 public List<PendingInvoiceDto> getAllOpenInvoices() {
	
	 // responseDto = new ResponseDto();
	
	 List<PendingInvoiceDto> listPendingInvoice = new ArrayList<>();
	 PendingInvoiceDto pendingInvoiceDto;
	
	 List<PendingInvoiceDto> modifiedListPendingInvoice = new ArrayList<>();
	
	 JSONObject zsdGetInvoiceDetails = new JSONObject();
	 JSONObject customerNumbers;
	 JSONArray record = new JSONArray();
	
	 try {
	
	 // Getting Customers from customer table
	 List<String> listOfCust = customerDao.getAllCustomer();
	
	 if (listOfCust != null && listOfCust.size() > 0) {
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvoices]::Get+Customers from Customer table Count="+ listOfCust.size());
	
	 invoiceDetailsDto = new InvoiceDetailsDto();
	
	 JSONObject inputObject = new JSONObject();
	
	 JSONObject getInvoiceDetails = new JSONObject();
	
	 for (String customerId : listOfCust) {
	
	 customerNumbers = new JSONObject();
	
	 customerNumbers.put("CustomerNumbers", customerId);
	
	 record.put(customerNumbers);
	
	 }
	
	 getInvoiceDetails.put("Record", record);
	
	 inputObject.put("GetMultipleInvoiceDetails", getInvoiceDetails);
	
	 Map<String, Object> map = new HashMap<>();
		map.put("URL", "https://inccpidev.it-cpi001-rt.cfapps.eu10.hana.ondemand.com/");
		map.put("BP_CREATE", HciApiConstants.GET_INVOICE_DETAILS);

		String response = checkFunction(inputObject.toString(), map);
	
	 // getInvoiceDetails.put("CustomerNumber", customerId);
	
	 if (response != null && !(response.equals(""))) {
	
	 JSONObject responseObject = new JSONObject(response);
	
	 zsdGetInvoiceDetails =
	 responseObject.getJSONObject("ZSD_GET_INVOICE_DETAILS.Response");
	
	 if (zsdGetInvoiceDetails.optJSONObject("ET_INVOICES") != null) {
	
	 JSONObject gtInvoice = zsdGetInvoiceDetails.getJSONObject("ET_INVOICES");
	
	 invoiceLineItemDtoList = new ArrayList<>();
	
	 if (gtInvoice.optJSONArray("item") != null) {
	
	 JSONArray invoiceItemArray = gtInvoice.getJSONArray("item");
	
	 JSONObject invoiceItem;
	
	 int invoiceItemArrayLength = invoiceItemArray.length();
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvoices]::inovice size from RFC="
	 + invoiceItemArrayLength);
	
	 for (int i = 0; i < invoiceItemArrayLength; i++) {
	
	 pendingInvoiceDto = new PendingInvoiceDto();
	
	 invoiceItem = invoiceItemArray.getJSONObject(i);
	
	 pendingInvoiceDto.setCustId(invoiceItem.getString("KUNNR"));
	 pendingInvoiceDto.setAccountingDocument(invoiceItem.getString("BELNR"));
	 pendingInvoiceDto.setDocumentType(invoiceItem.getString("BLART"));
	 pendingInvoiceDto.setBillingDocumentNo(invoiceItem.getString("VBELN"));
	 pendingInvoiceDto.setCurrency(invoiceItem.getString("WAERS"));
	 pendingInvoiceDto
	 .setAmountInLocalCurrency(new
	 BigDecimal(invoiceItem.getString("DMBTR")));
	 pendingInvoiceDto.setAmount(new
	 BigDecimal(invoiceItem.getString("WRBTR")));
	 pendingInvoiceDto
	 .setPendingAmount(new
	 BigDecimal(invoiceItem.getString("PENDING_AMOUNT")));
	 pendingInvoiceDto.setPostingDate(
	 new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoiceItem.getString("BUDAT")));
	 pendingInvoiceDto.setDocumentDate(
	 new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoiceItem.getString("BLDAT")));
	
	 listPendingInvoice.add(pendingInvoiceDto);
	
	 }
	
	 } else if (gtInvoice.optJSONObject("item") != null) {
	
	 pendingInvoiceDto = new PendingInvoiceDto();
	
	 JSONObject invoiceItem = gtInvoice.optJSONObject("item");
	
	 /*
	 * String invoiceStatus =
	 * invoiceDao.getStatusByInvoiceNo(invoiceItem.
	 * optString( "VBELN"));
	 *
	 * if
	 * (!(invoiceStatus.equalsIgnoreCase("Inprogress")
	 * ||
	 * invoiceStatus.equalsIgnoreCase("Acknowledged")))
	 * {
	 */
	 pendingInvoiceDto.setCustId(invoiceItem.getString("KUNNR"));
	 pendingInvoiceDto.setAccountingDocument(invoiceItem.getString("BELNR"));
	 pendingInvoiceDto.setDocumentType(invoiceItem.getString("BLART"));
	 pendingInvoiceDto.setBillingDocumentNo(invoiceItem.getString("VBELN"));
	 pendingInvoiceDto.setCurrency(invoiceItem.getString("WAERS"));
	 pendingInvoiceDto.setAmountInLocalCurrency(invoiceItem.getBigDecimal("DMBTR"));
	 pendingInvoiceDto.setAmount(invoiceItem.getBigDecimal("WRBTR"));
	 pendingInvoiceDto.setPendingAmount(invoiceItem.getBigDecimal("PENDING_AMOUNT"));
	 pendingInvoiceDto.setPostingDate(
	 new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoiceItem.getString("BUDAT")));
	 pendingInvoiceDto.setDocumentDate(
	 new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoiceItem.getString("BLDAT")));
	
	 listPendingInvoice.add(pendingInvoiceDto);
	
	 // }
	
	 }
	
	 }
	
	 if (listPendingInvoice != null && listPendingInvoice.size() > 0) {
	
	 PendingInvoiceDto obj = new PendingInvoiceDto();
	
	 for (PendingInvoiceDto listPending : listPendingInvoice) {
	
	 obj = getOpenInvoiceDetails(listPending,
	 zsdGetInvoiceDetails.toString());
	
	 obj.setAccountingDocument(listPending.getAccountingDocument());
	 obj.setCustId(listPending.getCustId());
	 obj.setDocumentType(listPending.getDocumentType());
	 obj.setBillingDocumentNo(listPending.getBillingDocumentNo());
	 obj.setCurrency(listPending.getCurrency());
	 obj.setAmount(listPending.getAmount());
	 obj.setAmountInLocalCurrency(listPending.getAmountInLocalCurrency());
	 obj.setPendingAmount(listPending.getPendingAmount());
	 obj.setPostingDate(listPending.getPostingDate());
	 obj.setDocumentDate(listPending.getDocumentDate());
	
	 modifiedListPendingInvoice.add(obj);
	
	 }
	 }
	
	 }
	
	 }
	
	 } catch (Exception e) {
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvoices]::" +
	 e.getMessage());
	 /*
	 * responseDto.setMessage(e.getMessage());
	 * responseDto.setStatus(false); responseDto.setCode(1);
	 */
	
	 }
	
	 /*
	 * responseDto.setData(invoiceDetailsDto);
	 * responseDto.setMessage("success"); responseDto.setStatus(true);
	 * responseDto.setCode(0);
	 */
	
	 return modifiedListPendingInvoice;
	 }
	
	 private PendingInvoiceDto getOpenInvoiceDetails(PendingInvoiceDto
	 pendingInvDto, String response) {
	
	 // responseDto = new ResponseDto();
	
	 PendingInvoiceDto pendingInvoiceDto = new PendingInvoiceDto();
	
	 if (!pendingInvDto.getBillingDocumentNo().trim().isEmpty()) {
	
	 try {
	
	 if (response != null && !(response.equals(""))) {
	
	 JSONObject responseObject = new JSONObject(response);
	
	 JSONObject invoiceHeader = responseObject.optJSONObject("ET_INVHEADER");
	
	 JSONArray headerItemArray = invoiceHeader.optJSONArray("item");
	
	 int headerItemArrayLength = headerItemArray.length();
	
	 for (int counter = 0; counter < headerItemArrayLength; counter++) {
	
	 JSONObject itemObj = new JSONObject();
	
	 itemObj = headerItemArray.getJSONObject(counter);
	
	 if
	 (itemObj.getString("INV_NUM").equalsIgnoreCase(pendingInvDto.getBillingDocumentNo()))
	 {
	
	 pendingInvoiceDto.setInvoiceNumber(itemObj.getString("INV_NUM"));
	 pendingInvoiceDto.setBillDate(
	 new
	 SimpleDateFormat("yyyy-MM-dd").parse(itemObj.getString("BILL_DATE")));
	 pendingInvoiceDto.setSoldToParty(itemObj.getString("SOLD_TO_PARTY"));
	 pendingInvoiceDto.setPayer(itemObj.getString("PAYER"));
	 pendingInvoiceDto.setNetValue(itemObj.getBigDecimal("NET_VALUE"));
	 pendingInvoiceDto.setTaxAmount(itemObj.getBigDecimal("TAX_AMOUNT"));
	 pendingInvoiceDto.setCurrency(itemObj.getString("CURRENCY"));
	
	 }
	
	 JSONObject invoiceItemsHeader =
	 responseObject.optJSONObject("ET_INVLINES");
	
	 JSONArray invoiceItemArray = invoiceItemsHeader.optJSONArray("item");
	
	 List<PendingInvoiceItemDto> invoiceItemDtoList = new ArrayList<>();
	
	 if (invoiceItemArray != null) {
	
	 int invoiceItemArrayLength = invoiceItemArray.length();
	
	 for (int i = 0; i < invoiceItemArrayLength; i++) {
	
	 JSONObject item = invoiceItemArray.getJSONObject(i);
	
	 if
	 (item.getString("INV_NUM").equalsIgnoreCase(pendingInvDto.getBillingDocumentNo()))
	 {
	
	 PendingInvoiceItemDto pendingInvoiceItemDto = new
	 PendingInvoiceItemDto();
	
	 pendingInvoiceItemDto.setInvoiceNumber(item.getString("INV_NUM"));
	 pendingInvoiceItemDto.setItemNumber(item.getString("ITEM_NUM"));
	 pendingInvoiceItemDto.setMatID(item.getString("MATERIAL"));
	 pendingInvoiceItemDto.setDescription(item.getString("DESCRIPTION"));
	 pendingInvoiceItemDto.setHighItem(item.getBigDecimal("HIGH_ITEM"));
	 pendingInvoiceItemDto.setBilledQty(item.getBigDecimal("BILLED_QTY"));
	 pendingInvoiceItemDto.setSalesUnit(item.getString("SALES_UNIT"));
	 pendingInvoiceItemDto.setNetPrice(item.getBigDecimal("NET_PRICE"));
	 pendingInvoiceItemDto.setTaxAmount(item.getBigDecimal("TAX_AMOUNT"));
	 pendingInvoiceItemDto.setSalesDoc(item.getString("SALES_DOC"));
	 pendingInvoiceItemDto.setItemCateg(item.getString("ITEM_CATEG"));
	 pendingInvoiceItemDto.setFreegoodInd(item.getString("FREEGOOD_IND"));
	
	 pendingInvoiceItemDto.setPendingInvoicedto(pendingInvDto);
	
	 invoiceItemDtoList.add(pendingInvoiceItemDto);
	 }
	
	 }
	
	 pendingInvoiceDto.setInvoiceItemList(invoiceItemDtoList);
	
	 }
	
	 }
	 }
	 } catch (Exception e) {
	
	 logger.error("[HciInvoiceDetailService][getOpenInvoiceDetails]" +
	 e.getMessage());
	 /*
	 * responseDto.setMessage(e.getMessage());
	 * responseDto.setStatus(false); responseDto.setCode(1);
	 */
	
	 }
	
	 /*
	 * responseDto.setData(invoiceHeaderDto);
	 * responseDto.setMessage("success"); responseDto.setStatus(true);
	 * responseDto.setCode(0);
	 */
	 }
	
	 return pendingInvoiceDto;
	
	 }
	
	 // it is new service to get All open inovices
	
	 @Override
	 public List<PendingInvoiceDo> getAllOpenInvocesFromRFC() {
	
	 List<PendingInvoiceDo> pendingInvoiceDoList = new ArrayList<>();
	
	 List<String> custList = new ArrayList<>();
	 List<String> soldCustList = new ArrayList<>();
	 List<String> shipCustList = new ArrayList<>();
	
	 Set<String> custSet = new HashSet<>();
	
	 shipCustList = customerDao.getAllCustomer();
	 soldCustList = customerDao.getAllSoldCustomer();
	
	 custSet.addAll(shipCustList);
	 custSet.addAll(soldCustList);
	
	 custList.addAll(custSet);
	
	 if (custList != null && !custList.isEmpty()) {
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]::Get Customers from Customer table Count="
	 + custList.size());
	
	 JSONObject inputObject = new JSONObject();
	
	 JSONObject getMultipleInvoiceDetails = new JSONObject();
	
	 JSONArray record = new JSONArray();
	
	 JSONObject customerObj;
	
	 for (String customerId : custList) {
	
	 customerObj = new JSONObject();
	
	 customerObj.put("CustomerNumbers", customerId);
	
	 record.put(customerObj);
	
	 }
	
	 getMultipleInvoiceDetails.put("Record", record);
	 inputObject.put("GetMultipleInvoiceDetails", getMultipleInvoiceDetails);
	
	 String response =
	 restInvoker.postDataToServer(HciApiConstants.INVOICE_DETAILS_OF_MULTIPLE_CUSTOMERS,
	 inputObject.toString());
	
	 if (response != null && !(response.equals(""))) {
	
	 Map<String, Object> invoiceMap = new HashMap<>();
	
	 Map<String, List<Object>> invoiceItemsMap = new HashMap<>();
	
	 Map<String, Object> invoiceHeaderMap = new HashMap<>();
	
	 JSONObject responseObject = new JSONObject(response);
	
	 JSONObject zsdGetInvoiceDetails = new JSONObject();
	
	 zsdGetInvoiceDetails =
	 responseObject.getJSONObject("ZSD_GET_INVOICE_DETAILS.Response");
	
	 JSONObject invoice = new JSONObject();
	
	 invoice = zsdGetInvoiceDetails.optJSONObject("ET_INVOICES");
	
	 if (invoice != null) {
	
	 // forming invoice map
	
	 if (invoice.optJSONArray("item") != null) {
	
	 JSONArray invoiceArray = new JSONArray();
	
	 invoiceArray = invoice.optJSONArray("item");
	
	 int invoiceLength = invoiceArray.length();
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]::Get invoice Count="
	 + invoiceLength);
	
	 JSONObject invoiceItem;
	
	 for (int i = 0; i < invoiceLength; i++) {
	
	 invoiceItem = invoiceArray.getJSONObject(i);
	
	 invoiceMap.put(invoiceItem.getString("BELNR"), invoiceItem);
	 }
	
	 } else if (invoice.optJSONObject("item") != null) {
	
	 JSONObject invoiceItem = invoice.optJSONObject("item");
	
	 invoiceMap.put(invoiceItem.getString("BELNR"), invoiceItem);
	 }
	
	 // forming invoice map end
	
	 }
	
	 JSONObject invoiceHeader = new JSONObject();
	
	 invoiceHeader = zsdGetInvoiceDetails.optJSONObject("ET_INVHEADER");
	
	 if (invoiceHeader != null) {
	
	 // forming invoice header map
	
	 if (invoiceHeader.optJSONArray("item") != null) {
	
	 JSONArray invoiceHeaderArray = new JSONArray();
	
	 invoiceHeaderArray = invoiceHeader.optJSONArray("item");
	
	 int invoiceHeaderLength = invoiceHeaderArray.length();
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]::Get invoiceHeader Count="
	 + invoiceHeaderLength);
	
	 JSONObject invoiceHeaderItem;
	
	 for (int i = 0; i < invoiceHeaderLength; i++) {
	
	 invoiceHeaderItem = invoiceHeaderArray.getJSONObject(i);
	
	 invoiceHeaderMap.put(invoiceHeaderItem.getString("INV_NUM"),
	 invoiceHeaderItem);
	 }
	
	 } else if (invoiceHeader.optJSONObject("item") != null) {
	
	 JSONObject invoiceHeaderItem = invoiceHeader.optJSONObject("item");
	
	 invoiceHeaderMap.put(invoiceHeaderItem.getString("INV_NUM"),
	 invoiceHeaderItem);
	 }
	
	 // forming invoice header map end
	 }
	
	 JSONObject invoiceItems = new JSONObject();
	
	 invoiceItems = zsdGetInvoiceDetails.optJSONObject("ET_INVLINES");
	
	 if (invoiceItems != null) {
	
	 // forming invoiceItems
	
	 if (invoiceItems.optJSONArray("item") != null) {
	
	 JSONArray invoiceItemsArray = new JSONArray();
	
	 invoiceItemsArray = invoiceItems.optJSONArray("item");
	
	 int invoiceItemsLength = invoiceItemsArray.length();
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]::Get   invoice Item Count="
	 + invoiceItemsLength);
	
	 JSONObject invoiceArrayItem;
	
	 for (int j = 0; j < invoiceItemsLength; j++) {
	
	 invoiceArrayItem = invoiceItemsArray.getJSONObject(j);
	
	 if (invoiceItemsMap.containsKey(invoiceArrayItem.getString("INV_NUM"))) {
	
	 List<Object> objList =
	 invoiceItemsMap.get(invoiceArrayItem.getString("INV_NUM"));
	
	 objList.add(invoiceArrayItem);
	
	 invoiceItemsMap.put(invoiceArrayItem.getString("INV_NUM"), objList);
	
	 } else {
	
	 List<Object> objList = new ArrayList<>();
	
	 objList.add(invoiceArrayItem);
	
	 invoiceItemsMap.put(invoiceArrayItem.getString("INV_NUM"), objList);
	
	 }
	
	 }
	
	 }
	
	 // forming invoiceItems
	
	 }
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]:invoiceMapsize:" + invoiceMap.size()
	 + ":invoiceHeaderMap:" + invoiceHeaderMap.size() + ":invoiceItemsMap:"
	 + invoiceItemsMap.size());
	
	 pendingInvoiceDoList = getpendingInvoiceDoList(invoiceMap,
	 invoiceHeaderMap, invoiceItemsMap);
	
	 }
	
	 }
	
	 return pendingInvoiceDoList;
	 }
	
	 private List<PendingInvoiceDo> getpendingInvoiceDoList(Map<String,
	 Object> invoiceMap,
	 Map<String, Object> invoiceHeaderMap, Map<String, List<Object>>
	 invoiceItemsMap) {
	
	 List<PendingInvoiceDo> pendingInvoiceDoList = new ArrayList<>();
	
	 PendingInvoiceDo pendingInvoiceDo;
	
	 try {
	
	 Set<Entry<String, Object>> entrySet = invoiceMap.entrySet();
	
	 for (Entry<String, Object> entry : entrySet) {
	
	 pendingInvoiceDo = new PendingInvoiceDo();
	
	 JSONObject invoice = (JSONObject) entry.getValue();
	
	 pendingInvoiceDo.setCustId(invoice.getString("KUNNR"));
	 pendingInvoiceDo.setAccountingDocument(invoice.getString("BELNR"));
	 pendingInvoiceDo.setDocumentType(invoice.getString("BLART"));
	 pendingInvoiceDo.setBillingDocumentNo(invoice.getString("VBELN"));
	 pendingInvoiceDo.setCurrency(invoice.getString("WAERS"));
	 pendingInvoiceDo.setAmountInLocalCurrency(new
	 BigDecimal(invoice.getString("DMBTR")));
	 pendingInvoiceDo.setAmount(new BigDecimal(invoice.getString("WRBTR")));
	 pendingInvoiceDo.setPendingAmount(new
	 BigDecimal(invoice.getString("PENDING_AMOUNT")));
	 pendingInvoiceDo.setPostingDate(new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoice.getString("BUDAT")));
	 pendingInvoiceDo.setDocumentDate(new
	 SimpleDateFormat("yyyy-MM-dd").parse(invoice.getString("BLDAT")));
	
	 if (pendingInvoiceDo.getBillingDocumentNo() != null
	 && !pendingInvoiceDo.getBillingDocumentNo().trim().isEmpty()) {
	
	 JSONObject header = (JSONObject)
	 invoiceHeaderMap.get(pendingInvoiceDo.getBillingDocumentNo());
	
	 if (header != null) {
	
	 pendingInvoiceDo.setInvoiceNumber(header.getString("INV_NUM"));
	
	 pendingInvoiceDo
	 .setBillDate(new
	 SimpleDateFormat("yyyy-MM-dd").parse(header.getString("BILL_DATE")));
	
	 pendingInvoiceDo.setSoldToParty(header.getString("SOLD_TO_PARTY"));
	 pendingInvoiceDo.setPayer(header.getString("PAYER"));
	 pendingInvoiceDo.setNetValue(header.getBigDecimal("NET_VALUE"));
	 pendingInvoiceDo.setTaxAmount(header.getBigDecimal("TAX_AMOUNT"));
	 pendingInvoiceDo.setCurrency(header.getString("CURRENCY"));
	
	 List<PendingInvoiceItemDo> itemList = new ArrayList<>();
	
	 PendingInvoiceItemDo item;
	
	 List<Object> objList =
	 invoiceItemsMap.get(pendingInvoiceDo.getInvoiceNumber());
	
	 if (objList != null && objList.size() > 0) {
	
	 for (Object obj : objList) {
	
	 JSONObject itemObj = (JSONObject) obj;
	
	 item = new PendingInvoiceItemDo();
	
	 item.setInvoiceNumber(itemObj.getString("INV_NUM"));
	 item.setItemNumber(itemObj.getString("ITEM_NUM"));
	 item.setMatID(itemObj.getString("MATERIAL"));
	 item.setDescription(itemObj.getString("DESCRIPTION"));
	 item.setHighItem(itemObj.getBigDecimal("HIGH_ITEM"));
	 item.setBilledQty(itemObj.getBigDecimal("BILLED_QTY"));
	 item.setSalesUnit(itemObj.getString("SALES_UNIT"));
	 item.setNetPrice(itemObj.getBigDecimal("NET_PRICE"));
	 item.setTaxAmount(itemObj.getBigDecimal("TAX_AMOUNT"));
	 item.setSalesDoc(itemObj.getString("SALES_DOC"));
	 item.setItemCateg(itemObj.getString("ITEM_CATEG"));
	 item.setFreegoodInd(itemObj.getString("FREEGOOD_IND"));
	 item.setPendingInvoice(pendingInvoiceDo);
	
	 itemList.add(item);
	
	 }
	
	 }
	
	 pendingInvoiceDo.setInvoiceItemList(itemList);
	
	 }
	 }
	
	 pendingInvoiceDoList.add(pendingInvoiceDo);
	
	 }
	
	 } catch (Exception e) {
	
	 logger.error("[HciInvoiceDetailService][getpendingInvoiceDoList]" +
	 e.getMessage());
	 }
	
	 logger.error("[HciInvoiceDetailService][getAllOpenInvocesFromRFC]: final pendingInvoiceDoList size:"
	 + pendingInvoiceDoList.size());
	
	 return pendingInvoiceDoList;
	 }


	


	

}
