package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

import lombok.Data;
@Data
public class InvoDto {

	private int pageNo;
	private String documentType;
	private String invId;
	private EnOrderActionStatus documentProcessStatus;
	private String createdBy;
	private String salesHeaderId;
	private String obdId;
	private String invoiceStatus;
	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;
	

}