package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.CreditLimitDto;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.PaymentTermsDto;
import com.incture.cherrywork.entities.CustomerDo;
import com.incture.cherrywork.util.HciApiConstants;
import com.incture.cherrywork.util.RestInvoker;


@Service
@Transactional
public class HciGetCustomerDetailsService implements HciGetCustomerDetailsServiceLocal {

	private static final Logger logger = LoggerFactory.getLogger(HciGetCustomerDetailsService.class);

	@Autowired
	private RestInvoker restInvoker;

	/*
	 * List<ShippingAddressDto> shippingAddressList; List<ControllingAreaDto>
	 * controllingAreaList; ControllingAreaDto controllingAreaDto;
	 * ShippingAddressDto shippingAddressDto;
	 */
	CustomerDto customerDto;
	List<CustomerDto> customerDtoList;
	CustomerDto customerDetails;

	/*
	 * Map<String, List<ShippingAddressDto>> shippingAddressMap; Map<String,
	 * List<ControllingAreaDto>> controllingAreaMap;
	 */
	Map<String, CustomerDto> customerMap;

	@Override
	public CustomerDto getCustomerDetailsFromEcc(String customerName, String creditSegment) {

		JSONObject inputObject = new JSONObject();

		JSONObject getCustomerDetails = new JSONObject();

		getCustomerDetails.put("CustomerName", customerName);
		getCustomerDetails.put("CreditSegment", creditSegment);

		inputObject.put("GetCustomerDetails", getCustomerDetails);

		try {

			String data = inputObject.toString();
			String response = restInvoker.postDataToServer(HciApiConstants.GET_CUSTOMER_DETAILS, data);

			if (response != null && !(response.equals(""))) {

				JSONObject responseObject = new JSONObject(response);
				JSONObject zsdCutomerDetails;
				JSONObject eCdetails;
				zsdCutomerDetails = responseObject.getJSONObject("ZSD_CUSTOMER_DETAILS.Response");
				eCdetails = zsdCutomerDetails.getJSONObject("E_CDETAILS");

				customerDto = new CustomerDto();

				customerDto.setCustId(customerName);
				customerDto.setCustName(eCdetails.getString("NAME"));
				customerDto.setCustCity(eCdetails.getString("CITY"));
				customerDto.setCustCountry(eCdetails.getString("COUNTRY"));
				customerDto.setCustPostalCode(eCdetails.getString("PO_CODE"));
				customerDto.setCustCategory(eCdetails.getString("CATEGORY"));
				customerDto.setCustCreditLimit(zsdCutomerDetails.getString("E_CRLIMIT"));
			}

		} catch (Exception e) {

			logger.error("[HciGetCustomerDetailsService][getCustomerDetailsFromEcc]:::" + e.getMessage());
		}

		return customerDto;
	}

