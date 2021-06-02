package com.incture.cherrywork.OdataS;





import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.Odat.Dto.SalesOrderHeaderOdataDto;
import com.incture.cherrywork.Odat.Dto.SalesOrderHeaderOdataSaveDto;
import com.incture.cherrywork.Odat.Dto.SalesOrderItemOdataDto;
import com.incture.cherrywork.Odat.Dto.SalesOrderItemOdataSaveDto;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;

import com.incture.cherrywork.dto.new_workflow.ListOfChangedItemData;
import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.new_workflow.dao.SalesOrderItemStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.new_workflow.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.SalesDocItemService;
import com.incture.cherrywork.services.SalesOrderHistoryServiceImpl;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;


@Service
@Transactional
public class OdataServiceImpl implements OdataService {

	@Autowired
	private SalesOrderLevelStatusDao salesOrderLevelStatusDao;

	@Autowired
	private SalesOrderTaskStatusDao salesOrderTaskStatusDao;

	@Autowired
	private SalesOrderItemStatusDao salesOrderItemStatusDao;

	@Autowired
	private SalesDocItemService salesDocItemService;

	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

	@Override
	public ResponseEntity updateSalesOrderInEccUsingOdata(OnSubmitTaskDto headerData,
			Boolean flagToAddAcceptCaseAtLastLevel) {
		try {

			LocalTime start = LocalTime.now();
			System.err.println("Entry time on ON-SUBMIT = " + start);
			if (!HelperClass.checkString(headerData.getSalesOrderNum())) {

				if (!headerData.getListOfChangedItemData().isEmpty()) {

					List<SalesOrderItemOdataDto> itemList = new ArrayList<>();

					SalesOrderHeaderOdataDto salesOrderHeader = new SalesOrderHeaderOdataDto();
					salesOrderHeader.setDocNumber(headerData.getSalesOrderNum());
					salesOrderHeader.setPurchNo("SUBMIT");

					if (headerData.getHeaderAcceptOrReject() != null) {
						salesOrderHeader.setDlvBlock(headerData.getHeaderAcceptOrReject());
					}
					salesOrderHeader.setDlvBlock(headerData.getHeaderAcceptOrReject());

					for (ListOfChangedItemData itemData : headerData.getListOfChangedItemData()) {

						if (itemData.getAcceptOrReject() != null) {
							SalesOrderItemOdataDto itemOdataDto = new SalesOrderItemOdataDto();

							itemOdataDto.setDocNumber(headerData.getSalesOrderNum());
							itemOdataDto.setItmNumber(itemData.getSalesItemOrderNo());

							if (itemData.getMaterial() != null) {
								itemOdataDto.setMaterial(itemData.getMaterial());
							} else {
								itemOdataDto.setMaterial("");
							}

							if (itemData.getReasonForRejection() != null) {
								itemOdataDto.setReaForRe(itemData.getReasonForRejection());
							} else {
								itemOdataDto.setReaForRe("");
							}

							if (headerData.getLoggedInUserName() != null) {
								itemOdataDto.setCreatedBy(headerData.getLoggedInUserName().split(" ")[0]);
							} else {
								itemOdataDto.setCreatedBy("");
							}

							// True - Accepted Items
							// False - Rejected items
							if (flagToAddAcceptCaseAtLastLevel && itemData.getAcceptOrReject().equalsIgnoreCase("A")) {

								// remove the block code from item
								salesDocItemService.removeItemDeliveryBlockFromSalesDocItem(
										headerData.getSalesOrderNum(), itemData.getSalesItemOrderNo());

								itemList.add(itemOdataDto);
							}

							if (itemData.getAcceptOrReject().equalsIgnoreCase("R")) {

								// add reason of rejection for item
								salesDocItemService.addReasonOfRejectionFromSalesDocItem(headerData.getSalesOrderNum(),
										itemData.getSalesItemOrderNo(), itemData.getReasonForRejection());

								itemList.add(itemOdataDto);
							}

						}

					}
					salesOrderHeader.setOrderToItems(itemList);

					ObjectMapper objectMapper = new ObjectMapper();
					String entity = objectMapper.writeValueAsString(salesOrderHeader);
					System.err.println(entity);

					String responseFromServer = consumingOdataService(entity);
					LocalTime exit = LocalTime.now();
					System.err.println("Exit time on ON-SUBMIT = " + exit);
					if (responseFromServer == null) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST, "No Headers are generated",
								ResponseStatus.FAILED);
					} else {

						if (responseFromServer.equalsIgnoreCase(Constants.SUCCESSFULLY_UPDATED_IN_ECC)) {
							return new ResponseEntity(responseFromServer, HttpStatus.CREATED,
									"Data is successfully updated in ECC", ResponseStatus.SUCCESS);

						} else if (responseFromServer.equalsIgnoreCase(Constants.TOKEN_GENERATION_FAILED)) {
							return new ResponseEntity(responseFromServer, HttpStatus.BAD_REQUEST,
									"No Token is generated", ResponseStatus.FAILED);
						} else {

							updateOldItemStatusInCaseOfFailureFromECC(headerData);
							return new ResponseEntity(responseFromServer, HttpStatus.BAD_REQUEST,
									"Failed to update in ECC , Reason := " + responseFromServer, ResponseStatus.FAILED);
						}
					}

				} else {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "No item is selected", ResponseStatus.FAILED);
				}

			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						Constants.INVALID_INPUT + ", Sales Order Number is not available", ResponseStatus.FAILED);
			}
		} catch (

		Exception e) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}

	}

	private void updateOldItemStatusInCaseOfFailureFromECC(OnSubmitTaskDto headerData) {
		// update item status in case of failure
		List<SalesOrderItemStatusDto> itemsInAPerticularTask = soItemStatusRepo
				.getItemsBySapTaskId(headerData.getWorkflowId());
		Map<String, SalesOrderItemStatusDto> mapOfItemsInDb = itemsInAPerticularTask.stream().collect(Collectors
				.toMap(SalesOrderItemStatusDto::getSalesOrderItemNum, item -> item, (oldValue, newValue) -> newValue));

		System.err.println("Map from hana : " + mapOfItemsInDb);
		// iterating items and creating map for items
		headerData.getListOfChangedItemData().forEach(itemOnSubmit -> {

			// Item to revert back it must be Approved and contain data in db
			if (!HelperClass.checkString(itemOnSubmit.getAcceptOrReject())
					&& "A".equalsIgnoreCase(itemOnSubmit.getAcceptOrReject())
					&& mapOfItemsInDb.containsKey(itemOnSubmit.getSalesItemOrderNo())) {

				SalesOrderItemStatusDto itemToUpdateInDb = mapOfItemsInDb.get(itemOnSubmit.getSalesItemOrderNo());

				itemToUpdateInDb.setItemStatus(StatusConstants.BLOCKED);
				itemToUpdateInDb.setVisiblity(StatusConstants.VISIBLITY_ACTIVE);
				System.err.println("Item to update : " + itemToUpdateInDb);
				try {
					soItemStatusRepo.saveOrUpdateSalesOrderItemStatus(itemToUpdateInDb);
				} catch (ExecutionFault e) {
					return;
				}

			}
		});
	}

	private static String consumingOdataService(String entity) throws IOException, URISyntaxException {

		Map<String, Object> map = DestinationReaderUtil
				.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);

		String url = map.get("URL") + "/sap/opu/odata/sap/ZCC_SALESORDER_DATA_SRV/soheaderSet";

		// TenantContext tenantctx = new HelperClass().getTenantInformation();

		String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
		int proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
		HttpClient client = HttpClientBuilder.create().build();

		// Header[] headers = generateXCSRFToken(url, (String) map.get("User"),
		// (String) map.get("Password"), client,
		// tenantctx, proxyHost, proxyPort, (String) map.get("sap-client"));

		Header[] headers = generateXCSRFToken(url, (String) map.get("User"), (String) map.get("Password"), client,
				proxyHost, proxyPort, (String) map.get("sap-client"));

		if (headers.length != 0) {

			HttpPost httpPost = new HttpPost(url);

//			if (tenantctx != null) {
//				httpPost.addHeader("SAP-Connectivity-ConsumerAccount", tenantctx.getTenant().getAccount().getId());
//			}

			String token = null;
			List<String> cookies = new ArrayList<>();
			for (Header header : headers) {

				if (header.getName().equalsIgnoreCase("x-csrf-token")) {
					token = header.getValue();
				}

				if (header.getName().equalsIgnoreCase("set-cookie")) {
					cookies.add(header.getValue());
				}

			}

			if (token == null) {
				return Constants.TOKEN_GENERATION_FAILED;
			}
			// Encoding username and password
			String auth = HelperClass.encodeUsernameAndPassword((String) map.get("User"), (String) map.get("Password"));
			httpPost.addHeader("Authorization", auth);
			System.err.println("Token for update in ECC : " + token);
			if (token != null) {
				httpPost.addHeader("X-CSRF-Token", token);
			}
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("sap-client", (String) map.get("sap-client"));

			if (!cookies.isEmpty()) {
				for (String cookie : cookies) {

					String tmp = cookie.split(";", 2)[0];
					httpPost.addHeader("Cookie", tmp);

				}
			}
			if (!HelperClass.checkString(entity)) {
				StringEntity jsonEntity = new StringEntity(entity);
				jsonEntity.setContentType("application/json");
				httpPost.setEntity(jsonEntity);
				System.err.println(jsonEntity + "********");
			}

			HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpPost);

			String responseFromECC = HelperClass.getDataFromStream(response.getEntity().getContent());

			if (response.getStatusLine().getStatusCode() == HttpStatus.CREATED.value()) {
				return Constants.SUCCESSFULLY_UPDATED_IN_ECC;
			} else {
				try {
					JSONObject xmlJSONObj = XML.toJSONObject(responseFromECC);
					System.err.println(xmlJSONObj);
					System.err.println(xmlJSONObj.getJSONObject("error").getJSONObject("message").get("content"));
					xmlJSONObj.toString(Constants.PRETTY_PRINT_INDENT_FACTOR);
					return xmlJSONObj.getJSONObject("error").getJSONObject("message").get("content").toString();
				} catch (JSONException je) {
					throw new ParseException("XML to JSON parsing failed due to, " + je.getMessage());
				}
			}

		} else {
			return null;
		}

	}

