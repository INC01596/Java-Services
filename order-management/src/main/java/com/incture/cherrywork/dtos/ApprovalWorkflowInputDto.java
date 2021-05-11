package com.incture.cherrywork.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class ApprovalWorkflowInputDto {

	
	
	private String headerBlocReas;
	private String soCreatedECC;

	private String salesOrderNo;
	private String requestId;
	private String approver;
	private String level;
	private String dataSet;
	private String approvalType;
	private String threshold;
	private String decisionSetAmount;
	private String country;
	private String customerPo;
	private String requestType;
	private String requestCategory;
	private String salesOrderType;
	private String soldToParty;
	private String shipToParty;
	 private String division;
	   private String distributionChannel;
	  
	   private String salesOrg;
	   private String returnReason;
	public String getHeaderBlocReas() {
		return headerBlocReas;
	}
	public void setHeaderBlocReas(String headerBlocReas) {
		this.headerBlocReas = headerBlocReas;
	}
	public String getSoCreatedECC() {
		return soCreatedECC;
	}
	public void setSoCreatedECC(String soCreatedECC) {
		this.soCreatedECC = soCreatedECC;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDataSet() {
		return dataSet;
	}
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getDecisionSetAmount() {
		return decisionSetAmount;
	}
	public void setDecisionSetAmount(String decisionSetAmount) {
		this.decisionSetAmount = decisionSetAmount;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestCategory() {
		return requestCategory;
	}
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
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
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	   
}
