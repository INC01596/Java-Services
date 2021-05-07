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

}
