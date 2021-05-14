package com.incture.cherrywork.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
public class RequestMasterInsertDto {
	
	private String salesOrderNo;
	
	private boolean noItemBlock;

	public String getSalesOrderNo() {
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}

	public boolean isNoItemBlock() {
		return noItemBlock;
	}

	public void setNoItemBlock(boolean noItemBlock) {
		this.noItemBlock = noItemBlock;
	}

	public RequestMasterInsertDto(String salesOrderNo, boolean noItemBlock) {
		super();
		this.salesOrderNo = salesOrderNo;
		this.noItemBlock = noItemBlock;
	}

	public RequestMasterInsertDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}

