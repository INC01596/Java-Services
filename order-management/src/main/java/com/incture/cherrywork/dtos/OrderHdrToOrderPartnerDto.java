package com.incture.cherrywork.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderHdrToOrderPartnerDto {

	private String salesDocument;
	private String partnerRole;
	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String street2;
	private String street3;
	private String street5;
	private String district;
	private String differentCity;
	private String postalCode;
	private String city;
	private String region;
	private String country; 
	private String telephone; 
	private String mobilePhone;
	private String taxId; 
	private String bCode;
	private String bpNummr;
	public String getSalesDocument() {
		return salesDocument;
	}
	public void setSalesDocument(String salesDocument) {
		this.salesDocument = salesDocument;
	}
	public String getPartnerRole() {
		return partnerRole;
	}
	public void setPartnerRole(String partnerRole) {
		this.partnerRole = partnerRole;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
	public String getName4() {
		return name4;
	}
	public void setName4(String name4) {
		this.name4 = name4;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getStreet3() {
		return street3;
	}
	public void setStreet3(String street3) {
		this.street3 = street3;
	}
	public String getStreet5() {
		return street5;
	}
	public void setStreet5(String street5) {
		this.street5 = street5;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDifferentCity() {
		return differentCity;
	}
	public void setDifferentCity(String differentCity) {
		this.differentCity = differentCity;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getBCode() {
		return bCode;
	}
	public void setBCode(String bCode) {
		this.bCode = bCode;
	}
	public String getBpNummr() {
		return bpNummr;
	}
	public void setBpNummr(String bpNummr) {
		this.bpNummr = bpNummr;
	}
	
	
	
}
