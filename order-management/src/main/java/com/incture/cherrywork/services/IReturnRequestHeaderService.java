package com.incture.cherrywork.services;

import java.util.Map;

import javax.naming.NamingException;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.DecisionSetAndLevelDto;
import com.incture.cherrywork.dtos.DecisionSetAndLevelKeyDto;
import com.incture.cherrywork.dtos.FilterOnReturnHeaderDto;
import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.dtos.ReturnOrderDto;
import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;

public interface IReturnRequestHeaderService {
	ResponseEntity<Object> saveAsDraft(ReturnOrderRequestPojo requestData);

	ResponseEntity<Object> createReturnRequest(ReturnOrderRequestPojo requestData);

	ResponseEntity<Object> findByReturnReqNum(String returnReqNum);

	ResponseEntity<Object> listAllReturnReqHeaders(ReturnFilterDto dto);

	ResponseEntity<Object> getReturnOrderCreationMessage(String returnReqNum);

	ResponseEntity<Object> findAll(int pageNo);

	ResponseEntity<Object> listAllReturn(ReturnFilterDto dto);
	ResponseEntity<Object> fetchReturnHeaderListForUserWhichHasTasksForNewDac(FilterOnReturnHeaderDto filterDto,
			Integer indexNum, Integer count);
	ResponseEntity<Object> fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(String userId, String customerPo,
			String projectCode);
	ResponseEntity<Object> returnApprovalOnSubmit(ReturnOrderDto returnOrderDto);
	ResponseEntity<Object> postSalesOrderToEcc(ReturnOrderDto returnOrderDto,
			Map<DecisionSetAndLevelKeyDto, DecisionSetAndLevelDto> uniqueDecisionSetAndLevelIdMap);
	ResponseEntity<Object> getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum);
	ResponseEntity<Object> updateTaskCompletionBasedOnWorkflowInstanceId(String taskId);
	ResponseEntity<?> getInstanceIdByWorkflowInstanceId(String workflowInstaceId);
	
}
