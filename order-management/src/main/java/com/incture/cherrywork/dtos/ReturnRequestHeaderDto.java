
package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;



import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.Attachment;

import lombok.Data;

@Data
public class ReturnRequestHeaderDto {

	private String returnReqNum;
	private String roType;
	private String requestType;
	private String orderType;
	private String salesOrg;
	private String distributionChannel;
	private String division;
	private String soldToParty;
	private String shipToParty;
	private String billToParty;
	private String payer;
	private String contactPerson;
	private String contactDivision;
	private String contactTelephone;// send as a string of numers
	private String referenceNum;
	private String orderReason;
	private String orderReasonText;
	private String reasonOwner;
	private String reasonOwnerDesc;
	private String requestRemark;
	private String workflowInstance;
	private String overallWorkflowStatus;
	private String requestedBy;
	private Date createdAt;
	private String updatedAt;
	private String updatedBy;
	private String processingStatus;
	private String logisticalStatus;
	private boolean creationStatus;
	private String message;
	private String docVersion;
	private String roTypeText;
	private String totalRoAmount;
	private String requestorName;
	private String orderTypeText;
	private String referenceInvDate;
	private String customerPo;
	private String soldToPartyDesc;//os
	private String shipToPartyDesc;//os
	private String billToDesc;//os
	private String payerDesc;//os
	private String requestorEmail;//os
	private String returnTotalAmt;//os
	private String flagRoSo;
	private String mappingId;//os
	private String exchangeOrderType;//
	private String emailTrigger;
	private String divisionDesc;
	private String salesOrgDesc;
	private String distributionChannelDesc;
	private String documentUrl;
	private Boolean smsTrigger;
	private String smsFrom;
	private List<ReturnItemDto> listItemDto;
	private List<Attachment> listAttachementDo;
	private List<Address> listAddressDo;
	private ExchangeHeaderDto exchangeDto;
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
	public String getReferenceInvDate() {
		return referenceInvDate;
	}
	public void setReferenceInvDate(String referenceInvDate) {
		this.referenceInvDate = referenceInvDate;
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
	public String getEmailTrigger() {
		return emailTrigger;
	}
	public void setEmailTrigger(String emailTrigger) {
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
	public List<ReturnItemDto> getListItemDto() {
		return listItemDto;
	}
	public void setListItemDto(List<ReturnItemDto> listItemDto) {
		this.listItemDto = listItemDto;
	}
	public List<Attachment> getListAttachementDo() {
		return listAttachementDo;
	}
	public void setListAttachementDo(List<Attachment> listAttachementDo) {
		this.listAttachementDo = listAttachementDo;
	}
	public List<Address> getListAddressDo() {
		return listAddressDo;
	}
	public void setListAddressDo(List<Address> listAddressDo) {
		this.listAddressDo = listAddressDo;
	}
	public ExchangeHeaderDto getExchangeDto() {
		return exchangeDto;
	}
	public void setExchangeDto(ExchangeHeaderDto exchangeDto) {
		this.exchangeDto = exchangeDto;
	}

}

