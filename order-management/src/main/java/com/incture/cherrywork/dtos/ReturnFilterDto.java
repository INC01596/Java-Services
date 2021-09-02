package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReturnFilterDto {

	private Integer pageNo;
	private String returnReqNumber;
	private List<String> customerId;
	private Date createdAt;
	private String salesOrg;
	private String requestedBy;
	private String division;
	private String distributionChannel;
	private String returnReason;
	private String shipToParty;

	private Date createdDateFrom;
	private Date createdDateTo;

	private String docVersion;
	private String orderReason;

}
