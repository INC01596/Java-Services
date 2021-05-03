package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;

public interface IOutBoundHeaderService {
	
	ResponseEntity<Object> createObd(SalesOrderHeaderItemDto dto);
	ResponseEntity<Object> createPgi(@RequestBody SalesOrderHeaderItemDto dto);

}
