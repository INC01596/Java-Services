package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ODataCustomerDto {
	@Expose
	@SerializedName("custNumEx")
	private String custNumEx;
	
	@Expose
	@SerializedName("custCode")
	private String custCode;
	
	@Expose
	@SerializedName("oldCustCode")
	private String oldCustCode;
	
	@Expose
	@SerializedName("nameInTH")
	private String nameInTH;
	
	@Expose
	@SerializedName("nameInEN")
	private String nameInEN;
	
	@Expose
	@SerializedName("salesOrg")
	private String salesOrg;
	
	@Expose
	@SerializedName("titleInLocalName")
	private String titleInLocalName;
	
	@Expose
	@SerializedName("channel")
	private String channel;
	
	@Expose
	@SerializedName("streetHouseNum")
	private String streetHouseNum;
	
	@Expose
	@SerializedName("city")
	private String city;
	
	@Expose
	@SerializedName("postCode")
	private String postCode;
	
	@Expose
	@SerializedName("province")
	private String province;
	
	@Expose
	@SerializedName("phoneNum")
	private String phoneNum;
	
	@Expose
	@SerializedName("languageID")
	private String languageID;
	
	@Expose
	@SerializedName("division")
	private String division;
	
	@Expose
	@SerializedName("dName")
	private String dName;
	
	@Expose
	@SerializedName("dcName")
	private String dcName;
	
	@Expose
	@SerializedName("sOrgName")
	private String sOrgName;

	public String getCustNumEx() {
		return custNumEx;
	}

	public void setCustNumEx(String custNumEx) {
		this.custNumEx = custNumEx;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getOldCustCode() {
		return oldCustCode;
	}

	public void setOldCustCode(String oldCustCode) {
		this.oldCustCode = oldCustCode;
	}

	public String getNameInTH() {
		return nameInTH;
	}

	public void setNameInTH(String nameInTH) {
		this.nameInTH = nameInTH;
	}

	public String getNameInEN() {
		return nameInEN;
	}

	public void setNameInEN(String nameInEN) {
		this.nameInEN = nameInEN;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getTitleInLocalName() {
		return titleInLocalName;
	}

	public void setTitleInLocalName(String titleInLocalName) {
		this.titleInLocalName = titleInLocalName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStreetHouseNum() {
		return streetHouseNum;
	}

	public void setStreetHouseNum(String streetHouseNum) {
		this.streetHouseNum = streetHouseNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getLanguageID() {
		return languageID;
	}

	public void setLanguageID(String languageID) {
		this.languageID = languageID;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public String getsOrgName() {
		return sOrgName;
	}

	public void setsOrgName(String sOrgName) {
		this.sOrgName = sOrgName;
	}

	public ODataCustomerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ODataCustomerDto(String custNumEx, String custCode, String oldCustCode, String nameInTH, String nameInEN,
			String salesOrg, String titleInLocalName, String channel, String streetHouseNum, String city,
			String postCode, String province, String phoneNum, String languageID, String division, String dName,
			String dcName, String sOrgName) {
		super();
		this.custNumEx = custNumEx;
		this.custCode = custCode;
		this.oldCustCode = oldCustCode;
		this.nameInTH = nameInTH;
		this.nameInEN = nameInEN;
		this.salesOrg = salesOrg;
		this.titleInLocalName = titleInLocalName;
		this.channel = channel;
		this.streetHouseNum = streetHouseNum;
		this.city = city;
		this.postCode = postCode;
		this.province = province;
		this.phoneNum = phoneNum;
		this.languageID = languageID;
		this.division = division;
		this.dName = dName;
		this.dcName = dcName;
		this.sOrgName = sOrgName;
	}

	@Override
	public String toString() {
		return "ODataCustomerDto [custNumEx=" + custNumEx + ", custCode=" + custCode + ", oldCustCode=" + oldCustCode
				+ ", nameInTH=" + nameInTH + ", nameInEN=" + nameInEN + ", salesOrg=" + salesOrg + ", titleInLocalName="
				+ titleInLocalName + ", channel=" + channel + ", streetHouseNum=" + streetHouseNum + ", city=" + city
				+ ", postCode=" + postCode + ", province=" + province + ", phoneNum=" + phoneNum + ", languageID="
				+ languageID + ", division=" + division + ", dName=" + dName + ", dcName=" + dcName + ", sOrgName="
				+ sOrgName + "]";
	}
	
	
	
	
}