//	private static Header[] generateXCSRFToken(String url, String username, String password, HttpClient client,
//			TenantContext tenantctx, String proxyHost, int proxyPort, String sapClient) throws IOException {
//
//		HttpGet httpGet = new HttpGet(url);
//
//		// Encoding username and password
//		String auth = HelperClass.encodeUsernameAndPassword(username, password);
//		httpGet.addHeader("Authorization", auth);
//		httpGet.addHeader("X-CSRF-Token", "Fetch");
//		httpGet.addHeader("Content-Type", "application/json");
//		httpGet.addHeader("sap-client", sapClient);
//
//		if (tenantctx != null) {
//			httpGet.addHeader("SAP-Connectivity-ConsumerAccount", tenantctx.getTenant().getAccount().getId());
//		}
//
//		HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpGet);
//		return response.getAllHeaders();
//	}
	private static Header[] generateXCSRFToken(String url, String username, String password, HttpClient client,
			 String proxyHost, int proxyPort, String sapClient) throws IOException {

		HttpGet httpGet = new HttpGet(url);

		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(username, password);
		httpGet.addHeader("Authorization", auth);
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);

		// if (tenantctx != null) {
		// httpGet.addHeader("SAP-Connectivity-ConsumerAccount",
		// tenantctx.getTenant().getAccount().getId());
		// }

		HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpGet);
		return response.getAllHeaders();
	}

	@Override
	public ResponseEntity onSaveOrEdit(OnSubmitTaskDto onSubmitTaskDto) {

		// 1. updating data in SAP ECC
		// ResponseEntity responseEntity =
		// updateSalesOrderInEccUsingOdata(onSubmitTaskDto, false);

		// adding different data for save functionality

		// checking time for ECC call
		LocalTime entry = LocalTime.now();
		System.err.println("entry time for Ecc update=" + entry);

		ResponseEntity responseEntity = updateSalesOrderInEccUsingOdataForSave(onSubmitTaskDto);

		LocalTime exit = LocalTime.now();
		System.err.println("exit time for Ecc update=" + exit);
		System.err.println("total time taken for Ecc update= " + Duration.between(entry, exit));

		if (HttpStatus.CREATED == responseEntity.getStatusCode()) {
			System.err.println("Data is successfully updated in ECC");

			// 2. update Tables for item ,task ,level
			// updatingDataInTables(onSubmitTaskDto.getDecisionSetId(),
			// onSubmitTaskDto.getLevelNum());

			LocalTime entry1 = LocalTime.now();
			System.err.println("entry time for hana saving=" + entry1);

			// 3. updating Tables for Soheader and Item
			updatingDataInSoRelatedTables(onSubmitTaskDto);

			LocalTime exit1 = LocalTime.now();
			System.err.println("exit time for hana saving==" + exit1);
			System.err.println("total time taken for hana saving= = " + Duration.between(entry1, exit1));
			// updating version for sales order history
			// updateItemInSalesOrderHistory(onSubmitTaskDto);

			String msg = responseEntity.getMessage() + " and upadated in Hana dB also";
			responseEntity.setMessage(msg);
			return responseEntity;
		} else {
			// return "Problem in Updating ECC" ;
			return responseEntity;

		}

	}

	private void updateItemInSalesOrderHistory(OnSubmitTaskDto onSubmitTaskDto) {
		for (ListOfChangedItemData item : onSubmitTaskDto.getListOfChangedItemData()) {

			SalesOrderHistoryDto salesOrderHistoryDto = new SalesOrderHistoryDto();

			salesOrderHistoryDto.setBatchNum(item.getBatchNum());
			// salesOrderHistoryDto.setItemBillingBlock(item.getIte);
			salesOrderHistoryDto.setItemDlvBlock(item.getItemDeliveryBlock());
			salesOrderHistoryDto.setNetPrice(Double.parseDouble(item.getUnitPrice()));
			salesOrderHistoryDto.setNetWorth(item.getAmount());
			salesOrderHistoryDto.setOrderedQtySales(item.getSalesQty());
			// salesOrderHistoryDto.setPlant(item.get);
			salesOrderHistoryDto.setReasonOfRejection(item.getReasonForRejection());
			salesOrderHistoryDto.setSalesDocNum(onSubmitTaskDto.getSalesOrderNum());
			salesOrderHistoryDto.setSalesItemNum(item.getSalesItemOrderNo());
			salesOrderHistoryDto.setSalesUnit(item.getSalesUnit());
			salesOrderHistoryDto.setStorageLoc(item.getStorageLoc());
			salesOrderHistoryDto.setUpdatedBy(onSubmitTaskDto.getLoggedInUserName());
			salesOrderHistoryDto.setUpdatedOn(new Date());

			ResponseEntity response = new SalesOrderHistoryServiceImpl().saveSalesOrderItem(salesOrderHistoryDto);
			System.err.println("Response History : " + response);
		}
	}

	public ResponseEntity updateSalesOrderInEccUsingOdataForSave(OnSubmitTaskDto headerData) {
		try {
			if (!HelperClass.checkString(headerData.getSalesOrderNum())) {

				if (!headerData.getListOfChangedItemData().isEmpty()) {

					List<SalesOrderItemOdataSaveDto> itemList = new ArrayList<>();

					SalesOrderHeaderOdataSaveDto salesOrderHeader = new SalesOrderHeaderOdataSaveDto();
					salesOrderHeader.setDocNumber(headerData.getSalesOrderNum());
					salesOrderHeader.setPurchNo("SAVE");

					for (ListOfChangedItemData itemData : headerData.getListOfChangedItemData()) {

						System.err.println("Inside save method for save update functionality");
						SalesOrderItemOdataSaveDto itemOdataDto = new SalesOrderItemOdataSaveDto();

						itemOdataDto.setDocNumber(headerData.getSalesOrderNum());
						itemOdataDto.setItmNumber(itemData.getSalesItemOrderNo());

						if (itemData.getBatchNum() != null) {
							itemOdataDto.setBatch(itemData.getBatchNum());
						} else {
							itemOdataDto.setBatch("");
						}
						if (itemData.getMaterial() != null) {
							itemOdataDto.setMaterial(itemData.getMaterial());
						} else {
							itemOdataDto.setMaterial("");
						}

						if (headerData.getLoggedInUserName() != null) {
							itemOdataDto.setCreatedBy(headerData.getLoggedInUserName().split(" ")[0]);
						} else {
							itemOdataDto.setCreatedBy("");
						}

						// setting fields for save/edit functionality Mohit
						// Basak
						if (itemData.getSalesQty() != null) {
							itemOdataDto.setReqQty(itemData.getSalesQty().toString());
						} else {
							itemOdataDto.setReqQty("0.00");
						}

						if (itemData.getSalesUnit() != null) {
							itemOdataDto.setSalesUnit(itemData.getSalesUnit());
						} else {
							itemOdataDto.setSalesUnit("");
						}

						if (itemData.getUnitPrice() != null) {
							itemOdataDto.setNetPrice(itemData.getUnitPrice());
						} else {
							itemOdataDto.setNetPrice("0.00");
						}
						if (itemData.getStorageLocValue() != null) {
							itemOdataDto.setStgeLoc(itemData.getStorageLocValue());
						} else {
							itemOdataDto.setStgeLoc("");
						}

						// adding to itemlist
						itemList.add(itemOdataDto);
					}
					salesOrderHeader.setOrderToItems(itemList);

					System.err.println("Print data ****" + salesOrderHeader.toString());

					ObjectMapper objectMapper = new ObjectMapper();
					String entity = objectMapper.writeValueAsString(salesOrderHeader);
					System.err.println(entity);

					// For Live Service
					String responseFromServer = consumingOdataService(entity);

					// For Local Testing
					// String responseFromServer =
					// consumingOdataServiceFromLocal(entity);

					if (responseFromServer == null) {
						return new ResponseEntity("", HttpStatus.BAD_REQUEST, "No Headers are generated",
								ResponseStatus.FAILED);
					} else {

						if (responseFromServer.equalsIgnoreCase(Constants.SUCCESSFULLY_UPDATED_IN_ECC)) {
							return new ResponseEntity(responseFromServer, HttpStatus.CREATED,
									"Data is successfully updated in ECC", ResponseStatus.SUCCESS);

						} else if (responseFromServer.equalsIgnoreCase(Constants.TOKEN_GENERATION_FAILED)) {
							return new ResponseEntity(responseFromServer, HttpStatus.BAD_REQUEST,
									"No Token is generated", ResponseStatus.FAILED);
						} else {
							return new ResponseEntity(responseFromServer, HttpStatus.BAD_REQUEST,
									"Failed to update in ECC , Reason := " + responseFromServer, ResponseStatus.FAILED);
						}
					}

				} else {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "No item is selected", ResponseStatus.FAILED);
				}

			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						Constants.INVALID_INPUT + ", Sales Order Number is not available", ResponseStatus.FAILED);
			}
		} catch (

		Exception e) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}

	}

	@SuppressWarnings("unused")
	private void updatingDataInTables(String decisionSetId, String levelId) {

		// Fetch tasks from decision set id and level
		/*
		 * List<SalesOrderTaskStatusDto> soTaskStatusDtoList = soTaskStatusRepo
		 * .getAllTasksFromDecisionSetAndLevel(decisionSetId, levelId);
		 */
		try {
			List<SalesOrderLevelStatusDto> levelsList = salesOrderLevelStatusDao
					.getSalesOrderLevelStatusByDecisionSet(decisionSetId);

			for (SalesOrderLevelStatusDto salesOrderLevelStatusDto : levelsList) {
				List<SalesOrderTaskStatusDto> listOfTasks = salesOrderTaskStatusDao
						.getListOfAllTasksFromLevelStatusSerialId(salesOrderLevelStatusDto.getLevelStatusSerialId());
				for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : listOfTasks) {
					List<SalesOrderItemStatusDto> listOfItems = salesOrderItemStatusDao
							.getItemStatusDataUsingTaskSerialId(salesOrderTaskStatusDto.getTaskStatusSerialId());
					for (SalesOrderItemStatusDto salesOrderItemStatusDto : listOfItems) {
						salesOrderItemStatusDto.setItemStatus(StatusConstants.ITEM_REJECT);
						salesOrderItemStatusDao.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);
					}
//					salesOrderTaskStatusDto.setTaskStatus(StatusConstants.ITEM_REJECT);
					salesOrderTaskStatusDao.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
				}
				salesOrderLevelStatusDto.setLevelStatus(StatusConstants.LEVEL_BREACH);
				salesOrderLevelStatusDao.saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);
			}
		} catch (ExecutionFault e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	private void updatingDataInSoRelatedTables(OnSubmitTaskDto headerData) {
		String salesOrderNum = headerData.getSalesOrderNum();
		List<ListOfChangedItemData> listOfChangedItems = headerData.getListOfChangedItemData();
		for (ListOfChangedItemData changedItemDetail : listOfChangedItems) {
			String salesItemOrderNo = changedItemDetail.getSalesItemOrderNo();
			SalesDocItemDto salesDocItemDto = (SalesDocItemDto) salesDocItemService
					.getSalesDocItemById(salesItemOrderNo, salesOrderNum).getData();

			// getting the changed data and setting to the db Entity

			if (changedItemDetail.getMaterial() != null) {
				salesDocItemDto.setSapMaterialNum(changedItemDetail.getMaterial());
			}
			if (changedItemDetail.getSalesQty() != null) {
				salesDocItemDto.setOrderedQtySales(changedItemDetail.getSalesQty());
			}
			if (changedItemDetail.getSalesUnit() != null) {
				salesDocItemDto.setSalesUnit(changedItemDetail.getSalesUnit());
			}
			if (changedItemDetail.getUnitPrice() != null) {
				salesDocItemDto.setNetPrice(changedItemDetail.getUnitPrice());
			}
			if (changedItemDetail.getStorageLoc() != null) {
				salesDocItemDto.setStorageLoc(changedItemDetail.getStorageLoc());
			}
			if (changedItemDetail.getBatchNum() != null) {
				salesDocItemDto.setBatchNum(changedItemDetail.getBatchNum());
			}

			salesDocItemService.saveOrUpdateSalesDocItemUsingMerge(salesDocItemDto);
		}
	}

	// For Local Testing
	private static Header[] generateXCSRFTokenFromLocal(String url, String username, String password, HttpClient client,
			String sapClient) throws IOException {

		HttpGet httpGet = new HttpGet(url);

		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(username, password);
		httpGet.addHeader("Authorization", auth);
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);

		/*
		 * if (tenantctx != null) {
		 * httpGet.addHeader("SAP-Connectivity-ConsumerAccount",
		 * tenantctx.getTenant().getAccount().getId()); }
		 */

		// HttpResponse response = client.execute(new HttpHost(proxyHost,
		// proxyPort), httpGet);
		HttpResponse response = client.execute(httpGet);
		return response.getAllHeaders();
	}

	// for checking from local
	public static String consumingOdataServiceFromLocal(String entity) throws IOException, URISyntaxException {

		Map<String, Object> map = DestinationReaderUtil
				.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);

		String url = map.get("URL") + "/sap/opu/odata/sap/ZCC_SALESORDER_DATA_SRV/soheaderSet";

		// TenantContext tenantctx = new HelperClass().getTenantInformation();

		// String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
		// int proxyPort =
		// Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
		HttpClient client = HttpClientBuilder.create().build();

		Header[] headers = generateXCSRFTokenFromLocal(url, (String) map.get("User"), (String) map.get("Password"),
				client, (String) map.get("sap-client"));

		if (headers.length != 0) {

			HttpPost httpPost = new HttpPost(url);

			/*
			 * if (tenantctx != null) {
			 * httpPost.addHeader("SAP-Connectivity-ConsumerAccount",
			 * tenantctx.getTenant().getAccount().getId()); }
			 */
			String token = null;
			List<String> cookies = new ArrayList<>();
			for (Header header : headers) {

				if (header.getName().equalsIgnoreCase("x-csrf-token")) {
					token = header.getValue();
				}

				if (header.getName().equalsIgnoreCase("set-cookie")) {
					cookies.add(header.getValue());
				}

			}

			if (token == null) {
				return Constants.TOKEN_GENERATION_FAILED;
			}
			// Encoding username and password
			String auth = HelperClass.encodeUsernameAndPassword((String) map.get("User"), (String) map.get("Password"));
			httpPost.addHeader("Authorization", auth);
			System.err.println("Token for update in ECC : " + token);
			if (token != null) {
				httpPost.addHeader("X-CSRF-Token", token);
			}
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("sap-client", (String) map.get("sap-client"));

			if (!cookies.isEmpty()) {
				for (String cookie : cookies) {

					String tmp = cookie.split(";", 2)[0];
					httpPost.addHeader("Cookie", tmp);

				}
			}
			if (!HelperClass.checkString(entity)) {
				StringEntity jsonEntity = new StringEntity(entity);
				jsonEntity.setContentType("application/json");
				httpPost.setEntity(jsonEntity);
				System.err.println(jsonEntity + "********");
			}

			HttpResponse response = client.execute(httpPost);

			String responseFromECC = HelperClass.getDataFromStream(response.getEntity().getContent());

			if (response.getStatusLine().getStatusCode() == HttpStatus.CREATED.value()) {
				return Constants.SUCCESSFULLY_UPDATED_IN_ECC;
			} else {
				try {
					JSONObject xmlJSONObj = XML.toJSONObject(responseFromECC);
					System.err.println(xmlJSONObj);
					System.err.println(xmlJSONObj.getJSONObject("error").getJSONObject("message").get("content"));
					xmlJSONObj.toString(Constants.PRETTY_PRINT_INDENT_FACTOR);
					return xmlJSONObj.getJSONObject("error").getJSONObject("message").get("content").toString();
				} catch (JSONException je) {
					throw new ParseException("XML to JSON parsing failed due to, " + je.getMessage());
				}
			}

		} else {
			return null;
		}

	}

}
