package com.incture.cherrywork.dto.new_workflow;


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
public @Data class SalesOrderItemStatusDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String itemStatusSerialId;

	private String taskStatusSerialId;

	private String salesOrderItemNum;

	private Integer itemStatus;

	private Integer visiblity;

	public String getItemStatusSerialId() {
		return itemStatusSerialId;
	}

	public void setItemStatusSerialId(String itemStatusSerialId) {
		this.itemStatusSerialId = itemStatusSerialId;
	}

	public String getTaskStatusSerialId() {
		return taskStatusSerialId;
	}

	public void setTaskStatusSerialId(String taskStatusSerialId) {
		this.taskStatusSerialId = taskStatusSerialId;
	}

	public String getSalesOrderItemNum() {
		return salesOrderItemNum;
	}

	public void setSalesOrderItemNum(String salesOrderItemNum) {
		this.salesOrderItemNum = salesOrderItemNum;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getVisiblity() {
		return visiblity;
	}

	public void setVisiblity(Integer visiblity) {
		this.visiblity = visiblity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();

	}

	@Override
	public String toString() {
		return "SalesOrderItemStatusDto [itemStatusSerialId=" + itemStatusSerialId + ", taskStatusSerialId="
				+ taskStatusSerialId + ", salesOrderItemNum=" + salesOrderItemNum + ", itemStatus=" + itemStatus
				+ ", visiblity=" + visiblity + "]";
	}

}

