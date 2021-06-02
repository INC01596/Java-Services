package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.entities.ReturnItem;

import lombok.Data;

@Data
public class ReturnOrder {
	private String returnReqNum;
	private String roType;//os
	private String customerPo;
	private String salesOrg;//os
	private String distributionChannel;//os
	private String division;//os
	private String soldToParty;//os
	private String soldToPartyDesc;//os
	private String shipToParty;//os
	private String shipToPartyDesc;//os
	private String billToParty;//os
	private String billToDesc;//os
	private String payer;//os
	private String payerDesc;//os
	private String requestedBy;//os
	private String requestorEmail;//os
	private String contactPerson;//os
	private String contactDivision;//os
	private String contactTelephone;//os save in database the numbers , conver it to text filed.
	private List<String> smsNumberList;// get from list of SMSNumber as list
	private Boolean smsTrigger;
	private String smsFrom;
	private String referenceNum;//os
	private String requestRemark;//os
	private String orderReason;//os
	private String reasonOwner;//os
	private String reasonOwnerDesc;//os
	private String orderType;//os
	private String orderTypeText;//os
	private String returnTotalAmt;//os
	private String workflowInstance;
	private String overallWorkflowStatus;
	private String processingStatus;
	private String logisticalStatus;
	private Date createdAt;//OS
	private String docVersion;//OS
	private String flagRoSo;
	private String roTypeText;//os
	private String totalRoAmount;
	private String requestorName;//os
	private String orderReasonText;//os
	private String mappingId;//os
	private String exchangeOrderType;
	private Boolean emailTrigger;
	private String exchangeReqNum;
	private String divisionDesc;
	private String salesOrgDesc;
	private String distributionChannelDesc;
	private String documentUrl;
	private String message;
	private String updatedBy;
	private String country;
	private List<Attachment> attachment;
	private List<Address> address;
	private List<ReturnItem> items;
	private List<OrderConditionDto> orderCondition;
	private List<OrderHdrToOrderPartnerDto> orderHdrToOrderPartnerDto;
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
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
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
	public String getBillToParty() {
		return billToParty;
	}
	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}
	public String getBillToDesc() {
		return billToDesc;
	}
	public void setBillToDesc(String billToDesc) {
		this.billToDesc = billToDesc;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayerDesc() {
		return payerDesc;
	}
	public void setPayerDesc(String payerDesc) {
		this.payerDesc = payerDesc;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
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
	public List<String> getSmsNumberList() {
		return smsNumberList;
	}
	public void setSmsNumberList(List<String> smsNumberList) {
		this.smsNumberList = smsNumberList;
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
	public String getReferenceNum() {
		return referenceNum;
	}
	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}
	public String getRequestRemark() {
		return requestRemark;
	}
	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
	}
	public String getOrderReason() {
		return orderReason;
	}
	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderTypeText() {
		return orderTypeText;
	}
	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}
	public String getReturnTotalAmt() {
		return returnTotalAmt;
	}
	public void setReturnTotalAmt(String returnTotalAmt) {
		this.returnTotalAmt = returnTotalAmt;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getFlagRoSo() {
		return flagRoSo;
	}
	public void setFlagRoSo(String flagRoSo) {
		this.flagRoSo = flagRoSo;
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
	public String getOrderReasonText() {
		return orderReasonText;
	}
	public void setOrderReasonText(String orderReasonText) {
		this.orderReasonText = orderReasonText;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getExchangeOrderType() {
		return exchangeOrderType;
	}
	public void setExchangeOrderType(String exchangeOrderType) {
		this.exchangeOrderType = exchangeOrderType;
	}
	public Boolean getEmailTrigger() {
		return emailTrigger;
	}
	public void setEmailTrigger(Boolean emailTrigger) {
		this.emailTrigger = emailTrigger;
	}
	public String getExchangeReqNum() {
		return exchangeReqNum;
	}
	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Attachment> getAttachment() {
		return attachment;
	}
	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public List<ReturnItem> getItems() {
		return items;
	}
	public void setItems(List<ReturnItem> items) {
		this.items = items;
	}
	public List<OrderConditionDto> getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(List<OrderConditionDto> orderCondition) {
		this.orderCondition = orderCondition;
	}
	public List<OrderHdrToOrderPartnerDto> getOrderHdrToOrderPartnerDto() {
		return orderHdrToOrderPartnerDto;
	}
	public void setOrderHdrToOrderPartnerDto(List<OrderHdrToOrderPartnerDto> orderHdrToOrderPartnerDto) {
		this.orderHdrToOrderPartnerDto = orderHdrToOrderPartnerDto;
	}
	
	
	
	
}
