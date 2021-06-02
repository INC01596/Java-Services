package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "ADDRESS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@TableGenerator(name="tab", initialValue=0, allocationSize=50)
public class Address {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ADRESS_ID")
	private String id;

	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;
	
	
	@Column(name = "ADDRESS_NAME_3")
	private String addressName3;

	@Column(name = "ADDRES_STR_HOU_NUM")
	private String addressStrHouNum;

	@Column(name = "ADDRESSSTREET2")
	private String addressStreet2;

	@Column(name = "ADDRESSS_STREET_5")
	private String addressStreet5;

	@Column(name = "CITY")
	private String city;

	@Column(name = "DIFFERENT_CITY")
	private String differentCity;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "INVOICE_NUM")
	private String invoiceNum;

	@Column(name = "PARTNER_NAME")
	private String partnerName;

	@Column(name = "PARTNERNUM")
	private String partnerNum;

	@Column(name = "PATNER_ROLE")
	private String partnerRole;
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "TELEPHONE")
	private String telephone;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "EMAIL")
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReturnReqNum() {
		return returnReqNum;
	}

	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}

	public String getAddressName3() {
		return addressName3;
	}

	public void setAddressName3(String addressName3) {
		this.addressName3 = addressName3;
	}

	public String getAddressStrHouNum() {
		return addressStrHouNum;
	}

	public void setAddressStrHouNum(String addressStrHouNum) {
		this.addressStrHouNum = addressStrHouNum;
	}

	public String getAddressStreet2() {
		return addressStreet2;
	}

	public void setAddressStreet2(String addressStreet2) {
		this.addressStreet2 = addressStreet2;
	}

	public String getAddressStreet5() {
		return addressStreet5;
	}

	public void setAddressStreet5(String addressStreet5) {
		this.addressStreet5 = addressStreet5;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDifferentCity() {
		return differentCity;
	}

	public void setDifferentCity(String differentCity) {
		this.differentCity = differentCity;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}

	public String getPartnerRole() {
		return partnerRole;
	}

	public void setPartnerRole(String partnerRole) {
		this.partnerRole = partnerRole;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
