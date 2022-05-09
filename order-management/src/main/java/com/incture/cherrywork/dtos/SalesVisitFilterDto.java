package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class SalesVisitFilterDto {

	private String salesRepId;
	private String visitId;
	private Date visitPlannedDateFrom;
	private Date visitPlannedDateTo;
	private Date actualVisitDateFrom;
	private Date actualVisitDateTo;
	private Date visitCompletedAtFrom;
	private Date visitCompletedAtTo;
	private Date visitClosedAtFrom;
	private Date visitClosedAtTo;
	private String status;
	private String custCode;

}
