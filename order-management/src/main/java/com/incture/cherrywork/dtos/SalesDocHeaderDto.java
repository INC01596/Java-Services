package com.incture.cherrywork.dtos;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SalesDocHeaderDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String salesOrderNum;

	private String reqMasterId;

	private BigInteger salesOrderDate;

	private String createdBy;

	private String orderCategory;

	private String orderType;

	private String orderTypeText;

	private String decisionSetId;

	private String billToParty;

	private String billToPartyText;

	private String payer;

	private String payerText;

	private String salesman;

	private String levelNum;

	private String headerMsg;

	private String salesOrg;

	private Double decisionSetAmount;

	private Double salesOrderAmount;

	private String distributionChannel;

	private String division;
	private String salesOrgText;
	private String distrChanText;
	private String divisionText;
	private String orderReason;

	private String customerPo;

	private Date customerPoDate;

	private String customerPoType;

	private String soldToParty;

	private String soldToPartyText;

	private String shipToParty;

	private String shipToPartyText;

	private Double totalNetAmount;

	private String docCurrency;

	private String overallStatus;

	private String rejectionStatus;

	private String deliveryStatus;

	private String creditStatus;

	private String headerBillBlockCode;

	private String headerBillBlockCodeText;

	private String deliveryBlockCode;

	private String deliveryBlockCodeText;

	private String creditBlock;

	private String approvalStatus;

	private String sdProcessStatus;

	private String orderApprovalReason;

	private String flagFromScheduler;

	private String docTypeText;

	private String orderReasonText;

	private String condGroup5;

	private String orderRemark;

	private String ordererNA;

	private String condGroup5Text;

	private String attachmentUrl;

	private String requestedBy;

	private List<SalesDocItemDto> salesDocItemList;

	private List<String> specialClientListForHeader;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();

	}

	public SalesDocHeaderDto(String salesOrderNum, String deliveryBlockCode) {
		super();
		this.salesOrderNum = salesOrderNum;
		this.deliveryBlockCode = deliveryBlockCode;
	}

	public SalesDocHeaderDto() {
		// TODO Auto-generated constructor stub
	}

	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public String getReqMasterId() {
		return reqMasterId;
	}

	public void setReqMasterId(String reqMasterId) {
		this.reqMasterId = reqMasterId;
	}

	public BigInteger getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(BigInteger salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getOrderTypeText() {
		return orderTypeText;
	}

	public void setOrderTypeText(String orderTypeText) {
		this.orderTypeText = orderTypeText;
	}

	public String getDecisionSetId() {
		return decisionSetId;
	}

	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}

	public String getBillToParty() {
		return billToParty;
	}

	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}

	public String getBillToPartyText() {
		return billToPartyText;
	}

	public void setBillToPartyText(String billToPartyText) {
		this.billToPartyText = billToPartyText;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayerText() {
		return payerText;
	}

	public void setPayerText(String payerText) {
		this.payerText = payerText;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	public String getHeaderMsg() {
		return headerMsg;
	}

	public void setHeaderMsg(String headerMsg) {
		this.headerMsg = headerMsg;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public Double getDecisionSetAmount() {
		return decisionSetAmount;
	}

	public void setDecisionSetAmount(Double decisionSetAmount) {
		this.decisionSetAmount = decisionSetAmount;
	}

	public Double getSalesOrderAmount() {
		return salesOrderAmount;
	}

	public void setSalesOrderAmount(Double salesOrderAmount) {
		this.salesOrderAmount = salesOrderAmount;
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

	public String getSalesOrgText() {
		return salesOrgText;
	}

	public void setSalesOrgText(String salesOrgText) {
		this.salesOrgText = salesOrgText;
	}

	public String getDistrChanText() {
		return distrChanText;
	}

	public void setDistrChanText(String distrChanText) {
		this.distrChanText = distrChanText;
	}

	public String getDivisionText() {
		return divisionText;
	}

	public void setDivisionText(String divisionText) {
		this.divisionText = divisionText;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public Date getCustomerPoDate() {
		return customerPoDate;
	}

	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
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

	public String getSoldToPartyText() {
		return soldToPartyText;
	}

	public void setSoldToPartyText(String soldToPartyText) {
		this.soldToPartyText = soldToPartyText;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getShipToPartyText() {
		return shipToPartyText;
	}

	public void setShipToPartyText(String shipToPartyText) {
		this.shipToPartyText = shipToPartyText;
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

	public String getHeaderBillBlockCode() {
		return headerBillBlockCode;
	}

	public void setHeaderBillBlockCode(String headerBillBlockCode) {
		this.headerBillBlockCode = headerBillBlockCode;
	}

	public String getHeaderBillBlockCodeText() {
		return headerBillBlockCodeText;
	}

	public void setHeaderBillBlockCodeText(String headerBillBlockCodeText) {
		this.headerBillBlockCodeText = headerBillBlockCodeText;
	}

	public String getDeliveryBlockCode() {
		return deliveryBlockCode;
	}

	public void setDeliveryBlockCode(String deliveryBlockCode) {
		this.deliveryBlockCode = deliveryBlockCode;
	}

	public String getDeliveryBlockCodeText() {
		return deliveryBlockCodeText;
	}

	public void setDeliveryBlockCodeText(String deliveryBlockCodeText) {
		this.deliveryBlockCodeText = deliveryBlockCodeText;
	}

	public String getCreditBlock() {
		return creditBlock;
	}

	public void setCreditBlock(String creditBlock) {
		this.creditBlock = creditBlock;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getSdProcessStatus() {
		return sdProcessStatus;
	}

	public void setSdProcessStatus(String sdProcessStatus) {
		this.sdProcessStatus = sdProcessStatus;
	}

	public String getOrderApprovalReason() {
		return orderApprovalReason;
	}

	public void setOrderApprovalReason(String orderApprovalReason) {
		this.orderApprovalReason = orderApprovalReason;
	}

	public String getFlagFromScheduler() {
		return flagFromScheduler;
	}

	public void setFlagFromScheduler(String flagFromScheduler) {
		this.flagFromScheduler = flagFromScheduler;
	}

	public String getDocTypeText() {
		return docTypeText;
	}

	public void setDocTypeText(String docTypeText) {
		this.docTypeText = docTypeText;
	}

	public String getOrderReasonText() {
		return orderReasonText;
	}

	public void setOrderReasonText(String orderReasonText) {
		this.orderReasonText = orderReasonText;
	}

	public String getCondGroup5() {
		return condGroup5;
	}

	public void setCondGroup5(String condGroup5) {
		this.condGroup5 = condGroup5;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getOrdererNA() {
		return ordererNA;
	}

	public void setOrdererNA(String ordererNA) {
		this.ordererNA = ordererNA;
	}

	public String getCondGroup5Text() {
		return condGroup5Text;
	}

	public void setCondGroup5Text(String condGroup5Text) {
		this.condGroup5Text = condGroup5Text;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public List<SalesDocItemDto> getSalesDocItemList() {
		return salesDocItemList;
	}

	public void setSalesDocItemList(List<SalesDocItemDto> salesDocItemList) {
		this.salesDocItemList = salesDocItemList;
	}

	public List<String> getSpecialClientListForHeader() {
		return specialClientListForHeader;
	}

	public void setSpecialClientListForHeader(List<String> specialClientListForHeader) {
		this.specialClientListForHeader = specialClientListForHeader;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

