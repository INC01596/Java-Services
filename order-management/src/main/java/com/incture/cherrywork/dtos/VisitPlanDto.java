package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class VisitPlanDto {

	private String visitId;

	private String salesRepName;

	private String salesRepId;

	private String salesRepEmail;

	private String salesRepPhone;

	private Date visitCretedAt;

	private Date plannedFor;

	private Date approvedAt;

	private Date completedAt;

	private Date closedAt;

	private String custCode;

	private String custDesc;

	private String status;

	private String purpose;

	private String cost;

	private String visitSummary;

	private String currentStock;

	private List<CustomerAddressDto> custAddress;

	private List<CustomerContactDto> customerContact;

	private List<SalesVisitAttachmentDto> attachment;

}
