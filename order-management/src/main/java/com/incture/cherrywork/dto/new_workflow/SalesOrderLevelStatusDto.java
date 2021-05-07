package com.incture.cherrywork.dto.new_workflow;

import java.util.List;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderLevelStatusDto extends BaseDto {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String levelStatusSerialId;

	private String decisionSetId;

	private String level;

	private String userGroup;

	private String approverType;

	private Integer levelStatus;
	

	public String getLevelStatusSerialId() {
		return levelStatusSerialId;
	}

	public void setLevelStatusSerialId(String levelStatusSerialId) {
		this.levelStatusSerialId = levelStatusSerialId;
	}

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

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public Integer getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(Integer levelStatus) {
		this.levelStatus = levelStatus;
	}

	public List<SalesOrderTaskStatusDto> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<SalesOrderTaskStatusDto> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<SalesOrderTaskStatusDto> taskStatusList;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();

	}

}

