package com.incture.cherrywork.entities.workflow;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.incture.cherrywork.entities.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALES_DOC_HEADER")
public @Data class SalesDocHeaderDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SALES_ORDER_NUM", length = 100)
	private String salesOrderNum;

	@JsonManagedReference("task_list-task")
	@OneToMany(mappedBy = "salesDocItemKey.salesDocHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	@OrderBy("salesDocItemKey.salesItemOrderNo")
	private List<SalesDocItemDo> salesDocItemList;

	// @OneToOne
	// @JoinColumn(name = "REQUEST_ID", nullable = false, referencedColumnName =
	// "REQUEST_ID", unique = true)
	// private RequestMasterDo reqMaster;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@Column(name = "SALES_ORDER_DATE")
	private BigInteger salesOrderDate;

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "ORDER_CATEGORY", length = 20)
	private String orderCategory;

	@Column(name = "ORDER_TYPE", length = 20)
	private String orderType;

	@Column(name = "SALES_ORG", length = 20)
	private String salesOrg;

	@Column(name = "DISTRIBUTION_CHANNEL", length = 20)
	private String distributionChannel;

	@Column(name = "DIVISION", length = 20)
	private String division;

	@Column(name = "SALES_ORG_TEXT")
	private String salesOrgText;

	@Column(name = "DISTRIBUTION_CHANNEL_TEXT")
	private String distributionChannelText;

	@Column(name = "DIVISION_TEXT")
	private String divisionText;

	@Column(name = "ORDER_REASON", length = 20)
	private String orderReason;

	@Column(name = "CUSTOMER_PO", length = 100)
	private String customerPo;

	@Column(name = "CUSTOMER_PO_TYPE", length = 20)
	private String customerPoType;

	@Column(name = "SOLD_TO_PARTY", length = 20)
	private String soldToParty;
	// new added
	@Column(name = "SOLD_TO_PARTY_TEXT", length = 100)
	private String soldToPartyText;

	@Column(name = "SHIP_TO_PARTY_TEXT", length = 100)
	private String shipToPartyText;

	@Column(name = "SHIP_TO_PARTY", length = 20)
	private String shipToParty;

	@Column(name = "TOTAL_NET_AMOUNT", precision = 3)
	private Double totalNetAmount;

	@Column(name = "DOC_CURRENCY", length = 20)
	private String docCurrency;

	// new added
	@Column(name = "DOC_TYPE_TEXT", length = 100)
	private String docTypeText;

	@Column(name = "OVERALL_STATUS", length = 20)
	private String overallStatus;

	@Column(name = "REJECTION_STATUS", length = 20)
	private String rejectionStatus;

	@Column(name = "DELIVERY_STATUS", length = 20)
	private String deliveryStatus;

	@Column(name = "CREDIT_STATUS", length = 20)
	private String creditStatus;

	@Column(name = "BILL_TO_PARTY", length = 20)
	private String billToParty;

	@Column(name = "BILL_TO_PARTY_TEXT")
	private String billToPartyText;

	@Column(name = "PAYER", length = 20)
	private String payer;

	@Column(name = "PAYER_TEXT")
	private String payerText;

	@Column(name = "SALESMAN", length = 20)
	private String salesman;

	@Column(name = "HEADER_BILLING_BLOCK_CODE", length = 20)
	private String headerBillBlockCode;

	// new added
	@Column(name = "DLV_BLOCK_TEXT", length = 100)
	private String deliveryBlockText;

	@Column(name = "DELIVERY_BLOCK_CODE", length = 20)
	private String deliveryBlockCode;

	@Column(name = "CREDIT_BLOCK_CODE", length = 20)
	private String creditBlock;

	@Column(name = "APPROVAL_STATUS", length = 20)
	private String approvalStatus;

	@Column(name = "SD_PROCESS_STATUS", length = 20)
	private String sdProcessStatus;

	@Column(name = "ORDER_APPROVAL_REASON", length = 20)
	private String orderApprovalReason;

	@Column(name = "ORDER_REASON_TEXT")
	private String orderReasonText;

	@Column(name = "ORDER_REMARK", length = 2000)
	private String orderRemark;

	@Column(name = "COND_GROUP5")
	private String condGroup5;

	@Column(name = "COND_GROUP5_TEXT")
	private String condGroup5Text;

	@Column(name = "ORDERER_NA")
	private String ordererNA;

	@Column(name = "ATTACHMENT_URL", length = 1000)
	private String attachmentUrl;

	@Column(name = "REQUESTED_BY")
	private String requestedBy;

	@Override
	public Object getPrimaryKey() {
		return salesOrderNum;
	}

}