	/*
	 * @Override public List<CustomerDto> getCustomerAlldetails(String country)
	 * {
	 * 
	 * customerDtoList = new ArrayList<>();
	 * 
	 * JSONObject inputObject = new JSONObject();
	 * 
	 * JSONObject getShippingDetails = new JSONObject();
	 * 
	 * getShippingDetails.put("Country", country);
	 * 
	 * inputObject.put("GetShippingDetails", getShippingDetails);
	 * 
	 * try {
	 * 
	 * String data = inputObject.toString(); String response =
	 * restInvoker.postDataToServer("/http/getshippingdetails", data);
	 * 
	 * if (response != null && !(response.equals(""))) {
	 * 
	 * JSONObject responseObject = new JSONObject(response);
	 * 
	 * JSONObject zsdGetShippingDetails;
	 * 
	 * JSONObject gtControlArea;
	 * 
	 * JSONArray controlAreaItemArray;
	 * 
	 * JSONObject controlAreaItem;
	 * 
	 * JSONObject gtShipping;
	 * 
	 * JSONArray shippingItemArray;
	 * 
	 * JSONObject shippingItem;
	 * 
	 * zsdGetShippingDetails =
	 * responseObject.getJSONObject("rfc:Z_SD_GET_SHIPPING_DETAILS.Response");
	 * 
	 * gtControlArea = zsdGetShippingDetails.getJSONObject("GT_CONTROL_AREA");
	 * 
	 * controlAreaItemArray = gtControlArea.getJSONArray("item");
	 * 
	 * gtShipping = zsdGetShippingDetails.getJSONObject("GT_SHIPPING");
	 * 
	 * shippingItemArray = gtShipping.getJSONArray("item");
	 * 
	 * shippingAddressMap = new HashMap<>();
	 * 
	 * controllingAreaMap = new HashMap<>();
	 * 
	 * customerMap = new HashMap<>();
	 * 
	 * for (int i = 0; i < controlAreaItemArray.length(); i++) {
	 * 
	 * controlAreaItem = controlAreaItemArray.getJSONObject(i);
	 * 
	 * customerDto =
	 * getCustomerDetailsFromEcc(controlAreaItem.getString("PARTNER"),
	 * controlAreaItem.getString("CREDIT_SGMNT"));
	 * 
	 * customerMap.put(controlAreaItem.getString("PARTNER"), customerDto);
	 * 
	 * if (controllingAreaMap.containsKey(controlAreaItem.getString("PARTNER")))
	 * {
	 * 
	 * controllingAreaDto = new ControllingAreaDto();
	 * 
	 * controllingAreaDto.setControllingAreaId(controlAreaItem.getString(
	 * "CREDIT_SGMNT"));
	 * controllingAreaDto.setControllingArea(controlAreaItem.getString(
	 * "CREDIT_SEG_DESC"));
	 * 
	 * controllingAreaList =
	 * controllingAreaMap.get(controlAreaItem.getString("PARTNER"));
	 * 
	 * controllingAreaList.add(controllingAreaDto);
	 * 
	 * controllingAreaMap.put(controlAreaItem.getString("PARTNER"),
	 * controllingAreaList);
	 * 
	 * } else {
	 * 
	 * controllingAreaDto = new ControllingAreaDto();
	 * 
	 * controllingAreaDto.setControllingAreaId(controlAreaItem.getString(
	 * "CREDIT_SGMNT"));
	 * controllingAreaDto.setControllingArea(controlAreaItem.getString(
	 * "CREDIT_SEG_DESC"));
	 * 
	 * controllingAreaList = new ArrayList<>();
	 * 
	 * controllingAreaList.add(controllingAreaDto);
	 * 
	 * controllingAreaMap.put(controlAreaItem.getString("PARTNER"),
	 * controllingAreaList);
	 * 
	 * }
	 * 
	 * if (i == 3) {
	 * 
	 * break; }
	 * 
	 * }
	 * 
	 * for (int i = 0; i < shippingItemArray.length(); i++) {
	 * 
	 * shippingItem = shippingItemArray.getJSONObject(i);
	 * 
	 * if (shippingAddressMap.containsKey(shippingItem.getString("KUNNR"))) {
	 * 
	 * shippingAddressDto = new ShippingAddressDto();
	 * 
	 * shippingAddressDto.setShipToId(shippingItem.getString("KUNN2"));
	 * shippingAddressDto.setShipToName(shippingItem.getString("NAME1"));
	 * shippingAddressDto.setShipToCity(shippingItem.getString("ORT01"));
	 * shippingAddressDto.setShipToPostalCode(shippingItem.getString("PSTLZ"));
	 * shippingAddressDto.setShipToRegion(shippingItem.getString("REGIO"));
	 * 
	 * shippingAddressList =
	 * shippingAddressMap.get(shippingItem.getString("KUNNR"));
	 * 
	 * shippingAddressList.add(shippingAddressDto);
	 * 
	 * shippingAddressMap.put(shippingItem.getString("KUNNR"),
	 * shippingAddressList);
	 * 
	 * } else {
	 * 
	 * shippingAddressDto = new ShippingAddressDto();
	 * 
	 * shippingAddressDto.setShipToId(shippingItem.getString("KUNN2"));
	 * shippingAddressDto.setShipToName(shippingItem.getString("NAME1"));
	 * shippingAddressDto.setShipToCity(shippingItem.getString("ORT01"));
	 * shippingAddressDto.setShipToPostalCode(shippingItem.getString("PSTLZ"));
	 * shippingAddressDto.setShipToRegion(shippingItem.getString("REGIO"));
	 * 
	 * shippingAddressList = new ArrayList<>();
	 * 
	 * shippingAddressList.add(shippingAddressDto);
	 * 
	 * shippingAddressMap.put(shippingItem.getString("KUNNR"),
	 * shippingAddressList); }
	 * 
	 * if (i == 3) {
	 * 
	 * break; } }
	 * 
	 * for (String customerId : customerMap.keySet()) {
	 * 
	 * customerDto = new CustomerDto();
	 * 
	 * customerDto.setCustId(customerId);
	 * 
	 * customerDetails = customerMap.get(customerId);
	 * 
	 * customerDto.setCustCategory(customerDetails.getCustCategory());
	 * customerDto.setCustCity(customerDetails.getCustCity());
	 * customerDto.setCustCountry(customerDetails.getCustCountry());
	 * customerDto.setCustCreditLimit(customerDetails.getCustCreditLimit());
	 * customerDto.setCustName(customerDetails.getCustName());
	 * customerDto.setCustPostalCode(customerDetails.getCustPostalCode());
	 * 
	 * if (shippingAddressMap.containsKey(customerId)) {
	 * customerDto.setListOfShippingAddressDto(shippingAddressMap.get(customerId
	 * )); }
	 * 
	 * if (controllingAreaMap.containsKey(customerId)) {
	 * customerDto.setListOfControllingAreaDto(controllingAreaMap.get(customerId
	 * )); }
	 * 
	 * customerDtoList.add(customerDto);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * logger.error("[HciGetCustomerDetailsService][getCustomerAlldetails]" +
	 * e.getMessage()); }
	 * 
	 * return customerDtoList; }
	 */

