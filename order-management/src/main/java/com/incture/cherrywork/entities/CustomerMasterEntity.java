package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer_Master")
public class CustomerMasterEntity {
	
	@Id
	@Column(name = "Customer_Number")
	private String custCode;
	
	@Column(name = "Dac_Parameter")
	private String custNumEx;
	
	@Column(name = "Old_Customer_Code")
	private String oldCustCode;
	
	@Column(name = "Name")
	private String nameInTH;
	
	@Column(name = "Name_In_English")
	private String nameInEN;
	
	@Column(name = "Sales_Organisation")
	private String salesOrg;
	
	@Column(name = "Title_In_Local_Name")
	private String titleInLocalName;
	
	@Column(name = "Distribution_Channel")
	private String channel;
	
	@Column(name = "Street_House_Num")
	private String streetHouseNum;
	
	@Column(name = "City")
	private String city;
	
	@Column(name = "Postal_Code")
	private String postCode;
	
	@Column(name = "Province")
	private String province;
	
	@Column(name = "Phone_Number")
	private String phoneNum;
	
	@Column(name = "Language")
	private String languageID;
	
	@Column(name = "Division")
	private String division;
	
	@Column(name = "D_Name")
	private String dName;
	
	@Column(name = "Dc_Name")
	private String dcName;
	
	@Column(name = "Sales_Org_Name")
	private String sOrgName;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustNumEx() {
		return custNumEx;
	}

	public void setCustNumEx(String custNumEx) {
		this.custNumEx = custNumEx;
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

	public CustomerMasterEntity(String custCode, String custNumEx, String oldCustCode, String nameInTH, String nameInEN,
			String salesOrg, String titleInLocalName, String channel, String streetHouseNum, String city,
			String postCode, String province, String phoneNum, String languageID, String division, String dName,
			String dcName, String sOrgName) {
		super();
		this.custCode = custCode;
		this.custNumEx = custNumEx;
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

	public CustomerMasterEntity() {
		super();
	}

	@Override
	public String toString() {
		return "CustomerMasterEntity [custCode=" + custCode + ", custNumEx=" + custNumEx + ", oldCustCode="
				+ oldCustCode + ", nameInTH=" + nameInTH + ", nameInEN=" + nameInEN + ", salesOrg=" + salesOrg
				+ ", titleInLocalName=" + titleInLocalName + ", channel=" + channel + ", streetHouseNum="
				+ streetHouseNum + ", city=" + city + ", postCode=" + postCode + ", province=" + province
				+ ", phoneNum=" + phoneNum + ", languageID=" + languageID + ", division=" + division + ", dName="
				+ dName + ", dcName=" + dcName + ", sOrgName=" + sOrgName + "]";
	}
	
	
	
	
	
}
