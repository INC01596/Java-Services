package com.incture.cherrywork.dtos;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
public @Data class SalesDocHeaderDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnReqNum;
	private String salesOrderNum;

	private String reqMasterId;

	private BigInteger salesOrderDate;

	private String createdBy;

	private String orderCategory;

	private String orderType;

	private String orderTypeText;

	private String decisionSetId;

	private String billToParty;

	private String billToPartyText;

	private String payer;

	private String payerText;

	private String salesman;

	private String levelNum;

	private String headerMsg;

	private String salesOrg;

	private Double decisionSetAmount;

	private Double salesOrderAmount;

	private String distributionChannel;

	private String division;
	private String salesOrgText;
	private String distrChanText;
	private String divisionText;
	private String orderReason;

	private String customerPo;

	private Date customerPoDate;

	private String customerPoType;

	private String soldToParty;

	private String soldToPartyText;

	private String shipToParty;

	private String shipToPartyText;

	private Double totalNetAmount;

	private String docCurrency;

	private String overallStatus;

	private String rejectionStatus;

	private String deliveryStatus;

	private String creditStatus;

	private String headerBillBlockCode;

	private String headerBillBlockCodeText;

	private String deliveryBlockCode;

	private String deliveryBlockText;

	private String creditBlock;

	private String approvalStatus;

	private String sdProcessStatus;

	private String orderApprovalReason;

	private String flagFromScheduler;

	private String docTypeText;

	private String orderReasonText;

	private String condGroup5;

	private String orderRemark;

	private String ordererNA;

	private String condGroup5Text;

	private String attachmentUrl;

	private String requestedBy;

	private List<SalesDocItemDto> salesDocItemList;

	private List<String> specialClientListForHeader;
	private String totalRoAmount;
	private BigInteger createdOn;
	private String headerDeliveryBlockText;
	private String requestRemark;
	private String returnReasonText;
	private String headerDeliveryBlockCode;

	private String distributionChannelText;

	private String roType;

	private String roTypeText;

	private String returnReason;

	private String documentUrl;

	private String purchNum;

	private List<ItemDataInReturnOrderDto> listOfItemsInReturn;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();

	}

	public SalesDocHeaderDto(String salesOrderNum, String deliveryBlockCode) {
		super();
		this.salesOrderNum = salesOrderNum;
		this.deliveryBlockCode = deliveryBlockCode;
	}

	public SalesDocHeaderDto() {
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SalesDocHeaderDto(String requested_by, String order_remark, String order_reason_text, String order_category,
			String order_type, String doc_type_text, String sales_org, String distribution_channel, String division,
			String customer_po, String sold_to_party, String sold_to_party_text, String ship_to_party,
			String ship_to_party_text, String doc_currency, String delivery_block_code, String dlv_block_text,
			String cond_group5, String cond_group5_text, BigInteger sales_order_date, String order_reason,
			String orderer_na, String created_by, String attachment_url, String approval_status, String overall_status,
			String sales_org_text, String distribution_channel_text, String division_text, String payer,
			String payer_text, String bill_to_party, String bill_to_party_text) {
		super();
		this.orderCategory = order_category;
		this.orderType = order_type;
		this.salesOrg = sales_org;
		this.salesOrgText = sales_org_text;
		this.distributionChannel = distribution_channel;
		this.distributionChannelText = distribution_channel_text;
		this.division = division;
		this.divisionText = division_text;
		this.payer = payer;
		this.payerText = payer_text;
		this.billToParty = bill_to_party;
		this.billToPartyText = bill_to_party_text;
		this.returnReqNum = customer_po;
		this.soldToParty = sold_to_party;
		this.soldToPartyText = sold_to_party_text;
		this.shipToParty = ship_to_party;
		this.shipToPartyText = ship_to_party_text;
		this.docCurrency = doc_currency;
		this.headerDeliveryBlockCode = delivery_block_code;
		this.approvalStatus = approval_status;
		this.overallStatus = overall_status;
		this.headerDeliveryBlockText = dlv_block_text;
		this.orderTypeText = doc_type_text;
		this.requestRemark = order_remark;
		this.returnReasonText = order_reason_text;
		this.returnReason = order_reason;
		this.roType = cond_group5;
		this.roTypeText = cond_group5_text;
		this.createdOn = sales_order_date;
		this.requestedBy = requested_by;
		this.documentUrl = attachment_url;
		this.purchNum = orderer_na;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((returnReqNum == null) ? 0 : returnReqNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesDocHeaderDto other = (SalesDocHeaderDto) obj;
		if (returnReqNum == null) {
			if (other.returnReqNum != null)
				return false;
		} else if (!returnReqNum.equals(other.returnReqNum))
			return false;
		return true;
	}

}
