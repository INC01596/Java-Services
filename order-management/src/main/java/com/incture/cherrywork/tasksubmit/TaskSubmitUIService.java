package com.incture.cherrywork.tasksubmit;

import java.util.List;

import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderValidatorDto;
import com.incture.cherrywork.dtos.ResponseEntity;



public interface TaskSubmitUIService {

	public ResponseEntity mainMethod(OnSubmitTaskDto submitTaskDto);

	public ResponseEntity validateSalesOrder(String salesOrderNum, String decisionSetId, String sapTaskId,
			String levelNum);

	public ResponseEntity validateAllSalesOrders(List<SalesOrderValidatorDto> salesOrderValidatorList);

	public ResponseEntity checkStatusTriggerIME(OnSubmitTaskDto submitTaskDto);

	public ResponseEntity updateToECC(OnSubmitTaskDto submitTaskDto);

	public ResponseEntity updateTaskAndLevelStatus(String decisionSet, String salesOrderNum);

	ResponseEntity releaseHdbBlock(String salesOrderNumber);

}
