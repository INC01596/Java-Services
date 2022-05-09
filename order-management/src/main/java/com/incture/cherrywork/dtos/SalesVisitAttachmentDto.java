package com.incture.cherrywork.dtos;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class SalesVisitAttachmentDto {

	private String attachmentId;

	private String attachmentType;

	private String uploadedBy;

	private Date uploadedOn;

	private String attachmentName;

	private String attachmentSize;

	private String visitId;

	private byte[] data;

}
