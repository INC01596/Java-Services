package com.incture.cherrywork.entities;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SALES_DOC_HEADER")
public @Data class SalesDocHeaderDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SALES_ORDER_NUM", length = 100)
	private String salesOrderNum;

	@JsonManagedReference("task_list-task")
	@OneToMany(mappedBy = "salesDocItemKey.salesDocHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	@OrderBy("salesDocItemKey.salesItemOrderNo")
	private List<SalesDocItemDo> salesDocItemList;

	// @OneToOne
	// @JoinColumn(name = "REQUEST_ID", nullable = false, referencedColumnName =
	// "REQUEST_ID", unique = true)
	// private RequestMasterDo reqMaster;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@Column(name = "SALES_ORDER_DATE")
	private BigInteger salesOrderDate;

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "ORDER_CATEGORY", length = 20)
	private String orderCategory;

	@Column(name = "ORDER_TYPE", length = 20)
	private String orderType;

	@Column(name = "SALES_ORG", length = 20)
	private String salesOrg;

	@Column(name = "DISTRIBUTION_CHANNEL", length = 20)
	private String distributionChannel;

	@Column(name = "DIVISION", length = 20)
	private String division;

	@Column(name = "SALES_ORG_TEXT")
	private String salesOrgText;

	@Column(name = "DISTRIBUTION_CHANNEL_TEXT")
	private String distributionChannelText;

	@Column(name = "DIVISION_TEXT")
	private String divisionText;

	@Column(name = "ORDER_REASON", length = 20)
	private String orderReason;

	@Column(name = "CUSTOMER_PO", length = 100)
	private String customerPo;

	@Column(name = "CUSTOMER_PO_TYPE", length = 20)
	private String customerPoType;

	@Column(name = "SOLD_TO_PARTY", length = 20)
	private String soldToParty;
	// new added
	@Column(name = "SOLD_TO_PARTY_TEXT", length = 100)
	private String soldToPartyText;

	@Column(name = "SHIP_TO_PARTY_TEXT", length = 100)
	private String shipToPartyText;

	@Column(name = "SHIP_TO_PARTY", length = 20)
	private String shipToParty;

	@Column(name = "TOTAL_NET_AMOUNT", precision = 3)
	private Double totalNetAmount;

	@Column(name = "DOC_CURRENCY", length = 20)
	private String docCurrency;

	// new added
	@Column(name = "DOC_TYPE_TEXT", length = 100)
	private String docTypeText;

	@Column(name = "OVERALL_STATUS", length = 20)
	private String overallStatus;

	@Column(name = "REJECTION_STATUS", length = 20)
	private String rejectionStatus;

	@Column(name = "DELIVERY_STATUS", length = 20)
	private String deliveryStatus;

	@Column(name = "CREDIT_STATUS", length = 20)
	private String creditStatus;

	@Column(name = "BILL_TO_PARTY", length = 20)
	private String billToParty;

	@Column(name = "BILL_TO_PARTY_TEXT")
	private String billToPartyText;

	@Column(name = "PAYER", length = 20)
	private String payer;

	@Column(name = "PAYER_TEXT")
	private String payerText;

	@Column(name = "SALESMAN", length = 20)
	private String salesman;

	@Column(name = "HEADER_BILLING_BLOCK_CODE", length = 20)
	private String headerBillBlockCode;

	// new added
	@Column(name = "DLV_BLOCK_TEXT", length = 100)
	private String deliveryBlockText;

	@Column(name = "DELIVERY_BLOCK_CODE", length = 20)
	private String deliveryBlockCode;

	@Column(name = "CREDIT_BLOCK_CODE", length = 20)
	private String creditBlock;

	@Column(name = "APPROVAL_STATUS", length = 20)
	private String approvalStatus;

	@Column(name = "SD_PROCESS_STATUS", length = 20)
	private String sdProcessStatus;

	@Column(name = "ORDER_APPROVAL_REASON", length = 20)
	private String orderApprovalReason;

	@Column(name = "ORDER_REASON_TEXT")
	private String orderReasonText;

	@Column(name = "ORDER_REMARK", length = 2000)
	private String orderRemark;

	@Column(name = "COND_GROUP5")
	private String condGroup5;

	@Column(name = "COND_GROUP5_TEXT")
	private String condGroup5Text;

	@Column(name = "ORDERER_NA")
	private String ordererNA;

	@Column(name = "ATTACHMENT_URL", length = 1000)
	private String attachmentUrl;

	@Column(name = "REQUESTED_BY")
	private String requestedBy;

	@Override
	public Object getPrimaryKey() {
		return salesOrderNum;
	}

	public SalesDocHeaderDo() {
		super();
		
	}

	public SalesDocHeaderDo(String salesOrderNum, List<SalesDocItemDo> salesDocItemList, String requestId,
			BigInteger salesOrderDate, String createdBy, String orderCategory, String orderType, String salesOrg,
			String distributionChannel, String division, String salesOrgText, String distributionChannelText,
			String divisionText, String orderReason, String customerPo, String customerPoType, String soldToParty,
			String soldToPartyText, String shipToPartyText, String shipToParty, Double totalNetAmount,
			String docCurrency, String docTypeText, String overallStatus, String rejectionStatus, String deliveryStatus,
			String creditStatus, String billToParty, String billToPartyText, String payer, String payerText,
			String salesman, String headerBillBlockCode, String deliveryBlockText, String deliveryBlockCode,
			String creditBlock, String approvalStatus, String sdProcessStatus, String orderApprovalReason,
			String orderReasonText, String orderRemark, String condGroup5, String condGroup5Text, String ordererNA,
			String attachmentUrl, String requestedBy) {
		super();
		this.salesOrderNum = salesOrderNum;
		this.salesDocItemList = salesDocItemList;
		this.requestId = requestId;
		this.salesOrderDate = salesOrderDate;
		this.createdBy = createdBy;
		this.orderCategory = orderCategory;
		this.orderType = orderType;
		this.salesOrg = salesOrg;
		this.distributionChannel = distributionChannel;
		this.division = division;
		this.salesOrgText = salesOrgText;
		this.distributionChannelText = distributionChannelText;
		this.divisionText = divisionText;
		this.orderReason = orderReason;
		this.customerPo = customerPo;
		this.customerPoType = customerPoType;
		this.soldToParty = soldToParty;
		this.soldToPartyText = soldToPartyText;
		this.shipToPartyText = shipToPartyText;
		this.shipToParty = shipToParty;
		this.totalNetAmount = totalNetAmount;
		this.docCurrency = docCurrency;
		this.docTypeText = docTypeText;
		this.overallStatus = overallStatus;
		this.rejectionStatus = rejectionStatus;
		this.deliveryStatus = deliveryStatus;
		this.creditStatus = creditStatus;
		this.billToParty = billToParty;
		this.billToPartyText = billToPartyText;
		this.payer = payer;
		this.payerText = payerText;
		this.salesman = salesman;
		this.headerBillBlockCode = headerBillBlockCode;
		this.deliveryBlockText = deliveryBlockText;
		this.deliveryBlockCode = deliveryBlockCode;
		this.creditBlock = creditBlock;
		this.approvalStatus = approvalStatus;
		this.sdProcessStatus = sdProcessStatus;
		this.orderApprovalReason = orderApprovalReason;
		this.orderReasonText = orderReasonText;
		this.orderRemark = orderRemark;
		this.condGroup5 = condGroup5;
		this.condGroup5Text = condGroup5Text;
		this.ordererNA = ordererNA;
		this.attachmentUrl = attachmentUrl;
		this.requestedBy = requestedBy;
	}

	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public List<SalesDocItemDo> getSalesDocItemList() {
		return salesDocItemList;
	}

	public void setSalesDocItemList(List<SalesDocItemDo> salesDocItemList) {
		this.salesDocItemList = salesDocItemList;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getSalesOrgText() {
		return salesOrgText;
	}

	public void setSalesOrgText(String salesOrgText) {
		this.salesOrgText = salesOrgText;
	}

	public String getDistributionChannelText() {
		return distributionChannelText;
	}

	public void setDistributionChannelText(String distributionChannelText) {
		this.distributionChannelText = distributionChannelText;
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

	public String getShipToPartyText() {
		return shipToPartyText;
	}

	public void setShipToPartyText(String shipToPartyText) {
		this.shipToPartyText = shipToPartyText;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
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

	public String getDocTypeText() {
		return docTypeText;
	}

	public void setDocTypeText(String docTypeText) {
		this.docTypeText = docTypeText;
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

	public String getHeaderBillBlockCode() {
		return headerBillBlockCode;
	}

	public void setHeaderBillBlockCode(String headerBillBlockCode) {
		this.headerBillBlockCode = headerBillBlockCode;
	}

	public String getDeliveryBlockText() {
		return deliveryBlockText;
	}

	public void setDeliveryBlockText(String deliveryBlockText) {
		this.deliveryBlockText = deliveryBlockText;
	}

	public String getDeliveryBlockCode() {
		return deliveryBlockCode;
	}

	public void setDeliveryBlockCode(String deliveryBlockCode) {
		this.deliveryBlockCode = deliveryBlockCode;
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

	public String getOrderReasonText() {
		return orderReasonText;
	}

	public void setOrderReasonText(String orderReasonText) {
		this.orderReasonText = orderReasonText;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getCondGroup5() {
		return condGroup5;
	}

	public void setCondGroup5(String condGroup5) {
		this.condGroup5 = condGroup5;
	}

	public String getCondGroup5Text() {
		return condGroup5Text;
	}

	public void setCondGroup5Text(String condGroup5Text) {
		this.condGroup5Text = condGroup5Text;
	}

	public String getOrdererNA() {
		return ordererNA;
	}

	public void setOrdererNA(String ordererNA) {
		this.ordererNA = ordererNA;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
