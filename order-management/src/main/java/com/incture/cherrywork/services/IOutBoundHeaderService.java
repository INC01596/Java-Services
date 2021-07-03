
<<<<<<< HEAD
package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;

public interface IOutBoundHeaderService {
	
	ResponseEntity<Object> createObd(SalesOrderHeaderItemDto dto);
	ResponseEntity<Object> createPgi(String obdId);
	ResponseEntity<Object> createInv(String invId);
	
	ResponseEntity<Object> submitToEcc(SalesOrderHeaderItemDto inputDto);
	ResponseEntity<Object> submitOdataObd(OdataOutBoudDeliveryInputDto odataHeaderDto, String docType);
	ResponseEntity<Object> getInvDetail(String invId);
	void mailService(SalesOrderHeaderItemDto dto);
	void printService(SalesOrderHeaderItemDto dto);
	int setStatusAsClosed(String obdId);

}
=======
package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;

public interface IOutBoundHeaderService {
	
	ResponseEntity<Object> createObd(SalesOrderHeaderItemDto dto);
	ResponseEntity<Object> createPgi(String obdId);
	ResponseEntity<Object> createInv(String invId);
	
	ResponseEntity<Object> submitToEcc(SalesOrderHeaderItemDto inputDto);
	ResponseEntity<Object> submitOdataObd(OdataOutBoudDeliveryInputDto odataHeaderDto, String docType);
	ResponseEntity<Object> getInvDetail(String invId);
	void mailService(SalesOrderHeaderItemDto dto);
	void printService(SalesOrderHeaderItemDto dto);

}

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
