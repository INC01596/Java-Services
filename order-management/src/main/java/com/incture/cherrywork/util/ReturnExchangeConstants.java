package com.incture.cherrywork.util;

public class ReturnExchangeConstants {

	// Sequences Prefix
	public static final String EXCHANGE_SEQ_PREFIX = "CE";
	public static final String RETURN_WITH_EXCHANGE_SEQ_PREFIX = "XCE";
	public static final String RETURN_SEQ_PREFIX = "CR";
	public static final String RETURN_EXCHANGE_PREFIX = "XC";
	public static final String RETURN_SEQ_EXCHANGE_PREFIX = "XCR";
	
	// Return Request Order and Exchange Request Order
	public static final String RETURN_REQUEST_BATCH_ENDPOINTS = "/sap/opu/odata/sap/ZCOM_RETURNS_MANAGEMENT_SRV/$batch";
	public static final String RETURN_REQUEST_BATCH_TAG = "orderHeaderSet";

	// Email constants
	public static final String RETURN_REQUEST_MAIL_SUBJECT = "Return Request Update";
	public static final String RETURN_HEADER_SUBJECT = "Return Request Update";
	public static final String RETURN_REQUEST_INIT_MSG = "Your Return request is initiated.";

	// Return-Exchange
	public static final String RETURN_ITEM_NOT_FOUND = "No items received to return";
	
	// DAC
	public static final String DATA_NOT_FOUND = "Data not available in our record.";
	public static final String INVALID_INPUT = "Invalid Input, ";

	public static final String EXCEPTION_POST_MSG = "Error occured due to : ";
	
	
	public static final String COM_ODATA_DESTINATION_NAME = "COM_OdataServices";
	
	// Destination Service Key
	public static final String DESTINATION_CLIENT_ID = "sb-cloned8d5fe79e99e4a9ab916e72948ddf02a!b19391|destination-xsappname!b404";
	public static final String DESTINATION_CLIENT_SECRET = "d73b7ead-fe5a-4be8-99f8-5969fcd51667$PnfrG3sJJOhR1QSQZdk0z5jaw-LNCCCCHFH9gwlq2AY=";
	public static final String DESTINATION_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String DESTINATION_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com/";
	public static final String BASE_URL_RETURN = "http://incturedec:8080/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/";
	public static final String OAUTH_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String WORKFLOW_CLIENT_ID = "sb-clone-b487dd67-d351-4d24-9c7b-94e20c7bc35b!b19391|workflow!b10150";
	public static final String WORKFLOW_CLIENT_SECRETE="0ed5a596-c2ee-44fc-aa2b-3ec56926c48c$JMRj5a6zHi8Hr2D051YjXMVCECnDL8lnhrbKWWut8N8=";
	public static final String WORKFLOW_REST_BASE_URL = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	
	// connectivity service key
	// connectivity service key
	public static final String CONECTIVITY_CLIENT_ID = "sb-clone90edf2c53da04e99a2d67a015e6be8e4!b19391|connectivity!b17";
	public static final String CONECTIVITY_CLIENT_SECRET = "a968544e-320b-49a9-8eb2-94650ada01b1$fMX-Z4IlnJJOXMs0tIkELupXeQQXexClEM011ukXT8E=";
	public static final String CONECTIVITY_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String CONECTIVITY_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com";

}
