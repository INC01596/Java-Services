package com.incture.cherrywork.util;

import java.util.HashMap;
import java.util.Map;

public final class ComConstants {

	private ComConstants() {
		throw new IllegalStateException(getClass().getSimpleName());
	}

	// Destination Service Key
	public static final String DESTINATION_CLIENT_ID = "sb-cloned95c06f6b3f14ed1866b76fbd8a0cf06!b19391|destination-xsappname!b404";
	public static final String DESTINATION_CLIENT_SECRET = "450f9cb3-ac10-4c4e-b8a9-775eb499e769$sGSJwCELf4rKcujpPmdLBs8uJDoRLmFne5DH6oBUxdo=";
	public static final String DESTINATION_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String DESTINATION_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com";
	public static final String OAUTH_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String WORKFLOW_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String WORKFLOW_CLIENT_ID = "sb-clone-b487dd67-d351-4d24-9c7b-94e20c7bc35b!b19391|workflow!b10150";
	public static final String WORKFLOW_CLIENT_SECRETE = "0ed5a596-c2ee-44fc-aa2b-3ec56926c48c$JMRj5a6zHi8Hr2D051YjXMVCECnDL8lnhrbKWWut8N8=";
	public static final String WORKFLOW_REST_BASE_URL = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	// connectivity service key
	public static final String CONECTIVITY_CLIENT_ID = "sb-clone85f1c7c75ce74ba2872efaa3d990f4a0!b19391|connectivity!b17";
	public static final String CONECTIVITY_CLIENT_SECRET = "6e5f4771-ad79-4a7e-abc0-c34881d31d3a$v-Kz2lboLXLpqiuSh8qACE8N_zidEMwJSvuTChENebo=";
	public static final String CONECTIVITY_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String CONECTIVITY_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com";

	public static final String CREATION_FAILED = "Creation failed";

	public static final String EXCEPTION_FAILED = "Failed Due to : Exception Occur on ";

	public static final String DATA_FOUND = "Data found";

	public static final String DATA_NOT_FOUND = "Data not found";

	public static final String EMPTY_LIST = "Empty list, Please enter some entries";

	public static final String INVALID_INPUT = "Invalid Input";

	public static final String WORKFLOW_USERNAME = "P2000982477";

	public static final String WORKFLOW_PASSWORD = "3Pg13ec022";

	public static final String TASK_COMPLETED = "COMPLETED";

	public static final String WORKFLOW_CLOSE_TASK_DESTINATION = "DKSHWorkflowInstanceToCloseTask";

	public static final String WORKFLOW_X_CSRF_TOKEN_GEN_URL = "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/xsrf-token";

	public static final String ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME = "COM_OdataServices";
	public static final String COM_ODATA_DESTINATION_NAME = "COM_OdataServices";
	public static final String USERS_FROM_GROUP_IN_IDP_DESTINATION_NAME = "idpServices";
	public static final String SUCCESSFULLY_UPDATED_IN_ECC = "Successfully Updated in ECC";

	public static final String WORKFLOW_TRIGGER = "DKSHWorkflowInstance";
	public static final String DEFAULT = "Default";
	public static final String TOKEN_GENERATION_FAILED = "Token Failed";
	public static final String TRIGGER_FAILED = "Trigger Failed";

	public static final String TRIGGER_SUCCESS = "Further Process Initiated";

	public static final String LEVEL_COMPLETE_FOR_ITEM = "Item Block Approval Process Completed";

	public static final String WORKFLOW_TRIGGER_ID = "workflowTrigger";

	public static final String PENDING_DS_LEVELS = "Pending Decision Sets or Pending Levels";

	public static final String SALES_HEADER_ITEM_ID_MANDATORY = "Sales header id and Sales item id fields are mandatory";

	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT = "/sap/opu/odata/sap/ZCOM_SALESORDER_DATA_SRV/$batch";
	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT_TAG = "soheaderSet";
	public static final String RETURN_REQUEST_BATCH_TAG = "orderHeaderSet";

	// Number of spaces from the starting for JSON
	public static final int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static final String OAUTH_TOKEN_GENERATION_FROM_CLIENT = "GetBearerTokenForOauth";

	public static final String BLOCKED_SALES_ORDER = "BS";

	public static final String RETURN_ORDER = "CR";

	public static final String RETUN_ORDER_EXCHANGE = "XC";

	public static final String RETURN_EXCHANGE = "XC";

	public static final Map<Integer, String> MAP_TO_PRINT_ITEM_STATUS = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -6703344640406100704L;
		{
			put(22, "Pending Approval");
			put(23, "Pending Approval by previous level");
			put(24, "Approved");
			put(25, "Rejected");
			put(27, "Rejected by Previous Level");
			put(32, "Display Only");
			put(70, "Rejected from ECC");
		}
	};

	public static final Integer LEVEL_NEW = 1;
	public static final Integer LEVEL_READY = 2;
	public static final Integer LEVEL_IN_PROGRESS = 3;
	public static final Integer LEVEL_COMPLETE = 4;
	public static final Integer LEVEL_ABANDON = 17;
	public static final Integer TASK_NEW = 5;
	public static final Integer TASK_READY = 6;
	public static final Integer TASK_IN_PROGRESS = 7;
	public static final Integer TASK_COMPLETE = 8;
	public static final Integer BLOCKED = 9;
	public static final Integer ITEM_APPROVE = 10;
	public static final Integer ITEM_REJECT = 11;
	public static final Integer ITEM_INDIRECT_REJECT = 12;
	public static final Integer VISIBLITY_ACTIVE = 13;
	public static final Integer VISIBLITY_INACTIVE = 14;
	public static final Integer VISIBLITY_INACTIVE_INDIRECT_REJECT = 15;
	public static final Integer DISPLAY_ONLY_ITEM = 19;
	public static final Integer REJECTED_FROM_ECC = 35;

}