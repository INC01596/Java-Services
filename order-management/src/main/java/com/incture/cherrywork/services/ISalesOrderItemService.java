package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
public interface ISalesOrderItemService {

	ResponseEntity<Object> create(String s4DocumentId, SalesOrderItemDto salesOrderItemDto);
	ResponseEntity<Object> read(String s4DocumentId, String salesItemId);
	ResponseEntity<Object> update(String s4DocumentId, String salesItemId, SalesOrderItemDto salesOrderItemDto);
	ResponseEntity<Object> delete(String s4DocumentId, String salesItemId);
	ResponseEntity<Object> readAll(String search);
	//ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId, String salesItemId);
  
}