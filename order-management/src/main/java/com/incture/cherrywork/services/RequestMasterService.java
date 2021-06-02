package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface RequestMasterService {

	public ResponseEntity saveOrUpdateRequestMaster(RequestMasterDto requestMasterDto);

	public ResponseEntity listAllRequests();

	public ResponseEntity deleteRequestMasterById(String reqId);

	ResponseEntity deleteRequestMasterByRefDocNum(String reqId);

	public ResponseEntity getRequestMasterById(String reqId);

	public ResponseEntity updateStatusPostBlock(String salesOrderNumber, boolean noItemBlock);

	public ResponseEntity updateRequestMaster(RequestMasterDto requestMasterDto);

}
