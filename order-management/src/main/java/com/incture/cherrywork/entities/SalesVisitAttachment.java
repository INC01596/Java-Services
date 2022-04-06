package com.incture.cherrywork.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity(name = "SalesVisitAttachment")
public class SalesVisitAttachment {

	@Id
	@Column(name = "attachmentId")
	private String attachmentId;

	@Column(name = "visitId")
	private String visitId;

	@Column(name = "attachmentType")
	private String attachmentType;

	@Column(name = "uploadedBy")
	private String uploadedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadedOn")
	private Date uploadedOn;

	@Column(name = "data")
	private byte[] data;

}
