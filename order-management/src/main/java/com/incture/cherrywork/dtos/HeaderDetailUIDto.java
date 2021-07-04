package com.incture.cherrywork.dtos;
//<----------Sandeep Kumar---------------------------------->

import java.sql.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

import lombok.Data;

@Data
public class HeaderDetailUIDto {

	private String salesHeaderId;
	private String documentType;
	private List<EnOrderActionStatus> documentProcessStatus;
	private String createdBy;
	private Date createdDateFrom;
	private Date createdDateTo;
	private Boolean isOpen;
	private List<String> stpId;
	private String customer;
	private String shipToParty;
		private Boolean isCustomer;
	private List<String> plant;
	private Date requestDeliveryDateFrom;
	private Date requestDeliveryDateTo;
	private String salesGroup;
	
	
	
	
	public int pageNo;
	
	}

	

