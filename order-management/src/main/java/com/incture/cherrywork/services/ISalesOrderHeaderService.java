package com.incture.cherrywork.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
@Repository
public interface ISalesOrderHeaderService {

	ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> read(String s4DocumentId);
	ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto);
	ResponseEntity<Object> delete(String s4DocumentId);
	ResponseEntity<Object> readAll(String search);
	
	//ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto);
	//ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId);
	
	/*-----------------AWADHESH KUMAR---------------*/
	
	ResponseEntity<Object> submitSalesOrder(SalesOrderHeaderItemDto dto);
	ResponseEntity<Object> submitSalesOrder1(SalesOrderHeaderItemDto dto);
	
	ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto);
	
	ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto dto);
	

	
	//sandeep

	//<-----------------sandeep-------------------->

	ResponseEntity<Object> getHeaderById(HeaderIdDto dto);
	ResponseEntity<Object> getManageService(HeaderDetailUIDto dto);
	
	ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto);
	ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto);
	ResponseEntity<Object> save(SalesOrderHeaderDto dto);
	//ResponseEntity<Object> getUserDetailsBySTP(String stpId);
	ResponseEntity<Object> deleteDraftedVersion(HeaderIdDto d);
	ResponseEntity<Object> getHeader(String stp);
	
//	List<SalesOrderHeader> getManage(HeaderDetailUIDto dto, Pageable pageable);
//	Page<SalesOrderHeader> findPaginated(int pageNo, HeaderDetailUIDto dto);
//	
	
	
}