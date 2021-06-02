package com.incture.cherrywork.dtos;

import java.util.Date;

public class ReturnFilterDto {

	private Integer pageNo;
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	private String returnReqNumber;
	private String customerId;
	private Date createdDate;
	
	private String salesOrg;
	private String division;
	private String distributionChannel;
	
	public ReturnFilterDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnFilterDto( String returnReqNumber, String customerId, Date createdDate, String salesOrg,
			String division, String distributionChannel) {
		super();
		
		this.returnReqNumber = returnReqNumber;
		this.customerId = customerId;
		this.createdDate = createdDate;
		this.salesOrg = salesOrg;
		this.division = division;
		this.distributionChannel = distributionChannel;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
