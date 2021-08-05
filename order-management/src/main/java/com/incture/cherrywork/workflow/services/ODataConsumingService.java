package com.incture.cherrywork.workflow.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.Odat.Dto.WorkflowTriggerInputDto;
import com.incture.cherrywork.OdataSe.ContextDto;
import com.incture.cherrywork.OdataSe.WorkFlowTriggerFromJava;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.RequestMasterDao;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.dtos.SchedulerTableDto;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ReturnExchangeConstants;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

/**
 * @author Mohit.Basak
 *
 */
@SuppressWarnings("unused")
@Service("ODataConsumingService")
@Transactional
public class ODataConsumingService {

	private static final String DOC_NUMBER = "DocNumber";

	private static final String RESULTS = "results";

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ODataConsumingService.class);

	@Lazy
	@Autowired
	private IRequestMasterService requestMasterService;

	@Lazy
	@Autowired
	private ApprovalworkflowTrigger approvalWorkflowTrigger;

	@Lazy
	@Autowired
	private SalesOrderHistoryService salesOrderHistoryService;

	@Autowired
	private SalesDocHeaderService salesDocHeaderService;

	@Autowired
	private SchedulerTableService schedulerLogService;

	@Autowired
	private SalesOrderItemStatusService salesOrderItemStatusService;

	@Autowired
	private TriggerImeDestinationService triggerImeService;

	@Autowired
	private BlockTypeDeterminationService btdService;

	// @Autowired
	// private SessionFactory sf;

	private SequenceNumberGen sequenceNumberGen;

	@PersistenceContext
	private EntityManager entityManager;

	public void mainScheduler() {
		JSONObject jsonObjectcontainingListOfSo = null;
		try {
			System.err.println("inside main method of ODataConsumingService");

			Map<String, Object> destinationInfo = DestinationReaderUtil
					.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);
			System.err.println("[scheduler controller][main scheduler]destinationInfo: " + destinationInfo);

			// get list of salesOrder from Odata service using ODATA service1
			String url1 = destinationInfo.get("URL") + createUrlToGetListOfSalesOrder();

			/*
			 * schedulerLogService.saveInDB( new
			 * SchedulerTableDto("URL created for odata : " + url1.toString(),
			 * new Date().toString()));
			 */
			/*
			 * schedulerLogService.saveInDB(new
			 * SchedulerTableDto("First URL created for odata : " +
			 * url1.toString(), new Date().toString(),
			 * LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			 */

			System.err.println("STEP 3:First url generated for bringing list of SO = " + url1);

			jsonObjectcontainingListOfSo = consumingOdataService(url1, "", "GET", destinationInfo);
			System.err.println("[scheduler controller][main scheduler]jsonObjectcontainingListOfSo: "
					+ jsonObjectcontainingListOfSo.toString());

			schedulerLogService.saveInDB(new SchedulerTableDto(
					"Sales orders came from First odata service : " + jsonObjectcontainingListOfSo.toString(),
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			System.err
					.println("STEP 7 inside ODataConsumingService.mainScheduler(); blocked sales order in last 2 hrs ="
							+ jsonObjectcontainingListOfSo);

			// forming the JSON structure from output of OData
			Map<String, String> soListWithMode = convertingJsonObjToListOfSo(jsonObjectcontainingListOfSo);

			schedulerLogService.saveInDB(new SchedulerTableDto(
					"Number of Sales order with mode are : " + soListWithMode.size() + " and Data is : "
							+ soListWithMode.toString(),
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			System.err.println("STEP 9 ODataConsumingService.mainScheduler()" + soListWithMode);

			if (!soListWithMode.isEmpty()) {
				// then make a batch of 5 data from list
				List<String> listOfSoWithbatch = batchingTheListOfSo(soListWithMode);

				schedulerLogService.saveInDB(new SchedulerTableDto(
						"Number of batches are : " + listOfSoWithbatch.size() + " and Data in each batch : "
								+ listOfSoWithbatch.toString(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

				System.err.println("STEP 11 listOfSoWithbatch" + listOfSoWithbatch);
				if (!listOfSoWithbatch.isEmpty()) {
					insertionOfData(listOfSoWithbatch, soListWithMode);
				}
			} else {
				System.err.println("scheduler didn't pick any sales order");
			}
		} catch (Exception e) {
			// schedulerLogService.saveInDB(new SchedulerTableDto(
			// "Failed due to Exception......." + e.getMessage() + " on " +
			// e.getStackTrace()[4],
			// new Date().toString(),
			// LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			System.err.println("[ODataConsumingService][main scheduler] exception " + e.getMessage());
			e.printStackTrace();

		}

	}

	/**
	 * @param listOfSoWithbatch
	 * @throws IOException
	 * 
	 *             this will insert data to hana dB by consuming the 2 different
	 *             Odata Service
	 */
	public void insertionOfData(List<String> listOfSoWithbatch, Map<String, String> soListWithMode) throws Exception {
		Map<String, Object> destinationInfo = DestinationReaderUtil
				.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);

		System.err.println("[odata consuming service][insertionOfData] destinationInfo: " + destinationInfo);
		// getting data for each so
		List<JSONObject> jsonObjectFromService2 = new ArrayList<>();

		for (String soNumbers : listOfSoWithbatch) {

			/*
			 * String url2 = destinationInfo.get("URL") +
			 * "/sap/opu/odata/sap/ZDKSH_CC_SALES_ORDER_SRV/SalesDocumentDetailsSet('"
			 * + soNumbers +
			 * "')?$expand=NAVTOHEADER,NAVTOITEM,NAVTOSCHEDULE,NAVTOISTATUS,NAVTOPARTNER,NAVTOHSTATUS&$format=json";
			 */

			String url2 = destinationInfo.get("URL")
					+ "/sap/opu/odata/sap/ZDKSH_CC_SALES_ORDER_CR_COM_SRV/SalesDocumentDetailsSet('" + soNumbers
					+ "')?$expand=NAVTOHEADER,NAVTOITEM,NAVTOSCHEDULE,NAVTOISTATUS,NAVTOPARTNER,NAVTOHSTATUS&$format=json";
			System.err.println("STEP 12 URL2 with each batch iteration = " + url2);

			schedulerLogService.saveInDB(new SchedulerTableDto("STEP 12 Odata URL2 with each batch iteration = " + url2,
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			try {
				JSONObject obj = null;
				obj = consumingOdataService(url2, "", "GET", destinationInfo);

				System.err.println("STEP 13 result from each batch hitting odata = " + obj);

				if (obj.length() != 0) {
					jsonObjectFromService2.add(obj);
				}
			} catch (Exception e) {
				System.err.println("Exception after step 12" + e.getMessage());
				e.printStackTrace();
			}

		}

		if (!jsonObjectFromService2.isEmpty()) {
			// Forming proper data for hana dB
			List<SalesDocHeaderDto> listOfSalesOrderDtos = importJsonfromOdataOutput(jsonObjectFromService2,
					soListWithMode);

			/*
			 * schedulerLogService.saveInDB(new SchedulerTableDto(
			 * "Number of sales order going for next hana saving  are : " +
			 * listOfSalesOrderDtos.size(), new Date().toString(),
			 * LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			 */

			// call rest service to insert data into Hana dB
			System.err.println("[main scheduler] before saveDataToHanaDb");
			saveDataToHanaDb(listOfSalesOrderDtos);
		}
	}

	public List<String> manualSchedulerInsertionOfData(List<String> listOfSoWithbatch,
			Map<String, String> soListWithMode) throws Exception {
		Map<String, Object> destinationInfo = DestinationReaderUtil
				.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);

		List<String> triggeredOrderSoNum = null;
		// getting data for each so
		List<JSONObject> jsonObjectFromService2 = new ArrayList<>();

		for (String soNumbers : listOfSoWithbatch) {

			/*
			 * String url2 = destinationInfo.get("URL") +
			 * "/sap/opu/odata/sap/ZDKSH_CC_SALES_ORDER_SRV/SalesDocumentDetailsSet('"
			 * + soNumbers +
			 * "')?$expand=NAVTOHEADER,NAVTOITEM,NAVTOSCHEDULE,NAVTOISTATUS,NAVTOPARTNER,NAVTOHSTATUS&$format=json";
			 */

			String url2 = destinationInfo.get("URL")
					+ "/sap/opu/odata/sap/ZDKSH_CC_SALES_ORDER_CR_COM_SRV/SalesDocumentDetailsSet('" + soNumbers
					+ "')?$expand=NAVTOHEADER,NAVTOITEM,NAVTOSCHEDULE,NAVTOISTATUS,NAVTOPARTNER,NAVTOHSTATUS&$format=json";
			System.err.println("STEP 12 URL2 with each batch iteration = " + url2);

			schedulerLogService.saveInDB(new SchedulerTableDto("STEP 12 Odata URL2 with each batch iteration = " + url2,
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			JSONObject obj = null;
			obj = consumingOdataService(url2, "", "GET", destinationInfo);

			System.err.println("STEP 13 result from each batch hitting odata = " + obj);

			if (obj.length() != 0) {
				jsonObjectFromService2.add(obj);
			}
		}

		if (!jsonObjectFromService2.isEmpty()) {
			// Forming proper data for hana dB
			List<SalesDocHeaderDto> listOfSalesOrderDtos = importJsonfromOdataOutput(jsonObjectFromService2,
					soListWithMode);

			/*
			 * schedulerLogService.saveInDB(new SchedulerTableDto(
			 * "Number of sales order going for next hana saving  are : " +
			 * listOfSalesOrderDtos.size(), new Date().toString(),
			 * LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			 */

			// call rest service to insert data into Hana dB
			triggeredOrderSoNum = ManualSchedulersaveDataToHanaDb(listOfSalesOrderDtos);
		}
		return triggeredOrderSoNum;
	}

	/**
	 * @param soList
	 * @return
	 */
	private List<String> batchingTheListOfSo(Map<String, String> soListWithMode) {

		int totalLength = 5;

		StringBuilder appendedSoNumber = new StringBuilder();
		List<String> listOfSobatch = new ArrayList<>();
		int count = 0;
		int sizeCount = 0;
		Set<String> soList = soListWithMode.keySet();
		for (String p : soList) {
			sizeCount++;
			if (count < totalLength) {
				count++;
			} else {
				count = 0;
				count++;

				listOfSobatch.add(appendedSoNumber.toString());
				appendedSoNumber = new StringBuilder();
			}
			if (count % totalLength - 1 != 0) {
				appendedSoNumber.append("@");
				appendedSoNumber.append(p);
			} else {
				appendedSoNumber.append(p);
			}
			if (sizeCount == soList.size()) {
				listOfSobatch.add(appendedSoNumber.toString());
			}
		}
		System.err.println("STEP 10 list of SO in batch of 5 = " + listOfSobatch);
		return listOfSobatch;

	}

	/**
	 * @param jsonObjectcontainingListOfSo
	 * @return
	 */
	private Map<String, String> convertingJsonObjToListOfSo(JSONObject jsonObjectcontainingListOfSo) {

		Map<String, String> listOfSoWithMode = new HashMap<>();

		JSONArray array = jsonObjectcontainingListOfSo.getJSONObject("d").getJSONArray(RESULTS);

		for (Object object : array) {
			JSONObject res = (JSONObject) object;
			JSONArray subArry = res.getJSONObject("NAVTOBLOCKEDORDER").getJSONArray(RESULTS);
			for (Object object2 : subArry) {
				JSONObject res2 = (JSONObject) object2;
				String salesDoc = res2.getString("SalesDoc");
				String mode = res2.getString("Mode");
				if (mode.equalsIgnoreCase("I")) {
					listOfSoWithMode.put(salesDoc, mode);
				}
				if (mode.equalsIgnoreCase("U")) {

					listOfSoWithMode.put(salesDoc, mode);

					/*
					 * commented because we just need to put U and I mode here
					 * logic of removal of SO in U Mode if not present in hana
					 * dB is not required here we have taken care of this in
					 * save method for U mode because SO may come first time in
					 * U mode also without being in Hana DB which has to be
					 * inserted and also workfloe has to be triggered
					 * 
					 * SalesDocHeaderDto salesDocHeaderDto = (SalesDocHeaderDto)
					 * salesDocHeaderService
					 * .getSalesDocHeadersWithoutItems(salesDoc).getData();
					 * 
					 * if (salesDocHeaderDto != null) {
					 * listOfSoWithMode.put(salesDoc, mode); }else{
					 * schedulerLogService.saveInDB(new SchedulerTableDto(
					 * "Sales orders came from First odata service with U mode but not present in Hana DB so removed from U mode List : "
					 * + salesDoc, new Date().toString(),
					 * LocalDateTime.now(ZoneId.of("GMT+05:30")))); }
					 */
				}

			}
		}
		System.err.println("STEP 8 jsonStructure with so containing mode = " + listOfSoWithMode);
		return listOfSoWithMode;
	}

	/**
	 * @return String containing url
	 */
	private String createUrlToGetListOfSalesOrder() {

		System.err.println("STEP 2");
		// String saleOrg = "TH54";
		// managed for now 6+1 hrs
		LocalDateTime endDate1 = LocalDateTime.parse(LocalDateTime.now(ZoneId.of("GMT+05:30")).toString());
		String endTime = "PT" + endDate1.getHour() + "H" + endDate1.getMinute() + "M" + endDate1.getSecond() + "S";

		LocalDateTime startDate = endDate1.minusSeconds((1 * 35 * 60));

		String startTime = "PT" + startDate.getHour() + "H" + startDate.getMinute() + "M" + startDate.getSecond() + "S";

		System.err.println("SCHEDULER URL1 Start Time = " + startDate.toLocalTime() + " SCHEDULER URL1 End Time = "
				+ endDate1.toLocalTime());

		/*
		 * String odata =
		 * "/sap/opu/odata/sap/ZDKSH_CC_BLOCKED_SO_SRV/OrderSet?$filter=Salesorg%20eq%20%27"
		 * + saleOrg + "%27%20and%20Startdate%20eq%20datetime%27" + startDate +
		 * "%27%20and%20Enddate%20eq%20datetime%27" + endDate1 +
		 * "%27%20and%20Starttime%20eq%20time%27" + startTime +
		 * "%27%20and%20Endtime%20eq%20time%27" + endTime +
		 * "%27&$expand=NAVTOBLOCKEDORDER&$format=json";
		 * 
		 */
		// Awadhesh Kumar This is combination of DKSH and what got from Sai for
		// COM.
		String odata = "/sap/opu/odata/sap/ZCOM_CC_BLOCKED_SO_SRV/OrderSet?$"
				+ "filter=(Sotype%20eq%20%27TG%27%20or%20Sotype%20eq%20%27COM%27%20or%20Sotype%20eq%20%27OR%27%20)%20"
				+ "and%20Startdate%20eq%20datetime%27" + startDate + "%27%20and%20Enddate%20eq%20datetime%27" + endDate1
				+ "%27%20and%20Starttime%20eq%20time%27" + startTime + "%27%20and%20Endtime%20eq%20time%27" + endTime
				+ "%27&$expand=NAVTOBLOCKEDORDER&$format=json";
		// This was uncommented because of above one this got commented.

		/*
		 * String odata =
		 * "/sap/opu/odata/sap/ZDKSH_CC_BLOCKED_SO_SRV/OrderSet?$" +
		 * "filter=(Sotype%20eq%20%27TG%27%20or%20Sotype%20eq%20%27COM%27%20or%20Sotype%20eq%20%27OR%27%20)%20"
		 * + "and%20Startdate%20eq%20datetime%27" + startDate +
		 * "%27%20and%20Enddate%20eq%20datetime%27" + endDate1 +
		 * "%27%20and%20Starttime%20eq%20time%27" + startTime +
		 * "%27%20and%20Endtime%20eq%20time%27" + endTime +
		 * "%27&$expand=NAVTOBLOCKEDORDER&$format=json";
		 */
		// Awadhesh Kumar this got from Sai for COM.
		/*
		 * String odata= "/sap/opu/odata/sap/ZCOM_CC_BLOCKED_SO_SRV/OrderSet?$"
		 * + "filter=(Sotype eq 'TG' or Sotype eq 'COM' or Sotype eq 'OR' ) " +
		 * "and Startdate eq datetime'"+startDate+"' and " +
		 * "Enddate eq datetime'"+endDate1+"' and Starttime eq " +
		 * "time'"+startTime+"' and Endtime eq " +
		 * "time'"+endTime+"'&$expand=NAVTOBLOCKEDORDER&$format=json";
		 */

		System.err.println("ODATA generated URL = " + odata);

		ResponseEntity res1 = schedulerLogService.saveInDB(new SchedulerTableDto(
				"First URL created for odata as malaysian time  : with startDate = " + startDate + " , endDate = "
						+ endDate1 + " , startTime = " + startTime + " , endTime = " + endTime + " = Complete URL =   "

						+ odata.toString(),
				new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		System.err.println("[odata consuming service] res1: " + res1.toString());
		return odata;

	}

	/**
	 * @param listOfJsons
	 * @return
	 */
	public List<SalesDocHeaderDto> importJsonfromOdataOutput(List<JSONObject> listOfJsons,
			Map<String, String> soListWithMode) {
		System.err.println("STEP 14 importing required json objects from odata result");
		List<SalesDocHeaderDto> salesDocHeaderDtoList = new ArrayList<>();
		List<SalesDocItemDto> salesDocItemDtoList = new ArrayList<>();
		List<ScheduleLineDto> scheduleLineDtoList = new ArrayList<>();

		for (JSONObject obj : listOfJsons) {

			JSONArray headerdetailsArray = obj.getJSONObject("d").getJSONObject("NAVTOHEADER").getJSONArray(RESULTS);
			for (Object headerDetailOfEachSalesOrder : headerdetailsArray) {

				JSONObject res = (JSONObject) headerDetailOfEachSalesOrder;

				SalesDocHeaderDto salesDocHeaderDto = new SalesDocHeaderDto();

				salesDocHeaderDto.setSalesOrderNum(res.getString(DOC_NUMBER));
				// if (!res.isNull("DocDate")) {
				// salesDocHeaderDto.setSalesOrderDate(
				// new
				// BigInteger(ServicesUtil.dateConversionFromECC(res.getString("DocDate")).toString()));
				// }
				salesDocHeaderDto.setCreatedBy(res.getString("CreatedBy"));
				salesDocHeaderDto.setOrderCategory(res.getString("SdDocCat"));
				salesDocHeaderDto.setOrderType(res.getString("DocType"));
				salesDocHeaderDto.setSalesOrg(res.getString("SalesOrg"));
				salesDocHeaderDto.setDistributionChannel(res.getString("DistrChan"));
				salesDocHeaderDto.setDivision(res.getString("Division"));
				salesDocHeaderDto.setOrderReason(res.getString("OrdReason"));
				salesDocHeaderDto.setSoldToParty(res.getString("SoldTo"));
				salesDocHeaderDto.setTotalNetAmount(res.getDouble("NetValHd"));
				salesDocHeaderDto.setDocCurrency(res.getString("Currency"));
				salesDocHeaderDto.setDeliveryBlockCode(res.getString("DlvBlock"));
				salesDocHeaderDto.setHeaderBillBlockCode(res.getString("BillBlock"));
				salesDocHeaderDto.setApprovalStatus("1");
				salesDocHeaderDto.setCondGroup5(res.getString("condGroup5"));
				salesDocHeaderDto.setOrderReasonText(res.getString("orderReasonText"));
				salesDocHeaderDto.setOrderRemark(res.getString("returnRemarks"));
				salesDocHeaderDto.setDeliveryBlockText(res.getString("DlvBlockText"));
				salesDocHeaderDto.setDocTypeText(res.getString("DocTypeText"));
				salesDocHeaderDto.setOrdererNA(res.getString("PurchNoC"));
				
				salesDocHeaderDto.setCustomerPo(res.getString("ordererNA"));
				if (salesDocHeaderDto.getCustomerPo() == null || salesDocHeaderDto.getCustomerPo().isEmpty()) {
					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempId = sequenceNumberGen.getNextSeqNumber("CR-", 6, session);
					// if its of Blocked sales order type
					String decisionSetId = tempId;
					session.close();
					salesDocHeaderDto.setCustomerPo(decisionSetId);
					System.err.println("495 CustomerPo" + salesDocHeaderDto.getCustomerPo());
				}

				// salesDocHeaderDto.setCustomerPo(res.getString("PurchNoC"));
				salesDocHeaderDto.setCondGroup5Text(res.getString("condGroup5Desc"));
				salesDocHeaderDto.setAttachmentUrl(res.getString("attachmentUrl"));
				salesDocHeaderDto.setRequestedBy(res.getString("requestedBy"));

				salesDocHeaderDto.setSalesOrgText(res.getString("SalesOrgName"));
				salesDocHeaderDto.setDistrChanText(res.getString("DistrChanName"));
				salesDocHeaderDto.setDivisionText(res.getString("DivisionName"));
				if (res.getString("DocDateTime") != null) {
					String dateInString = res.getString("DocDateTime").substring(6,
							res.getString("DocDateTime").length() - 2);
					System.err.println("[odataConsuming Service][ImportJsonFromOdata] dateInString: "+dateInString);
					salesDocHeaderDto.setSalesOrderDate(new BigInteger(dateInString));
				}

				// pull the credit block code from NAVTOHSTATUS and setting
				JSONArray headerdetailsStatus = obj.getJSONObject("d").getJSONObject("NAVTOHSTATUS")
						.getJSONArray(RESULTS);
				for (Object headerStatus : headerdetailsStatus) {

					JSONObject resStatusForHeader = (JSONObject) headerStatus;
					if (res.getString(DOC_NUMBER).equalsIgnoreCase(resStatusForHeader.getString("SdDoc"))
							&& !HelperClass.checkString(resStatusForHeader.getString("Totstatcch"))) {
						salesDocHeaderDto.setCreditBlock(resStatusForHeader.getString("Totstatcch"));
					}

				}

				// pull the Customer from NAVTOPARTNER and put to set it to
				JSONArray navPartnerArray = obj.getJSONObject("d").getJSONObject("NAVTOPARTNER").getJSONArray(RESULTS);
				for (Object eachnavPartnerArray : navPartnerArray) {
					JSONObject result = (JSONObject) eachnavPartnerArray;
					if (res.getString(DOC_NUMBER).equalsIgnoreCase(result.getString("SdDoc"))) {
						if (result.getString("PartnRole").equals("SH") || result.getString("PartnRole").equals("WE")) {
							salesDocHeaderDto.setShipToParty(result.getString("Customer"));
							salesDocHeaderDto.setShipToPartyText(result.getString("CustomerName"));
						}
						if (result.getString("PartnRole").equals("SP") || result.getString("PartnRole").equals("AG")) {
							salesDocHeaderDto.setSoldToParty(result.getString("Customer"));
							salesDocHeaderDto.setSoldToPartyText(result.getString("CustomerName"));
						}

						if (result.getString("PartnRole").equals("RE")) {
							salesDocHeaderDto.setBillToParty(result.getString("Customer"));
							salesDocHeaderDto.setBillToPartyText(result.getString("CustomerName"));
						}

						if (result.getString("PartnRole").equals("RG")) {
							salesDocHeaderDto.setPayer(result.getString("Customer"));
							salesDocHeaderDto.setPayerText(result.getString("CustomerName"));
						}

						// ship to party text
						// result.getString("PartnRole").equals("SH")
						// salesDocHeaderDto.setShipToPartyText(result.getString("CustomerName")
						// sold to party text
						// result.getString("PartnRole").equals("SP")
						// salesDocHeaderDto.setSoldToPartyText(result.getString("CustomerName")
					}
				}

				salesDocHeaderDtoList.add(salesDocHeaderDto);
			}

			JSONArray headerItemsArray = obj.getJSONObject("d").getJSONObject("NAVTOITEM").getJSONArray(RESULTS);
			for (Object eachHeaderItemsArray : headerItemsArray) {
				JSONObject res = (JSONObject) eachHeaderItemsArray;
				SalesDocItemDto docItemDto = new SalesDocItemDto();
				docItemDto.setSalesHeaderNo(res.getString(DOC_NUMBER));
				docItemDto.setSalesItemOrderNo(res.getString("ItmNumber"));
				docItemDto.setHigherLevelItem(res.getString("HgLvItem"));
				docItemDto.setSapMaterialNum(res.getString("Material"));
				docItemDto.setBatchNum(res.getString("Batch"));
				docItemDto.setMaterialGroup(res.getString("MatlGroup"));
				docItemDto.setMaterialGroup4(res.getString("PrcGroup4"));
				docItemDto.setSalesUnit(res.getString("SalesUnit"));
				docItemDto.setSplPrice(res.getString("SplPrice"));

				docItemDto.setReasonForRejection(res.getString("ReaForRe"));
				// this is pending as the thai text is not accepted in dB
				docItemDto.setShortText(res.getString("ShortText"));
				docItemDto.setOldMatCode(res.getString("OldMatCode"));
				docItemDto.setItemCategory(res.getString("ItemCateg"));
				docItemDto.setItemType(res.getString("ItemType"));
				docItemDto.setOrderedQtySales(res.getDouble("ReqQty"));
				docItemDto.setBaseUnit(res.getString("BaseUom"));
				docItemDto.setConvNum(res.getDouble("SalesQty1"));
				docItemDto.setConvDen(res.getDouble("SalesQty2"));
				docItemDto.setItemBillingBlock(res.getString("BillBlock"));
				docItemDto.setRefDocNum(res.getString("RefDoc"));
				docItemDto.setRefDocItem(res.getString("PosnrVor"));
				docItemDto.setPlant(res.getString("Plant"));
				docItemDto.setStorageLoc(res.getString("StgeLoc"));
				docItemDto.setNetPrice(res.getString("NetPrice"));
				docItemDto.setDocCurrency(res.getString("Currency"));
				docItemDto.setNetWorth(res.getString("NetValue"));
				docItemDto.setItemCategText(res.getString("ItemCategText"));
				docItemDto.setSalesTeam(res.getString("salesTeam"));
				docItemDto.setSalesArea(res.getString("salesArea"));
				//
				// if (!res.isNull("matExpiryDate")) {
				// docItemDto.setMatExpiryDate(new BigInteger(
				// ServicesUtil.dateConversionFromECC(res.getString("matExpiryDate")).toString()));
				// }
				docItemDto.setSerialNumber(res.getString("serialNumber"));

				salesDocItemDtoList.add(docItemDto);

			}

			JSONArray itemsScheduleLinesArray = obj.getJSONObject("d").getJSONObject("NAVTOSCHEDULE")
					.getJSONArray(RESULTS);

			for (Object scheduleLineofEachItem : itemsScheduleLinesArray) {
				JSONObject res = (JSONObject) scheduleLineofEachItem;
				ScheduleLineDto scheduleLineDto = new ScheduleLineDto();
				String salesOrderNum = res.getString(DOC_NUMBER);
				scheduleLineDto.setSalesHeaderNo(salesOrderNum);
				scheduleLineDto.setSalesItemOrderNo(res.getString("ItmNumber"));
				scheduleLineDto.setScheduleLineNum(res.getString("SchedLine"));
				scheduleLineDto.setReqQtyBase(res.getDouble("ReqQty1"));
				scheduleLineDto.setConfQtySales(res.getDouble("ConfirQty"));
				scheduleLineDto.setSalesUnit(res.getString("SalesUnit"));
				scheduleLineDto.setReqQtySales(res.getDouble("ReqQty"));
				scheduleLineDto.setBaseUnit(res.getString("BaseUom"));
				scheduleLineDto.setSchlineDeliveryBlock(res.getString("ReqDlvBl"));
				scheduleLineDto.setRelfordelText(res.getString("RelfordelText"));

				scheduleLineDtoList.add(scheduleLineDto);
			}

		}

		return jsonStructureForHanadB(salesDocHeaderDtoList, salesDocItemDtoList, scheduleLineDtoList, soListWithMode);

	}

	/**
	 * @param salesDocHeaderDtoList
	 * @param salesDocItemDtoList
	 * @param scheduleLineDtoList
	 * @return
	 */
	private List<SalesDocHeaderDto> jsonStructureForHanadB(List<SalesDocHeaderDto> salesDocHeaderDtoList,
			List<SalesDocItemDto> salesDocItemDtoList, List<ScheduleLineDto> scheduleLineDtoList,
			Map<String, String> soListWithMode) {

		List<SalesDocHeaderDto> listOfSalesOrderDtos = new ArrayList<>();

		if (!salesDocHeaderDtoList.isEmpty()) {

			for (SalesDocHeaderDto salesDocHeaderDto : salesDocHeaderDtoList) {

				int countBlocksFound = 0;
				if (soListWithMode.containsKey(salesDocHeaderDto.getSalesOrderNum())) {

					List<SalesDocItemDto> listOfChild = new ArrayList<>();
					for (SalesDocItemDto salesDocItemDto : salesDocItemDtoList) {

						List<ScheduleLineDto> listOfSubChild = new ArrayList<>();
						for (ScheduleLineDto scheduleLineDto : scheduleLineDtoList) {

							if (scheduleLineDto.getSalesHeaderNo().equals(salesDocItemDto.getSalesHeaderNo())
									&& scheduleLineDto.getSalesItemOrderNo()
											.equals(salesDocItemDto.getSalesItemOrderNo())
									&& scheduleLineDto.getSalesHeaderNo()
											.equalsIgnoreCase(salesDocHeaderDto.getSalesOrderNum())) {

								listOfSubChild.add(scheduleLineDto);
								salesDocItemDto.setScheduleLineList(listOfSubChild);

								if (!HelperClass.checkString(scheduleLineDto.getSchlineDeliveryBlock())) {
									countBlocksFound++;
								}

							}

						}

						if (salesDocHeaderDto.getSalesOrderNum().equals(salesDocItemDto.getSalesHeaderNo())) {

							listOfChild.add(salesDocItemDto);
							salesDocHeaderDto.setSalesDocItemList(listOfChild);
						}

					}

					if (!HelperClass.checkString(salesDocHeaderDto.getDeliveryBlockCode())) {
						countBlocksFound++;
					}

				}

				if (countBlocksFound != 0) {
					System.err.println(countBlocksFound);
					salesDocHeaderDto.setFlagFromScheduler(soListWithMode.get(salesDocHeaderDto.getSalesOrderNum()));

					listOfSalesOrderDtos.add(salesDocHeaderDto);

					// creating requestId for each sales object
					// Below Two lines commented by Awadhesh Kumar
					// String requestId =
					// attachingRequestObject(salesDocHeaderDto);
					// salesDocHeaderDto.setReqMasterId(requestId);

				} else {
					System.err.println(
							"Sales order is removed, Due to no blocks found in sales order and sale order number is : "
									+ salesDocHeaderDto.getSalesOrderNum());
					schedulerLogService.saveInDB(new SchedulerTableDto(
							"Sales order is removed, Due to no blocks found in sales order and sale order number is : "
									+ salesDocHeaderDto.getSalesOrderNum(),
							new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
					soListWithMode.remove(salesDocHeaderDto.getSalesOrderNum());

				}
			}

		}

		System.err.println("STEP 15 list of dtos for db Insert = " + listOfSalesOrderDtos);
		/*
		 * schedulerLogService.saveInDB(new SchedulerTableDto(
		 * "STEP 15 list of dtos for db Insert = " +
		 * listOfSalesOrderDtos.size(), new
		 * Date().toString(),LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		 */
		return listOfSalesOrderDtos;

	}

	/**
	 * @param salesDocHeaderDto
	 * @return
	 */
	private String attachingRequestObject(SalesDocHeaderDto salesDocHeaderDto) {
		String requestId;

		System.err.println("inside attachingRequestObject.....");
		// checking whether SO is associated with any requestId
		String requestIdWithSoHeader = (String) salesDocHeaderService
				.getRequestIdBySoNum(salesDocHeaderDto.getSalesOrderNum()).getData();

		// created request id if Sales OrderNumber is not associated with any
		// requestId
		if (requestIdWithSoHeader != null && !requestIdWithSoHeader.isEmpty()) {
			requestId = requestIdWithSoHeader;
		} else {
			// creating request
			RequestMasterDto reqMasterDto = new RequestMasterDto();
			reqMasterDto.setRequestStatusCode(StatusConstants.REQUEST_NEW.toString());
			requestId = requestMasterService.saveOrUpdateRequestMaster(reqMasterDto).getMessage();
		}
		return requestId;
	}

	// inserting data to hana dB
	/**
	 * @param listOfSalesOrderDtos
	 */
	@SuppressWarnings("unchecked")
	public void saveDataToHanaDb(List<SalesDocHeaderDto> listOfSalesOrderDtos) {

		schedulerLogService.saveInDB(
				new SchedulerTableDto("No of sales order came for hana db save = " + listOfSalesOrderDtos.size(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

		for (SalesDocHeaderDto salesDocHeaderDtoFromEcc : listOfSalesOrderDtos) {
			try {

				System.err.println("Sales order data came from ECC : " + salesDocHeaderDtoFromEcc);

				if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("I")
				/*
				 * && StatusConstants.REQUEST_NEW.toString().equals(
				 * salesDocHeaderDto.getReqMasterId())
				 */) {
					// Below 4 lines commented by Awadhesh Kumar
					salesDocHeaderDtoFromEcc.setReqMasterId("1");
					List<RequestMasterDto> reqList = requestMasterService
							.getRequestMasterById(salesDocHeaderDtoFromEcc.getReqMasterId());// here
					System.err.println("req list size" + reqList.size());

					RequestMasterDto req1 = new RequestMasterDto();
					// if (!reqList.isEmpty()) {
					// req1 = reqList.get(0);
					// }
					req1.setRequestStatusCode("33");
					if (StatusConstants.REQUEST_NEW.toString().equals(req1.getRequestStatusCode())) {

						// i need to check whether req have status for which i
						// need to get info
						System.err.println("in if condition");
						SalesDocHeaderDto salesOrderInHana = null;
						try {
							salesOrderInHana = (SalesDocHeaderDto) salesDocHeaderService
									.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum())
									.getData();
						} catch (Exception e) {
							System.err.println(
									"[odataConsuming service][saveDataToHanaDb] Exception in getSalesDocHeadersWithoutItems: "
											+ e.getMessage());
							e.printStackTrace();
						}

						System.err.println("outside if");
						System.err.println("salesOrderInHana " + salesOrderInHana);
						System.err.println("check1");
						if (salesOrderInHana == null) {
							ResponseEntity saveSalesDocHeader = null;
							try {
								// these multiline comments have to be removed
								// once
								// temp test is ok.
								System.err.println("salesOrderInHana is null ");
								saveSalesDocHeader = salesDocHeaderService.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
								System.err.println(
										"[odata consuming service][saveDataToHanaDb] ResponseEntity After Saving  = "
												+ saveSalesDocHeader);
							} catch (Exception e) {
								System.err.println(
										"exception occurred during saving to hana db for sales order with id = "
												+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								e.printStackTrace();

								schedulerLogService.saveInDB(new SchedulerTableDto(
										"exception occurred during saving to hana db for sales order with id = "
												+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
										new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
							}

							// Updating base version for items
							// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

							// below line was commented by Awadhesh kumar
							// because of
							// issue in saving data to doc header.
							if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
								// these multiline comments have to be removed
								// once
								// temp test is ok.
								schedulerLogService.saveInDB(new SchedulerTableDto(
										"STEP 16 saved data in Sales order table and sales order number is := "
												+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
												+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
										new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

								SalesDocHeaderDto soDto = null;
								try {
									soDto = (SalesDocHeaderDto) salesDocHeaderService
											.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum())
											.getData();
								} catch (Exception e) {
									System.err.println(
											"[odataConsuming service][saveDataToHanaDb] Exception getSalesDocHeadersWithoutItems: "
													+ e.getMessage());
									e.printStackTrace();
								}

								// Below 4 lines commented by Awadhesh Kumar
								// List<RequestMasterDto> reqList2 =
								// requestMasterDao
								// .getRequestMasterById(soDto.getReqMasterId());
								System.err.println("soDto " + soDto);
								RequestMasterDto req = new RequestMasterDto();
								// if (!reqList2.isEmpty()) {
								// req = reqList2.get(0);
								// }
								// checking if the workflow is triggered for the
								// req id
								req.setRequestStatusCode("33");
								if ((StatusConstants.REQUEST_NEW.toString().equals(req.getRequestStatusCode()))) {

									// call method
									System.err.println("check2 at blockTypeFilterDetrmination");
									ResponseEntity btdSalesOrderResponse = null;
									try {
										btdSalesOrderResponse = btdService.blockTypeFilterBasedOnSoId(
												salesDocHeaderDtoFromEcc.getSalesOrderNum());
									} catch (Exception e) {
										System.err.println(
												"[odataConsuming service][saveDataToHanaDb] Exception blockTypeFilterBasedOnSoId: "
														+ e.getMessage());
										e.printStackTrace();
									}
									System.err.println(
											"[odataConsuming service][saveDataToHanaDb] btdSalesOrderResponse: "
													+ btdSalesOrderResponse);
									Map<DkshBlockConstant, Object> dataFromBtd = null;
									if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
										dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
										System.err.println(
												"[odataConsuming service][saveDataToHanaDb] dataFromBtd" + dataFromBtd);
									}

									try {
										triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
									} catch (Exception e) {
										System.err.println(
												"[odataConsuming service][saveDataToHanaDb] Exception triggeringWorkFlow: "
														+ e.getMessage());
										e.printStackTrace();
									}
									req.setRequestStatusCode(StatusConstants.REQUEST_IN_PROGRESS.toString());
									// updating the req status to in progress as
									// worflow is triggered
									// Below line commented by Awadhesh Kumar
									// requestMasterService.updateRequestMaster(req);
									System.err.println("STEP 20 workflow triggered successfully for ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								} else {
									System.err.println("STEP 20 workflow already exist for  ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								}

							}
							/*
							 * triggeringWorkFlow(salesDocHeaderDto);
							 * System.err.println(
							 * "STEP 20 workflow triggered successfully for =" +
							 * salesDocHeaderDto.getSalesOrderNum());
							 */
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Sales Order In Hana is not null and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}

				} else if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("R")) {
					List<RequestMasterDto> reqList = requestMasterService
							.getRequestMasterById(salesDocHeaderDtoFromEcc.getReqMasterId());

					RequestMasterDto req1 = null;
					if (!reqList.isEmpty()) {
						req1 = reqList.get(0);
					}

					if (StatusConstants.REQUEST_NEW.toString().equals(req1.getRequestStatusCode())) {

						// i need to check whether req have status for which i
						// need to get info

						ResponseEntity saveSalesDocHeader = salesDocHeaderService
								.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
						System.err.println(" ResponseEntity After Saving  = " + saveSalesDocHeader);

						// Updating base version for items
						// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

						if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"STEP 16 saved data in Sales order table and sales order number is := "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
											+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

							SalesDocHeaderDto soDto = (SalesDocHeaderDto) salesDocHeaderService
									.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum())
									.getData();
							if (soDto != null && soDto.getReqMasterId() != null) {
								List<RequestMasterDto> reqList2 = requestMasterService
										.getRequestMasterById(soDto.getReqMasterId());
								RequestMasterDto req = null;
								if (!reqList2.isEmpty()) {
									req = reqList2.get(0);
								}
								// checking if the workflow is triggered for the
								// req id
								if (req != null && (StatusConstants.REQUEST_NEW.toString()
										.equals(req.getRequestStatusCode()))) {

									// call method
									ResponseEntity btdSalesOrderResponse = btdService
											.blockTypeFilterBasedOnSoId(salesDocHeaderDtoFromEcc.getSalesOrderNum());
									Map<DkshBlockConstant, Object> dataFromBtd = null;
									if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
										dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
									}

									triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
									req.setRequestStatusCode(StatusConstants.REQUEST_IN_PROGRESS.toString());
									// updating the req status to in progress as
									// worflow is triggered
									requestMasterService.updateRequestMaster(req);
									System.err.println("STEP 20 workflow triggered successfully for ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								} else {
									System.err.println("STEP 20 workflow already exist for  ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								}

							}
							/*
							 * triggeringWorkFlow(salesDocHeaderDto);
							 * System.err.println(
							 * "STEP 20 workflow triggered successfully for =" +
							 * salesDocHeaderDto.getSalesOrderNum());
							 */
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Exception occured in saving data in Sales order table and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}
				} else if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("U")) {

					/*
					 * if exist in Hana dB and Active workflow is there then
					 * just update the SO in Hana if does not exist in hana dB
					 * then create in Hana DB and trigger the workflow also just
					 * like I Mode
					 */

					SalesDocHeaderDto salesOrderDtoFromHana = (SalesDocHeaderDto) salesDocHeaderService
							.getSalesDocHeaderById(salesDocHeaderDtoFromEcc.getSalesOrderNum()).getData();
					System.err.println("sales order data came from hana : " + salesOrderDtoFromHana);

					if (salesOrderDtoFromHana != null) {

						Set<String> decisionSetIdList = new HashSet<>();
						salesOrderDtoFromHana.getSalesDocItemList()
								.forEach(itemInHana -> decisionSetIdList.add(itemInHana.getDecisionSetId()));

						System.err.println("Decision Set id List in a sales order : "
								+ salesOrderDtoFromHana.getSalesOrderNum() + " and list : " + decisionSetIdList);

						Map<String, SalesDocItemDto> mapOfItemsFromHana = salesOrderDtoFromHana.getSalesDocItemList()
								.stream().collect(Collectors.toMap(SalesDocItemDto::getSalesItemOrderNo, item -> item,
										(oldValue, newValue) -> newValue, LinkedHashMap::new));

						salesDocHeaderDtoFromEcc.getSalesDocItemList().forEach(itemInECC -> {

							if (!HelperClass.checkString(itemInECC.getReasonForRejection())
									&& mapOfItemsFromHana.containsKey(itemInECC.getSalesItemOrderNo())
									&& HelperClass.checkString(mapOfItemsFromHana.get(itemInECC.getSalesItemOrderNo())
											.getReasonForRejection())) {

								ResponseEntity responseFromHanaForAllTheItemStatusDtos = salesOrderItemStatusService
										.getItemsStatusFromDecisionSetAndItemNumForAllLevels(mapOfItemsFromHana
												.get(itemInECC.getSalesItemOrderNo()).getDecisionSetId(),
												itemInECC.getSalesItemOrderNo());

								System.err.println("responseFromHanaForAllTheItemStatusDtos : "
										+ responseFromHanaForAllTheItemStatusDtos);

								if (responseFromHanaForAllTheItemStatusDtos.getStatus()
										.equals(ResponseStatus.SUCCESS)) {

									List<SalesOrderItemStatusDto> listOfSalesOrderItemStatus = (List<SalesOrderItemStatusDto>) responseFromHanaForAllTheItemStatusDtos
											.getData();
									listOfSalesOrderItemStatus.forEach(itemStatusDto -> {
										itemStatusDto.setItemStatus(StatusConstants.REJECTED_FROM_ECC);
										itemStatusDto.setVisiblity(StatusConstants.REJECTED_FROM_ECC);

										// Save the updated Item Status
										salesOrderItemStatusService.saveOrUpdateSalesOrderItemStatus(itemStatusDto);

									});

								}

							}

						});

						decisionSetIdList.forEach(decisionSetId -> {
							ResponseEntity responseForPendingDecisionSetId = salesOrderItemStatusService
									.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
											decisionSetId);

							System.err.println("responseForPendingDecisionSetId : " + responseForPendingDecisionSetId);

							if (responseForPendingDecisionSetId.getStatusCode().value() == HttpStatus.NO_CONTENT
									.value()) {

								System.err.println(
										"Trigger Ime is successfully triggered for decision set id : " + decisionSetId);
								triggerImeService.triggerIme(decisionSetId);
							}

						});

						salesDocHeaderService.updateSalesDocHeaderForSchedular(salesDocHeaderDtoFromEcc);
						schedulerLogService.saveInDB(new SchedulerTableDto(
								"updated SalesOrder data in hana dB with sales order number with U mode is =  "
										+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
								new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
					} else {

						ResponseEntity saveSalesDocHeader = salesDocHeaderService
								.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
						System.err.println(" ResponseEntity After Saving  = " + saveSalesDocHeader);

						if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"STEP 16 saved data in Sales order table and sales order number is := "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
											+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

							// call method
							ResponseEntity btdSalesOrderResponse = btdService
									.blockTypeFilterBasedOnSoId(salesDocHeaderDtoFromEcc.getSalesOrderNum());
							Map<DkshBlockConstant, Object> dataFromBtd = null;
							if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
								dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
							}

							triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
							System.err.println("STEP 20 workflow triggered successfully for ="
									+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Exception occured in saving data in Sales order table and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}

					// Updating base version for items
					// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

				}

			} catch (Exception e) {
				System.err.println("exception occurred during saving to hana db for sales order with id = "
						+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
				e.printStackTrace();

				schedulerLogService.saveInDB(new SchedulerTableDto(
						"exception occurred during saving to hana db for sales order with id = "
								+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			}

		}

	}

	/*
	 * private void saveSOAndTriggerWorkFlow(SalesDocHeaderDto
	 * salesDocHeaderDto) { ResponseEntity saveSalesDocHeader =
	 * salesDocHeaderService.saveSalesDocHeader(salesDocHeaderDto);
	 * System.err.println(" ResponseEntity After Saving  = " +
	 * saveSalesDocHeader);
	 * 
	 * // Updating base version for items //
	 * updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());
	 * 
	 * if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
	 * schedulerLogService.saveInDB(new SchedulerTableDto(
	 * "STEP 16 saved data in Sales order table and sales order number is := " +
	 * salesDocHeaderDto.getSalesOrderNum() + " with mode ="+
	 * salesDocHeaderDto.getFlagFromScheduler(), new Date().toString(),
	 * LocalDateTime.now(ZoneId.of("GMT+05:30"))));
	 * 
	 * triggeringWorkFlow(salesDocHeaderDto); System.err.println(
	 * "STEP 20 workflow triggered successfully for =" +
	 * salesDocHeaderDto.getSalesOrderNum()); } else {
	 * schedulerLogService.saveInDB(new SchedulerTableDto(
	 * "Exception occured in saving data in Sales order table and sales order number is : "
	 * + salesDocHeaderDto.getSalesOrderNum(), new Date().toString(),
	 * LocalDateTime.now(ZoneId.of("GMT+05:30"))));
	 * 
	 * } }
	 */

	@SuppressWarnings("unchecked")
	private List<String> ManualSchedulersaveDataToHanaDb(List<SalesDocHeaderDto> listOfSalesOrderDtos) {
		schedulerLogService.saveInDB(
				new SchedulerTableDto("No of sales order came for hana db save = " + listOfSalesOrderDtos.size(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		List<String> triggeredNewly = new ArrayList<String>();

		for (SalesDocHeaderDto salesDocHeaderDtoFromEcc : listOfSalesOrderDtos) {
			try {

				System.err.println("Sales order data came from ECC : " + salesDocHeaderDtoFromEcc);

				if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("I")
				/*
				 * && StatusConstants.REQUEST_NEW.toString().equals(
				 * salesDocHeaderDto.getReqMasterId())
				 */) {
					List<RequestMasterDto> reqList = requestMasterService
							.getRequestMasterById(salesDocHeaderDtoFromEcc.getReqMasterId());

					RequestMasterDto req1 = null;
					if (!reqList.isEmpty()) {
						req1 = reqList.get(0);
					}

					if (StatusConstants.REQUEST_NEW.toString().equals(req1.getRequestStatusCode())) {

						// i need to check whether req have status for which i
						// need to get info
						SalesDocHeaderDto salesOrderInHana = (SalesDocHeaderDto) salesDocHeaderService
								.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum()).getData();

						if (salesOrderInHana == null) {
							ResponseEntity saveSalesDocHeader = salesDocHeaderService
									.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
							System.err.println(" ResponseEntity After Saving  = " + saveSalesDocHeader);

							// Updating base version for items
							// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

							if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
								schedulerLogService.saveInDB(new SchedulerTableDto(
										"STEP 16 saved data in Sales order table and sales order number is := "
												+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
												+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
										new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

								SalesDocHeaderDto soDto = (SalesDocHeaderDto) salesDocHeaderService
										.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum())
										.getData();

								List<RequestMasterDto> reqList2 = requestMasterService
										.getRequestMasterById(soDto.getReqMasterId());
								RequestMasterDto req = null;
								if (!reqList2.isEmpty()) {
									req = reqList2.get(0);
								}
								// checking if the workflow is triggered for the
								// req id
								if (req != null && (StatusConstants.REQUEST_NEW.toString()
										.equals(req.getRequestStatusCode()))) {

									// call method
									ResponseEntity btdSalesOrderResponse = btdService
											.blockTypeFilterBasedOnSoId(salesDocHeaderDtoFromEcc.getSalesOrderNum());
									Map<DkshBlockConstant, Object> dataFromBtd = null;
									if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
										dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
									}

									triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
									req.setRequestStatusCode(StatusConstants.REQUEST_IN_PROGRESS.toString());
									// updating the req status to in progress as
									// worflow is triggered
									requestMasterService.updateRequestMaster(req);
									triggeredNewly.add(salesDocHeaderDtoFromEcc.getSalesOrderNum());
									System.err.println("STEP 20 workflow triggered successfully for ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								} else {
									System.err.println("STEP 20 workflow already exist for  ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								}

							}
							/*
							 * triggeringWorkFlow(salesDocHeaderDto);
							 * System.err.println(
							 * "STEP 20 workflow triggered successfully for =" +
							 * salesDocHeaderDto.getSalesOrderNum());
							 */
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Exception occured in saving data in Sales order table and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}

				} else if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("R")) {
					List<RequestMasterDto> reqList = requestMasterService
							.getRequestMasterById(salesDocHeaderDtoFromEcc.getReqMasterId());

					RequestMasterDto req1 = null;
					if (!reqList.isEmpty()) {
						req1 = reqList.get(0);
					}

					if (StatusConstants.REQUEST_NEW.toString().equals(req1.getRequestStatusCode())) {

						// i need to check whether req have status for which i
						// need to get info

						ResponseEntity saveSalesDocHeader = salesDocHeaderService
								.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
						System.err.println(" ResponseEntity After Saving  = " + saveSalesDocHeader);

						// Updating base version for items
						// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

						if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"STEP 16 saved data in Sales order table and sales order number is := "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
											+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

							SalesDocHeaderDto soDto = (SalesDocHeaderDto) salesDocHeaderService
									.getSalesDocHeadersWithoutItems(salesDocHeaderDtoFromEcc.getSalesOrderNum())
									.getData();
							if (soDto != null && soDto.getReqMasterId() != null) {
								List<RequestMasterDto> reqList2 = requestMasterService
										.getRequestMasterById(soDto.getReqMasterId());
								RequestMasterDto req = null;
								if (!reqList2.isEmpty()) {
									req = reqList2.get(0);
								}
								// checking if the workflow is triggered for the
								// req id
								if (req != null && (StatusConstants.REQUEST_NEW.toString()
										.equals(req.getRequestStatusCode()))) {

									// call method
									ResponseEntity btdSalesOrderResponse = btdService
											.blockTypeFilterBasedOnSoId(salesDocHeaderDtoFromEcc.getSalesOrderNum());
									Map<DkshBlockConstant, Object> dataFromBtd = null;
									if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
										dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
									}

									triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
									req.setRequestStatusCode(StatusConstants.REQUEST_IN_PROGRESS.toString());
									// updating the req status to in progress as
									// worflow is triggered
									requestMasterService.updateRequestMaster(req);
									System.err.println("STEP 20 workflow triggered successfully for ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								} else {
									System.err.println("STEP 20 workflow already exist for  ="
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
								}

							}
							/*
							 * triggeringWorkFlow(salesDocHeaderDto);
							 * System.err.println(
							 * "STEP 20 workflow triggered successfully for =" +
							 * salesDocHeaderDto.getSalesOrderNum());
							 */
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Exception occured in saving data in Sales order table and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}
				} else if (salesDocHeaderDtoFromEcc.getFlagFromScheduler().equalsIgnoreCase("U")) {

					/*
					 * if exist in Hana dB and Active workflow is there then
					 * just update the SO in Hana if does not exist in hana dB
					 * then create in Hana DB and trigger the workflow also just
					 * like I Mode
					 */

					SalesDocHeaderDto salesOrderDtoFromHana = (SalesDocHeaderDto) salesDocHeaderService
							.getSalesDocHeaderById(salesDocHeaderDtoFromEcc.getSalesOrderNum()).getData();
					System.err.println("sales order data came from hana : " + salesOrderDtoFromHana);

					if (salesOrderDtoFromHana != null) {

						Set<String> decisionSetIdList = new HashSet<>();
						salesOrderDtoFromHana.getSalesDocItemList()
								.forEach(itemInHana -> decisionSetIdList.add(itemInHana.getDecisionSetId()));

						System.err.println("Decision Set id List in a sales order : "
								+ salesOrderDtoFromHana.getSalesOrderNum() + " and list : " + decisionSetIdList);

						Map<String, SalesDocItemDto> mapOfItemsFromHana = salesOrderDtoFromHana.getSalesDocItemList()
								.stream().collect(Collectors.toMap(SalesDocItemDto::getSalesItemOrderNo, item -> item,
										(oldValue, newValue) -> newValue, LinkedHashMap::new));

						salesDocHeaderDtoFromEcc.getSalesDocItemList().forEach(itemInECC -> {

							if (!HelperClass.checkString(itemInECC.getReasonForRejection())
									&& mapOfItemsFromHana.containsKey(itemInECC.getSalesItemOrderNo())
									&& HelperClass.checkString(mapOfItemsFromHana.get(itemInECC.getSalesItemOrderNo())
											.getReasonForRejection())) {

								ResponseEntity responseFromHanaForAllTheItemStatusDtos = salesOrderItemStatusService
										.getItemsStatusFromDecisionSetAndItemNumForAllLevels(mapOfItemsFromHana
												.get(itemInECC.getSalesItemOrderNo()).getDecisionSetId(),
												itemInECC.getSalesItemOrderNo());

								System.err.println("responseFromHanaForAllTheItemStatusDtos : "
										+ responseFromHanaForAllTheItemStatusDtos);

								if (responseFromHanaForAllTheItemStatusDtos.getStatus()
										.equals(ResponseStatus.SUCCESS)) {

									List<SalesOrderItemStatusDto> listOfSalesOrderItemStatus = (List<SalesOrderItemStatusDto>) responseFromHanaForAllTheItemStatusDtos
											.getData();
									listOfSalesOrderItemStatus.forEach(itemStatusDto -> {
										itemStatusDto.setItemStatus(StatusConstants.REJECTED_FROM_ECC);
										itemStatusDto.setVisiblity(StatusConstants.REJECTED_FROM_ECC);

										// Save the updated Item Status
										salesOrderItemStatusService.saveOrUpdateSalesOrderItemStatus(itemStatusDto);

									});

								}

							}

						});

						decisionSetIdList.forEach(decisionSetId -> {
							ResponseEntity responseForPendingDecisionSetId = salesOrderItemStatusService
									.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
											decisionSetId);

							System.err.println("responseForPendingDecisionSetId : " + responseForPendingDecisionSetId);

							if (responseForPendingDecisionSetId.getStatusCode().value() == HttpStatus.NO_CONTENT
									.value()) {

								System.err.println(
										"Trigger Ime is successfully triggered for decision set id : " + decisionSetId);
								triggerImeService.triggerIme(decisionSetId);
							}

						});

						salesDocHeaderService.updateSalesDocHeaderForSchedular(salesDocHeaderDtoFromEcc);
						schedulerLogService.saveInDB(new SchedulerTableDto(
								"updated SalesOrder data in hana dB with sales order number with U mode is =  "
										+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
								new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
					} else {

						ResponseEntity saveSalesDocHeader = salesDocHeaderService
								.saveSalesDocHeader(salesDocHeaderDtoFromEcc);
						System.err.println(" ResponseEntity After Saving  = " + saveSalesDocHeader);

						if (ResponseStatus.SUCCESS == saveSalesDocHeader.getStatus()) {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"STEP 16 saved data in Sales order table and sales order number is := "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum() + " with mode ="
											+ salesDocHeaderDtoFromEcc.getFlagFromScheduler(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

							// call method
							ResponseEntity btdSalesOrderResponse = btdService
									.blockTypeFilterBasedOnSoId(salesDocHeaderDtoFromEcc.getSalesOrderNum());
							Map<DkshBlockConstant, Object> dataFromBtd = null;
							if (btdSalesOrderResponse.getStatus().equals(ResponseStatus.SUCCESS)) {
								dataFromBtd = (Map<DkshBlockConstant, Object>) btdSalesOrderResponse.getData();
							}

							triggeringWorkFlow(salesDocHeaderDtoFromEcc, dataFromBtd);
							System.err.println("STEP 20 workflow triggered successfully for ="
									+ salesDocHeaderDtoFromEcc.getSalesOrderNum());
						} else {
							schedulerLogService.saveInDB(new SchedulerTableDto(
									"Exception occured in saving data in Sales order table and sales order number is : "
											+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

						}

					}

					// Updating base version for items
					// updateBaseVersionForEachItem(salesDocHeaderDto.getSalesDocItemList());

				}

			} catch (Exception e) {
				System.err.println("exception occurred during saving to hana db for sales order with id = "
						+ salesDocHeaderDtoFromEcc.getSalesOrderNum());

				schedulerLogService.saveInDB(new SchedulerTableDto(
						"exception occurred during saving to hana db for sales order with id = "
								+ salesDocHeaderDtoFromEcc.getSalesOrderNum(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			}
		}
		return triggeredNewly;
	}

	private void updateBaseVersionForEachItem(List<SalesDocItemDto> listOfItems) {
		for (SalesDocItemDto item : listOfItems) {
			SalesOrderHistoryDto salesOrderHistoryDto = new SalesOrderHistoryDto();
			salesOrderHistoryDto.setBatchNum(item.getBatchNum());
			salesOrderHistoryDto.setItemBillingBlock(item.getItemBillingBlock());
			salesOrderHistoryDto.setItemDlvBlock(item.getItemDlvBlock());
			salesOrderHistoryDto.setNetPrice(Double.parseDouble(item.getNetPrice()));
			salesOrderHistoryDto.setNetWorth(Double.parseDouble(item.getNetWorth()));
			salesOrderHistoryDto.setOrderedQtySales(item.getOrderedQtySales());
			salesOrderHistoryDto.setPlant(item.getPlant());
			salesOrderHistoryDto.setReasonOfRejection(item.getReasonForRejection());
			salesOrderHistoryDto.setSalesDocNum(item.getSalesHeaderNo());
			salesOrderHistoryDto.setSalesItemNum(item.getSalesItemOrderNo());
			salesOrderHistoryDto.setSalesUnit(item.getSalesUnit());
			salesOrderHistoryDto.setStorageLoc(item.getStorageLoc());
			salesOrderHistoryDto.setUpdatedBy("ADMIN");
			salesOrderHistoryDto.setUpdatedOn(new Date());

			System.err.println("Item from scheduler : " + item);
			System.err.println("Item dto for history : " + salesOrderHistoryDto);

			ResponseEntity response = salesOrderHistoryService.saveSalesOrderItem(salesOrderHistoryDto);
			System.err.println("Base Sales Order : " + response);
		}
	}

	/**
	 * @param salesDocHeaderDto
	 */
	public void triggeringWorkFlow(SalesDocHeaderDto salesDocHeaderDto, Map<DkshBlockConstant, Object> dataFromBtd) {
		System.err.println("[odata Consuming service][triggeringWorkFlow] starts..");
		System.err.println("[odata Consuming service][triggeringWorkFlow] salesDocHeaderDto: " + salesDocHeaderDto
				+ " dataFromBtd: " + dataFromBtd);
		ContextDto context = new ContextDto("blocktypedeterminationworkflow", salesDocHeaderDto, dataFromBtd);
		System.err.println("Context in wf for sales order " + salesDocHeaderDto.getSalesOrderNum() + " : " + context);
		WorkFlowTriggerFromJava workFlowTriggerFromJava = new WorkFlowTriggerFromJava();
		String resposefromTriggerWorkflow = workFlowTriggerFromJava.triggerWorkFlowAfterSavingToHana(context);
		schedulerLogService.saveInDB(
				new SchedulerTableDto("workflow triggered successfully for" + salesDocHeaderDto.getSalesOrderNum(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
	}

	public JSONObject consumingOdataService(String url, String entity, String method,
			Map<String, Object> destinationInfo) throws IOException, URISyntaxException {

		// TenantContext tenantctx = new HelperClass().getTenantInformation();

		System.err.println("com.incture.utils.HelperClass  + Inside consumingOdataService==================");
		// String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");

		String proxyHost = "connectivityproxy.internal.cf.eu10.hana.ondemand.com";
		System.err.println("proxyHost-- " + proxyHost);
		// int proxyPort =
		// Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
		int proxyPort = 20003;
		System.err.println("proxyPort-- " + proxyPort);

		// JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));

		// System.err.println("116 - jsonObj =" + jsonObj);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(
				(String) destinationInfo.get("User"), (String) destinationInfo.get("Password")));

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();

		clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort))
				.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
				.setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

		CloseableHttpClient httpClient = clientBuilder.build();
		HttpRequestBase httpRequestBase = null;
		CloseableHttpResponse httpResponse = null;
		StringEntity input = null;
		String json = null;
		JSONObject obj = null;
		String jwToken = DestinationReaderUtil.getConectivityProxy();
		if (url != null) {
			if (method.equalsIgnoreCase("GET")) {
				httpRequestBase = new HttpGet(url);
			} else if (method.equalsIgnoreCase("POST")) {
				httpRequestBase = new HttpPost(url);
				try {
					input = new StringEntity(entity);
					input.setContentType("application/json");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				((HttpPost) httpRequestBase).setEntity(input);
			}
			if (destinationInfo.get("sap-client") != null) {
				httpRequestBase.addHeader("sap-client", (String) destinationInfo.get("sap-client"));
			}
			httpRequestBase.addHeader("accept", "application/json");

			if (destinationInfo.get("User") != null && destinationInfo.get("Password") != null) {
				String encoded = HelperClass.encodeUsernameAndPassword((String) destinationInfo.get("User"),
						(String) destinationInfo.get("Password"));
				httpRequestBase.addHeader("Authorization", encoded);
				httpRequestBase.setHeader("Proxy-Authorization", "Bearer " + jwToken);
				httpRequestBase.addHeader("SAP-Connectivity-SCC-Location_ID", "incture");

			}
			// if (tenantctx != null) {
			// httpRequestBase.addHeader("SAP-Connectivity-ConsumerAccount",
			// tenantctx.getTenant().getAccount().getId());
			// }
			try {

				System.err.println("this is requestBase ============" + Arrays.asList(httpRequestBase));
				httpResponse = httpClient.execute(new HttpHost(proxyHost, proxyPort), httpRequestBase);
				System.err.println(
						"com.incture.utils.HelperClass ============" + Arrays.asList(httpResponse.getAllHeaders()));
				System.err.println("STEP 4 com.incture.utils.HelperClass ============StatusCode from odata hit="
						+ httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
					json = EntityUtils.toString(httpResponse.getEntity());
				} else {
					String responseFromECC = HelperClass.getDataFromStream(httpResponse.getEntity().getContent());
					schedulerLogService.saveInDB(
							new SchedulerTableDto("Response from odata service, Failed due to : " + responseFromECC,
									new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
					return XML.toJSONObject(responseFromECC);
				}

				System.err.println("STEP 5 Result from odata hit ============" + json);

			} catch (IOException e) {
				System.err.print("IOException : " + e);
				schedulerLogService.saveInDB(new SchedulerTableDto(
						"Response from odata service, Failed due to please Check VPN Connection ......."
								+ e.getMessage() + " on " + e.getStackTrace()[4],
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

				throw new IOException(
						"Please Check VPN Connection ......." + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

			try {

				obj = new JSONObject(json);
			} catch (JSONException e) {
				System.err.print("JSONException : check " + e + "JSON Object : " + json);
				schedulerLogService.saveInDB(new SchedulerTableDto(
						"Response from odata service, Failed due to Exception occured during json conversion ......."
								+ e.getMessage() + " on " + e.getStackTrace()[4],
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

				throw new JSONException(
						"Exception occured during json conversion" + e.getMessage() + " on " + e.getStackTrace()[4]);
			}

			try {
				httpClient.close();
			} catch (IOException e) {
				System.err.print("Closing HttpClient Exception : " + e);
				schedulerLogService.saveInDB(new SchedulerTableDto(
						"Response from odata service, Failed due to Closing due to HttpClient Exception......."
								+ e.getMessage() + " on " + e.getStackTrace()[4],
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

				throw new IOException("Closing  due to HttpClient Exception : " + e);
			}

		}

		System.err.print("STEP 6 object returned from odata " + obj);
		return obj;

	}

	public ResponseEntity manualScheduler(String startDate, String endDate, String startTime, String endTime) {
		JSONObject jsonObjectcontainingListOfSo = null;
		List<String> salesOrderNumReTriggered = null;
		try {
			System.err.println("inside main method of ODataConsumingService");

			Map<String, Object> destinationInfo = DestinationReaderUtil
					.getDestination(Constants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);

			// get list of salesOrder from Odata service using ODATA service1
			String url1 = destinationInfo.get("URL")
					+ createUrlToGetListOfSalesOrderManual(startDate, endDate, startTime, endTime);

			System.err.println("STEP 3:First url generated for bringing list of SO = " + url1);

			jsonObjectcontainingListOfSo = consumingOdataService(url1, "", "GET", destinationInfo);

			schedulerLogService.saveInDB(new SchedulerTableDto(
					"Sales orders came from First odata service : " + jsonObjectcontainingListOfSo.toString(),
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			System.err
					.println("STEP 7 inside ODataConsumingService.mainScheduler(); blocked slaes order in last 2 hrs ="
							+ jsonObjectcontainingListOfSo);

			// forming the JSON structure from output of OData
			Map<String, String> soListWithMode = convertingJsonObjToListOfSo(jsonObjectcontainingListOfSo);

			schedulerLogService.saveInDB(new SchedulerTableDto(
					"Number of Sales order with mode are : " + soListWithMode.size() + " and Data is : "
							+ soListWithMode.toString(),
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

			System.err.println("STEP 9 ODataConsumingService.mainScheduler()" + soListWithMode);

			if (!soListWithMode.isEmpty()) {
				// then make a batch of 5 data from list
				List<String> listOfSoWithbatch = batchingTheListOfSo(soListWithMode);

				schedulerLogService.saveInDB(new SchedulerTableDto(
						"Number of batches are : " + listOfSoWithbatch.size() + " and Data in each batch : "
								+ listOfSoWithbatch.toString(),
						new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));

				System.err.println("STEP 11 listOfSoWithbatch" + listOfSoWithbatch);
				if (!listOfSoWithbatch.isEmpty()) {
					salesOrderNumReTriggered = manualSchedulerInsertionOfData(listOfSoWithbatch, soListWithMode);
				}
				if (salesOrderNumReTriggered != null && !salesOrderNumReTriggered.isEmpty()) {
					return new ResponseEntity(salesOrderNumReTriggered, HttpStatus.OK,
							"Orders Successfully Retriggered.", ResponseStatus.SUCCESS);
				} else {

					return new ResponseEntity(salesOrderNumReTriggered, HttpStatus.OK, "No Orders Found For  Retrigger",
							ResponseStatus.SUCCESS);
				}

			} else {
				System.err.println("scheduler didn't pick any Order");
				return new ResponseEntity("", HttpStatus.OK, "Didnt Find Any Of the Orders In The Given Time",
						ResponseStatus.SUCCESS);

			}
		} catch (Exception e) {
			schedulerLogService.saveInDB(new SchedulerTableDto(
					"Failed due to Exception......." + e.getMessage() + " on " + e.getStackTrace()[4],
					new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
			return new ResponseEntity("", HttpStatus.OK, "Failed due to Exception......." + e.getMessage() + " on "
					+ e.getStackTrace()[4] + " scheduler didn't pick any sales order.", ResponseStatus.SUCCESS);

		}

	}

	private String createUrlToGetListOfSalesOrderManual(String startManualDate, String endManualDate,
			String startManualTime, String endManualTime) {
		System.err.println("STEP 2");
		// String saleOrg = "TH54";
		// managed for now 6+1 hrs
		LocalDateTime endDate1 = LocalDateTime.parse(LocalDateTime.now(ZoneId.of("GMT+08:00")).toString());
		String endTime = "PT" + endDate1.getHour() + "H" + endDate1.getMinute() + "M" + endDate1.getSecond() + "S";

		LocalDateTime startDate = endDate1.minusSeconds((1 * 35 * 60));

		String startTime = "PT" + startDate.getHour() + "H" + startDate.getMinute() + "M" + startDate.getSecond() + "S";

		System.err.println("SCHEDULER URL1 Start Time = " + startDate.toLocalTime() + " SCHEDULER URL1 End Time = "
				+ endDate1.toLocalTime());

		/*
		 * String odata =
		 * "/sap/opu/odata/sap/ZDKSH_CC_BLOCKED_SO_SRV/OrderSet?$filter=Salesorg%20eq%20%27"
		 * + saleOrg + "%27%20and%20Startdate%20eq%20datetime%27" + startDate +
		 * "%27%20and%20Enddate%20eq%20datetime%27" + endDate1 +
		 * "%27%20and%20Starttime%20eq%20time%27" + startTime +
		 * "%27%20and%20Endtime%20eq%20time%27" + endTime +
		 * "%27&$expand=NAVTOBLOCKEDORDER&$format=json";
		 * 
		 */
		String odata = "/sap/opu/odata/sap/ZDKSH_CC_BLOCKED_SO_SRV/OrderSet?$"
				+ "filter=(Sotype%20eq%20%27ZORM%27%20or%20Sotype%20eq%20%27ZREM%27%20or%20Sotype%20eq%20%27ZREB%27%20or%20Sotype%20eq%20%27ZTKR%27%20or%20Sotype%20eq%20%27ZTRF%27%20or%20Sotype%20eq%20%27ZR3M%27%20or%20Sotype%20eq%20%27ZTKB%27%20or%20Sotype%20eq%20%27ZTFC%27%20or%20Sotype%20eq%20%27Z3PM%27%20or%20Sotype%20eq%20%27Z3FM%27%20or%20Sotype%20eq%20%27ZF2M%27%20)%20"
				+ "and%20(Salesorg%20eq%20%27TH54%27%20or%20Salesorg%20eq%20%27TH53%27%20)%20"
				+ "and%20Startdate%20eq%20datetime%27" + startManualDate + "%27%20and%20Enddate%20eq%20datetime%27"
				+ endManualDate + "%27%20and%20Starttime%20eq%20time%27" + startManualTime
				+ "%27%20and%20Endtime%20eq%20time%27" + endManualTime + "%27&$expand=NAVTOBLOCKEDORDER&$format=json";

		System.err.println("ODATA generated URL = " + odata);

		schedulerLogService.saveInDB(new SchedulerTableDto(
				"First URL created for odata as malaysian time  : with startDate = " + startDate + " , endDate = "
						+ endDate1 + " , startTime = " + startTime + " , endTime = " + endTime + " = Complete URL =   "

						+ odata.toString(),
				new Date().toString(), LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		return odata;

	}

	public ResponseEntity identifySalesOrdersToRetriggerWorkflow(WorkflowTriggerInputDto workflowTriggerDto) {
		try {
			if (workflowTriggerDto.getSalesOrderNumbers() != null
					&& !workflowTriggerDto.getSalesOrderNumbers().isEmpty()) {

				StringJoiner batchOfSalesOrderNotAvailInHana = new StringJoiner("@");
				List<String> salesOrderNumListWithRunningWorkflow = new ArrayList<>();

				for (String salesOrderNum : workflowTriggerDto.getSalesOrderNumbers()) {

					SalesDocHeaderDto soHeaderDto = (SalesDocHeaderDto) salesDocHeaderService
							.getSalesDocHeadersWithoutItems(salesOrderNum).getData();

					if (soHeaderDto != null) {

						List<RequestMasterDto> requestMasterDtoList = requestMasterService
								.getRequestMasterById(soHeaderDto.getReqMasterId());

						if (!requestMasterDtoList.isEmpty()) {

							RequestMasterDto requestMasterDto = requestMasterDtoList.get(0);

							// Workflow is active
							if (requestMasterDto.getRequestStatusCode() != null && requestMasterDto
									.getRequestStatusCode().equals(StatusConstants.REQUEST_IN_PROGRESS.toString())) {

								salesOrderNumListWithRunningWorkflow.add(salesOrderNum);

							} else {
								batchOfSalesOrderNotAvailInHana.add(salesOrderNum);
							}
						}
					} else {
						batchOfSalesOrderNotAvailInHana.add(salesOrderNum);
					}
				}

				// When Sales Orders are not available in HANA
				if (batchOfSalesOrderNotAvailInHana.length() != 0) {

					insertionOfData(Stream.of(batchOfSalesOrderNotAvailInHana.toString()).collect(Collectors.toList()),
							workflowTriggerDto.getSalesOrderNumbers().stream()
									.collect(Collectors.toMap(soNum -> soNum, so -> "R")));
					return new ResponseEntity(salesOrderNumListWithRunningWorkflow, HttpStatus.OK,
							"Workflow Triggered Successfully for " + batchOfSalesOrderNotAvailInHana.toString(),
							ResponseStatus.SUCCESS);
				} else {

					return new ResponseEntity(salesOrderNumListWithRunningWorkflow, HttpStatus.OK,
							"Active Workflow Found for " + salesOrderNumListWithRunningWorkflow,
							ResponseStatus.SUCCESS);
				}
			} else if (workflowTriggerDto.getSchedulerEndDate() != null
					&& workflowTriggerDto.getSchedulerStartDate() != null) {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Start and End dates for picking sales orders is under development.", ResponseStatus.FAILED);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Enter either Sales Order Numbers or Start and End dates for picking sales orders.",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}

	}

	public ResponseEntity reTriggerSalesOrders(List<String> salesOrderNumList) {
		try {
			if (salesOrderNumList != null && !salesOrderNumList.isEmpty()) {

				StringJoiner batchOfSalesOrderNotAvailInHana = new StringJoiner("@");

				for (String salesOrderNum : salesOrderNumList) {

					// Call Api to close all workflow
					ResponseEntity resFromDeletinWF = approvalWorkflowTrigger
							.closeAllWorkflowsByBussinessKey(salesOrderNum);
					System.err.println("resFromDeletinWF : " + resFromDeletinWF);

					if (resFromDeletinWF.getStatus().equals(ResponseStatus.SUCCESS)) {

						// Clear Hana data and all worflow
						ResponseEntity resFromDeletingSo = salesDocHeaderService
								.deleteSalesDocHeaderById(salesOrderNum);
						System.err.println("resFromDeletingSo : " + resFromDeletingSo);

						if (resFromDeletingSo.getStatus().equals(ResponseStatus.SUCCESS)) {

							ResponseEntity resFromDeletingReq = requestMasterService
									.deleteRequestMasterByRefDocNum(salesOrderNum);
							System.err.println("resFromDeletingReq : " + resFromDeletingReq);

							if (resFromDeletingReq.getStatus().equals(ResponseStatus.SUCCESS)) {

								// Save data in hana and trigger WF
								batchOfSalesOrderNotAvailInHana.add(salesOrderNum);
							}
						}
					}
				}

				// When Sales Orders are not available in HANA
				if (batchOfSalesOrderNotAvailInHana.length() != 0) {
					insertionOfData(Stream.of(batchOfSalesOrderNotAvailInHana.toString()).collect(Collectors.toList()),
							salesOrderNumList.stream().collect(Collectors.toMap(soNum -> soNum, so -> "R")));
				}

				if (batchOfSalesOrderNotAvailInHana.length() != 0) {
					return new ResponseEntity(batchOfSalesOrderNotAvailInHana.toString(), HttpStatus.OK,
							batchOfSalesOrderNotAvailInHana.toString()
									+ ", these sales orders successfully retriggered.",
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.CONFLICT,
							"Failed to update the data, Due to some error in closing workflow API's or saving Sales Order.",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Enter either Sales Order Numbers or Start and End dates for picking sales orders.",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
