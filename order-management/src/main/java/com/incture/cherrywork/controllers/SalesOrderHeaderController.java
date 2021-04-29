package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.services.ISalesOrderHeaderService;
import com.incture.cherrywork.services.ISalesOrderItemService;

@SuppressWarnings("unused")
@RestController
@Api(value = "Sales Order Header", tags = { "Sales Order Header" })
@RequestMapping("/api/v1")

public class SalesOrderHeaderController {

	@Autowired
	private ISalesOrderHeaderService salesOrderHeaderService;
	@Autowired
	private ISalesOrderItemService salesOrderItemService;

	@GetMapping("/test")
	public String test() {
		return "Hello";
	}

	@PostMapping("/SalesOrderHeader")
	@ApiOperation(value = "Create a SalesOrderHeader Dataset")
	public ResponseEntity<Object> create(@Valid @RequestBody SalesOrderHeaderDto salesOrderHeaderDto) {
		return salesOrderHeaderService.create(salesOrderHeaderDto);
	}

	@GetMapping("/SalesOrderHeader/{s4DocumentId}/")
	@ApiOperation(value = "Read SalesOrderHeader Dataset")
	public ResponseEntity<Object> read(@PathVariable String s4DocumentId) {
		return salesOrderHeaderService.read(s4DocumentId);
	}

	@PutMapping("/SalesOrderHeader/{s4DocumentId}/")
	@ApiOperation(value = "Update SalesOrderHeader Dataset")
	public ResponseEntity<Object> update(@PathVariable String s4DocumentId,
			@Valid @RequestBody SalesOrderHeaderDto salesOrderHeaderDto) {
		return salesOrderHeaderService.update(s4DocumentId, salesOrderHeaderDto);
	}

	@DeleteMapping("/SalesOrderHeader/{s4DocumentId}/")
	@ApiOperation(value = "Delete SalesOrderHeader Dataset")
	public ResponseEntity<Object> delete(@PathVariable String s4DocumentId) {
		return salesOrderHeaderService.delete(s4DocumentId);
	}

	@GetMapping("/SalesOrderHeader")
	@ApiOperation(value = "List all SalesOrderHeader Datasets")
	public ResponseEntity<Object> readAll(@RequestParam(value = "search") String search) {
		return salesOrderHeaderService.readAll(search);
	}

	// <-----------------------Sandeep
	// Kumar------------------------------git status---------->

	@PostMapping("/getHeaderById")
	public ResponseEntity<Object> getHeaderById(@RequestBody HeaderIdDto dto) {
		return salesOrderHeaderService.getHeaderById(dto);
	}

	@PostMapping("/getDraftedVersion")
	public ResponseEntity<Object> getDraftedVersion(@RequestBody HeaderDetailUIDto dto) {
		return salesOrderHeaderService.getDraftedVersion(dto);
	}

	@PostMapping("/getManageService")
	public ResponseEntity<Object> getManageService(@RequestBody HeaderDetailUIDto dto) {
		return salesOrderHeaderService.getManageService(dto);
	}

	@PostMapping("/getReferenceList")
	public ResponseEntity<Object> getReferenceList(@RequestBody HeaderDetailUIDto dto) {
		return salesOrderHeaderService.getReferenceList(dto);
	}

	@PostMapping("/deleteDraftedVersion")
	public ResponseEntity<Object> deleteDraftedVersion(@RequestBody HeaderIdDto d) {

		return salesOrderHeaderService.deleteDraftedVersion(d);
	}
<<<<<<< HEAD
	@PostMapping("/SalesOrderItemDelete")
=======

	@DeleteMapping("/SalesOrderItemDelete")
>>>>>>> refs/remotes/origin/master
	@ApiOperation(value = "Delete SalesOrderItem Dataset")
	public ResponseEntity<Object> deleteItemOnly(@RequestParam String salesItemId) {
		return salesOrderItemService.deleteItemOnly(salesItemId);
	}

	@PostMapping("/SalesOrderHeader/save")
	@ApiOperation(value = "Create a SalesOrderHeader Dataset")
	public ResponseEntity<Object> save(@Valid @RequestBody SalesOrderHeaderDto salesOrderHeaderDto) {
		return salesOrderHeaderService.save(salesOrderHeaderDto);
	}

	@PostMapping("/get1")
	public ResponseEntity<Object> getHeader(@RequestParam String stp) {
		return salesOrderHeaderService.getHeader(stp);
	}
<<<<<<< HEAD
	
	
	
	//@PostMapping("/page/{pageNo}")
=======

	// @PostMapping("/page/{pageNo}")
>>>>>>> refs/remotes/origin/master
	//
	/*
	 * @GetMapping("/getUserDetailsBySTP/{stpId}") public ResponseEntity<Object>
	 * getUserDetailsBySTP(@PathVariable("stpId") String stpId) { return
	 * salesOrderHeaderService.getUserDetailsBySTP(stpId); }
	 */

	/*---------------AWADHESH KUMAR---------------------------*/

	@PostMapping("/submit")
	@ApiOperation(value = "Submit The Enquiry, Quotation and Order")
	public ResponseEntity<Object> submitEnquiry(@RequestBody SalesOrderHeaderItemDto dto) {
		return salesOrderHeaderService.submitSalesOrder(dto);
	}

	@GetMapping("/getSearchDropDown")
	@ApiOperation(value = "List Of Search Drop Down values")
	public ResponseEntity<Object> getSearchDropDown(@RequestBody SalesOrderSearchHeaderDto dto) {
		return salesOrderHeaderService.getSearchDropDown(dto);
	}

	@GetMapping("/manualSearchResult")
	@ApiOperation(value = "Mannual Search Result")
	public ResponseEntity<Object> getMannualSearchResult(@RequestBody SalesOrderSearchHeaderDto searchDto) {
		return salesOrderHeaderService.getMannualSearch(searchDto);
	}

}
