package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrackingDto {

	private String title;

	private Date approvedDate;

	private String orderStatus;

	private String pendingWith;

	private Long orderStatusId;

	private String createdBy;

	private String createdByName;

	private Date createdDate;

	private String approverComments;

	private String approverName;

	private String approverId;

}
