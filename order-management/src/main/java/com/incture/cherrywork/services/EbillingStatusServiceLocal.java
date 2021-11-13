package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.BillingApprovalDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface EbillingStatusServiceLocal {

	ResponseDto getTrackingDetails(String transactionId);

	ResponseDto getPendingApprovals(String pendingWith);

	ResponseDto updateStatus(BillingApprovalDto approvalDto);

}
