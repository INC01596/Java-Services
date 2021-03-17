package com.incture.cherrywork.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.OutBoundDeliveryItemDto;
import com.incture.cherrywork.services.IOutBoundDeliveryItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Sales Order Item", tags = {"Sales Order Item"})
@RequestMapping("/api/v1/OutDelivery/{obdNumber}/")
public class OutBoundItemController {
	
	
	@Autowired
	private IOutBoundDeliveryItemService   outBoundDeliveryItemService;

	@PostMapping("/OutBoundeDelvItem")
	@ApiOperation(value = "Create a SalesOrderItem Dataset")
	public ResponseEntity<Object> create(@PathVariable String obdNumber, @Valid @RequestBody OutBoundDeliveryItemDto outBoundDelieryItemDto) {
			return outBoundDeliveryItemService.create(obdNumber,outBoundDelieryItemDto);
	}
	@GetMapping("/OutBoundeDelvItem/{soItemNumber}/")
	@ApiOperation(value = "Read SalesOrderItem Dataset")
	public ResponseEntity<Object> read(@PathVariable String obdNumber, @PathVariable String soItemNumber) { 
			return outBoundDeliveryItemService.read(obdNumber,soItemNumber);
	}
	@PutMapping("/OutBoundeDelvItem/{soItemNumber}/")
	@ApiOperation(value = "Update SalesOrderItem Dataset")
	public ResponseEntity<Object> update(@PathVariable String obdNumber, @PathVariable String soItemNumber, @Valid @RequestBody OutBoundDeliveryItemDto outBoundDelieryItemDto) { 
			return outBoundDeliveryItemService.update(obdNumber,soItemNumber, outBoundDelieryItemDto);
	}
	@DeleteMapping("/OutBoundeDelvItem/{soItemNumber}/")
	@ApiOperation(value = "Delete SalesOrderItem Dataset")
	public ResponseEntity<Object> delete(@PathVariable String obdNumber, @PathVariable String soItemNumber) { 
			return outBoundDeliveryItemService.delete(obdNumber,soItemNumber);
	}
	@GetMapping("/OutBoundeDelvItem")
	@ApiOperation(value = "List all SalesOrderItem Datasets")
	public ResponseEntity<Object> readAll(@RequestParam(value = "search") String search) {
			return outBoundDeliveryItemService.readAll(search);
	}
	
	
	

}
