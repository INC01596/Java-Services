package com.incture.cherrywork.dto.workflow;

import java.util.Date;

import lombok.Data;

@Data
public class SalesVisitPlannerContextDto {
	private String definitionId;
	private String visitId;
	private String salesRepId;
	private String salesRepEmail;
	private String salesRepName;
	private Date visitCreatedAt;
	private Date plannedFor;
	private Date scheduledStartTime;
	private Date scheduledEndTime;
	private String custCode;

}

