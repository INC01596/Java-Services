package com.incture.cherrywork.dtos;



import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class SalesOrderHeaderItemDto {

	private SalesOrderHeaderDto headerDto;
	private List<SalesOrderItemDto> lineItemList;
	
//  Awadhesh Kumar
	
	
	private String paymentTerms;
	private String incoTerms1;
	private String incoTerms2;
	private String weight;

	private String netValueSA;
	private BigDecimal totalSalesOrderQuantitySA;
	private BigDecimal totalSalesOrderQuantity;
	private String overDeliveryTolerance;
	private String underDeliveryTolerance;
	
	private String salesHeaderId;

	public String getSalesHeaderId() {
		return salesHeaderId;
	}

	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}

	
	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	

	public String getIncoTerms1() {
		return incoTerms1;
	}

	public void setIncoTerms1(String incoTerms1) {
		this.incoTerms1 = incoTerms1;
	}
	
	

	public String getIncoTerms2() {
		return incoTerms2;
	}

	public void setIncoTerms2(String incoTerms2) {
		this.incoTerms2 = incoTerms2;
	}
	
	

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	
	

	public String getNetValueSA() {
		return netValueSA;
	}

	public void setNetValueSA(String netValueSA) {
		this.netValueSA = netValueSA;
	}
	
	

	public BigDecimal getTotalSalesOrderQuantitySA() {
		return totalSalesOrderQuantitySA;
	}

	public void setTotalSalesOrderQuantitySA(BigDecimal totalSalesOrderQuantitySA) {
		this.totalSalesOrderQuantitySA = totalSalesOrderQuantitySA;
	}
	
	

	public BigDecimal getTotalSalesOrderQuantity() {
		return totalSalesOrderQuantity;
	}

	public void setTotalSalesOrderQuantity(BigDecimal totalSalesOrderQuantity) {
		this.totalSalesOrderQuantity = totalSalesOrderQuantity;
	}

	

	public String getOverDeliveryTolerance() {
		return overDeliveryTolerance;
	}

	public void setOverDeliveryTolerance(String overDeliveryTolerance) {
		this.overDeliveryTolerance = overDeliveryTolerance;
	}
	
	

	public String getUnderDeliveryTolerance() {
		return underDeliveryTolerance;
	}

	public void setUnderDeliveryTolerance(String underDeliveryTolerance) {
		this.underDeliveryTolerance = underDeliveryTolerance;
	}
	

	
	
	public SalesOrderHeaderDto getHeaderDto() {
		return headerDto;
	}
	public void setHeaderDto(SalesOrderHeaderDto headerDto) {
		this.headerDto = headerDto;
	}
	public List<SalesOrderItemDto> getLineItemList() {
		return lineItemList;
	}
	public void setLineItemList(List<SalesOrderItemDto> lineItemList) {
		this.lineItemList = lineItemList;
	}
	@Override
	public String toString() {
		return "SalesHeaderItemDto [headerDto=" + headerDto + ", lineItemList=" + lineItemList + "]";
	}
}
