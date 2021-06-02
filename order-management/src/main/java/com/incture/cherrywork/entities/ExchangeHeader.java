package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ExchangeHeaderPk.class)
@Table(name = "EXCHANGE_HEADER")
public @Data class ExchangeHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXCHANGE_REQ_NUM")
	private String exchangeReqNum;

	@Id
	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@Column(name = "ORDER_CATEGORY")
	private String orderCategory;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "SALES_ORG")
	private String salesOrg;

	@Column(name = "DISTRIBUTION_CHANNEL")
	private String distributionChannel;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "CUSTOMER_PO")
	private String customerPo;

	@Column(name = "CUSTOMER_PO_TYPE")
	private String customerPoType;

	@Column(name = "SOLD_TO_PARTY")
	private String soldToParty;

	@Column(name = "TOTAL_NET_AMOUNT")
	private Double totalNetAmount;

	@Column(name = "DOC_CURRENCY")
	private String docCurrency;

	@Column(name = "DELIVERY_BLOCK")
	private String deliveryBlock;

	@Column(name = "BILLING_BLOCK")
	private String billingBlock;

	@Column(name = "OVERALL_STATUS")
	private String overallStatus;

	@Column(name = "REJECTION_STATUS")
	private String rejectionStatus;

	@Column(name = "DELIVERY_STATUS")
	private String deliveryStatus;

	@Column(name = "CREDIT_STATUS")
	private String creditStatus;

	@Column(name = "OVERALL_WORKFLOW_STATUS")
	private String overallWorkflowStatus;
	
	@Column(name = "CREATION_STATUS")
	private boolean creationStatus;
	
	@Column(name = "CREATION_MESSAGE" ,columnDefinition = "TEXT")
	private String message;
	
	@Column(name = "DOC_VERSION")
	private String docVersion;
	
	@Column(name = "TOTAL_EXCHANGE_AMOUNT")
    private String totalExchangeAmount;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "RO_TYPE")
	private String roType;
	
	@Column(name = "PAYER")
	private String payer;
	
	@Column(name = "REF_NUM")
	private String referenceNum;
	
	@Column(name = "REASON_OWNER")
	private String reasonOwner;
	
	@Column(name = "REQUEST_REMARK",columnDefinition = "TEXT")
	private String requestRemark;
	
	@Column(name = "BILL_TO_PARTY")
	private String billToParty;
	
	@Column(name = "SOLD_TO_PARTY_DESC")
	private String soldToPartyDesc;
	
	@Column(name = "SHIP_TO_PARTY")
	private String shipToParty;
	
	@Column(name = "SHIP_TO_PARTY_DESC")
	private String shipToPartyDesc;
	
	@Column(name = "REMARK",columnDefinition = "TEXT")
	private String remarks;
	
	@Column(name = "DEL_COMPLETE")
	private String delComplete;
	
	@Column(name = "ORDER_REASON")
	private String ordReason;
	
	@Column(name = "FLAG_RO_SO")
	private String flagRoSo;
	
	@Column(name = "ORDER_TYPE_TEXT")
    private String orderTypeText;
	
	@Column(name = "PAYER_DESC")
	private String payerDescription ;
	
	@Column(name = "BILL_TO_PARTY_DESC")
	private String billToPartyDesc;
	
	@Column(name = "DOCUMENT_URL")
	private String documentUrl;

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getPayerDescription() {
		return payerDescription;
	}

	public void setPayerDescription(String payerDescription) {
		this.payerDescription = payerDescription;
	}

	public String getBillToPartyDesc() {
		return billToPartyDesc;
	}

	public void setBillToPartyDesc(String billToPartyDesc) {
		this.billToPartyDesc = billToPartyDesc;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
	
   
}
