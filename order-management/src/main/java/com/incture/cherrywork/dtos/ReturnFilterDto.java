package com.incture.cherrywork.dtos;

import java.util.Date;

public class ReturnFilterDto {

	private Integer pageNo;
	private String returnReqNumber;
	private String customerId;
	private Date createdAt;
    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	private String salesOrg;
    private String requestedBy; 
	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	private String division;
	private String distributionChannel;
	private String returnReason;
	private String shipToParty;
	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	
	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public ReturnFilterDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public ReturnFilterDto(Integer pageNo, String returnReqNumber, String customerId, Date createdAt, String salesOrg,
			String requestedBy, String division, String distributionChannel, String returnReason, String shipToParty) {
		super();
		this.pageNo = pageNo;
		this.returnReqNumber = returnReqNumber;
		this.customerId = customerId;
		this.createdAt = createdAt;
		this.salesOrg = salesOrg;
		this.requestedBy = requestedBy;
		this.division = division;
		this.distributionChannel = distributionChannel;
		this.returnReason = returnReason;
		this.shipToParty = shipToParty;
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	
	
	
	
}
