 package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.services.ISalesOrderItemService;

@RestController
@Api(value = "Sales Order Item", tags = {"Sales Order Item"})
@RequestMapping("/api/v1/SalesOrderHeader/{s4DocumentId}")

public class SalesOrderItemController {

@Autowired
private ISalesOrderItemService salesOrderItemService; 

@PostMapping("/SalesOrderItem")
@ApiOperation(value = "Create a SalesOrderItem Dataset")
public ResponseEntity<Object> create(@PathVariable String s4DocumentId, @Valid @RequestBody SalesOrderItemDto salesOrderItemDto) {
		return salesOrderItemService.create(s4DocumentId,salesOrderItemDto);
}
@GetMapping("/SalesOrderItem/{salesItemId}/")
@ApiOperation(value = "Read SalesOrderItem Dataset")
public ResponseEntity<Object> read(@PathVariable String s4DocumentId, @PathVariable String salesItemId) { 
		return salesOrderItemService.read(s4DocumentId,salesItemId);
}
@PutMapping("/SalesOrderItem/{salesItemId}/")
@ApiOperation(value = "Update SalesOrderItem Dataset")
public ResponseEntity<Object> update(@PathVariable String s4DocumentId, @PathVariable String salesItemId, @Valid @RequestBody SalesOrderItemDto salesOrderItemDto) { 
		return salesOrderItemService.update(s4DocumentId,salesItemId, salesOrderItemDto);
}
@DeleteMapping("/SalesOrderItem/{salesItemId}/")
@ApiOperation(value = "Delete SalesOrderItem Dataset")
public ResponseEntity<Object> delete(@PathVariable String s4DocumentId, @PathVariable String salesItemId) { 
		return salesOrderItemService.delete(s4DocumentId,salesItemId);
}
@GetMapping("/SalesOrderItem")
@ApiOperation(value = "List all SalesOrderItem Datasets")
public ResponseEntity<Object> readAll(@RequestParam(value = "search") String search) {
		return salesOrderItemService.readAll(search);
}

//@GetMapping("/SalesOrderItem/{salesItemId}/")
//@ApiOperation(value = "Delete SalesOrderHeader drafted version Dataset")
//public ResponseEntity<Object> deleteDraftedVersion(@PathVariable String s4DocumentId, @PathVariable String salesItemId){
//	return salesOrderItemService.deleteDraftedVersion(s4DocumentId, salesItemId);
//}
}