	/*
	 * // this is need to change
	 * 
	 * @Override public PaymentTermsDto getCustomerPaymentTerms(String
	 * companyCode, String customerNumber) {
	 * 
	 * PaymentTermsDto paymentTermsDto = null;
	 * 
	 * try {
	 * 
	 * JSONObject inputObject = new JSONObject();
	 * 
	 * JSONObject getCustomerPaymentTerms = new JSONObject();
	 * 
	 * getCustomerPaymentTerms.put("CompanyCode", companyCode);
	 * getCustomerPaymentTerms.put("CustomerNumber", customerNumber);
	 * 
	 * inputObject.put("GetCustomerPaymentTerms", getCustomerPaymentTerms);
	 * 
	 * String response = restInvoker.postDataToServer("/http//getpaymentterms",
	 * inputObject.toString());
	 * 
	 * if (response != null && !(response.equals(""))) {
	 * 
	 * JSONObject result = new JSONObject(response);
	 * 
	 * JSONObject zsd_Customer_PaymentTerms_Get = new JSONObject();
	 * 
	 * JSONObject e_Payterms = new JSONObject();
	 * 
	 * JSONObject item = new JSONObject();
	 * 
	 * zsd_Customer_PaymentTerms_Get =
	 * result.getJSONObject("ZSD_CUSTOMER_PAYMENTTERMS_GET.Response");
	 * 
	 * e_Payterms = zsd_Customer_PaymentTerms_Get.getJSONObject("E_PAYTERMS");
	 * 
	 * item = e_Payterms.getJSONObject("item");
	 * 
	 * paymentTermsDto = new PaymentTermsDto();
	 * 
	 * 
	 * paymentTermsDto.setMandt(item.getString("MANDT"));
	 * paymentTermsDto.setZterm(item.getString("ZTERM"));
	 * paymentTermsDto.setZtagg(item.getString("ZTAGG"));
	 * paymentTermsDto.setZdart(item.getString("ZDART"));
	 * paymentTermsDto.setZfael(item.getString("ZFAEL"));
	 * paymentTermsDto.setZmona(item.getString("ZMONA"));
	 * paymentTermsDto.setZtag1(item.getString("ZTAG1"));
	 * paymentTermsDto.setZprz1(item.getString("ZPRZ1"));
	 * paymentTermsDto.setZtag2(item.getString("ZTAG2"));
	 * paymentTermsDto.setZprz2(item.getString("ZPRZ2"));
	 * paymentTermsDto.setZtag3(item.getString("ZTAG3"));
	 * paymentTermsDto.setZstg1(item.getString("ZSTG1"));
	 * paymentTermsDto.setZsmn1(item.getString("ZSMN1"));
	 * paymentTermsDto.setZstg2(item.getString("ZSTG2"));
	 * paymentTermsDto.setZsmn2(item.getString("ZSMN2"));
	 * paymentTermsDto.setZstg3(item.getString("ZSTG3"));
	 * paymentTermsDto.setZsmn3(item.getString("ZSMN3"));
	 * paymentTermsDto.setXzbrv(item.getString("XZBRV"));
	 * paymentTermsDto.setZschf(item.getString("ZSCHF"));
	 * paymentTermsDto.setXchpb(item.getString("XCHPB"));
	 * paymentTermsDto.setTxn08(item.getString("TXN08"));
	 * paymentTermsDto.setZlsch(item.getString("ZLSCH"));
	 * paymentTermsDto.setXchpm(item.getString("XCHPM"));
	 * paymentTermsDto.setKoart(item.getString("KOART"));
	 * paymentTermsDto.setXsplt(item.getString("XSPLT"));
	 * paymentTermsDto.setXscrc(item.getString("XSCRC"));
	 * 
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * logger.error("[HciGetCustomerDetailsService][getCustomerPaymentTerms]" +
	 * e.getMessage()); }
	 * 
	 * return paymentTermsDto;
	 * 
	 * }
	 */

