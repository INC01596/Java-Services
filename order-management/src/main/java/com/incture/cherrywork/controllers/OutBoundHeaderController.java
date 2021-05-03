package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.services.IOutBoundHeaderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "OutBound Delivery Header", tags = {"Out Bound"})
@RequestMapping("/api/v1/OutBound/")
public class OutBoundHeaderController {

	
	@Autowired
	private IOutBoundHeaderService outBoundHeaderService;
	
	@PostMapping("/createObd")
	@ApiOperation(value = "Create Obd")
	public ResponseEntity<Object> createObd(@RequestBody SalesOrderHeaderItemDto dto){
		return outBoundHeaderService.createObd(dto);
	}
	
	@PostMapping("/createPgi")
	@ApiOperation(value = "Create PGI")
	public ResponseEntity<Object> createPgi(@RequestBody SalesOrderHeaderItemDto dto){
		return outBoundHeaderService.createPgi(dto);
	}
}
