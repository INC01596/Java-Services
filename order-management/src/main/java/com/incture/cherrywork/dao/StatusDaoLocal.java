package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.BillingPendingTaskDto;
import com.incture.cherrywork.dtos.StatusDto;



public interface StatusDaoLocal {

	void createStatus(StatusDto dto);

	void updateStatus(StatusDto dto);

	void deleteStatus(StatusDto dto);

	StatusDto getStatus(StatusDto dto);
//
	List<StatusDto> getTrackingDetails(String transactionId);

	List<BillingPendingTaskDto> getPendingApprovals(String pendingWith);
	
//	List<BillingPendingTaskDto> getPendingApprovalsFromView(String pendingWith);

}