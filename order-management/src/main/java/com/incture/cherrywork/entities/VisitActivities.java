package com.incture.cherrywork.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "VisitActivities")
public class VisitActivities {

	@Id
	@Column(name = "activityId")
	private String activityId;

	@Column(name = "docId")
	private String docId;

	@Column(name = "visitId")
	private String visitId;

	@Column(name = "activityType")
	private String activityType;

	@Column(name = "docStatus")
	private String docStatus;

	@Column(name = "docAmount")
	private Double docAmount;

	@Column(name = "docCreatedAt")
	private Date docCreatedAt;

}