	// for sales Order
	@Override
	public PaymentTermsDto getCustomerPaymentTerms(String companyCode, String customerNumber) {

		PaymentTermsDto paymentTermsDto = new PaymentTermsDto();

		JSONObject inputObject = new JSONObject();

		JSONObject getCustomerPaymentTerms = new JSONObject();

		JSONArray recordArray = new JSONArray();

		JSONObject item = new JSONObject();

		item.put("CustomerNumber", customerNumber);
		item.put("CompanyCode", companyCode);

		recordArray.put(item);

		getCustomerPaymentTerms.put("Record", recordArray);

		inputObject.put("GetCustomerPaymentTerms", getCustomerPaymentTerms);
		
		logger.error("Payment Term PAyload: "+ inputObject.toString());

		String response = restInvoker.postDataToServer(HciApiConstants.GET_PAYMENT_TERMS, inputObject.toString());
		
		logger.error("Payment Term Response: "+ response);

		try {

			if (response != null && !(response.equals(""))) {

				JSONObject result = new JSONObject(response);

				JSONObject resultObject = new JSONObject();

				resultObject = result.getJSONObject("ZSD_CUSTOMER_PAYMENTTERMS_GET.Response");

				String errorMsg = resultObject.optString("E_ERR");

				if (errorMsg != null && (errorMsg.trim().isEmpty())) {

					JSONObject payterms = new JSONObject();

					payterms = resultObject.getJSONObject("E_PAYTERMS");

					JSONArray itemArray;

					if (payterms.optJSONArray("item") != null) {

						itemArray = payterms.optJSONArray("item");

						for (int i = 0; i < itemArray.length(); i++) {

							JSONObject item1 = new JSONObject();

							item1 = itemArray.getJSONObject(i);

							paymentTermsDto.setZfael(item1.getString("ZFAEL"));
							paymentTermsDto.setZterm(item1.optString("ZTERM"));
							paymentTermsDto.setCustId(item1.getString("KUNNR"));
							paymentTermsDto.setCompanyCode(item1.getString("BUKRS"));

						}

					} else if (payterms.optJSONObject("item") != null) {

						JSONObject item1 = new JSONObject();

						item1 = payterms.optJSONObject("item");

						paymentTermsDto.setZfael(item1.getString("ZFAEL"));
						paymentTermsDto.setZterm(item1.optString("ZTERM"));
						paymentTermsDto.setCustId(item1.getString("KUNNR"));
						paymentTermsDto.setCompanyCode(item1.getString("BUKRS"));

					}
				} else {
					paymentTermsDto.setZfael("0");
					paymentTermsDto.setCustId(customerNumber);
					paymentTermsDto.setZterm(" ");
					paymentTermsDto.setCompanyCode(companyCode);
				}

			}

		} catch (Exception e) {
			paymentTermsDto.setZfael("0");
			logger.error("getCustomerPaymentTerms:" + e.getMessage());

		}

		return paymentTermsDto;

	}

