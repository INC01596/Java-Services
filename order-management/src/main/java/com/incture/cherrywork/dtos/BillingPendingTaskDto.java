package com.incture.cherrywork.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BillingPendingTaskDto {

	private String transactionId;
	private List<InvoiceDto> listInvoiceDto;
	private String requester;
	private String customerId;
	private String modeOfPayment;
	private String bankName;
	private BigDecimal amount;
	private String status;
	private String comment;
	private String approverComments;
	private String rejectionReason;
	private String acknowledgeComments;
	private String salesRepName;
	private String customerName;
	private String currency;
	private String salesRepPhoneNo;
	private String customerPhoneNo;
	private String chequeNumber;
	private List<AttachmentDto> attachmentList;
	private Date paymentDate;
	private String branch;
	private String approverName;

}