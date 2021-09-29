package com.incture.cherrywork.controllers;

import java.util.List;

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

import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.workflow.services.SalesOrderItemStatusService;



@RestController
@RequestMapping("/itemStatus")
public class SalesOrderItemStatusController {

	public SalesOrderItemStatusController() {
		//HelperClass.getLogger(this.getClass().getName()).info("inside SalesOrderItemStatus controller");
	}

	@Autowired
	private SalesOrderItemStatusService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createSalesOrderItemStatus(@RequestBody SalesOrderItemStatusDto salesOrderItemStatusDto) {
		//HelperClass.getLogger(this.getClass().getName()).info("SalesOrderItemStatus Created Successfully");
		return services.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateSalesOrderItemStatus(@RequestBody SalesOrderItemStatusDto salesOrderItemStatusDto) {
		System.err.println("SalesOrderItemStatus Updated Successfully");
		if (salesOrderItemStatusDto.getItemStatusSerialId() != null) {
			return services.saveOrUpdateSalesOrderItemStatus(salesOrderItemStatusDto);
		} else {
			return new ResponseEntity(salesOrderItemStatusDto, HttpStatus.OK,
					"SalesOrderItemStatus Id field is mandatory.", ResponseStatus.SUCCESS);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllSalesOrderItemStatus() {
		//HelperClass.getLogger(this.getClass().getName()).info("Inside SalesOrderItemStatuss List");
		return services.listAllSalesOrderItemStatuses();
	}

	@DeleteMapping("/deleteById/{salesOrderItemStatusId}")
	public ResponseEntity deleteSalesOrderItemStatusById(
			@PathVariable("salesOrderItemStatusId") String salesOrderItemStatusId) {
		System.err.println("Inside Delete SalesOrderItemStatus by Id");
		return services.deleteSalesOrderItemStatusById(salesOrderItemStatusId);
	}

	@GetMapping("/findById/{salesOrderItemStatusId}")
	public ResponseEntity findSalesOrderItemStatusById(
			@PathVariable("salesOrderItemStatusId") String salesOrderItemStatusId) {
		System.err.println("Inside SalesOrderItemStatus find by Id method");
		return services.getSalesOrderItemStatusById(salesOrderItemStatusId);
	}

	@GetMapping("/findItemsByDS&L/{decisionSetId}&{levelNum}")
	public ResponseEntity findSalesOrderItemStatusByDecisionSetAndLevelNum(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("levelNum") String levelNum) {
		System.err.println("Inside SalesOrderItemStatus find by Id method");
		return services.getItemStatusFromDecisionSetAndLevel(decisionSetId, levelNum);
	}

	@GetMapping("/findItemsByDS&ItemNum/{decisionSetId}&{workflowTaskId}&{itemNum}")
	public ResponseEntity getItemsStatusFromDecisionSetAndItemNumForAllLevels(
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("workflowTaskId") String workflowTaskId,
			@PathVariable("itemNum") String itemNum) {
		System.err.println("Inside SalesOrderItemStatus find by Id method");
		return services.getItemsStatusFromDecisionSetAndItemNumForAllLevels(decisionSetId, workflowTaskId, itemNum);
	}

	@PostMapping(path = "/fetchMap", consumes = "application/json", produces = "application/json")
	public ResponseEntity getItemStatusListByTaskSerIdItemId(@RequestBody List<TaskItemDto> taskItemList) {
		//HelperClass.getLogger(this.getClass().getName()).info("SalesOrderItemStatus Created Successfully");
		return services.getItemStatusListByTaskSerIdItemId(taskItemList);
	}

	@PostMapping(path = "/updateItemStatus", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateItemStatus(@RequestBody OnSubmitTaskDto submitTaskDto) {
		return services.onSubmitCheckAndUpdateItemStatus(submitTaskDto);
	}

	@GetMapping("/findBySapTaskId/{sapTaskId}")
	public ResponseEntity findSalesOrderItemStatusBySapTaskId(@PathVariable("sapTaskId") String sapTaskId) {
		System.err.println("Inside SalesOrderItemStatus find by Id method");
		return services.getItemListBySapTaskId(sapTaskId);
	}

	@GetMapping(path = "/checkBlockItems/{decisionSetId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity checkBlockItemInDecisionSet(@PathVariable("decisionSetId") String decisionSetId) {
		return services.getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(decisionSetId);
	}

}

