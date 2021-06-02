package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInvoiceInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryPgiInputDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.odata.dto.OdataSchHeaderStartDto;


@Repository
public interface ISalesOrderHeaderCustomRepository {
	ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto);
	ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto searchDto);
	SalesOrderOdataHeaderDto getOdataReqPayload(SalesOrderHeaderItemDto dto);
	String updateError(String temp_id, String value, String DocType);
	String getLookupValue(String key);
	OdataOutBoudDeliveryInputDto getOdataReqPayloadObd(SalesOrderHeaderItemDto dto);
	OdataOutBoudDeliveryPgiInputDto getOdataReqPayloadPgi(SalesOrderHeaderItemDto inputDto);
	List<SalesOrderHeaderDto> convertData(OdataSchHeaderStartDto odataSchHeaderStartDto);
	OdataOutBoudDeliveryInvoiceInputDto getOdataReqPayloadInv(SalesOrderHeaderItemDto inputDto);
//	ResponseEntity<Object> headerScheduler();
	

}
