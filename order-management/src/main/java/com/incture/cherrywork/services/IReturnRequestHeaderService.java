package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;



public interface IReturnRequestHeaderService {
	ResponseEntity<Object> saveAsDraft(ReturnOrderRequestPojo requestData);
	ResponseEntity<Object> createReturnRequest(ReturnOrderRequestPojo requestData);
	ResponseEntity<Object> findByReturnReqNum(String returnReqNum);
	ResponseEntity<Object> findAll(int pageNo);
	ResponseEntity<Object> listAllReturn(ReturnFilterDto dto);
	

}
