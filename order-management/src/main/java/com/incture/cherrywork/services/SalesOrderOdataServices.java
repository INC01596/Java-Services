package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInvoiceInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryPgiInputDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderOdataLineItemDto;
import com.incture.cherrywork.odata.dto.OdataMaterialStartDto;
import com.incture.cherrywork.odata.dto.OdataSchHeaderStartDto;
import com.incture.cherrywork.odata.dto.OdataSchItemStartDto;
//import com.incture.cherrywork.odataServices.OdataServices;
import com.incture.cherrywork.repositories.ISalesOrderHeaderCustomRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.SalesOrderOdataConstants;

@SuppressWarnings("unused")
@Service("OdataServices")
public class SalesOrderOdataServices {

	// private Logger logger = LoggerFactory.getLogger(OdataServices.class);

	@Async
	public String postData(SalesOrderOdataHeaderDto headerDto, String docType) {
		// logger.debug("[OdataServices][postData] Started : " +
		// headerDto.toString());
		System.err.println("[OdataServices][postData] Started : " + headerDto.toString());
		String response = null;
		try {
			String requestURL = null;
			
			requestURL = SalesOrderOdataConstants.BASE_URL + "salesDocumentSet";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			String reqPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerDto);
			// logger.debug("[OdataServices][postData] reqPayload :
			// "+reqPayload);
			System.err.println("[OdataServices][postData] reqPayload : " + reqPayload);
			response = SalesOrderOdataUtilService.callOdata(requestURL, "POST", reqPayload, null);
			// logger.debug("[OdataServices][postData] Response : "+response);
			System.err.println("[OdataServices][postData] Response : " + response);
		} catch (Exception e) {
			// logger.error("[OdataServices][postData] Exception : " +
			// e.getMessage());
			System.err.println("[OdataServices][postData] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataServices][postData] Done");
		System.err.println("[OdataServices][postData] Done");

		return response;
	}

	@Async
	public String postDataObd(OdataOutBoudDeliveryInputDto headerDto, String docType) {
		// logger.debug("[OdataServices][postData] Started : " +
		// headerDto.toString());
		System.err.println("[OdataServices][postData] Started : " + headerDto.toString());
		String response = null;
		try {
			String requestURL = null;
			requestURL = SalesOrderOdataConstants.BASE_URL_OBD + "likpSet";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			String reqPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerDto);
			// logger.debug("[OdataServices][postData] reqPayload :
			// "+reqPayload);
			System.err.println("[OdataServices][postData] reqPayload : " + reqPayload);
			response = SalesOrderOdataUtilService.callOdataObd(requestURL, "POST", reqPayload, null);
			// logger.debug("[OdataServices][postData] Response : "+response);
			System.err.println("[OdataServices][postData] Response : " + response);
		} catch (Exception e) {
			// logger.error("[OdataServices][postData] Exception : " +
			// e.getMessage());
			System.err.println("[OdataServices][postData] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataServices][postData] Done");
		System.err.println("[OdataServices][postData] Done");

		return response;
	}

	@Async
	public String postDataPgi(OdataOutBoudDeliveryPgiInputDto headerDto, String docType) {
		// logger.debug("[OdataServices][postData] Started : " +
		// headerDto.toString());
		System.err.println("[OdataServices][postData] Started : " + headerDto.toString());
		String response = null;
		try {
			String requestURL = null;
			requestURL = SalesOrderOdataConstants.BASE_URL_OBD + "likpSet";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			String reqPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerDto);
			// logger.debug("[OdataServices][postData] reqPayload :
			// "+reqPayload);
			System.err.println("[OdataServices][postData] reqPayload : " + reqPayload);
			response = SalesOrderOdataUtilService.callOdataObd(requestURL, "POST", reqPayload, null);
			// logger.debug("[OdataServices][postData] Response : "+response);
			System.err.println("[OdataServices][postData] Response : " + response);
		} catch (Exception e) {
			// logger.error("[OdataServices][postData] Exception : " +
			// e.getMessage());
			System.err.println("[OdataServices][postData] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataServices][postData] Done");
		System.err.println("[OdataServices][postData] Done");

		return response;
	}
	
