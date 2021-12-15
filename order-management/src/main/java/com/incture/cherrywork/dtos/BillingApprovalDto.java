package com.incture.cherrywork.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BillingApprovalDto {

	private String transactionId;
	private String approvalStatus;
	private String approverId;
	private String comments;
	private String requester;
	private String rejectionReason;
	private String approverName;

}