	// new service to get all terms
	@Override
	public List<PaymentTermsDto> getCustomerPaymentTermsFromRFC(Map<String, String> companyMap) {

		List<PaymentTermsDto> paymentTermsList = new ArrayList<>();
		PaymentTermsDto paymentTermsDto;

		try {

			if (companyMap != null && !companyMap.isEmpty()) {

				JSONObject inputObject = new JSONObject();

				JSONObject getCustomerPaymentTerms = new JSONObject();

				JSONArray recordArray = new JSONArray();

				Set<Entry<String, String>> entrySet = companyMap.entrySet();

				for (Entry<String, String> entry : entrySet) {

					JSONObject item = new JSONObject();

					item.put("CustomerNumber", entry.getKey());
					item.put("CompanyCode", entry.getValue());

					recordArray.put(item);

				}

				getCustomerPaymentTerms.put("Record", recordArray);

				inputObject.put("GetCustomerPaymentTerms", getCustomerPaymentTerms);

				String response = restInvoker.postDataToServer(HciApiConstants.GET_PAYMENT_TERMS,
						inputObject.toString());

				if (response != null && !(response.equals(""))) {

					JSONObject result = new JSONObject(response);

					JSONObject resultObject = new JSONObject();

					resultObject = result.getJSONObject("ZSD_CUSTOMER_PAYMENTTERMS_GET.Response");

					String errMsg = resultObject.optString("E_ERR");

					if (errMsg != null && errMsg.trim().isEmpty()) {

						JSONObject payterms = new JSONObject();

						payterms = resultObject.getJSONObject("E_PAYTERMS");

						JSONArray itemArray = new JSONArray();

						if (payterms.optJSONArray("item") != null) {

							itemArray = payterms.optJSONArray("item");

							for (int i = 0; i < itemArray.length(); i++) {

								JSONObject item = new JSONObject();

								item = itemArray.getJSONObject(i);

								paymentTermsDto = new PaymentTermsDto();

								paymentTermsDto.setZfael(item.getString("ZFAEL"));
								paymentTermsDto.setZterm(item.optString("ZTERM"));
								paymentTermsDto.setCustId(item.getString("KUNNR"));
								paymentTermsDto.setCompanyCode(item.getString("BUKRS"));

								paymentTermsList.add(paymentTermsDto);

							}

						} else if (payterms.optJSONObject("item") != null) {

							JSONObject item = new JSONObject();

							item = payterms.optJSONObject("item");

							paymentTermsDto = new PaymentTermsDto();

							paymentTermsDto.setZfael(item.getString("ZFAEL"));
							paymentTermsDto.setZterm(item.optString("ZTERM"));
							paymentTermsDto.setCustId(item.getString("KUNNR"));
							paymentTermsDto.setCompanyCode(item.getString("BUKRS"));

							paymentTermsList.add(paymentTermsDto);

						}

					}

				}

			}

		} catch (Exception e) {

			logger.error("[HciGetCustomerDetailsService][getCustomerPaymentTermsFromRFC]" + e.getMessage());
		}

		return paymentTermsList;

	}

