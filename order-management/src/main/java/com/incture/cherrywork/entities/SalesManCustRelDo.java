package com.incture.cherrywork.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "SALES_MAN_CUST_REL")
public class SalesManCustRelDo implements BaseDo {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRIMARY_ID")
	private String primaryId;

	@Column(name = "SAP_SALESMAN_CODE")
	private String sapSalesmanCode;

	@Column(name = "SALES_REP_NAME")
	private String salesRepName;

	@Column(name = "MANAGER_CODE")
	private String managerCode;

	@Column(name = "MANAGER_NAME")
	private String managerName;

	@Column(name = "SALES_TEAM")
	private String salesTeam;

	@Column(name = "CUST_NAME")
	private String custName;

	@Column(name = "SAP_CUST_CODE")
	private String sapCustCode;

	@Column(name = "CUST_TYPE")
	private String custType;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "CUST_GROUP_CODE")
	private String custGroupCode;

	@Column(name = "CUST_GROUP_NAME")
	private String custGroupName;

	@Column(name = "TRADE_TYPE_NAME")
	private String tradeTypeName;

	@Column(name = "TRADE_TYPE_ID")
	private String tradeTypeId;

	@Column(name = "REGION_CODE")
	private String regionCode;

	@Column(name = "REGION_NAME")
	private String regionName;

	@Column(name = "AREA_CODE")
	private String areaCode;

	@Column(name = "AREA_NAME")
	private String areaName;

	@Column(name = "TERRITORY")
	private String territory;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "APPROVAL_1_CODE")
	private String approval1Code;

	@Column(name = "APPROVAL_1_NAME")
	private String approval1Name;

	@Column(name = "APPROVAL_2_CODE")
	private String approval2Code;

	@Column(name = "APPROVAL_2_NAME")
	private String approval2Name;

	@Column(name = "APPROVAL_3_CODE")
	private String approval3Code;

	@Column(name = "APPROVAL_3_NAME")
	private String approval3Name;

	@Column(name = "SALES_ORGANIZATION")
	private String salesOrganization;

	@Column(name = "DISTRIBUTION_CHANNEL")
	private String distributionChannel;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "CONTROLLING_AREA")
	private String controllingArea;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="LONGITUDE")
	private Double Longitude;

	@Override
	public Object getPrimaryKey() {
		return primaryId;
	}

}
