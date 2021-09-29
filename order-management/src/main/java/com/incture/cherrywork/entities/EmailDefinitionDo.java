package com.incture.cherrywork.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "CW_ONEAPP_EMAIL_DEFINITION")
public class EmailDefinitionDo {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "EMAIL_DEFINITION_ID", columnDefinition = "NVARCHAR(36)")
	private String emailDefinitionId;

	@Column(name = "APPLICATION", columnDefinition = "NVARCHAR(100)")
	private String application;
	@Column(name = "APPLICATION_DESC", columnDefinition = "NVARCHAR(100)")
	private String applicationDesc;

	@Column(name = "PROCESS", columnDefinition = "NVARCHAR(100)")
	private String process;
	@Column(name = "PROCESS_DESC", columnDefinition = "NVARCHAR(100)")
	private String processDesc;

	@Column(name = "ENTITY_NAME", columnDefinition = "NVARCHAR(100)")
	private String entity;

	@Column(name = "ENTITY_DESC", columnDefinition = "NVARCHAR(100)")
	private String entityDesc;

	@Column(name = "NAME", columnDefinition = "NVARCHAR(100)")
	private String name;

	@Column(name = "SUBJECT", columnDefinition = "NVARCHAR(100)")
	private String subject;

	@Lob
	@Column(name = "CONTENT")
	private String content;

	@Column(name = "SIGNATURE", columnDefinition = "NVARCHAR(200)")
	private String signature;

	@Column(name = "FROM_ADDRESS", columnDefinition = "NVARCHAR(100)")
	private String fromAddress;

	@Column(name = "STATUS", columnDefinition = "NVARCHAR(50)")
	private String status;

	@Column(name = "BCC_ALLOWED", columnDefinition = "BOOLEAN")
	private Boolean bccAllowed;

	@Column(name = "CREATED_BY", columnDefinition = "NVARCHAR(200)")
	private String createdBy;

	@Column(name = "CREATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "UPDATED_BY", columnDefinition = "NVARCHAR(200)")
	private String updatedBy;

	@Column(name = "UPDATED_ON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

}
