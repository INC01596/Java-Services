package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.services.ISalesVisitPlannerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "SalesVisitPlannerController", tags = { "Visit Planner" })
@RequestMapping("/api/v1/salesVisitPlanner")
public class SalesVisitPlannerController {

	@Autowired
	private ISalesVisitPlannerService salesVisitPlannerService;

	@PostMapping("/createVisit")
	@ApiOperation(value = "Create a visit Dataset")
	public ResponseEntity<Response> create(@RequestBody VisitPlanDto dto) {
		return salesVisitPlannerService.createVisit(dto);
	}

	@GetMapping("/getVisitById/{visitId}/")
	@ApiOperation(value = "Read Visit Dataset")
	public ResponseEntity<Response> read(@PathVariable String visitId) {
		return salesVisitPlannerService.getVisitById(visitId);
	}

	@PutMapping("/updateVisit")
	@ApiOperation(value = "Update Visit Dataset")
	public ResponseEntity<Response> update(@RequestBody VisitPlanDto dto) {
		return salesVisitPlannerService.updateVisit(dto);
	}

	@DeleteMapping("/deletVisit/{visitId}")
	@ApiOperation(value = "Delete Visit Dataset")
	public ResponseEntity<Response> delete(@PathVariable String visitId) {
		return salesVisitPlannerService.deleteVisit(visitId);
	}

	@GetMapping("/getAllVisit")
	@ApiOperation(value = "List all Visists ")
	public ResponseEntity<Response> readAll() {
		return salesVisitPlannerService.getAllVisit();
	}

}
