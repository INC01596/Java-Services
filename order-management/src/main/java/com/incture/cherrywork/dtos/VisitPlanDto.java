package com.incture.cherrywork.dtos;

import java.util.ArrayList;
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

	private Date scheduledStartTime;

	private Date scheduledEndTime;

	private Date actualStartTime;

	private Date actualEndTime;

	private String custCode;

	private String custDesc;

	private String status;

	private String purpose;

	private String cost;

	private String visitSummary;

	private String currentStock;

	private String custPhone;

	private String custEmail;

	private List<CustomerAddressDto> custAddress = new ArrayList<>();

	private List<CustomerContactDto> customerContact = new ArrayList<>();

	private List<SalesVisitAttachmentDto> attachment = new ArrayList<>();

}
