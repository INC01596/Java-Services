package com.incture.cherrywork.dto.workflow;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class ItemStatusTrackingLevelMsgDto {

	private String itemNum;
	private String decisionSetId;
	private String level;
	private String levelStatusCode;
	private String levelStatusText;

	public ItemStatusTrackingLevelMsgDto(String so_item_num, String decision_set_id, String level,
			String level_status) {
		super();
		this.itemNum = so_item_num;
		this.decisionSetId = decision_set_id;
		this.level = level;
		this.levelStatusCode = level_status;
	}

}
