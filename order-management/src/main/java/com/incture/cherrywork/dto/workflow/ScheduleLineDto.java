package com.incture.cherrywork.dto.workflow;



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
public @Data class ScheduleLineDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String salesItemOrderNo;

	private String salesHeaderNo;

	private String scheduleLineNum;

	private Double reqQtySales;

	private Double confQtySales;

	private String salesUnit;

	private Double reqQtyBase;

	private String baseUnit;

	private String schlineDeliveryBlock;
	
	private String relfordelText;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

	public ScheduleLineDto(String salesItemOrderNo, String salesHeaderNo, String scheduleLineNum,
			String schlineDeliveryBlock) {
		super();
		this.salesItemOrderNo = salesItemOrderNo;
		this.salesHeaderNo = salesHeaderNo;
		this.scheduleLineNum = scheduleLineNum;
		this.schlineDeliveryBlock = schlineDeliveryBlock;
	}

}
