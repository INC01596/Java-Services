package com.incture.cherrywork.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity(name = "SALES_VISIT")
@Data
public class SalesVisit {

	@Id
	@Column(name = "visitId", length = 30)
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

	@JsonManagedReference("sales-visit-customer")
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<CustomerContact> customerContact;

	@JsonManagedReference("sales-visit-attachment")
	@OneToMany(mappedBy = "attachmentId", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<SalesVisitAttachment> attachment;
	
	@JsonManagedReference("sales-visit-address")
	@OneToMany(mappedBy = "customerAddressId", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<CustomerAddress> custAddress;
}
