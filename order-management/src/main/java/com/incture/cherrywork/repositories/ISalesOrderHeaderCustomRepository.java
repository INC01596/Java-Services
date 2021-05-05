package com.incture.cherrywork.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;

@Repository
public interface ISalesOrderHeaderCustomRepository {
	ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto);
	ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto searchDto);
	SalesOrderOdataHeaderDto getOdataReqPayload(SalesOrderHeaderItemDto dto);
	String updateError(String temp_id, String value);
	String getLookupValue(String key);
//	ResponseEntity<Object> headerScheduler();
	

}