	/*
	 * @Override public List<ShippingAddressDto>
	 * getShippingDetailsByCustId(String customerId) {
	 * 
	 * List<ShippingAddressDto> shippingAddressDtoList = new ArrayList<>();
	 * 
	 * ShippingAddressDto shippingAddressDto;
	 * 
	 * if (customerId != null && !customerId.trim().isEmpty()) {
	 * 
	 * try {
	 * 
	 * JSONObject inputObject = new JSONObject();
	 * 
	 * JSONObject getShippingInfo = new JSONObject();
	 * 
	 * getShippingInfo.put("Customer", customerId);
	 * 
	 * inputObject.put("GetShippingInfo", getShippingInfo);
	 * 
	 * String response = restInvoker.postDataToServer("/http/togetshippinginfo",
	 * inputObject.toString());
	 * 
	 * if (response != null && !(response.equals(""))) {
	 * 
	 * JSONObject result = new JSONObject(response);
	 * 
	 * JSONObject zsdGetCustShipDetailsResponse = new JSONObject();
	 * 
	 * zsdGetCustShipDetailsResponse =
	 * result.getJSONObject("ZSD_GET_CUST_SHIP_DETAILS.Response");
	 * 
	 * if (zsdGetCustShipDetailsResponse.optJSONObject("ET_SHIPPING") != null) {
	 * 
	 * JSONObject etShipping = new JSONObject();
	 * 
	 * etShipping = zsdGetCustShipDetailsResponse.getJSONObject("ET_SHIPPING");
	 * 
	 * if (etShipping.optJSONArray("item") != null) {
	 * 
	 * JSONArray itemArray = new JSONArray();
	 * 
	 * itemArray = etShipping.getJSONArray("item");
	 * 
	 * for (int i = 0; i < itemArray.length(); i++) {
	 * 
	 * JSONObject item = new JSONObject();
	 * 
	 * item = itemArray.getJSONObject(i);
	 * 
	 * shippingAddressDto = new ShippingAddressDto();
	 * 
	 * shippingAddressDto.setShipToId(item.getString("KUNN2"));
	 * shippingAddressDto.setShipToName(item.getString("NAME1"));
	 * shippingAddressDto.setShipToCity(item.getString("ORT01"));
	 * shippingAddressDto.setShipToPostalCode(item.getString("PSTLZ"));
	 * shippingAddressDto.setShipToRegion(item.getString("REGIO"));
	 * 
	 * shippingAddressDtoList.add(shippingAddressDto);
	 * 
	 * }
	 * 
	 * } else if (etShipping.optJSONObject("item") != null) {
	 * 
	 * JSONObject item = new JSONObject();
	 * 
	 * item = etShipping.getJSONObject("item");
	 * 
	 * shippingAddressDto = new ShippingAddressDto();
	 * 
	 * shippingAddressDto.setShipToId(item.getString("KUNN2"));
	 * shippingAddressDto.setShipToName(item.getString("NAME1"));
	 * shippingAddressDto.setShipToCity(item.getString("ORT01"));
	 * shippingAddressDto.setShipToPostalCode(item.getString("PSTLZ"));
	 * shippingAddressDto.setShipToRegion(item.getString("REGIO"));
	 * 
	 * shippingAddressDtoList.add(shippingAddressDto); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * logger.error("[HciGetCustomerDetailsService][getShippingDetailsByCustId]"
	 * + e.getMessage()); }
	 * 
	 * } return shippingAddressDtoList;
	 * 
	 * }
	 */

	// get customers from RFC for Batch inserts
	@Override
	public List<CustomerDo> getAllCustomerDetailsFromRFC(List<String> customerList) {

		List<CustomerDo> customerDoList = new ArrayList<>();
		CustomerDo customerDo;

		try {

			JSONObject inputObject = new JSONObject();

			JSONObject getMultipleCustomerList = new JSONObject();

			JSONArray record = new JSONArray();

			JSONObject customerNumbers;

			for (String customerId : customerList) {

				customerNumbers = new JSONObject();

				customerNumbers.put("CustomerNumbers", customerId);

				record.put(customerNumbers);

			}

			getMultipleCustomerList.put("Record", record);

			inputObject.put("GetMultipleCustomerList", getMultipleCustomerList);
			logger.error("PAYLOAD: " + inputObject.toString());

			String response = restInvoker.postDataToServer(HciApiConstants.GET_MULTIPLE_CUSTOMERS,
					inputObject.toString());

			if (response != null && !(response.equals(""))) {

				JSONObject result = new JSONObject(response);

				JSONObject zsdGetCustDetailsResponse = new JSONObject();

				zsdGetCustDetailsResponse = result.getJSONObject("ZSD_GET_CUSTOMERS_DETAILS.Response");

				JSONObject custDetails = new JSONObject();

				if (zsdGetCustDetailsResponse.optJSONObject("CUST_DETAILS") != null) {

					custDetails = zsdGetCustDetailsResponse.optJSONObject("CUST_DETAILS");

					if (custDetails.optJSONArray("item") != null) {

						JSONArray itemArray = new JSONArray();

						itemArray = custDetails.optJSONArray("item");

						for (int j = 0; j < itemArray.length(); j++) {

							JSONObject item = new JSONObject();

							item = itemArray.getJSONObject(j);

							customerDo = new CustomerDo();

							customerDo.setCustId(item.getString("CUSTOMER"));
							customerDo.setCustName(item.optString("NAME"));
							customerDo.setCustCity(item.optString("CITY"));
							customerDo.setCustCountry(item.optString("COUNTRY"));
							customerDo.setCustPostalCode(item.optString("PO_CODE"));
							customerDo.setCustCategory(item.optString("CATEGORY"));
							customerDo.setSpCustId(item.optString("SP_CUST"));
							customerDo.setSpName(item.optString("SP_NAME"));
							customerDo.setSpCity(item.optString("SP_CITY"));
							customerDo.setSpPostalCode(item.optString("SP_PSTLZ"));

							if (item.optString("CR_FLAG") != null
									&& item.optString("CR_FLAG").trim().equalsIgnoreCase("X")) {

								customerDo.setCrFlag(Boolean.TRUE);
							} else {

								customerDo.setCrFlag(Boolean.FALSE);

							}

							customerDo.setPhoneNumber(item.optString("TELF1"));
							customerDo.setTeleExtension(item.optString("TEL_EXT"));
							customerDo.setTelFax(item.optString("TELFX"));
							customerDo.setFaxExtension(item.optString("FAX_EXT"));
							customerDo.setEmail(item.optString("EMAIL"));
							customerDo.setSpPhoneNumber(item.optString("SP_TELF1"));
							customerDo.setSpTeleExtension(item.optString("SP_TELEXT"));
							customerDo.setSpTelFax(item.optString("SP_TELFX"));
							customerDo.setSpFaxExtension(item.optString("SP_FAXEXT"));
							customerDo.setSpEmail(item.optString("SP_EMAIL"));

							customerDoList.add(customerDo);

						}

					}

				}

			}

		} catch (Exception e) {

			logger.error("[HciGetCustomerDetailsService][getAllCustom" + "erDetailsFromRFC]" + e.getMessage());

		}

		return customerDoList;
	}

