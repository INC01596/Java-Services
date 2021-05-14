package com.incture.cherrywork.WConstants;

import java.util.HashMap;
import java.util.Map;

public final class DkshStatusConstants {

	public static final Integer LEVEL_NEW = 1;
	public static final Integer LEVEL_READY = 2;
	public static final Integer LEVEL_IN_PROGRESS = 3;
	public static final Integer LEVEL_COMPLETE = 4;
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

	// For thresold breach condition
	public static final Integer LEVEL_BREACH = 16;

	// Level abandon
	public static final Integer LEVEL_ABANDON = 17;
	public static final Integer REQUEST_COMPLETE = 18;
	public static final Integer DISPLAY_ONLY_ITEM = 19;
	public static final Integer EDIT_BY_SPECIAL_CLIENT_ITEM = 20;
	public static final Integer SO_PROCESS_COMPLETE = 21;
	public static final Integer REQUEST_COMPLETE_NO_ITEM_BLOCK = 22;
	public static final Integer SO_PROCESS_COMPLETE_NO_ITEM_BLOCK = 23;
	public static final Integer REQUEST_NEW = 33;
	public static final Integer REQUEST_IN_PROGRESS = 34;
	public static final Integer REJECTED_FROM_ECC = 35;
	public static final String DKSH_ODATA_DESTINATION_NAME = "DKSHODataService";
	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT = "/sap/opu/odata/sap/ZCC_SALESORDER_DATA_SRV/$batch";
	public static final String RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT_TAG = "soheaderSet";
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

}
