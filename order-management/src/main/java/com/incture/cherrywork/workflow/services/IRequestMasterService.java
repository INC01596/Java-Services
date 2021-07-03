package com.incture.cherrywork.workflow.services;

import java.util.List;

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface IRequestMasterService {
	
	List<RequestMasterDto> getRequestMasterById(String reqId);
	ResponseEntity saveOrUpdateRequestMaster(RequestMasterDto requestMasterDto);
	ResponseEntity updateRequestMaster(RequestMasterDto requestMasterDto);
	ResponseEntity deleteRequestMasterByRefDocNum(String reqId);

	ResponseEntity listAllRequests();
//
	ResponseEntity deleteRequestMasterById(String reqId);

	

	ResponseEntity getRequestMasterById1(String reqId);

	ResponseEntity updateStatusPostBlock(String salesOrderNumber, boolean noItemBlock);

	


}
