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

	
}
