package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CustomerMaster")
public class CustomerMaster {

	@Id
	@Column(name = "custCode")
	private String custCode;

	@Column(name = "custNumEx")
	private String custNumEx;

	@Column(name = "oldCustCode")
	private String oldCustCode;

	@Column(name = "nameInTH")
	private String nameInTH;

	@Column(name = "nameInEN")
	private String nameInEN;

	@Column(name = "salesOrg")
	private String salesOrg;

	@Column(name = "titleInLocalName")
	private String titleInLocalName;

	@Column(name = "channel")
	private String channel;

	@Column(name = "streetHouseNum")
	private String streetHouseNum;

	@Column(name = "city")
	private String city;

	@Column(name = "postCode")
	private String postCode;

	@Column(name = "province")
	private String province;

	@Column(name = "phoneNum")
	private String phoneNum;

	@Column(name = "email")
	private String custEmail;

	@Column(name = "languageID")
	private String languageID;

	@Column(name = "division")
	private String division;

	@Column(name = "dName")
	private String dName;

	@Column(name = "dcName")
	private String dcName;

	@Column(name = "sOrgName")
	private String sOrgName;

}
