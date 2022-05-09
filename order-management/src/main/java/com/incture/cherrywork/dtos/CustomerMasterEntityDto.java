package com.incture.cherrywork.dtos;

import javax.persistence.Column;

import lombok.Data;

@Data
public class CustomerMasterEntityDto {

	private String custCode;

	private String custNumEx;

	private String oldCustCode;

	private String nameInTH;

	private String nameInEN;

	private String salesOrg;

	private String titleInLocalName;

	private String channel;

	private String streetHouseNum;

	private String city;

	private String postCode;

	private String province;

	private String phoneNum;

	private String email;

	private String languageID;

	private String division;

	private String dName;

	private String dcName;

	private String sOrgName;

}
