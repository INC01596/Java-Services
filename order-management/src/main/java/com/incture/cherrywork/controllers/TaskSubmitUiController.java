package com.incture.cherrywork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderValidatorDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.TriggerImePostDSDto;
import com.incture.cherrywork.tasksubmit.TaskSubmitUIService;



@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/taskSubmit")
public class TaskSubmitUiController {

	@Autowired
	private TaskSubmitUIService taskSubmitUIService;

	@PostMapping("/onSubmit")
	public ResponseEntity checkSubmit(@RequestBody OnSubmitTaskDto submitTaskDto) {
		System.err.println("on submit check");
		return taskSubmitUIService.mainMethod(submitTaskDto);
	}

	@GetMapping("/getSalesOrder/{salesOrderNum}&{decisionSetId}&{sapTaskId}&{levelNum}")
	public ResponseEntity validateSalesOrder(@PathVariable("salesOrderNum") String salesOrderNum,
			@PathVariable("decisionSetId") String decisionSetId, @PathVariable("sapTaskId") String sapTaskId,
			@PathVariable("levelNum") String levelNum) {
		return taskSubmitUIService.validateSalesOrder(salesOrderNum, decisionSetId, sapTaskId, levelNum);
	}

	@PostMapping("/getSalesOrders")
	public ResponseEntity validateAllSalesOrders(@RequestBody List<SalesOrderValidatorDto> salesOrderValidatorList) {
		return taskSubmitUIService.validateAllSalesOrders(salesOrderValidatorList);
	}

	@PostMapping("/updateTaskAndLevelStatusAndCompleteTask")
	public ResponseEntity updateTaskAndLevelStatusAndCompleteTask(@RequestBody TriggerImePostDSDto triggerImePostDSDto) {
		return taskSubmitUIService.updateTaskAndLevelStatus(triggerImePostDSDto.getDataSet(),triggerImePostDSDto.getSalesOrderNo());
	}
	
	@GetMapping("/getOverallHeaderStatus/{salesOrderNumber}")
	public ResponseEntity getAllHdbStatus(@PathVariable ("salesOrderNumber") String salesOrderNumber){
		
		return taskSubmitUIService.releaseHdbBlock(salesOrderNumber);
	}

}
