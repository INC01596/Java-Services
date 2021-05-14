package com.incture.cherrywork.services.new_workflow;

import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface SalesOrderTaskStatusService {

	public ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto);

	public ResponseEntity listAllSalesOrderTaskStatuses();

	public ResponseEntity deleteSalesOrderTaskStatusById(String salesOrderTaskStatusId);

	public ResponseEntity getSalesOrderTaskStatusById(String salesOrderTaskStatusId);

	public String updateLevelStatusAndTaskStatus(String taskid, String decisionSetId, String level);

	public ResponseEntity getAllTasksFromDecisionSetAndLevelAndItemNum(String decisionSetId, String level,
			String itemNum);

	public ResponseEntity getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum);

	public ResponseEntity getAllTasksFromDecisionSetAndLevelWithItems(String decisionSetId, String levelNum);

}
