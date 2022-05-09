package com.incture.cherrywork.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "SalesVisitAttachment")
public class SalesVisitAttachment {

	@Id
	@Column(name = "attachmentId")
	private String attachmentId;

	@Column(name = "viisitId")
	private String visitId;

	@Column(name = "attachmentType")
	private String attachmentType;

	@Column(name = "attachmentName")
	private String attachmentName;

	@Column(name = "attachmentSize")
	private String attachmentSize;

	@Column(name = "uploadedBy")
	private String uploadedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadedOn")
	private Date uploadedOn;

	@Lob
	@Column(name = "data")
	private byte[] data;

}
