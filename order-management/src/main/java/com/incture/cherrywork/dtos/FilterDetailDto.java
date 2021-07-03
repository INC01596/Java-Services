package com.incture.cherrywork.dtos;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class FilterDetailDto {

	private BigInteger createdAtStart; // H
	private BigInteger createdAtEnd; // H
	private String createdBy; // H
	private List<String> distributionChannelList; // H DAC
	private List<String> divisionList; // H DAC
	private List<String> salesOrgList; // H DAC
	private String returnReqNum; // H
	private String orderType; // H DAC
	private String soldToParty; // H DAC (Customer Code)
	private String shipToParty; // H
	private String refDocNum; // I
	private String materialGroup; // I DAC
	private String materialGroup4; // I DAC
	private String loginInUserId; // UI
	private String projectCode; // UI
	public BigInteger getCreatedAtStart() {
		return createdAtStart;
	}
	public void setCreatedAtStart(BigInteger createdAtStart) {
		this.createdAtStart = createdAtStart;
	}
	public BigInteger getCreatedAtEnd() {
		return createdAtEnd;
	}
	public void setCreatedAtEnd(BigInteger createdAtEnd) {
		this.createdAtEnd = createdAtEnd;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<String> getDistributionChannelList() {
		return distributionChannelList;
	}
	public void setDistributionChannelList(List<String> distributionChannelList) {
		this.distributionChannelList = distributionChannelList;
	}
	public List<String> getDivisionList() {
		return divisionList;
	}
	public void setDivisionList(List<String> divisionList) {
		this.divisionList = divisionList;
	}
	public List<String> getSalesOrgList() {
		return salesOrgList;
	}
	public void setSalesOrgList(List<String> salesOrgList) {
		this.salesOrgList = salesOrgList;
	}
	public String getReturnReqNum() {
		return returnReqNum;
	}
	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getRefDocNum() {
		return refDocNum;
	}
	public void setRefDocNum(String refDocNum) {
		this.refDocNum = refDocNum;
	}
	public String getMaterialGroup() {
		return materialGroup;
	}
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	public String getMaterialGroup4() {
		return materialGroup4;
	}
	public void setMaterialGroup4(String materialGroup4) {
		this.materialGroup4 = materialGroup4;
	}
	public String getLoginInUserId() {
		return loginInUserId;
	}
	public void setLoginInUserId(String loginInUserId) {
		this.loginInUserId = loginInUserId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	

}