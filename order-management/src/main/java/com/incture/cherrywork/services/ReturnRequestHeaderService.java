package com.incture.cherrywork.services;


import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ReturnFilterDto;


public interface ReturnRequestHeaderService {

	

	

	  ResponseEntity<Object> listAllReturnReqHeaders(ReturnFilterDto dto);

	//public ResponseEntity<Object> getReturnReqHeaderById(String returnReqNum);

	//public ResponseEntity<Object> deleteReturnReqHeaderById(String returnReqNum);

}

