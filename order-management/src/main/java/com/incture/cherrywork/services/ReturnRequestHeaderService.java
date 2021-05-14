package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;

public interface ReturnRequestHeaderService {

	public ResponseEntity saveOrUpdateReturnReqHeader(ReturnRequestHeaderDto returnRequestHeaderDto);

	public ResponseEntity listAllReturnReqHeaders();

	public ResponseEntity getReturnReqHeaderById(String returnReqNum);

	public ResponseEntity deleteReturnReqHeaderById(String returnReqNum);

}

