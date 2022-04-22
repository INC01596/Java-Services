package com.incture.cherrywork.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Entity
@Table(name = "SalesVisitAttachment")
public class SalesVisitAttachment {

	@Id
	@Column(name = "attachmentId")
	private String attachmentId;

	//@Getter(value = AccessLevel.NONE)
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

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
