package com.incture.cherrywork.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import com.incture.cherrywork.dtos.SalesOrderDropDownDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.services.ISalesOrderItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Sales Order Item", tags = { "Sales Order Item" })
@RequestMapping("/api/v1/SalesOrderHeader/{s4DocumentId}")

public class SalesOrderItemController {

	@Autowired
	private ISalesOrderItemService salesOrderItemService;

	

	@PostMapping("/SalesOrderItem/create")
	@ApiOperation(value = "Create a SalesOrderItem Dataset")
	public ResponseEntity<Object> create(@PathVariable String s4DocumentId,
			@Valid @RequestBody SalesOrderItemDto salesOrderItemDto) {
		return salesOrderItemService.create(s4DocumentId, salesOrderItemDto);
	}

	@GetMapping("/SalesOrderItem/{salesItemId}/")
	@ApiOperation(value = "Read SalesOrderItem Dataset")
	public ResponseEntity<Object> read(@PathVariable String s4DocumentId, @PathVariable String salesItemId) {
		return salesOrderItemService.read(s4DocumentId, salesItemId);
	}

	@PutMapping("/SalesOrderItem/{salesItemId}/")
	@ApiOperation(value = "Update SalesOrderItem Dataset")
	public ResponseEntity<Object> update(@PathVariable String s4DocumentId, @PathVariable String salesItemId,
			@Valid @RequestBody SalesOrderItemDto salesOrderItemDto) {
		return salesOrderItemService.update(s4DocumentId, salesItemId, salesOrderItemDto);
	}

	@DeleteMapping("/SalesOrderItem/{salesItemId}/")
	@ApiOperation(value = "Delete SalesOrderItem Dataset")
	public ResponseEntity<Object> delete(@PathVariable String s4DocumentId, @PathVariable String salesItemId) {
		return salesOrderItemService.delete(s4DocumentId, salesItemId);
	}

	@GetMapping("/SalesOrderItem")
	@ApiOperation(value = "List all SalesOrderItem Datasets")
	public ResponseEntity<Object> readAll(@RequestParam(value = "search") String search) {
		return salesOrderItemService.readAll(search);
	}

	// @GetMapping("/SalesOrderItem/{salesItemId}/")
	// @ApiOperation(value = "Delete SalesOrderHeader drafted version Dataset")
	// public ResponseEntity<Object> deleteDraftedVersion(@PathVariable String
	// s4DocumentId, @PathVariable String salesItemId){
	// return salesOrderItemService.deleteDraftedVersion(s4DocumentId,
	// salesItemId);
	// }

	@PostMapping("/add")
	@ApiOperation(value = "Add Item with or without specification")
	public ResponseEntity<Object> addLineItem(@PathVariable String s4DocumentId,
			@Valid @RequestBody List<SalesOrderItemDto> dto) {
		System.out.println("Calling addLineItem.. ");
		return salesOrderItemService.addLineItem(s4DocumentId, dto);
	}

	@PostMapping("/update")
	@ApiOperation(value = "Update the Enq or Quot or Ord")
	public ResponseEntity<Object> updateLineItem(@RequestBody List<SalesOrderItemDto> dto) {
		return salesOrderItemService.updateLineItem(dto);
	}

	@GetMapping("/dropdown")
	@ApiOperation(value = "Get LookUp DropDown")
	public SalesOrderDropDownDto getLookUpValues() {
		return salesOrderItemService.getLookUpValues();
	}

}
