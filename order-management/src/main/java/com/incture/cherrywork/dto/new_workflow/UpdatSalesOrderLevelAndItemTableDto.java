package com.incture.cherrywork.dto.new_workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class UpdatSalesOrderLevelAndItemTableDto {
	
	
	private String decisionSetId;
	private String level;
	private String taskid;
	private String taskSerialId;
	public String getDecisionSetId() {
		return decisionSetId;
	}
	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskSerialId() {
		return taskSerialId;
	}
	public void setTaskSerialId(String taskSerialId) {
		this.taskSerialId = taskSerialId;
	}
	@Override
	public String toString() {
		return "UpdatSalesOrderLevelAndItemTableDto [decisionSetId=" + decisionSetId + ", level=" + level + ", taskid="
				+ taskid + ", taskSerialId=" + taskSerialId + "]";
	}
	
	

}