package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ReturnItemDto;

public interface ReturnItemService {

	public ResponseEntity saveOrUpdateReturnItem(ReturnItemDto returnItemDto);

	public ResponseEntity listAllReturnItems();

	public ResponseEntity getReturnItemById(String returnReqNum, String returnReqItemid);

	public ResponseEntity deleteReturnItemById(String returnReqNum, String returnReqItemid);

}

