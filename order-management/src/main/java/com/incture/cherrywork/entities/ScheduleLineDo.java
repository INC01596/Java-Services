package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCHEDULE_LINE")
public @Data class ScheduleLineDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private ScheduleLinePrimaryKeyDo scheduleLineKey;

	@Column(precision = 3, name = "REQ_QTY_SALES")
	private Double reqQtySales;

	@Column(precision = 3, name = "CONF_QTY_SALES")
	private Double confQtySales;

	@Column(name = "SALES_UNIT", length = 3)
	private String salesUnit;

	@Column(precision = 3, name = "REQ_QTY_BASE")
	private Double reqQtyBase;

	@Column(name = "BASE_UNIT", length = 3)
	private String baseUnit;

	@Column(name = "SCHLINE_DELIVERY_BLOCK", length = 3)
	private String schlineDeliveryBlock;
	
	@Column(name = "RELFORDEL_TEXT", length =100)
	private String relfordelText;

	@Override
	public Object getPrimaryKey() {
		return scheduleLineKey;
	}

	public ScheduleLinePrimaryKeyDo getScheduleLineKey() {
		return scheduleLineKey;
	}

	public void setScheduleLineKey(ScheduleLinePrimaryKeyDo scheduleLineKey) {
		this.scheduleLineKey = scheduleLineKey;
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

