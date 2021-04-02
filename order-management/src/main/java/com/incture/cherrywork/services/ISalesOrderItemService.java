package com.incture.cherrywork.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.SalesOrderDropDownDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
public interface ISalesOrderItemService {

	ResponseEntity<Object> create(String s4DocumentId, SalesOrderItemDto salesOrderItemDto);
	ResponseEntity<Object> read(String s4DocumentId, String salesItemId);
	ResponseEntity<Object> update(String s4DocumentId, String salesItemId, SalesOrderItemDto salesOrderItemDto);
	ResponseEntity<Object> delete(String s4DocumentId, String salesItemId);
	ResponseEntity<Object> readAll(String search);
	//ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId, String salesItemId);
  
	
	/*----------------awadhesh kumar--------------------*/
	 
	ResponseEntity<Object> addLineItem(String s4DocumentId, List<SalesOrderItemDto> dto);
	ResponseEntity<Object> updateLineItem(List<SalesOrderItemDto> dto);
	SalesOrderDropDownDto getLookUpValues();
	//ResponseEntity<Object> addLineItem(@PathVariable String s4DocumentId, @Valid @RequestBody List<SalesOrderItemDto> dto, @Valid @RequestBody SalesOrderHeaderDto headerDto);
}