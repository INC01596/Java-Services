package com.incture.cherrywork.WConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.incture.cherrywork.exceptions.ExecutionFault;



public final class DkshConstants {

	private static final Logger logger = LoggerFactory.getLogger(DkshConstants.class);

	private DkshConstants() {
		throw new IllegalStateException(getClass().getSimpleName());
	}

	// Destination Service Key
	public static final String DESTINATION_CLIENT_ID = "sb-cloned8d5fe79e99e4a9ab916e72948ddf02a!b19391|destination-xsappname!b404";
	public static final String DESTINATION_CLIENT_SECRET = "887019a9-7545-45b0-ad73-54edfecdf2ea$EPvv_KflmeKJ0Dsn4e6bOnHeTCuegTUfuAPLmUb1CaU=";
	public static final String DESTINATION_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String DESTINATION_BASE_URL = "https://destination-configuration.cfapps.eu10.hana.ondemand.com/";
	public static final String OAUTH_TOKEN_URL = "https://hrapps.authentication.eu10.hana.ondemand.com/oauth/token?grant_type=client_credentials";
	public static final String WORKFLOW_CLIENT_ID = "sb-clone-b487dd67-d351-4d24-9c7b-94e20c7bc35b!b19391|workflow!b10150";
	public static final String WORKFLOW_CLIENT_SECRETE="0ed5a596-c2ee-44fc-aa2b-3ec56926c48c$JMRj5a6zHi8Hr2D051YjXMVCECnDL8lnhrbKWWut8N8=";
	public static final String WORKFLOW_REST_BASE_URL = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	// connectivity service key 
		public static final String CONECTIVITY_CLIENT_ID = "sb-clone90edf2c53da04e99a2d67a015e6be8e4!b19391|connectivity!b17";
		public static final String CONECTIVITY_CLIENT_SECRET = "40afe54d-9895-4748-af49-d81bcfed3f66$xCzyLr3BY-zcKBXilEgj3zpeYEEZWXOr8fU44LdrKeM=";
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

	// Number of spaces from the starting for JSON
	public static final int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static final String OAUTH_TOKEN_GENERATION_FROM_CLIENT = "GetBearerTokenForOauth";

	public static final String BLOCKED_SALES_ORDER = "BS";

	public static final String RETURN_ORDER = "CR";

	public static final String RETUN_ORDER_EXCHANGE = "XC";

	public static final String RETURN_EXCHANGE = "XC";

	public static final void errorMsg(Exception e) throws ExecutionFault {
		logger.info(e + " on " + e.getStackTrace()[1]);
		throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
	}
}
