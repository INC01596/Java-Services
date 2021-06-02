package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;



public interface IReturnRequestHeaderService {
	ResponseEntity<Object> saveAsDraft(ReturnOrderRequestPojo requestData);
	ResponseEntity<Object> createReturnRequest(ReturnOrderRequestPojo requestData);
	ResponseEntity<Object> findByReturnReqNum(String returnReqNum);
	

}
