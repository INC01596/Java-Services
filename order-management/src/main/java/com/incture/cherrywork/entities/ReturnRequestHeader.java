
package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
// @GenericGenerator(name = "RR", strategy =
// "com.incture.sequenceGenerators.ReturnRequestIdGen", parameters = {
// @Parameter(name = ReturnRequestIdGen.INCREMENT_PARAM, value = "1"),
// @Parameter(name = ReturnRequestIdGen.VALUE_PREFIX_PARAMETER, value = "CR"),
// @Parameter(name = ReturnRequestIdGen.NUMBER_FORMAT_PARAMETER, value = "%04d")
// })
@Table(name = "RETURN_REQUEST")
public @Data class ReturnRequestHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RR")
	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;

	@Column(name = "RO_TYPE")
	private String roType;

	@Column(name = "REQUEST_TYPE")
	private String requestType;

	@Column(name = "ORDER_TYPE")
	private String orderType;

	@Column(name = "SALES_ORG")
	private String salesOrg;

	@Column(name = "MAPPING_ID")
	private String mappingId;

	@Column(name = "DISTRIBUTION_CHANNEL")
	private String distributionChannel;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "SOLD_TO_PARTY")
	private String soldToParty;

	@Column(name = "SHIP_TO_PARTY")
	private String shipToParty;

	@Column(name = "BILL_TO_PARTY")
	private String billToParty;

	@Column(name = "PAYER")
	private String payer;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "CONTACT_DIVISION")
	private String contactDivision;

	@Column(name = "CONTACT_TELEPHONE", columnDefinition = "TEXT")
	private String contactTelephone;

	@Column(name = "REFERENCE_NUM")
	private String referenceNum;

	@Column(name = "ORDER_REASON")
	private String orderReason;

	@Column(name = "ORDER_REASON_TEXT")
	private String orderReasonText;

	@Column(name = "REASON_OWNER")
	private String reasonOwner;

	@Column(name = "REASON_OWNER_DESC")
	private String reasonOwnerDesc;

	@Column(name = "REQUEST_REMARK", columnDefinition = "TEXT")
	private String requestRemark;

	@Column(name = "WORKFLOW_INSTANCE")
	private String workflowInstance;

	@Column(name = "OVERALL_WORKFLOW_STATUS")
	private String overallWorkflowStatus;

	@Column(name = "REQUESTED_BY")
	private String requestedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@Column(name = "UPDATED_AT")
	private String updatedAt;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "PROCESSING_STATUS")
	private String processingStatus;

	@Column(name = "LOGISTICAL_STATUS")
	private String logisticalStatus;

	@Column(name = "CREATION_STATUS")
	private boolean creationStatus;

	@Column(name = "CREATION_MESSAGE", columnDefinition = "TEXT")
	private String message;

	@Column(name = "DOC_VERSION")
	private String docVersion;

	@Column(name = "RO_TYPE_TEXT")
	private String roTypeText;

	@Column(name = "TOTAL_RO_AMOUNT")
	private String totalRoAmount;

	@Column(name = "REQUESTOR_NAME")
	private String requestorName;

	@Column(name = "ORDER_TYPE_TEXT")
	private String orderTypeText;

	@Column(name = "EXCHANGE_ORDER_TYPE")
	private String exchangeOrderType;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "CUSTOMER_PO")
	private String customerPo;

	@Column(name = "SHIP_TO_PARTY_DESC")
	private String soldToPartyDesc;// os

	@Column(name = "SOLD_TO_PARTY_DESC")
	private String shipToPartyDesc;// os

	@Column(name = "BILL_TO_PARTY_DESC")
	private String billToDesc;// os
	@Column(name = "PAYER_DESC")
	private String payerDesc;// os

	@Column(name = "REQUESTOR_EMAIL")
	private String requestorEmail;// os

	@Column(name = "RETURN_TOTAL_AMOUNT")
	private String returnTotalAmt;// os

	@Column(name = "FLAG_RO_SO")
	private String flagRoSo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REF_INV_DATE")
	private Date referenceInvDate;

	@Column(name = "EMAIL_TRIGGER")
	private Boolean emailTrigger;

	@Column(name = "DIVISION_DESC")
	private String divisionDesc;

	@Column(name = "SALES_ORG_DESC")
	private String salesOrgDesc;

	@Column(name = "DISTRIBUTION_CHANNEL_DESC")
	private String distributionChannelDesc;

	@Column(name = "DOCUMENT_URL")
	private String documentUrl;

	@Column(name = "SMS_TRIGGER")
	private Boolean smsTrigger;

	@Column(name = "SMS_FROM")
	private String smsFrom;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_BY_DESC")
	private String createdByDesc;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
