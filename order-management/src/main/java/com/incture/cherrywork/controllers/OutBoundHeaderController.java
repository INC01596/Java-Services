package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.services.IOutBoundHeaderService;
import com.incture.cherrywork.services.SchedulerServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "OutBound Delivery Header", tags = {"Out Bound"})
@RequestMapping("/api/v1/OutBound/")
public class OutBoundHeaderController {

	
	@Autowired
	private IOutBoundHeaderService outBoundHeaderService;
	
	@Autowired
	private SchedulerServices schedulerServices;
	
	@PostMapping("/createObd")
	@ApiOperation(value = "Create Obd")
	public ResponseEntity<Object> createObd(@RequestBody SalesOrderHeaderItemDto dto){
		return outBoundHeaderService.createObd(dto);
	}
	
	@PostMapping("/createPgi/{obdId}")
	@ApiOperation(value = "Create PGI")
	public ResponseEntity<Object> createPgi(@PathVariable String obdId){
		return outBoundHeaderService.createPgi(obdId);
	}
	
	@PostMapping("/createInv/{pgiId}")
	@ApiOperation(value = "Create Invoice")
	public ResponseEntity<Object> createInv(@PathVariable String pgiId){
		return outBoundHeaderService.createInv(pgiId);
	}
	
	
	
	@PostMapping("/headerScheduler")
	public ResponseEntity<Object> headerSch(){
		return schedulerServices.headerScheduler();
	}
	
	@PostMapping("/itemScheduler")
	public ResponseEntity<Object> itemSch(){
		return schedulerServices.itemScheduler();
	}
}
