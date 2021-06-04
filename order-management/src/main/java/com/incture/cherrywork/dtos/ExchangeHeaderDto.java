
package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ExchangeHeaderDto {
	
	private String exchangeReqNum; // 
	private String returnReqNum;
	private  Date   createdAt; // ui sending 
	private String orderCategory; // OS
	private String orderType;//OS
	private String salesOrg;//OS
	private String distributionChannel;//OS
	private String division;//OS
	private String customerPo; // no OS
	private String customerPoType;// no OS
	private String soldToParty;// OS
	private Double totalNetAmount; // OS
	private String docCurrency; // NO OS - currency not binded
	private String deliveryBlock; // NO OS
	private String billingBlock;// NO OS
	private String overallStatus;// NO OS
	private String rejectionStatus;// No OS
	private String deliveryStatus;// NO OS
	private String creditStatus; // NO OS
	private String overallWorkflowStatus;//NO OS
	private boolean creationStatus; //  on JAVA 
	private String message; // on JAVA 
	private String docVersion; // On OS
    private String totalExchangeAmount; // ON OS need clear 
	private String currency; // NO OS
	private String roType; // OS
	private String payer;// OS
	private String payerDescription ; // Need to send
	private String referenceNum; // OS
	private String reasonOwner;//OS
	private String requestRemark;//OS
	private String billToParty;//OS
	private String billToPartyDesc;// need to send
	private String soldToPartyDesc;// OS
	private String shipToParty;//OS
	private String shipToPartyDesc;//OS
	private String delComplete;// nedd to send
	private String ordReason;// OS
	private String flagRoSo;//OS
    private String orderTypeText;//OS
	private String createdBy;
	private String remarks;
	private String documentUrl;
	private List<ExchangeItemDto> listExhangeItemDto;
	public String getExchangeReqNum() {
		return exchangeReqNum;
	}
	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
	}
	public String getReturnReqNum() {
		return returnReqNum;
	}
	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getCustomerPoType() {
		return customerPoType;
	}
	public void setCustomerPoType(String customerPoType) {
		this.customerPoType = customerPoType;
	}
	public String getSoldToParty() {
		return soldToParty;
	}
	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}
	public Double getTotalNetAmount() {
		return totalNetAmount;
	}
	public void setTotalNetAmount(Double totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}
	public String getDocCurrency() {
		return docCurrency;
	}
	public void setDocCurrency(String docCurrency) {
		this.docCurrency = docCurrency;
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
	public boolean isCreationStatus() {
		return creationStatus;
	}
	public void setCreationStatus(boolean creationStatus) {
		this.creationStatus = creationStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getTotalExchangeAmount() {
		return totalExchangeAmount;
	}
	public void setTotalExchangeAmount(String totalExchangeAmount) {
		this.totalExchangeAmount = totalExchangeAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getPayerDescription() {
		return payerDescription;
	}
	public void setPayerDescription(String payerDescription) {
		this.payerDescription = payerDescription;
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
	public String getBillToPartyDesc() {
		return billToPartyDesc;
	}
	public void setBillToPartyDesc(String billToPartyDesc) {
		this.billToPartyDesc = billToPartyDesc;
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
	public String getDelComplete() {
		return delComplete;
	}
	public void setDelComplete(String delComplete) {
		this.delComplete = delComplete;
	}
	public String getOrdReason() {
		return ordReason;
	}
	public void setOrdReason(String ordReason) {
		this.ordReason = ordReason;
	}
	public String getFlagRoSo() {
		return flagRoSo;
	}
	public void setFlagRoSo(String flagRoSo) {
		this.flagRoSo = flagRoSo;
	}
	public String getOrderTypeText() {
		return orderTypeText;
	}
	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public List<ExchangeItemDto> getListExhangeItemDto() {
		return listExhangeItemDto;
	}
	public void setListExhangeItemDto(List<ExchangeItemDto> listExhangeItemDto) {
		this.listExhangeItemDto = listExhangeItemDto;
	}

}