	@Async
	public String postDataInv(OdataOutBoudDeliveryInvoiceInputDto headerDto, String docType) {
		// logger.debug("[OdataServices][postData] Started : " +
		// headerDto.toString());
		System.err.println("[OdataServices][postData] Started : " + headerDto.toString());
		String response = null;
		try {
			String requestURL = null;
			requestURL = SalesOrderOdataConstants.BASE_URL_OBD + "likpSet";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			String reqPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerDto);
			// logger.debug("[OdataServices][postData] reqPayload :
			// "+reqPayload);
			System.err.println("[OdataServices][postData] reqPayload : " + reqPayload);
			response = SalesOrderOdataUtilService.callOdataObd(requestURL, "POST", reqPayload, null);
			// logger.debug("[OdataServices][postData] Response : "+response);
			System.err.println("[OdataServices][postData] Response : " + response);
		} catch (Exception e) {
			// logger.error("[OdataServices][postData] Exception : " +
			// e.getMessage());
			System.err.println("[OdataServices][postData] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataServices][postData] Done");
		System.err.println("[OdataServices][postData] Done");

		return response;
	}


	// public Response acknowledge(String s4DocumentId) {
	// logger.debug("[OdataServices][acknowledge] Started : " + s4DocumentId);
	// Response response = new Response();
	// String odataResponse = null;
	// String URL = OdataConstants.BASE_URL+"getAckSet/?$filter=DocID%20eq%20'"
	// + s4DocumentId + "'";
	// try {
	// odataResponse = OdataUtilService.callOdata(URL, "GET", null, "fetch");
	// logger.debug("[OdataUtilService][acknowledge] Response : " +
	// odataResponse);
	// if (!ServicesUtils.isEmpty(odataResponse)) {
	// response.setMessage("Acknowledged Order Successfully");
	// response.setStatus(HttpStatus.OK.getReasonPhrase());
	// response.setStatusCode(HttpStatus.OK.value());
	// } else {
	// response.setMessage("Error Acknowledging Order");
	// response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	// response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	// }
	// } catch (Exception e) {
	// logger.error("[OdataUtilService][priceingSet] Exception : " +
	// e.getMessage());
	// e.printStackTrace();
	// }
	// return response;
	// }
	//
	public static OdataMaterialStartDto materialScheduler() {

		OdataMaterialStartDto odataMaterialStartDto = new OdataMaterialStartDto();
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getMaterialTabSet?$filter=Zzkey%20eq%20'GET'";
		try {
			String response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			odataMaterialStartDto = gson.fromJson(response.toString(), OdataMaterialStartDto.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return odataMaterialStartDto;
	}

	//
	public static String materialAckScheduler() {

		String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getMaterialTabSet?$filter=Zzkey%20eq%20'ACK'";
		try {
			response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return response;
	}

	public OdataSchHeaderStartDto headerScheduler() {
		// logger.debug("[SalesOrderOdataUtilService][headerScheduler]
		// Started");
		System.err.println("[SalesOrderOdataUtilService][headerScheduler] Started");
		OdataSchHeaderStartDto odataSchHeaderStartDto = new OdataSchHeaderStartDto();
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getSOHeaderTabSet?$filter=Vbeln%20eq%20'GET'";
		try {
			String response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			odataSchHeaderStartDto = gson.fromJson(response.toString(), OdataSchHeaderStartDto.class);
			// logger.debug("[SalesOrderOdataUtilService][headerScheduler]
			// OdataMaterialDto : " + odataSchHeaderStartDto.toString());
		} catch (Exception e) {
			// logger.error("[SalesOrderOdataUtilService][headerScheduler]
			// Exception in JSON : " + e.getMessage());
			System.err.println("[SalesOrderOdataUtilService][headerScheduler] Exception in JSON : " + e.getMessage());
			e.printStackTrace();
		}
		return odataSchHeaderStartDto;
	}

	public String headerAckScheduler() {
		// logger.debug("[SalesOrderOdataUtilService][headerAckScheduler]
		// Started");
		System.err.println("[SalesOrderOdataUtilService][headerAckScheduler] Started");
		String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getSOHeaderTabSet?$filter=Vbeln%20eq%20'ACK'";
		try {
			response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");
			// logger.debug("[SalesOrderOdataUtilService][headerAckScheduler]
			// Response : " + response);
			System.err.println("[SalesOrderOdataUtilService][headerAckScheduler] Response : " + response);
		} catch (Exception e) {
			// logger.error("[SalesOrderOdataUtilService][headerAckScheduler]
			// Exception : " + e.getMessage());
			System.err.println("[SalesOrderOdataUtilService][headerAckScheduler] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	public OdataSchItemStartDto itemScheduler() {
		// logger.debug("[SalesOrderOdataUtilService][itemScheduler] Started");
		System.err.println("[SalesOrderOdataUtilService][itemScheduler] Started");
		OdataSchItemStartDto odataSchItemStartDto = new OdataSchItemStartDto();
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getSOItemTabSet?$filter=Vbeln%20eq%20'GET'";
		try {
			String response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			odataSchItemStartDto = gson.fromJson(response.toString(), OdataSchItemStartDto.class);
			// logger.debug("[SalesOrderOdataUtilService][itemScheduler]
			// OdataMaterialDto : "
			System.err.println("[SalesOrderOdataUtilService][itemScheduler] OdataMaterialDto : "
					+ odataSchItemStartDto.toString());

		} catch (Exception e) {
			//logger.error("[SalesOrderOdataUtilService][itemScheduler] Exception in JSON : " + e.getMessage());
			System.err.println("[SalesOrderOdataUtilService][itemScheduler] Exception in JSON : " + e.getMessage());
			e.printStackTrace();
		}
		return odataSchItemStartDto;
	}

	public String itemAckScheduler() {
		//logger.debug("[SalesOrderOdataUtilService][itemAckScheduler] Started");
		System.err.println("[SalesOrderOdataUtilService][itemAckScheduler] Started");
		String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL_SCH + "getSOItemTabSet?$filter=Vbeln%20eq%20'ACK'";
		try {
			response = SalesOrderOdataUtilService.callOdataSch(URL, "GET", null, "fetch");
			//logger.debug("[SalesOrderOdataUtilService][itemAckScheduler] Response : " + response);
			System.err.println("[SalesOrderOdataUtilService][itemAckScheduler] Response : " + response);
		} catch (Exception e) {
			//logger.error("[SalesOrderOdataUtilService][itemAckScheduler] Exception : " + e.getMessage());
			System.err.println("[SalesOrderOdataUtilService][itemAckScheduler] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	//
	public String pricingSet(String request) {

		String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL + "getPriceSet";
		try {
			response = SalesOrderOdataUtilService.callOdata(URL, "POST", request, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return response;

	}

	//
	// public String usersByEmail(String email){
	// logger.debug("[OdataUtilService][usersByEmail] Started");
	// String response = null;
	// String URL =
	// OdataConstants.BASE_URL+"getUserSet/?$filter=Email%20eq%20'"+email.toLowerCase()+"'";
	// try {
	// response = OdataUtilService.callOdata(URL, "GET", null, null);
	// logger.debug("[OdataUtilService][usersByEmail] Response : " + response);
	// } catch (Exception e) {
	// logger.error("[OdataUtilService][usersByEmail] Exception : " +
	// e.getMessage());
	// e.printStackTrace();
	// }
	// return response;
	// }
	//
	public String usersBySoldToParty(String soldToParty) {

		String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL
				+ "searchHelpSet/?$filter=Path%20eq%20'EMAIL'%20and%20Key%20eq%20'" + soldToParty + "'";
		try {
			response = SalesOrderOdataUtilService.callOdata(URL, "GET", null, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return response;
	}

	// public static void main(String[] args){
	// String s = "MAYANK.PATEL@FOULATH.COM.BH";
	// String a = s.toLowerCase();
	// System.out.println(a);
	// }

}
