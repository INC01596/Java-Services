package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.workflow.SalesOrderTaskStatusesService;


@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/sots")
public class SalesOrderTaskStatusesController {

	public SalesOrderTaskStatusesController() {
		System.err.println("In Sales Order Task Status Controller");
	}

	@Autowired
	private SalesOrderTaskStatusesService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createComment(@RequestBody SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		System.err.println("SalesOrderTaskStatus Created Successfully");
		return services.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateComment(@RequestBody SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		System.err.println("SalesOrderTaskStatus Updated Successfully");

		return services.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
	}

	@GetMapping("/list")
	public ResponseEntity listAllComment() {
		System.err.println("Inside SalesOrderTaskStatus List");
		return services.listAllSalesOrderTaskStatus();
	}

	@DeleteMapping("/deleteById/{key}")
	public ResponseEntity deleteSalesOrderTaskStatusById(@PathVariable("key") SalesOrderTaskStatusKeyDto key) {
		System.err.println("Inside Delete SalesOrderTaskStatus by Id");
		return services.deleteSalesOrderTaskStatusById(key);
	}

	@PostMapping("/findById")
	public ResponseEntity findSalesOrderTaskStatusById(@RequestBody SalesOrderTaskStatusKeyDto key) {
		System.err.println("Inside SalesOrderTaskStatus find by Id method");
		return services.getSalesOrderTaskStatusById(key);
	}

	@GetMapping("/listAllData/{decisionSet}&{level}")
	public ResponseEntity listAllDataAccToDsAndLevel(@PathVariable("decisionSet") String decisionSet,
			@PathVariable("level") String level) {
		System.err.println("Inside SalesOrderTaskStatus List");
		return services.getSalesOrderTaskStatusAccToDsAndLevel(decisionSet, level);
	}
}