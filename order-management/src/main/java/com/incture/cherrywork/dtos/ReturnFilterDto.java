package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

public class ReturnFilterDto {

	private Integer pageNo;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	private String returnReqNumber;
	private List<String> customerId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;

	private String salesOrg;
	private String division;
	private String distributionChannel;

	private String docVersion;
	private String orderReason;
	private String requestedBy;
	
	
	public ReturnFilterDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnFilterDto(Integer pageNo, String returnReqNumber, List<String> customerId, Date createdDateFrom,
			Date createdDateTo, String shipToParty, String salesOrg, String division, String distributionChannel) {
		super();
		this.pageNo = pageNo;
		this.returnReqNumber = returnReqNumber;
		this.customerId = customerId;
		this.createdDateFrom = createdDateFrom;
		this.createdDateTo = createdDateTo;
		this.shipToParty = shipToParty;
		this.salesOrg = salesOrg;
		this.division = division;
		this.distributionChannel = distributionChannel;
	}

	
	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getReturnReqNumber() {
		return returnReqNumber;
	}

	public void setReturnReqNumber(String returnReqNumber) {
		this.returnReqNumber = returnReqNumber;
	}

	public Date getCreatedDateFrom() {
		return createdDateFrom;
	}

	public void setCreatedDateFrom(Date createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}

	public Date getCreatedDateTo() {
		return createdDateTo;
	}

	public void setCreatedDateTo(Date createdDateTo) {
		this.createdDateTo = createdDateTo;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public List<String> getCustomerId() {
		return customerId;
	}

	public void setCustomerId(List<String> customerId) {
		this.customerId = customerId;
	}

	
	
}
