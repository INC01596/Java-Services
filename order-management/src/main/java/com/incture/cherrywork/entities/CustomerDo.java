package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "CUSTOMER")
public class CustomerDo implements BaseDo {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "CUST_NAME")
	private String custName;

	@Column(name = "CUST_CITY")
	private String custCity;

	@Column(name = "CUST_COUNTRY")
	private String custCountry;

	@Column(name = "CUST_POSTAL_CODE")
	private String custPostalCode;

	@Column(name = "CUST_CATEGORY")
	private String custCategory;

	@Column(name = "CUST_CREDIT_LIMIT")
	private String custCreditLimit;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "TELE_EXTENSION")
	private String teleExtension;
	
	@Column(name = "TEL_FAX")
	private String telFax;
	
	@Column(name = "FAX_EXTENSION")
	private String faxExtension;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "CR_FLAG")
	private Boolean crFlag;
	
	@Column(name = "SP_PHONE_NUMBER")
	private String spPhoneNumber;
	
	@Column(name = "SP_TELE_EXTENSION")
	private String spTeleExtension;
	
	@Column(name = "SP_TEL_FAX")
	private String spTelFax;
	
	@Column(name = "SP_FAX_EXTENSION")
	private String spFaxExtension;
	
	@Column(name = "SP_EMAIL")
	private String spEmail;

	@Column(name = "SP_CUSTID")
	private String spCustId;

	@Column(name = "SP_NAME")
	private String spName;

	@Column(name = "SP_CITY")
	private String spCity;

	@Column(name = "SP_POSTAL_CODE")
	private String spPostalCode;

	/*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
	private List<ShippingAddressDo> listOfShippingAddress;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
	private List<ControllingAreaDo> listOfControllingAreas;*/

	@Override
	public Object getPrimaryKey() {
		return custId;
	}

}
