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

import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.new_workflow.UpdatSalesOrderLevelAndItemTableDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.new_workflow.SalesOrderTaskStatusService;



@RestController
@RequestMapping("/taskStatus")
public class SalesOrderTaskStatusController {

	public SalesOrderTaskStatusController() {
		//HelperClass.getLogger(this.getClass().getName()).info("inside SalesOrderTaskStatus controller");
	}

	@Autowired
	private SalesOrderTaskStatusService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createSalesOrderTaskStatus(@RequestBody SalesOrderTaskStatusDto salesOrderTaskStatusDto) {
		//HelperClass.getLogger(this.getClass().getName()).info("SalesOrderTaskStatus Created Successfully");
		return services.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateSalesOrderTaskStatus(@RequestBody SalesOrderTaskStatusDto salesOrderTaskStatusDto) {
		System.err.println("SalesOrderTaskStatus Updated Successfully");
		if (salesOrderTaskStatusDto.getTaskStatusSerialId() != null) {
			return services.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);
		} else {
			return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.OK,
					"SalesOrderTaskStatus Id field is mandatory.", ResponseStatus.SUCCESS);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllSalesOrderTaskStatuses() {
		//HelperClass.getLogger(this.getClass().getName()).info("Inside SalesOrderTaskStatuses List");
		return services.listAllSalesOrderTaskStatuses();
	}

	@DeleteMapping("/deleteById/{salesOrderTaskStatusId}")
	public ResponseEntity deleteSalesOrderTaskStatusById(
			@PathVariable("salesOrderTaskStatusId") String salesOrderTaskStatusId) {
		System.err.println("Inside Delete SalesOrderTaskStatus by Id");
		return services.deleteSalesOrderTaskStatusById(salesOrderTaskStatusId);
	}

	@GetMapping("/findById/{salesOrderTaskStatusId}")
	public ResponseEntity findSalesOrderTaskStatusById(
			@PathVariable("salesOrderTaskStatusId") String salesOrderTaskStatusId) {
		System.err.println("Inside SalesOrderTaskStatus find by Id method");
		return services.getSalesOrderTaskStatusById(salesOrderTaskStatusId);
	}

	// test method update
	@PostMapping(path = "/updateOrderTaskAndItemStaus", consumes = "application/json", produces = "application/json")
	public String updateSalesOrderTaskStatusAndItemstatus(
			@RequestBody UpdatSalesOrderLevelAndItemTableDto updatesalesorderlevelanditemdto) {
		//HelperClass.getLogger(this.getClass().getName()).info("SalesOrderTaskStatus Created Successfully");
		return services.updateLevelStatusAndTaskStatus(updatesalesorderlevelanditemdto.getTaskid(),
				updatesalesorderlevelanditemdto.getDecisionSetId(), updatesalesorderlevelanditemdto.getLevel());
	}

	@GetMapping("/getTasks/{decisionSetId}&{levelNum}")
	public ResponseEntity findSalesOrderTaskStatusByDecisionSetAndLevel(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("levelNum") String levelNum) {
		return services.getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(decisionSetId, levelNum);
	}

	@GetMapping("/getTasks/{decisionSetId}&{levelNum}&{itemNum}")
	public ResponseEntity findSalesOrderTaskStatusByDecisionSetAndLevelAndItemNum(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("levelNum") String levelNum,
			@PathVariable("itemNum") String itemNum) {
		return services.getAllTasksFromDecisionSetAndLevelAndItemNum(decisionSetId, levelNum, itemNum);
	}

	@GetMapping("/getTasksWithItems/{decisionSetId}&{levelNum}")
	public ResponseEntity getAllTasksFromDecisionSetAndLevelWithItems(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("levelNum") String levelNum) {
		return services.getAllTasksFromDecisionSetAndLevelWithItems(decisionSetId, levelNum);
	}

}

