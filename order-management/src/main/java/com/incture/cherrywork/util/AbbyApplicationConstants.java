package com.incture.cherrywork.util;



import java.util.Arrays;
import java.util.List;

public interface AbbyApplicationConstants {
	String SUCCESS = "Success";
	String CREATED_SUCCESS = "created successfully";
	String CREATE_FAILURE = "creation failed";
	String UPDATE_SUCCESS = "updated successfully";
	String UPDATE_FAILURE = "updation failed";
	String FETCHED_SUCCESS = "fetched successfully";
	String FETCHED_FAILURE = "fetching failed";
	String REJECT_SUCCESS="Rejected Successfully";
	String REJECT_FAILURE="Rejection failed";
	String DELETE_SUCCESS = "Deleted Successfully";
	String DELETE_FAILURE = "Deletion failed";
	String FAILURE = "Failure";
	String CREATE = "Create";
	String UPDATE = "Update";
	String CODE_SUCCESS = "0";
	String CODE_FAILURE = "1";
	Integer APPENDINGIDLENGTH = 12;

	//String OCR_TOKEN_URL = "https://43dcc804trial.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
			//String OCR_TOKEN_URL_DIPANJAN = "https://p2000982453trial.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	
	
	//String OCR_JOBS_URL = "https://aiservices-trial-dox.cfapps.eu10.hana.ondemand.com/document-information-extraction/v1/document/jobs";
	int BAD_REQUEST = 400;
	int UNAUTHORIZED = 401;
	String ISE = "500";
	int CREATED = 201;
	String METHOD_GET = "GET";
	String EMPTY_PAYLOAD = "";
	String METHOD_POST = "POST";
	String OCR_CLIENTS_URL = "https://aiservices-trial-dox.cfapps.eu10.hana.ondemand.com/document-information-extraction/v1/clients";// ?limit=4
	String DOC_POST_URL = "https://aiservices-trial-dox.cfapps.eu10.hana.ondemand.com/document-information-extraction/v1/document/jobs";
	String OPTIONS_BODY = "{\"extraction\":{\"headerFields\":[\"documentNumber\",\"taxId\",\"taxName\",\"purchaseOrderNumber\",\"shippingAmount\",\"netAmount\",\"senderAddress\",\"senderName\",\"grossAmount\",\"currencyCode\",\"receiverContact\",\"documentDate\",\"taxAmount\",\"taxRate\",\"receiverName\",\"receiverAddress\"],\"lineItemFields\":[\"description\",\"netAmount\",\"quantity\",\"unitPrice\",\"materialNumber\"]},\"clientId\":\"ap_01\",\"documentType\":\"invoice\",\"enrichment\":{\"sender\":{\"top\":5,\"type\":\"businessEntity\",\"subtype\":\"supplier\"},\"employee\":{\"type\":\"employee\"}}}";
	String TYPE_PDF = "application/pdf";
	List<String> HEADER_FILEDS = Arrays.asList("documentNumber", "taxId", "taxName", "purchaseOrderNumber",
			"shippingAmount", "netAmount", "senderAddress", "senderName", "grossAmount", "currencyCode",
			"receiverContact", "documentDate", "taxAmount", "taxRate", "receiverName", "receiverAddress");
	List<String> LINE_ITEM_FIELDS = Arrays.asList("description", "netAmount", "quantity", "unitPrice",
			"materialNumber");
	String ABBYY_OCR_TOKEN_URL = "https://vantage-us.abbyy.com/auth/connect/token";
	String ABBYY_TRANSACTIONS_URL = "https://vantage-us.abbyy.com/api/publicapi/v1/transactions";
}
