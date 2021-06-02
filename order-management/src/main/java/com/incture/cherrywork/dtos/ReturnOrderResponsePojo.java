package com.incture.cherrywork.dtos;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReturnOrderResponsePojo {
	private String refDocCat;
	private String roType;
	private String billToParty;
	private String salesDocument;
	private String payer;
	private String docType;
	private String hdrDelBlk;
	private String refDoc;
	private String customerPo;
	private String name;
	private String salesOrg;
	private String refNum;
	private String distrChan;
	private String division;
	private String soldToParty;
	private String shipToParty;
	private String flagRoSo;
	private String headerText;
	private String reasonOwner;
	private String remarks;
	private String divisionDesc;
	private String salesOrgDesc;
	private String distributionChannelDesc;
	private OrderHdrToOrderItem OrderHdrToOrderItem;	
	
	
}
