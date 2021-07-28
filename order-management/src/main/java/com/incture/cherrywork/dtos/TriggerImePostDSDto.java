package com.incture.cherrywork.dtos;

import lombok.Data;

@Data
public class TriggerImePostDSDto {
	String salesOrderNo;
	String dataSet;
	public TriggerImePostDSDto(String salesOrderNo, String dataSet) {
		super();
		this.salesOrderNo = salesOrderNo;
		this.dataSet = dataSet;
	}
	public TriggerImePostDSDto() {
		super();
	}
	
}

