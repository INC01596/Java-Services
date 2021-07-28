package com.incture.cherrywork.dto.new_workflow;

import java.util.List;

import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class SalesOrderTaskStatusDto {

	private String taskStatusSerialId;
	private String taskId;
	private Integer taskStatus;
	private String approver;
	private String completedBy;
	private String levelStatusSerialId;
	private List<SalesOrderItemStatusDto> itemStatusList;
	private SalesOrderLevelStatusDo salesOrderLevelStatus;

	public SalesOrderTaskStatusDto(String task_status_serial_id, String approver, String task_id, Integer task_Status,
			String level_status_serial_id, String completed_by) {
		super();
		this.taskStatusSerialId = task_status_serial_id;
		this.taskId = task_id;
		this.taskStatus = task_Status;
		this.approver = approver;
		this.levelStatusSerialId = level_status_serial_id;
		this.completedBy = completed_by;
	}
	

}
