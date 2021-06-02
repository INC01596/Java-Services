package com.incture.cherrywork.services;



import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ReturnFilterDto;



public interface ReturnRequestHeaderService {

	

	

	  ResponseEntity<Object> listAllReturnReqHeaders();
	  ResponseEntity<Object> listAllReturn(ReturnFilterDto dto);
	//public ResponseEntity<Object> getReturnReqHeaderById(String returnReqNum);
	  ResponseEntity<Object> findAll(int pageNo);

	//public ResponseEntity<Object> deleteReturnReqHeaderById(String returnReqNum);

}

