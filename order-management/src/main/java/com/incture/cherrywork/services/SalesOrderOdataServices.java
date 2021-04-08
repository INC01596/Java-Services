package com.incture.cherrywork.services;




<<<<<<< HEAD
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
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
=======

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
>>>>>>> d6f70bb107c0c3902d534e2883b7555f64d5faf0
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderOdataLineItemDto;
import com.incture.cherrywork.repositories.ISalesOrderHeaderCustomRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.SalesOrderOdataConstants;


@Service("OdataServices")
public class SalesOrderOdataServices {

	//private Logger logger = LoggerFactory.getLogger(OdataServices.class);
	

	@Async
	public String postData(SalesOrderOdataHeaderDto headerDto) {
		//logger.debug("[OdataServices][postData] Started : " + headerDto.toString());
		System.err.println("[OdataServices][postData] Started : " + headerDto.toString());
		String response = null;
		try {
			String requestURL = SalesOrderOdataConstants.BASE_URL+"salesDocumentSet";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			String reqPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerDto);
			//logger.debug("[OdataServices][postData] reqPayload : "+reqPayload);
			System.err.println("[OdataServices][postData] reqPayload : "+reqPayload);
			response = SalesOrderOdataUtilService.callOdata(requestURL, "POST", reqPayload, null);
		//	logger.debug("[OdataServices][postData] Response : "+response);
			System.err.println("[OdataServices][postData] Response : "+response);
		} catch (Exception e) {
			//logger.error("[OdataServices][postData] Exception : " + e.getMessage());
			System.err.println("[OdataServices][postData] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		//logger.debug("[OdataServices][postData] Done");
		System.err.println("[OdataServices][postData] Done");
		
		return response;
	}
	
//	public Response acknowledge(String s4DocumentId) {
//		logger.debug("[OdataServices][acknowledge] Started : " + s4DocumentId);
//		Response response = new Response();
//		String odataResponse = null;
//		String URL = OdataConstants.BASE_URL+"getAckSet/?$filter=DocID%20eq%20'" + s4DocumentId + "'";
//		try {
//			odataResponse = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			logger.debug("[OdataUtilService][acknowledge] Response : " + odataResponse);
//			if (!ServicesUtils.isEmpty(odataResponse)) {
//				response.setMessage("Acknowledged Order Successfully");
//				response.setStatus(HttpStatus.OK.getReasonPhrase());
//				response.setStatusCode(HttpStatus.OK.value());
//			} else {
//				response.setMessage("Error Acknowledging Order");
//				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//			}
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][priceingSet] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public OdataMaterialStartDto materialScheduler(){
//		logger.debug("[OdataUtilService][materialScheduler] Started");
//		OdataMaterialStartDto odataMaterialStartDto = new OdataMaterialStartDto();
//		String URL = OdataConstants.BASE_URL+"getMaterialTabSet?$filter=Zzkey%20eq%20'GET'";
//		try {
//			String response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//			odataMaterialStartDto = gson.fromJson(response.toString(), OdataMaterialStartDto.class);
//			logger.debug("[OdataUtilService][materialScheduler] OdataMaterialDto : " + odataMaterialStartDto.toString());
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][materialScheduler] Exception in JSON : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return odataMaterialStartDto;
//	}
//	
//	public String materialAckScheduler(){
//		logger.debug("[OdataUtilService][materialAckScheduler] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"getMaterialTabSet?$filter=Zzkey%20eq%20'ACK'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			logger.debug("[OdataUtilService][materialAckScheduler] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][materialAckScheduler] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public OdataSchHeaderStartDto headerScheduler(){
//		logger.debug("[OdataUtilService][headerScheduler] Started");
//		OdataSchHeaderStartDto odataSchHeaderStartDto = new OdataSchHeaderStartDto();
//		String URL = OdataConstants.BASE_URL+"getSOHeaderTabSet?$filter=Vbeln%20eq%20'GET'";
//		try {
//			String response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//			odataSchHeaderStartDto = gson.fromJson(response.toString(), OdataSchHeaderStartDto.class);
////			logger.debug("[OdataUtilService][headerScheduler] OdataMaterialDto : " + odataSchHeaderStartDto.toString());
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][headerScheduler] Exception in JSON : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return odataSchHeaderStartDto;
//	}
//	
//	public String headerAckScheduler(){
//		logger.debug("[OdataUtilService][headerAckScheduler] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"getSOHeaderTabSet?$filter=Vbeln%20eq%20'ACK'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			logger.debug("[OdataUtilService][headerAckScheduler] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][headerAckScheduler] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public OdataSchItemStartDto itemScheduler(){
//		logger.debug("[OdataUtilService][itemScheduler] Started");
//		OdataSchItemStartDto odataSchItemStartDto = new OdataSchItemStartDto();
//		String URL = OdataConstants.BASE_URL+"getSOItemTabSet?$filter=Vbeln%20eq%20'GET'";
//		try {
//			String response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//			odataSchItemStartDto = gson.fromJson(response.toString(), OdataSchItemStartDto.class);
//			logger.debug("[OdataUtilService][itemScheduler] OdataMaterialDto : " + odataSchItemStartDto.toString());
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][itemScheduler] Exception in JSON : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return odataSchItemStartDto;
//	}
//	
//	public String itemAckScheduler(){
//		logger.debug("[OdataUtilService][itemAckScheduler] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"getSOItemTabSet?$filter=Vbeln%20eq%20'ACK'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			logger.debug("[OdataUtilService][itemAckScheduler] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][itemAckScheduler] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public OdataRollingPlanStartDto rollingPlanScheduler(){
//		logger.debug("[OdataUtilService][rollingPlanScheduler] Started");
//		OdataRollingPlanStartDto rollingPlanStartDto = new OdataRollingPlanStartDto();
//		String URL = OdataConstants.BASE_URL+"getRollPlanTabSet?$filter=Werks%20eq%20'GET'";
//		try {
//			String response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//			rollingPlanStartDto = gson.fromJson(response.toString(), OdataRollingPlanStartDto.class);
//			logger.debug("[OdataUtilService][rollingPlanScheduler] OdataMaterialDto : " + rollingPlanStartDto.toString());
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][rollingPlanScheduler] Exception in JSON : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return rollingPlanStartDto;
//	}
//	
//	public String rollingPlanAckScheduler(){
//		logger.debug("[OdataUtilService][rollingPlanAckScheduler] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"getRollPlanTabSet?$filter=Werks%20eq%20'ACK'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, "fetch");
//			logger.debug("[OdataUtilService][rollingPlanAckScheduler] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][rollingPlanAckScheduler] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
	public String pricingSet(String request){
		
		
		
	String response = null;
		String URL = SalesOrderOdataConstants.BASE_URL+"getPriceSet";
		try {
			response = SalesOrderOdataUtilService.callOdata(URL, "POST", request, null);
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return response;
		
	}
//	
//	public String usersByEmail(String email){
//		logger.debug("[OdataUtilService][usersByEmail] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"getUserSet/?$filter=Email%20eq%20'"+email.toLowerCase()+"'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, null);
//			logger.debug("[OdataUtilService][usersByEmail] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][usersByEmail] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
//	
//	public String usersBySoldToParty(String soldToParty){
//		logger.debug("[OdataUtilService][usersBySoldToParty] Started");
//		String response = null;
//		String URL = OdataConstants.BASE_URL+"searchHelpSet/?$filter=Path%20eq%20'EMAIL'%20and%20Key%20eq%20'"+soldToParty+"'";
//		try {
//			response = OdataUtilService.callOdata(URL, "GET", null, null);
//			logger.debug("[OdataUtilService][usersBySoldToParty] Response : " + response);
//		} catch (Exception e) {
//			logger.error("[OdataUtilService][usersBySoldToParty] Exception : " + e.getMessage());
//			e.printStackTrace();
//		}
//		return response;
//	}
	
//	public static void main(String[] args){
//		String s = "MAYANK.PATEL@FOULATH.COM.BH";
//		String a = s.toLowerCase();
//		System.out.println(a);
//	}
	
	
	
}
