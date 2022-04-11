package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class SalesVisitAttachmentDto {

	private String attachmentId;

	private String visitId;

	private String attachmentType;

	private String uploadedBy;

	private Date uploadedOn;

	private byte[] data;

}
