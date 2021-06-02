package com.incture.cherrywork.dtos;



import java.math.BigInteger;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class FilterDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status; // This is not mapped in UI right now 10
	private String customerCode;// sold to party -------------- 10
	private String salesDocNumInitial; // this should be a range 100
	private String salesDocNumEnd; // 100
	private String materialGroupFor;// materialgroup4 --------- 9
	private String distributionChannel; // -------------------- 4
	private BigInteger initialDate; // sales doc creation date from
	private BigInteger endDate;// sales doc creation date to
	private String salesOrg; // 4
	private String division; // 2
	private String materialGroup; // Sales Item level field 9
	private String customerPo; // 10
	private String itemDlvBlock; // 100
	private String shipToParty; // Item level field
	private String headerDlvBlock; // Header
	private String sapMaterialNum; // Item
	private String userPID;//login user PID

	
	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSalesDocNumInitial() {
		return salesDocNumInitial;
	}

	public void setSalesDocNumInitial(String salesDocNumInitial) {
		this.salesDocNumInitial = salesDocNumInitial;
	}

	public String getSalesDocNumEnd() {
		return salesDocNumEnd;
	}

	public void setSalesDocNumEnd(String salesDocNumEnd) {
		this.salesDocNumEnd = salesDocNumEnd;
	}

	public String getMaterialGroupFor() {
		return materialGroupFor;
	}

	public void setMaterialGroupFor(String materialGroupFor) {
		this.materialGroupFor = materialGroupFor;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public BigInteger getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(BigInteger initialDate) {
		this.initialDate = initialDate;
	}

	public BigInteger getEndDate() {
		return endDate;
	}

	public void setEndDate(BigInteger endDate) {
		this.endDate = endDate;
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

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getItemDlvBlock() {
		return itemDlvBlock;
	}

	public void setItemDlvBlock(String itemDlvBlock) {
		this.itemDlvBlock = itemDlvBlock;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public String getHeaderDlvBlock() {
		return headerDlvBlock;
	}

	public void setHeaderDlvBlock(String headerDlvBlock) {
		this.headerDlvBlock = headerDlvBlock;
	}

	public String getSapMaterialNum() {
		return sapMaterialNum;
	}

	public void setSapMaterialNum(String sapMaterialNum) {
		this.sapMaterialNum = sapMaterialNum;
	}

	public String getUserPID() {
		return userPID;
	}

	public void setUserPID(String userPID) {
		this.userPID = userPID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
