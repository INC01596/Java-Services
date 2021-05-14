package com.incture.cherrywork.dto.new_workflow;



import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderValidatorDto extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String salesOrderNum;

	private String decisionSetId;

	private String sapTaskId;

	private String levelNum;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public String getDecisionSetId() {
		return decisionSetId;
	}

	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}

	public String getSapTaskId() {
		return sapTaskId;
	}

	public void setSapTaskId(String sapTaskId) {
		this.sapTaskId = sapTaskId;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
