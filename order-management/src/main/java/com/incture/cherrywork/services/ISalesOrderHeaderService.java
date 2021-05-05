package com.incture.cherrywork.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.InvoDto;
import com.incture.cherrywork.dtos.ObdDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;

@SuppressWarnings("unused")
@Repository
public interface ISalesOrderHeaderService {

	ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto);

	ResponseEntity<Object> read(String s4DocumentId);

	ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto);

	ResponseEntity<Object> delete(String s4DocumentId);

	ResponseEntity<Object> readAll(String search);

	

	/*-----------------AWADHESH KUMAR---------------*/

	ResponseEntity<Object> submitSalesOrder(SalesOrderHeaderItemDto dto);

	ResponseEntity<Object> submitSalesOrder1(SalesOrderHeaderItemDto dto);

	ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto);

	ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto dto);

	// sandeep

	// <-----------------sandeep-------------------->

	ResponseEntity<Object> getHeaderById(HeaderIdDto dto);

	ResponseEntity<Object> getManageService(HeaderDetailUIDto dto);

	ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto);

	ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto);

	ResponseEntity<Object> save(SalesOrderHeaderDto dto);

	
	ResponseEntity<Object> deleteDraftedVersion(HeaderIdDto d);

	

	ResponseEntity<Object> getManageServiceObd(ObdDto dto);

	ResponseEntity<Object> getManageServiceInvo(InvoDto dto);

	
	
	
	
	
	
	

	
	

}