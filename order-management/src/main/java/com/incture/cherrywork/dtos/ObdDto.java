package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

import lombok.Data;

@Data
public class ObdDto {

	private int pageNo;
	private String documentType;
	private String obdStatus;

	private String salesHeaderId;
	private String obdId;
	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;

	
	private String pgiStatus;
    private List<String>invoiceStatus ;


	
	

	
	
}
