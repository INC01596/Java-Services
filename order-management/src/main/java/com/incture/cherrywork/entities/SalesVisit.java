package com.incture.cherrywork.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "SALES_VISIT")
@Data
public class SalesVisit {

	@Id
	@Column(name = "visit_Id", length = 30)
	private String visitId;

	@Column(name = "salesRepName")
	private String salesRepName;

	@Column(name = "salesRepId")
	private String salesRepId;

	@Column(name = "salesRepEmail")
	private String salesRepEmail;

	@Column(name = "salesRepPhone")
	private String salesRepPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "visitCreatedAt")
	private Date visitCretedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plannedFor")
	private Date plannedFor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approvedAt")
	private Date approvedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completedAt")
	private Date completedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduledStartTime")
	private Date scheduledStartTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduledEndTime")
	private Date scheduledEndTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actualStartTime")
	private Date actualStartTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actualEndTime")
	private Date actualEndTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closedAt")
	private Date closedAt;

	@Column(name = "custCode")
	private String custCode;

	@Column(name = "custDesc")
	private String custDesc;

	@Column(name = "status")
	private String status;

	@Column(name = "purpose")
	private String purpose;

	@Column(name = "cost")
	private String cost;

	@Column(name = "visitSummary")
	private String visitSummary;

	@Column(name = "custPhone")
	private String custPhone;

	@Column(name = "custLocation")
	private String custLocation;

	@Column(name = "custEmail")
	private String custEmail;

	@Column(name = "taskStatus")
	private String taskStatus;

	@Column(name = "returnReqNum")
	private String returnReqNum;

	@Column(name = "level")
	private String level;

	@Column(name = "rejectionReason")
	private String rejectionReason;

	@Column(name = "onHoldReason")
	private String onHoldReason;

	@Column(name = "approvalStatus")
	private String approvalStatus;

	@Column(name = "taskInstanceId")
	private String taskInstanceId;

	@OneToMany(mappedBy = "salesVisit", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<CustomerContact> customerContact;

	@OneToMany(mappedBy = "salesVisit", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<CustomerAddress> custAddress;
}
