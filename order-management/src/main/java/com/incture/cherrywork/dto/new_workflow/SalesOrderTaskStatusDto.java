package com.incture.cherrywork.dto.new_workflow;

import java.util.List;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderTaskStatusDto extends BaseDto {

	public SalesOrderTaskStatusDto() {
		super();
		
	}

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	public SalesOrderTaskStatusDto(String taskStatusSerialId, String levelStatusSerialId, String taskId,
			Integer taskStatus, String approver, String completedBy, SalesOrderLevelStatusDo salesOrderLevelStatus,
			List<SalesOrderItemStatusDto> itemStatusList) {
		super();
		this.taskStatusSerialId = taskStatusSerialId;
		this.levelStatusSerialId = levelStatusSerialId;
		this.taskId = taskId;
		this.taskStatus = taskStatus;
		this.approver = approver;
		this.completedBy = completedBy;
		this.salesOrderLevelStatus = salesOrderLevelStatus;
		this.itemStatusList = itemStatusList;
	}

	private String taskStatusSerialId;

	private String levelStatusSerialId;

	private String taskId;

	private Integer taskStatus;

	private String approver;

	private String completedBy;

	private SalesOrderLevelStatusDo salesOrderLevelStatus;

	private List<SalesOrderItemStatusDto> itemStatusList;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();

	}

	public String getTaskStatusSerialId() {
		return taskStatusSerialId;
	}

	public void setTaskStatusSerialId(String taskStatusSerialId) {
		this.taskStatusSerialId = taskStatusSerialId;
	}

	public String getLevelStatusSerialId() {
		return levelStatusSerialId;
	}

	public void setLevelStatusSerialId(String levelStatusSerialId) {
		this.levelStatusSerialId = levelStatusSerialId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCompletedBy() {
		return completedBy;
	}

	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}

	public SalesOrderLevelStatusDo getSalesOrderLevelStatus() {
		return salesOrderLevelStatus;
	}

	public void setSalesOrderLevelStatus(SalesOrderLevelStatusDo salesOrderLevelStatus) {
		this.salesOrderLevelStatus = salesOrderLevelStatus;
	}

	public List<SalesOrderItemStatusDto> getItemStatusList() {
		return itemStatusList;
	}

	public void setItemStatusList(List<SalesOrderItemStatusDto> itemStatusList) {
		this.itemStatusList = itemStatusList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
