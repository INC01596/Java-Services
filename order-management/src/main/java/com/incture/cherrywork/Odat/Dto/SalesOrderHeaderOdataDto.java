package com.incture.cherrywork.Odat.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderHeaderOdataDto {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	private String docNumber;

	private String dlvBlock;
	
	private String purchNo;

	private List<SalesOrderItemOdataDto> orderToItems;

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getDlvBlock() {
		return dlvBlock;
	}

	public void setDlvBlock(String dlvBlock) {
		this.dlvBlock = dlvBlock;
	}

	public String getPurchNo() {
		return purchNo;
	}

	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}

	public List<SalesOrderItemOdataDto> getOrderToItems() {
		return orderToItems;
	}

	public void setOrderToItems(List<SalesOrderItemOdataDto> orderToItems) {
		this.orderToItems = orderToItems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
