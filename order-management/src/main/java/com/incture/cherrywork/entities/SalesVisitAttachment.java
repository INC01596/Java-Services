package com.incture.cherrywork.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "SalesVisitAttachment")
public class SalesVisitAttachment {

	@Id
	@Column(name = "attachmentId")
	private String attachmentId;

	@Column(name = "attachmentType")
	private String attachmentType;

	@Column(name = "uploadedBy")
	private String uploadedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadedOn")
	private Date uploadedOn;

	@Column(name = "data")
	private byte[] data;

	@ToString.Exclude
	@JsonBackReference("sales-visit-attachment")
	@ManyToOne
	@JoinColumn(name = "visitId", nullable = false, referencedColumnName = "visitId")
	private SalesVisit salesVisit;

}
