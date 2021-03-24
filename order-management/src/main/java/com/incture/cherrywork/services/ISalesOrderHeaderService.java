package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
@Repository
public interface ISalesOrderHeaderService {

	ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> read(String s4DocumentId);
	ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> delete(String s4DocumentId);
	ResponseEntity<Object> readAll(String search);
	ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto);
	//ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto);
	//ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId);
}