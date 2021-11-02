package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "CW_ONEAPP_ATTACHMENT")
public class AttachmentDo {

	@Id
	@Column(name = "DOCUMENT_ID", columnDefinition = "NVARCHAR(36)")
	private String documentId;

	@Column(name = "ENTITY_ID", columnDefinition = "NVARCHAR(36)")
	private String entityId;

	@Column(name = "ENTITY_TYPE", columnDefinition = "NVARCHAR(100)")
	private String entityType;

	@Column(name = "DOCUMENT_NAME", columnDefinition = "NVARCHAR(200)")
	private String documentName;

	@Column(name = "CONTENT_TYPE", columnDefinition = "NVARCHAR(200)")
	private String contentType;

	@Lob
	@Column(name = "FILE_CONTENT")
	private byte[] fileContent;

}

