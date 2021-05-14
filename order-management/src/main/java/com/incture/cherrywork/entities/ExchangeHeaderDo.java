package com.incture.cherrywork.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXCHANGE_HEADER")
public @Data class ExchangeHeaderDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private ExchangeHeaderPrimaryKey key;

	@JsonManagedReference("task-Exchange_Item")
	@OneToMany(mappedBy = "key.exchangeHeaderDo", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<ExchangeItemDo> exchangeItemList;

	@Temporal(TemporalType.DATE)
	@Column(name = "SALES_ORDER_DATE")
	private Date salesOrderDate;

	@Column(name = "CREATED_BY", length = 12)
	private String createdBy;

	@Column(name = "ORDER_CATEGORY", length = 1)
	private String orderCategory;

	@Column(name = "ORDER_TYPE", length = 4)
	private String orderType;

	@Column(name = "SALES_ORG", length = 4)
	private String salesOrg;

	@Column(name = "DISTRIBUTION_CHANNEL", length = 4)
	private String distributionChannel;

	@Column(name = "DIVISION", length = 4)
	private String division;

	@Column(name = "ORDER_REASON", length = 2)
	private String orderReason;

	@Column(name = "CUSTOMER_PO", length = 10)
	private String customerPo;

	@Column(name = "CUSTOMER_PO_TYPE", length = 10)
	private String customerPoType;

	@Column(name = "SOLD_TO_PARTY", length = 10)
	private String soldToParty;

	@Column(name = "TOTAL_NET_AMOUNT", precision = 3)
	private Double totalNetAmount;

	@Column(name = "DOC_CURRENCY", length = 3)
	private String docCurrency;

	@Column(name = "DELIVERY_BLOCK", length = 4)
	private String deliveryBlock;

	@Column(name = "BILLING_BLOCK", length = 4)
	private String billingBlock;

	@Column(name = "OVERALL_STATUS", length = 2)
	private String overallStatus;

	@Column(name = "REJECTION_STATUS", length = 1)
	private String rejectionStatus;

	@Column(name = "DELIVERY_STATUS", length = 1)
	private String deliveryStatus;

	@Column(name = "CREDIT_STATUS", length = 1)
	private String creditStatus;

	@Column(name = "APPROVAL_STATUS", length = 1)
	private String approvalStatus;

	@Column(name = "SD_PROCESS_STATUS", length = 1)
	private String sdProcessStatus;

	@Column(name = "ORDER_APPROVAL_REASON", length = 1)
	private String orderApprovalReason;

	@Override
	public Object getPrimaryKey() {
		return key;
	}

	public ExchangeHeaderPrimaryKey getKey() {
		return key;
	}

	public void setKey(ExchangeHeaderPrimaryKey key) {
		this.key = key;
	}

	public List<ExchangeItemDo> getExchangeItemList() {
		return exchangeItemList;
	}

	public void setExchangeItemList(List<ExchangeItemDo> exchangeItemList) {
		this.exchangeItemList = exchangeItemList;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
