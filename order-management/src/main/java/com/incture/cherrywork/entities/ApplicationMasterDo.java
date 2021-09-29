package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "CW_ONEAPP_EMAIL_APPLICATION")
public class ApplicationMasterDo {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ID", columnDefinition = "NVARCHAR(36)")
	private String id;

	@Column(name = "APPLICATION", columnDefinition = "NVARCHAR(100)")
	private String application;

	@Column(name = "APPLICATION_DESC", columnDefinition = "NVARCHAR(100)")
	private String applicationDesc;

	@Column(name = "PROCESS", columnDefinition = "NVARCHAR(100)")
	private String process;
	@Column(name = "PROCESS_DESC", columnDefinition = "NVARCHAR(100)")
	private String processDesc;
	@Column(name = "ENTITY", columnDefinition = "NVARCHAR(100)")
	private String entity;

	@Column(name = "ENTITY_DESC", columnDefinition = "NVARCHAR(100)")
	private String entityDesc;

}
