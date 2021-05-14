package com.incture.cherrywork.services.new_workflow;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;

public interface IWorkflowServices {
	String saveOrUpdateSalesOrderTaskStatusSynchronized(SalesOrderTaskStatusDto salesOrderTaskStatusDto);
	SalesOrderTaskStatusDto getSalesOrderTaskStatusById(String salesOrderTaskStatusId);
	String saveOrUpdateSalesOrderLevelStatusSynchronized(SalesOrderLevelStatusDto salesOrderLevelStatusDto);
	ResponseEntity getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(String dlvBlockCode);
	String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto);
	ResponseEntity<Object> getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum);
	String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto);
	String updateLevelStatusAndTaskStatus(String taskid, String decisionSetId, String level);
	List<SalesOrderTaskStatusDto> getAllTasksFromSapTaskId(String taskId);
	List<String> getItemListByDataSet(String dataSet);
	String saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto);
	List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId);

}
