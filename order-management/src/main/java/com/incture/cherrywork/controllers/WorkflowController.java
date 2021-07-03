<<<<<<< HEAD
=======

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
package com.incture.cherrywork.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ApprovalWorkflowInputDto;
import com.incture.cherrywork.dtos.CheckNextTriggerDto;
import com.incture.cherrywork.dtos.DecisionSetWorkflow;
import com.incture.cherrywork.dtos.DeletePayloadDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.WorkflowResponseDto;
import com.incture.cherrywork.dtos.WorkflowResponseEntity;
import com.incture.cherrywork.workflow.services.ApprovalworkflowTrigger;
import com.incture.cherrywork.workflow.services.DeletionOfWorflowTaskId;
import com.incture.cherrywork.workflow.services.ImeTrigger;
import com.incture.cherrywork.workflow.services.WorkflowTrigger;






@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/WorkflowController")
public class WorkflowController {

//	@Autowired
//	private WorkflowTrigger workflowtrigger;

	@Autowired
	private ApprovalworkflowTrigger approvalworkflowTrigger;

	@Autowired
	private ImeTrigger imetrigger;

	@Autowired
	private DeletionOfWorflowTaskId deletionOfWorkflowtaskId;
	
	public WorkflowController() {
		System.err.println("inside wkflow controller");
	}
	
	
	@PostMapping(path = "/triggerApproverWorkflow", consumes = "application/json", produces = "application/json")
	public WorkflowResponseEntity triggerApprovalWorkflow(@RequestBody ApprovalWorkflowInputDto inputDto) {
		System.err.println("inside workflow  aprrover controller");
		return approvalworkflowTrigger.approvalworkflowTriggeringAndUpdateTable(inputDto.getSalesOrderNo(),
				inputDto.getRequestId(), inputDto.getApprover(), inputDto.getLevel(), inputDto.getDataSet(),
				inputDto.getThreshold(), inputDto.getDecisionSetAmount(), inputDto.getHeaderBlocReas(),
				inputDto.getSoCreatedECC(), inputDto.getCountry(), inputDto.getCustomerPo(), inputDto.getRequestType(),
				inputDto.getRequestCategory(), inputDto.getSalesOrderType(), inputDto.getSoldToParty(),
				inputDto.getShipToParty(),inputDto.getDivision(),inputDto.getDistributionChannel(),inputDto.getSalesOrg(),inputDto.getReturnReason());
	}

	@PutMapping(path = "/triggerApproverWorkflowAnd", consumes = "application/json", produces = "application/json")
	public WorkflowResponseEntity triggerApprovalWorkflowAnd(@RequestBody ApprovalWorkflowInputDto inputDto)
			throws IOException {
		System.err.println("inside workflow  approver  controller triggerApprovalWorkflowAnd");
		return approvalworkflowTrigger.approvalworkflowTriggeringAndUpdateTables(inputDto.getSalesOrderNo(),
				inputDto.getRequestId(), inputDto.getApprover(), inputDto.getLevel(), inputDto.getDataSet(),
				inputDto.getThreshold(), inputDto.getDecisionSetAmount(), inputDto.getApprovalType(),
				inputDto.getHeaderBlocReas(), inputDto.getSoCreatedECC(), inputDto.getCountry(),
				inputDto.getCustomerPo(), inputDto.getRequestType(), inputDto.getRequestCategory(),
				inputDto.getSalesOrderType(), inputDto.getSoldToParty(), inputDto.getShipToParty(),inputDto.getDivision(),inputDto.getDistributionChannel(),inputDto.getSalesOrg()
				,inputDto.getReturnReason());
	}

//	@PostMapping(path = "/triggerDecisionsetWorkflow", consumes = "application/json", produces = "application/json")
//	public WorkflowResponseEntity triggerDecisionlWorkflow(@RequestBody DecisionSetWorkflow inputDto) {
//		System.err.println("inside workflow decision set controller");
//		return workflowtrigger.DecisionSetWorkflowTrigger(inputDto.getSalesOrderNo(), inputDto.getRequestId(),
//				inputDto.getDataSetKey(), inputDto.getApproverDtoList(), inputDto.getThreshold(),
//				inputDto.getDecisionSetAmount(), inputDto.getHeaderBlocReas(), inputDto.getSoCreatedECC(),
//				inputDto.getCountry(), inputDto.getCustomerPo(), inputDto.getRequestType(),
//				inputDto.getRequestCategory(), inputDto.getSalesOrderType(), inputDto.getSoldToParty(),
//				inputDto.getShipToParty(),inputDto.getDivision(),inputDto.getDistributionChannel(),inputDto.getSalesOrg(),inputDto.getReturnReason());
//	}