	@Override
	public Map<String, CreditLimitDto> getCreditLimitsOfCutomerList(Map<String, String> customerList) {

		Map<String, CreditLimitDto> creditMap = null;

		if (customerList != null) {

			creditMap = new HashMap<>();

			JSONObject inputObject = new JSONObject();

			JSONObject getCreditLimit = new JSONObject();

			JSONArray record = new JSONArray();

			JSONObject customerNumbers;

			Set<Entry<String, String>> customerListSet = customerList.entrySet();

			for (Entry<String, String> entry : customerListSet) {

				customerNumbers = new JSONObject();

				customerNumbers.put("CustomerNumbers", entry.getKey());
				customerNumbers.put("CreditSegment", entry.getValue());

				record.put(customerNumbers);

			}

			getCreditLimit.put("Record", record);

			inputObject.put("GetCreditLimit", getCreditLimit);

			String response = restInvoker.postDataToServer(HciApiConstants.GET_CREDIT_LIMIT, inputObject.toString());

			if (response != null && !(response.equals(""))) {

				JSONObject result = new JSONObject(response);

				JSONObject zsdGetCustCreditLimitsResponse = new JSONObject();

				zsdGetCustCreditLimitsResponse = result.getJSONObject("ZSD_CREDTLIMT_GET.Response");

				JSONObject eCrlimit = new JSONObject();

				eCrlimit = zsdGetCustCreditLimitsResponse.optJSONObject("E_CRLIMIT");

				if (eCrlimit != null) {

					if (eCrlimit.optJSONArray("item") != null) {

						JSONArray itemArray = new JSONArray();

						itemArray = eCrlimit.optJSONArray("item");

						for (int i = 0; i < itemArray.length(); i++) {

							JSONObject itemObject = new JSONObject();

							itemObject = itemArray.getJSONObject(i);

							CreditLimitDto creditLimitDto = new CreditLimitDto();

							creditLimitDto.setCustId(itemObject.getString("CUSTOMER"));
							creditLimitDto.setCreditLimit(itemObject.getBigDecimal("CR_LIMIT"));
							creditLimitDto.setExposure(itemObject.getBigDecimal("CR_EXPOSURE"));

							creditMap.put(itemObject.getString("CUSTOMER"), creditLimitDto);

						}

					} else if (eCrlimit.optJSONObject("item") != null) {

						JSONObject itemObject = new JSONObject();

						itemObject = eCrlimit.optJSONObject("item");

						CreditLimitDto creditLimitDto = new CreditLimitDto();

						creditLimitDto.setCustId(itemObject.getString("CUSTOMER"));
						creditLimitDto.setCreditLimit(itemObject.getBigDecimal("CR_LIMIT"));
						creditLimitDto.setExposure(itemObject.getBigDecimal("CR_EXPOSURE"));

						creditMap.put(itemObject.getString("CUSTOMER"), creditLimitDto);

					}
				}

			}

		}

		return creditMap;
	}
}
