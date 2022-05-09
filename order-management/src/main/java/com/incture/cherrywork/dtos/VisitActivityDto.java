package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class VisitActivityDto {

	private String activityId;

	private String docId;

	private String visitId;

	private String activityType;

	private String docStatus;

	private Double docAmount;

	private Date docCreatedAt;

}