	@PostMapping(path = "/checkNextTriggerLevelOR", consumes = "application/json", produces = "application/json")
	public ResponseEntity checkNextTriggerLevelOr(@RequestBody CheckNextTriggerDto checkNextTriggerDto) {
		System.err.println("inside workflow decision set controller" + checkNextTriggerDto.getDataSet()
				+ checkNextTriggerDto.getLevel());

		return approvalworkflowTrigger.checkForNextLevelTrigger(checkNextTriggerDto.getDataSet(),
				checkNextTriggerDto.getLevel());
	}

	@PostMapping(path = "/checkNextTriggerLevelAnd", consumes = "application/json", produces = "application/json")
	public ResponseEntity checkNextTriggerLevelAnd(@RequestBody CheckNextTriggerDto checkNextTriggerDto) {
		System.err.println("inside workflow decision set controller" + checkNextTriggerDto.getDataSet()
				+ checkNextTriggerDto.getLevel());

		return approvalworkflowTrigger.checkForNextLevelTriggerAnd(checkNextTriggerDto.getDataSet(),
				checkNextTriggerDto.getLevel());
	}

	@PostMapping(path = "/checkNextTriggerLevel", consumes = "application/json", produces = "application/json")
	public ResponseEntity checkNextTriggerLevel(@RequestBody CheckNextTriggerDto checkNextTriggerDto) {
		System.err.println("inside workflow decision set controller" + checkNextTriggerDto.getDataSet()
				+ checkNextTriggerDto.getLevel());
		return approvalworkflowTrigger.checkNextLevelTriggerForAND_ORR(checkNextTriggerDto.getDataSet(),
				checkNextTriggerDto.getLevel(), checkNextTriggerDto.getApprovalType());
	}

	@PostMapping(path = "/getTaskIdByWorkflowInstanceId", consumes = "application/json", produces = "application/json")
	public WorkflowResponseEntity getTaskIdByWorkflowInstanceId(@RequestBody CheckNextTriggerDto checkNextTriggerDto) {
		System.err.println("inside workflow decision set controller " + checkNextTriggerDto.getDataSet()
				+ checkNextTriggerDto.getLevel());

		WorkflowResponseEntity responseentity = approvalworkflowTrigger
				.workflowTaskInstanceIdByDecisionSetAndLevel(checkNextTriggerDto.getDataSet());

		System.err.println(" return of check next trigger " + responseentity.toString());

		return responseentity;

	}

	@PostMapping(path = "/deleteWorkflowByDefinitionid", consumes = "application/json", produces = "application/json")
	public List<String> deleteWorkflowByDefinitionId(@RequestBody DeletePayloadDto deletepayloadDto)
			throws UnsupportedOperationException, IOException, URISyntaxException {

		return deletionOfWorkflowtaskId.httpClientGet(deletepayloadDto.getId());

	}

	@PostMapping(path = "/deleteWorkflow", consumes = "application/json", produces = "application/json")
	public String deleteWorkflowByDefinitionId() throws UnsupportedOperationException, IOException, URISyntaxException {

		return deletionOfWorkflowtaskId.findAllTheRunningAndErrorenous();

	}
	
	@PostMapping(path = "/cancelWorkflow", produces = "application/json")
	public ResponseEntity cancelWorkflowByBussinesKey( @RequestBody WorkflowResponseDto  workflowRepsone) throws UnsupportedOperationException, IOException, URISyntaxException {

		return approvalworkflowTrigger.closeAllWorkflowsByBussinessKey(workflowRepsone.getBusinessKey());

}

}


