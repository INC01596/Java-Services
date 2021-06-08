
package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ExchangeHeaderDto {
	
	private String exchangeReqNum; // 
	private String returnReqNum;
	private  Date   createdAt; // ui sending 
	private String orderCategory; // OS
	private String orderType;//OS
	private String salesOrg;//OS
	private String distributionChannel;//OS
	private String division;//OS
	private String customerPo; // no OS
	private String customerPoType;// no OS
	private String soldToParty;// OS
	private Double totalNetAmount; // OS
	private String docCurrency; // NO OS - currency not binded
	private String deliveryBlock; // NO OS
	private String billingBlock;// NO OS
	private String overallStatus;// NO OS
	private String rejectionStatus;// No OS
	private String deliveryStatus;// NO OS
	private String creditStatus; // NO OS
	private String overallWorkflowStatus;//NO OS
	private boolean creationStatus; //  on JAVA 
	private String message; // on JAVA 
	private String docVersion; // On OS
    private String totalExchangeAmount; // ON OS need clear 
	private String currency; // NO OS
	private String roType; // OS
	private String payer;// OS
	private String payerDescription ; // Need to send
	private String referenceNum; // OS
	private String reasonOwner;//OS
	private String requestRemark;//OS
	private String billToParty;//OS
	private String billToPartyDesc;// need to send
	private String soldToPartyDesc;// OS
	private String shipToParty;//OS
	private String shipToPartyDesc;//OS
	private String delComplete;// nedd to send
	private String ordReason;// OS
	private String flagRoSo;//OS
    private String orderTypeText;//OS
	private String createdBy;
	private String remarks;
	private String documentUrl;
	private List<ExchangeItemDto> listExhangeItemDto;

}
