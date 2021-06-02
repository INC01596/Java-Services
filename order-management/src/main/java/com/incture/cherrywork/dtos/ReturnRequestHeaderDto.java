package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.Attachment;

import lombok.Data;

@Data
public class ReturnRequestHeaderDto {

	private String returnReqNum;
	private String roType;
	private String requestType;
	private String orderType;
	private String salesOrg;
	private String distributionChannel;
	private String division;
	private String soldToParty;
	private String shipToParty;
	private String billToParty;
	private String payer;
	private String contactPerson;
	private String contactDivision;
	private String contactTelephone;// send as a string of numers
	private String referenceNum;
	private String orderReason;
	private String orderReasonText;
	private String reasonOwner;
	private String reasonOwnerDesc;
	private String requestRemark;
	private String workflowInstance;
	private String overallWorkflowStatus;
	private String requestedBy;
	private Date createdAt;
	private String updatedAt;
	private String updatedBy;
	private String processingStatus;
	private String logisticalStatus;
	private boolean creationStatus;
	private String message;
	private String docVersion;
	private String roTypeText;
	private String totalRoAmount;
	private String requestorName;
	private String orderTypeText;
	private String referenceInvDate;
	private String customerPo;
	private String soldToPartyDesc;//os
	private String shipToPartyDesc;//os
	private String billToDesc;//os
	private String payerDesc;//os
	private String requestorEmail;//os
	private String returnTotalAmt;//os
	private String flagRoSo;
	private String mappingId;//os
	private String exchangeOrderType;//
	private String emailTrigger;
	private String divisionDesc;
	private String salesOrgDesc;
	private String distributionChannelDesc;
	private String documentUrl;
	private Boolean smsTrigger;
	private String smsFrom;
	private List<ReturnItemDto> listItemDto;
	private List<Attachment> listAttachementDo;
	private List<Address> listAddressDo;
	private ExchangeHeaderDto exchangeDto;

}
