package com.incture.cherrywork.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.new_workflow.SalesOrderLevelStatusService;



@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/levelStatus")
public class SalesOrderLevelStatusController {

	public SalesOrderLevelStatusController() {
		//HelperClass.getLogger(this.getClass().getName()).info("inside SalesOrderLevelStatusController controller");
	}

	@Autowired
	private SalesOrderLevelStatusService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createSalesOrderLevelStatus(@RequestBody SalesOrderLevelStatusDto salesOrderLevelStatusDto) {
		//HelperClass.getLogger(this.getClass().getName()).info("SalesOrderLevelStatus Created Successfully");
		return services.saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateSalesOrderLevelStatus(@RequestBody SalesOrderLevelStatusDto salesOrderLevelStatusDto) {
		System.err.println("SalesOrderLevelStatus Updated Successfully");
		if (salesOrderLevelStatusDto.getLevelStatusSerialId() != null) {
			return services.saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);
		} else {
			return new ResponseEntity(salesOrderLevelStatusDto, HttpStatus.OK,
					"SalesOrderLevelStatus Id field is mandatory.", ResponseStatus.SUCCESS);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllSalesOrderLevelStatuses() {
		
		return services.listAllSalesOrderLevelStatuses();
	}

	@DeleteMapping("/deleteById/{salesOrderLevelStatusId}")
	public ResponseEntity deleteSalesOrderLevelStatusById(
			@PathVariable("salesOrderLevelStatusId") String salesOrderLevelStatusId) {
		System.err.println("Inside Delete SalesOrderLevelStatus by Id");
		return services.deleteSalesOrderLevelStatusById(salesOrderLevelStatusId);
	}

	@GetMapping("/checkLevel/{decisionSetId}&{level}")
	public ResponseEntity checkLevelStatusAndSaveInDb(@PathVariable("decisionSetId") String decisionSetId,
			@PathVariable("level") String level) {
		System.err.println("Inside SalesOrderLevelStatus find by Id method");
		return services.checkLevelStatus(decisionSetId, level);
	}

	@GetMapping("/findById/{salesOrderLevelStatusId}")
	public ResponseEntity findSalesOrderLevelStatusById(
			@PathVariable("salesOrderLevelStatusId") String salesOrderLevelStatusId) {
		System.err.println("Inside SalesOrderLevelStatus find by Id method");
		return services.getSalesOrderLevelStatusById(salesOrderLevelStatusId);
	}

	@GetMapping("/checkLevelAndDecisionSet/{decisionSetId}&{level}")
	public ResponseEntity getSalesOrderLevelStatusByDecisionSetAndLevel(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("level") String level) {
		System.err.println("Inside SalesOrderLevelStatus find by Id method");
		return services.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSetId, level);
	}

}
