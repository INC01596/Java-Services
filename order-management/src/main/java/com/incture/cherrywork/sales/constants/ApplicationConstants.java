package com.incture.cherrywork.sales.constants;



public class ApplicationConstants {

	public static final String IDP_SERVICES_DESTINATION_NAME = "IDPService";

	// Destination Service Key
	public static final String DESTINATION_CLIENT_ID = "sb-cloned8d5fe79e99e4a9ab916e72948ddf02a!b19391|destination-xsappname!b404";
	public static final String DESTINATION_CLIENT_SECRET = "887019a9-7545-45b0-ad73-54edfecdf2ea$EPvv_KflmeKJ0Dsn4e6bOnHeTCuegTUfuAPLmUb1CaU=";
	public static final String DESTINATION_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String DESTINATION_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com";
	public static final String WORKFLOW_CLIENT_ID = "sb-clone-b487dd67-d351-4d24-9c7b-94e20c7bc35b!b19391|workflow!b10150";
	public static final String WORKFLOW_CLIENT_SECRETE="0ed5a596-c2ee-44fc-aa2b-3ec56926c48c$JMRj5a6zHi8Hr2D051YjXMVCECnDL8lnhrbKWWut8N8=";
	public static final String WORKFLOW_REST_BASE_URL = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	public static final String WORKFLOW_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
    
	// connectivity service key 
	public static final String CONECTIVITY_CLIENT_ID = "sb-clone90edf2c53da04e99a2d67a015e6be8e4!b19391|connectivity!b17";
	public static final String CONECTIVITY_CLIENT_SECRET = "40afe54d-9895-4748-af49-d81bcfed3f66$xCzyLr3BY-zcKBXilEgj3zpeYEEZWXOr8fU44LdrKeM=";
	public static final String CONECTIVITY_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String CONECTIVITY_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com";
    
	//
	
	// Sequences Prefix
	public static final String EXCHANGE_SEQ_PREFIX = "CE";
	public static final String RETURN_WITH_EXCHANGE_SEQ_PREFIX = "XCE";
	public static final String RETURN_SEQ_PREFIX = "CR";
	public static final String RETURN_EXCHANGE_PREFIX = "XC";
	public static final String RETURN_SEQ_EXCHANGE_PREFIX = "XCR";

	public static final String DKSH_ODATA_DESTINATION_NAME = "COM_OdataServices";
	public static final String WORKFLOW_TRIGGER = "DKSHWorkflowInstance";

	public static final String WORKFLOW_TRIGGER_ID = "workflowTrigger";
	public static final String TASK_COMPLETED = "COMPLETED";
	public static final String WORKFLOW_CLOSE_TASK_DESTINATION = "DKSHWorkflowInstanceToCloseTask";
	public static final String SHAREPOINTURL = "SharePointUrl";
	// Return Request Order and Exchange Request Order
	public static final String RETURN_REQUEST_BATCH_ENDPOINTS = "/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/$batch";
	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT = "/sap/opu/odata/sap/ZCC_SALESORDER_DATA_SRV/$batch";
	public static final String RETURN_REQUEST_BATCH_TAG = "orderHeaderSet";
	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT_TAG = "soheaderSet";

	// Email constants
	public static final String RETURN_REQUEST_MAIL_SUBJECT = "Return Request Update";
	public static final String RETURN_HEADER_SUBJECT = "Return Request Update";
	public static final String RETURN_REQUEST_INIT_MSG = "Your Return request is initiated.";

	// IAS USERS LIST constants
	public static final String IAS_USERS_LIST_EXCEL_SHEET_NAME = "IAS_USERS_DATA";
	public static final String IAS_USERS_LIST_FILE_NAME = "iasUserDataWithRights";
	public static final Integer IAS_USERS_LIST_START_INDEX = 1;
	public static final Integer IAS_USERS_LIST_COUNT = 100;

	// SMS sending constants
	public static final String SMS_SENDING_DESTINATION_NAME = "sms_sending_ais_vendor";
	public static final String SENDER_GROUP_NAME_KEY = "FROM";
	public static final String RECIPIENT_MOBILE_NUM_KEY = "TO";
	public static final String REPORT_KEY = "REPORT";
	public static final String MESSAGE_TYPE_KEY = "CTYPE";
	public static final String MESSAGE_KEY = "CONTENT";
	public static final String CHARGE_KEY = "CHARGE";
	public static final String CMD_KEY = "CMD";
	public static final String CODE_KEY = "CODE";

}
