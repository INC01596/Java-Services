
package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "RETURN_REQUEST")
public  class ReturnRequestHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RR")
	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;

	@Column(name = "RO_TYPE")
	private String roType;

	@Column(name = "REQUEST_TYPE")
	private String requestType;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "SALES_ORG")
	private String salesOrg;
	
	@Column(name = "MAPPING_ID")
	private String mappingId;

	@Column(name = "DISTRIBUTION_CHANNEL")
	private String distributionChannel;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "SOLD_TO_PARTY")
	private String soldToParty;

	@Column(name = "SHIP_TO_PARTY")
	private String shipToParty;

	@Column(name = "BILL_TO_PARTY")
	private String billToParty;

	@Column(name = "PAYER")
	private String payer;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "CONTACT_DIVISION")
	private String contactDivision;

	@Column(name = "CONTACT_TELEPHONE",columnDefinition = "TEXT")
	private String contactTelephone;

	@Column(name = "REFERENCE_NUM")
	private String referenceNum;

	@Column(name = "ORDER_REASON")
	private String orderReason;
	
	@Column(name = "ORDER_REASON_TEXT")
	private String orderReasonText;

	@Column(name = "REASON_OWNER")
	private String reasonOwner;
	
	@Column(name = "REASON_OWNER_DESC")
	private String reasonOwnerDesc;
	
 
	@Column(name = "REQUEST_REMARK",columnDefinition = "TEXT")
	private String requestRemark;

	@Column(name = "WORKFLOW_INSTANCE")
	private String workflowInstance;

	@Column(name = "OVERALL_WORKFLOW_STATUS")
	private String overallWorkflowStatus;

	@Column(name = "REQUESTED_BY")
	private String requestedBy;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date  createdAt;

	@Column(name = "UPDATED_AT")
	private String updatedAt;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "PROCESSING_STATUS")
	private String processingStatus;

	@Column(name = "LOGISTICAL_STATUS")
	private String logisticalStatus;
	
	@Column(name = "CREATION_STATUS")
	private boolean creationStatus;
	
	@Column(name = "CREATION_MESSAGE",columnDefinition = "TEXT")
	private String message;
	
	@Column(name = "DOC_VERSION")
	private String docVersion;
	
	@Column(name = "RO_TYPE_TEXT")
	private String roTypeText;
	
	@Column(name = "TOTAL_RO_AMOUNT")
	private String totalRoAmount;
	
	@Column(name = "REQUESTOR_NAME")
	private String requestorName;
	
	@Column(name = "ORDER_TYPE_TEXT")
	private String orderTypeText;
	
	@Column(name = "EXCHANGE_ORDER_TYPE")
	private String exchangeOrderType;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "CUSTOMER_PO")
	private String customerPo;

	@Column(name ="SHIP_TO_PARTY_DESC")
	private String soldToPartyDesc;//os
	
	@Column(name = "SOLD_TO_PARTY_DESC")
	private String shipToPartyDesc;//os
	
	@Column(name = "BILL_TO_PARTY_DESC")
	private String billToDesc;//os
	@Column(name = "PAYER_DESC")
	private String payerDesc;//os
	
	@Column(name = "REQUESTOR_EMAIL")
	private String requestorEmail;//os
	
	@Column(name = "RETURN_TOTAL_AMOUNT")
	private String returnTotalAmt;//os
	
	@Column(name = "FLAG_RO_SO")
	private String flagRoSo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REF_INV_DATE")
	private Date referenceInvDate;
	
	@Column(name = "EMAIL_TRIGGER")
	private Boolean emailTrigger;
	
	@Column(name = "DIVISION_DESC")
	private String divisionDesc;
	
	@Column(name = "SALES_ORG_DESC")
	private String salesOrgDesc;
	
	@Column(name = "DISTRIBUTION_CHANNEL_DESC")
	private String distributionChannelDesc;
	
	@Column(name = "DOCUMENT_URL")
	private String documentUrl;
	
	@Column(name = "SMS_TRIGGER")
	private Boolean smsTrigger;
	
	@Column(name = "SMS_FROM")
	private String smsFrom;
	
	@Column(name="Flag")
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getReturnReqNum() {
		return returnReqNum;
	}

	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}

	public String getRoType() {
		return roType;
	}

	public void setRoType(String roType) {
		this.roType = roType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
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

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getBillToParty() {
		return billToParty;
	}

	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactDivision() {
		return contactDivision;
	}

	public void setContactDivision(String contactDivision) {
		this.contactDivision = contactDivision;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getReferenceNum() {
		return referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public String getOrderReasonText() {
		return orderReasonText;
	}

	public void setOrderReasonText(String orderReasonText) {
		this.orderReasonText = orderReasonText;
	}

	public String getReasonOwner() {
		return reasonOwner;
	}

	public void setReasonOwner(String reasonOwner) {
		this.reasonOwner = reasonOwner;
	}

	public String getReasonOwnerDesc() {
		return reasonOwnerDesc;
	}

	public void setReasonOwnerDesc(String reasonOwnerDesc) {
		this.reasonOwnerDesc = reasonOwnerDesc;
	}

	public String getRequestRemark() {
		return requestRemark;
	}

	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
	}

	public String getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public String getOverallWorkflowStatus() {
		return overallWorkflowStatus;
	}

	public void setOverallWorkflowStatus(String overallWorkflowStatus) {
		this.overallWorkflowStatus = overallWorkflowStatus;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public String getLogisticalStatus() {
		return logisticalStatus;
	}

	public void setLogisticalStatus(String logisticalStatus) {
		this.logisticalStatus = logisticalStatus;
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

	public String getRoTypeText() {
		return roTypeText;
	}

	public void setRoTypeText(String roTypeText) {
		this.roTypeText = roTypeText;
	}

	public String getTotalRoAmount() {
		return totalRoAmount;
	}

	public void setTotalRoAmount(String totalRoAmount) {
		this.totalRoAmount = totalRoAmount;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public String getOrderTypeText() {
		return orderTypeText;
	}

	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}

	public String getExchangeOrderType() {
		return exchangeOrderType;
	}

	public void setExchangeOrderType(String exchangeOrderType) {
		this.exchangeOrderType = exchangeOrderType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getSoldToPartyDesc() {
		return soldToPartyDesc;
	}

	public void setSoldToPartyDesc(String soldToPartyDesc) {
		this.soldToPartyDesc = soldToPartyDesc;
	}

	public String getShipToPartyDesc() {
		return shipToPartyDesc;
	}

	public void setShipToPartyDesc(String shipToPartyDesc) {
		this.shipToPartyDesc = shipToPartyDesc;
	}

	public String getBillToDesc() {
		return billToDesc;
	}

	public void setBillToDesc(String billToDesc) {
		this.billToDesc = billToDesc;
	}

	public String getPayerDesc() {
		return payerDesc;
	}

	public void setPayerDesc(String payerDesc) {
		this.payerDesc = payerDesc;
	}

	public String getRequestorEmail() {
		return requestorEmail;
	}

	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}

	public String getReturnTotalAmt() {
		return returnTotalAmt;
	}

	public void setReturnTotalAmt(String returnTotalAmt) {
		this.returnTotalAmt = returnTotalAmt;
	}

	public String getFlagRoSo() {
		return flagRoSo;
	}

	public void setFlagRoSo(String flagRoSo) {
		this.flagRoSo = flagRoSo;
	}

	public Date getReferenceInvDate() {
		return referenceInvDate;
	}

	public void setReferenceInvDate(Date referenceInvDate) {
		this.referenceInvDate = referenceInvDate;
	}

	public Boolean getEmailTrigger() {
		return emailTrigger;
	}

	public void setEmailTrigger(Boolean emailTrigger) {
		this.emailTrigger = emailTrigger;
	}

	public String getDivisionDesc() {
		return divisionDesc;
	}

	public void setDivisionDesc(String divisionDesc) {
		this.divisionDesc = divisionDesc;
	}

	public String getSalesOrgDesc() {
		return salesOrgDesc;
	}

	public void setSalesOrgDesc(String salesOrgDesc) {
		this.salesOrgDesc = salesOrgDesc;
	}

	public String getDistributionChannelDesc() {
		return distributionChannelDesc;
	}

	public void setDistributionChannelDesc(String distributionChannelDesc) {
		this.distributionChannelDesc = distributionChannelDesc;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public Boolean getSmsTrigger() {
		return smsTrigger;
	}

	public void setSmsTrigger(Boolean smsTrigger) {
		this.smsTrigger = smsTrigger;
	}

	public String getSmsFrom() {
		return smsFrom;
	}

	public void setSmsFrom(String smsFrom) {
		this.smsFrom = smsFrom;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReturnRequestHeader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnRequestHeader(String returnReqNum, String roType, String requestType, String orderType,
			String salesOrg, String mappingId, String distributionChannel, String division, String soldToParty,
			String shipToParty, String billToParty, String payer, String contactPerson, String contactDivision,
			String contactTelephone, String referenceNum, String orderReason, String orderReasonText,
			String reasonOwner, String reasonOwnerDesc, String requestRemark, String workflowInstance,
			String overallWorkflowStatus, String requestedBy, Date createdAt, String updatedAt, String updatedBy,
			String processingStatus, String logisticalStatus, boolean creationStatus, String message, String docVersion,
			String roTypeText, String totalRoAmount, String requestorName, String orderTypeText,
			String exchangeOrderType, String currency, String customerPo, String soldToPartyDesc,
			String shipToPartyDesc, String billToDesc, String payerDesc, String requestorEmail, String returnTotalAmt,
			String flagRoSo, Date referenceInvDate, Boolean emailTrigger, String divisionDesc, String salesOrgDesc,
			String distributionChannelDesc, String documentUrl, Boolean smsTrigger, String smsFrom) {
		super();
		this.returnReqNum = returnReqNum;
		this.roType = roType;
		this.requestType = requestType;
		this.orderType = orderType;
		this.salesOrg = salesOrg;
		this.mappingId = mappingId;
		this.distributionChannel = distributionChannel;
		this.division = division;
		this.soldToParty = soldToParty;
		this.shipToParty = shipToParty;
		this.billToParty = billToParty;
		this.payer = payer;
		this.contactPerson = contactPerson;
		this.contactDivision = contactDivision;
		this.contactTelephone = contactTelephone;
		this.referenceNum = referenceNum;
		this.orderReason = orderReason;
		this.orderReasonText = orderReasonText;
		this.reasonOwner = reasonOwner;
		this.reasonOwnerDesc = reasonOwnerDesc;
		this.requestRemark = requestRemark;
		this.workflowInstance = workflowInstance;
		this.overallWorkflowStatus = overallWorkflowStatus;
		this.requestedBy = requestedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.processingStatus = processingStatus;
		this.logisticalStatus = logisticalStatus;
		this.creationStatus = creationStatus;
		this.message = message;
		this.docVersion = docVersion;
		this.roTypeText = roTypeText;
		this.totalRoAmount = totalRoAmount;
		this.requestorName = requestorName;
		this.orderTypeText = orderTypeText;
		this.exchangeOrderType = exchangeOrderType;
		this.currency = currency;
		this.customerPo = customerPo;
		this.soldToPartyDesc = soldToPartyDesc;
		this.shipToPartyDesc = shipToPartyDesc;
		this.billToDesc = billToDesc;
		this.payerDesc = payerDesc;
		this.requestorEmail = requestorEmail;
		this.returnTotalAmt = returnTotalAmt;
		this.flagRoSo = flagRoSo;
		this.referenceInvDate = referenceInvDate;
		this.emailTrigger = emailTrigger;
		this.divisionDesc = divisionDesc;
		this.salesOrgDesc = salesOrgDesc;
		this.distributionChannelDesc = distributionChannelDesc;
		this.documentUrl = documentUrl;
		this.smsTrigger = smsTrigger;
		this.smsFrom = smsFrom;
	}
	
	

}
