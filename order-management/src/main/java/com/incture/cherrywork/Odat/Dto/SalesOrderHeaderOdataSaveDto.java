package com.incture.cherrywork.Odat.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data

@EqualsAndHashCode(callSuper = false)
public class SalesOrderHeaderOdataSaveDto {
	private String docNumber;
	
	private String purchNo;

	private List<SalesOrderItemOdataSaveDto> orderToItems;

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getPurchNo() {
		return purchNo;
	}

	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}

	public List<SalesOrderItemOdataSaveDto> getOrderToItems() {
		return orderToItems;
	}

	public void setOrderToItems(List<SalesOrderItemOdataSaveDto> orderToItems) {
		this.orderToItems = orderToItems;
	}
}
