package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class ExchangeHeaderDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String exchangeReqNum;

	private String returnReqNum;

	private Date salesOrderDate;

	private String createdBy;

	private String orderCategory;

	private String orderType;

	private String salesOrg;

	private String distributionChannel;

	private String division;

	private String orderReason;

	private String customerPo;

	private String customerPoType;

	private String soldToParty;

	private Double totalNetAmount;

	private String docCurrency;

	private String deliveryBlock;

	private String billingBlock;

	private String overallStatus;

	private String rejectionStatus;

	private String deliveryStatus;

	private String creditStatus;

	private String approvalStatus;

	private String sdProcessStatus;

	private String orderApprovalReason;

	private List<ExchangeItemDto> exchangeItemList;

	private List<CommentDto> commentDtoList;

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

	public List<ExchangeItemDto> getExchangeItemList() {
		return exchangeItemList;
	}

	public void setExchangeItemList(List<ExchangeItemDto> exchangeItemList) {
		this.exchangeItemList = exchangeItemList;
	}

	public List<CommentDto> getCommentDtoList() {
		return commentDtoList;
	}

	public void setCommentDtoList(List<CommentDto> commentDtoList) {
		this.commentDtoList = commentDtoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

}

