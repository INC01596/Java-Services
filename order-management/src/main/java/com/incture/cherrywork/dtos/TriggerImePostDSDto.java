package com.incture.cherrywork.dtos;

import lombok.Data;

public @Data class TriggerImePostDSDto {
	String salesOrderNo;
	String dataSet;
	
	
	
	
	public TriggerImePostDSDto(String salesOrderNo, String dataSet) {
		super();
		this.salesOrderNo = salesOrderNo;
		this.dataSet = dataSet;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getDataSet() {
		return dataSet;
	}
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	
}

