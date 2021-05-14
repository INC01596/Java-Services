package com.incture.cherrywork.dtos;

import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public @Data class SalesOrderHeaderInput extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String requestId;
	private String salesOrderHeaderId;
	private List<String> salesOrderItemIdList;
	private String strategy;
	private String distributionChannel;
	private String headerBlocReas;
	private String salesOrg;
	private String soCreatedECC;
	private String customerPo;
	private String requestCategory;
	private String requestType;
	private String country;
	private String salesOrderType;
	private String soldToParty;
	private String shipToParty;
	private String division;
	private String returnReason;
	

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSalesOrderHeaderId() {
		return salesOrderHeaderId;
	}

	public void setSalesOrderHeaderId(String salesOrderHeaderId) {
		this.salesOrderHeaderId = salesOrderHeaderId;
	}

	public List<String> getSalesOrderItemIdList() {
		return salesOrderItemIdList;
	}

	public void setSalesOrderItemIdList(List<String> salesOrderItemIdList) {
		this.salesOrderItemIdList = salesOrderItemIdList;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getHeaderBlocReas() {
		return headerBlocReas;
	}

	public void setHeaderBlocReas(String headerBlocReas) {
		this.headerBlocReas = headerBlocReas;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getSoCreatedECC() {
		return soCreatedECC;
	}

	public void setSoCreatedECC(String soCreatedECC) {
		this.soCreatedECC = soCreatedECC;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getRequestCategory() {
		return requestCategory;
	}

	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSalesOrderType() {
		return salesOrderType;
	}

	public void setSalesOrderType(String salesOrderType) {
		this.salesOrderType = salesOrderType;
	}

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

