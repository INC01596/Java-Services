package com.incture.cherrywork.workflow.services;

import java.util.List;

import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface SalesOrderItemStatusService {

	public ResponseEntity saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto);

	public ResponseEntity listAllSalesOrderItemStatuses();

	public ResponseEntity deleteSalesOrderItemStatusById(String salesOrderItemStatusId);

	public ResponseEntity getSalesOrderItemStatusById(String salesOrderItemStatusId);

	public ResponseEntity getItemStatusListByTaskSerIdItemId(List<TaskItemDto> taskItemList);

	public ResponseEntity getItemListBySapTaskId(String sapTaskId);

	public ResponseEntity onSubmitCheckAndUpdateItemStatus(OnSubmitTaskDto submitTaskDto);

	public ResponseEntity getItemStatusFromDecisionSetAndLevel(String decisionSet, String levelNum);

	public ResponseEntity getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String workflowTaskId, String itemNum);

	public ResponseEntity getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId, String itemNum);

	public ResponseEntity getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(String decisionSetId);

	public String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault;

}
