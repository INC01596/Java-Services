package com.incture.cherrywork.dtos;

import java.util.List;

import javax.persistence.Column;

import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.ExchangeItem;

import lombok.Data;

@Data
public class ExchangeOrder {

	private String exchangeReqNum;
	private String roType;
	private String payer;
	private String referenceNum;
	private String reasonOwner;
	private String requestRemark;
	private String billToParty;
	private String returnReqNum;
	private String orderCategory;
	private String orderType;
	private String salesOrg;
	private String distributionChannel;
	private String division;
	private String soldToParty;
	private String soldToPartyDesc;
	private String shipToParty;
	private String shipToPartyDesc;
	private String remarks;
	private Double totalNetAmount;
	private String delComplete;
	private String docCurrency;
	private String docVersion;
	private String deliveryBlock;
	private String billingBlock;
	private String overallStatus;
	private String rejectionStatus;
	private String deliveryStatus;
	private String creditStatus;
	private String overallWorkflowStatus;
	private String ordReason;
	private String createdBy;
	private String flagRoSo;
    private String totalExchangeAmount;
    private String orderTypeText;
    private String documentUrl;
    private String message;
    private String customerPo;
    private String requestorName;
	private List<ExchangeItem> items;
	private List<OrderConditionDto> orderCondition;
	private List<Address> address;
	private List<OrderHdrToOrderPartnerDto> orderHdrToOrderPartnerDto;
	public String getExchangeReqNum() {
		return exchangeReqNum;
	}
	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
	}
	public String getRoType() {
		return roType;
	}
	public void setRoType(String roType) {
		this.roType = roType;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getReferenceNum() {
		return referenceNum;
	}
	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}
	public String getReasonOwner() {
		return reasonOwner;
	}
	public void setReasonOwner(String reasonOwner) {
		this.reasonOwner = reasonOwner;
	}
	public String getRequestRemark() {
		return requestRemark;
	}
	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
	}
	public String getBillToParty() {
		return billToParty;
	}
	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}
	public String getReturnReqNum() {
		return returnReqNum;
	}
	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}
	public String getOrderCategory() {
		return orderCategory;
	}
	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getSoldToParty() {
		return soldToParty;
	}
	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}
	public String getSoldToPartyDesc() {
		return soldToPartyDesc;
	}
	public void setSoldToPartyDesc(String soldToPartyDesc) {
		this.soldToPartyDesc = soldToPartyDesc;
	}
	public String getShipToParty() {
		return shipToParty;
	}
	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}
	public String getShipToPartyDesc() {
		return shipToPartyDesc;
	}
	public void setShipToPartyDesc(String shipToPartyDesc) {
		this.shipToPartyDesc = shipToPartyDesc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getTotalNetAmount() {
		return totalNetAmount;
	}
	public void setTotalNetAmount(Double totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}
	public String getDelComplete() {
		return delComplete;
	}
	public void setDelComplete(String delComplete) {
		this.delComplete = delComplete;
	}
	public String getDocCurrency() {
		return docCurrency;
	}
	public void setDocCurrency(String docCurrency) {
		this.docCurrency = docCurrency;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getDeliveryBlock() {
		return deliveryBlock;
	}
	public void setDeliveryBlock(String deliveryBlock) {
		this.deliveryBlock = deliveryBlock;
	}
	public String getBillingBlock() {
		return billingBlock;
	}
	public void setBillingBlock(String billingBlock) {
		this.billingBlock = billingBlock;
	}
	public String getOverallStatus() {
		return overallStatus;
	}
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}
	public String getRejectionStatus() {
		return rejectionStatus;
	}
	public void setRejectionStatus(String rejectionStatus) {
		this.rejectionStatus = rejectionStatus;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getOverallWorkflowStatus() {
		return overallWorkflowStatus;
	}
	public void setOverallWorkflowStatus(String overallWorkflowStatus) {
		this.overallWorkflowStatus = overallWorkflowStatus;
	}
	public String getOrdReason() {
		return ordReason;
	}
	public void setOrdReason(String ordReason) {
		this.ordReason = ordReason;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getFlagRoSo() {
		return flagRoSo;
	}
	public void setFlagRoSo(String flagRoSo) {
		this.flagRoSo = flagRoSo;
	}
	public String getTotalExchangeAmount() {
		return totalExchangeAmount;
	}
	public void setTotalExchangeAmount(String totalExchangeAmount) {
		this.totalExchangeAmount = totalExchangeAmount;
	}
	public String getOrderTypeText() {
		return orderTypeText;
	}
	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public List<ExchangeItem> getItems() {
		return items;
	}
	public void setItems(List<ExchangeItem> items) {
		this.items = items;
	}
	public List<OrderConditionDto> getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(List<OrderConditionDto> orderCondition) {
		this.orderCondition = orderCondition;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public List<OrderHdrToOrderPartnerDto> getOrderHdrToOrderPartnerDto() {
		return orderHdrToOrderPartnerDto;
	}
	public void setOrderHdrToOrderPartnerDto(List<OrderHdrToOrderPartnerDto> orderHdrToOrderPartnerDto) {
		this.orderHdrToOrderPartnerDto = orderHdrToOrderPartnerDto;
	}
	
	
	

	
}
