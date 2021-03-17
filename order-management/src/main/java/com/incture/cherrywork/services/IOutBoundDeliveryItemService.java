package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.OutBoundDeliveryItemDto;

public interface IOutBoundDeliveryItemService {
	
	ResponseEntity<Object> create(String obdNumber, OutBoundDeliveryItemDto outBoundDeliveryItemDto);
	ResponseEntity<Object> read(String obdNumber, String soItemNumber);
	ResponseEntity<Object> update(String obdNumber, String soItemNumber, OutBoundDeliveryItemDto outBoundDeliveryItemDto);
	ResponseEntity<Object> delete(String obdNumber, String soItemNumber);
	ResponseEntity<Object> readAll(String search);

}
