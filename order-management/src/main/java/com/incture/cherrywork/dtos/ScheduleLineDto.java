package com.incture.cherrywork.dtos;



import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


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

	public ScheduleLineDto() {
		super();
	}
	public ScheduleLineDto(String salesItemOrderNo, String salesHeaderNo, String scheduleLineNum,
			String schlineDeliveryBlock) {
		super();
		this.salesItemOrderNo = salesItemOrderNo;
		this.salesHeaderNo = salesHeaderNo;
		this.scheduleLineNum = scheduleLineNum;
		this.schlineDeliveryBlock = schlineDeliveryBlock;
	}

	public String getSalesItemOrderNo() {
		return salesItemOrderNo;
	}

	public void setSalesItemOrderNo(String salesItemOrderNo) {
		this.salesItemOrderNo = salesItemOrderNo;
	}

	public String getSalesHeaderNo() {
		return salesHeaderNo;
	}

	public void setSalesHeaderNo(String salesHeaderNo) {
		this.salesHeaderNo = salesHeaderNo;
	}

	public String getScheduleLineNum() {
		return scheduleLineNum;
	}

	public void setScheduleLineNum(String scheduleLineNum) {
		this.scheduleLineNum = scheduleLineNum;
	}

	public Double getReqQtySales() {
		return reqQtySales;
	}

	public void setReqQtySales(Double reqQtySales) {
		this.reqQtySales = reqQtySales;
	}

	public Double getConfQtySales() {
		return confQtySales;
	}

	public void setConfQtySales(Double confQtySales) {
		this.confQtySales = confQtySales;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public Double getReqQtyBase() {
		return reqQtyBase;
	}

	public void setReqQtyBase(Double reqQtyBase) {
		this.reqQtyBase = reqQtyBase;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public String getSchlineDeliveryBlock() {
		return schlineDeliveryBlock;
	}

	public void setSchlineDeliveryBlock(String schlineDeliveryBlock) {
		this.schlineDeliveryBlock = schlineDeliveryBlock;
	}

	public String getRelfordelText() {
		return relfordelText;
	}

	public void setRelfordelText(String relfordelText) {
		this.relfordelText = relfordelText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
