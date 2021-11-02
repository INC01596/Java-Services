package com.incture.cherrywork.dtos;

import java.util.List;

public class CustomerMasterFilterDto {
	private List<String> dac;
	private String customerNum;
	private String customerName;
	private List<String> distributionChannel;
	private List<String> division;
	private List<String> salesOrg;
	public List<String> getDac() {
		return dac;
	}
	public void setDac(List<String> dac) {
		this.dac = dac;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<String> getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(List<String> distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public List<String> getDivision() {
		return division;
	}
	public void setDivision(List<String> division) {
		this.division = division;
	}
	public List<String> getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(List<String> salesOrg) {
		this.salesOrg = salesOrg;
	}
	public CustomerMasterFilterDto() {
		super();
	}
	public CustomerMasterFilterDto(List<String> dac, String customerNum, String customerName,
			List<String> distributionChannel, List<String> division, List<String> salesOrg) {
		super();
		this.dac = dac;
		this.customerNum = customerNum;
		this.customerName = customerName;
		this.distributionChannel = distributionChannel;
		this.division = division;
		this.salesOrg = salesOrg;
	}
	@Override
	public String toString() {
		return "CustomerMasterFilterDto [dac=" + dac + ", customerNum=" + customerNum + ", customerName=" + customerName
				+ ", distributionChannel=" + distributionChannel + ", division=" + division + ", salesOrg=" + salesOrg
				+ "]";
	}
	
	
	
}
