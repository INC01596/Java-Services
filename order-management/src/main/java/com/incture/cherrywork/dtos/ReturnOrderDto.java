

package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class ReturnOrderDto {

	private String returnReqNum;
	private String roType;
	private String roTypeText;
	private String salesOrg;
	private String distributionChannel;
	private String division;
	private String soldToParty;
	private String soldToPartyText;
	private String shipToParty;
	private String shipToPartyText;
	private String billToParty;
	private String billToPartyText;
	private String payer;
	private String payerText;
	private String requesterName;
	private String returnRemark;
	private String orderReason;
	private String orderReasonText;
	private String reasonOwner;
	private String reasonOwnerText;
	private String orderType;
	private String orderTypeText;
	private String returnTotalAmt;
	private String workflowInstance;
	private String overallWorkflowStatus;
	private String processingStatus;
	private String logisticalStatus;
	private String headerDeliveryBlock;
	private String headerDeliveryBlockText;
	private String docCurrency;
	private String loggedInUserId;
	private String loggedInUserPid;
	private Boolean smsTrigger;
	private String smsFrom;

	private List<String> salesOrderNumList;

	private List<ItemDataInReturnOrderDto> returnItemsList;

	private ExchangeHeaderFromOrderDto exchangeHeaderDto;
}
