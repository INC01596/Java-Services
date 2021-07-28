package com.incture.cherrywork.dto.new_workflow;

import java.util.List;

import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SalesOrderLevelStatusDto {

	private String levelStatusSerialId;
	private String decisionSetId;
	private String level;
	private String userGroup;
	private String approverType;
	private Integer levelStatus;
	private List<SalesOrderTaskStatusDto> taskStatusList;

	public SalesOrderLevelStatusDto(String level_status_serial_id, String approver_type, String decision_set_id,
			String level, Integer level_status, String user_group) {
		super();
		this.levelStatusSerialId = level_status_serial_id;
		this.decisionSetId = decision_set_id;
		this.level = level;
		this.userGroup = user_group;
		this.approverType = approver_type;
		this.levelStatus = level_status;
	}

}
