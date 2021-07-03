package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

public class ReturnFilterDto {

	private Integer pageNo;
<<<<<<< HEAD
=======
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
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
<<<<<<< HEAD

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
=======
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	
	
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

<<<<<<< HEAD
	public ReturnFilterDto(Integer pageNo, String returnReqNumber, List<String> customerId, Date createdDateFrom,
			Date createdDateTo, String shipToParty, String salesOrg, String division, String distributionChannel) {
=======
	
	
	public ReturnFilterDto(Integer pageNo, String returnReqNumber, String customerId, Date createdAt, String salesOrg,
			String requestedBy, String division, String distributionChannel, String returnReason, String shipToParty) {
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
		super();
		this.pageNo = pageNo;
		this.returnReqNumber = returnReqNumber;
		this.customerId = customerId;
<<<<<<< HEAD
		this.createdDateFrom = createdDateFrom;
		this.createdDateTo = createdDateTo;
		this.shipToParty = shipToParty;
=======
		this.createdAt = createdAt;
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
		this.salesOrg = salesOrg;
		this.requestedBy = requestedBy;
		this.division = division;
		this.distributionChannel = distributionChannel;
		this.returnReason = returnReason;
		this.shipToParty = shipToParty;
	}

<<<<<<< HEAD
=======
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	
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
