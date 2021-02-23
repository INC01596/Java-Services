package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
public interface ISalesOrderHeaderService {

	ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> read(String s4DocumentId);
	ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> delete(String s4DocumentId);

}