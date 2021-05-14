package com.incture.cherrywork.dao;

import java.util.List;
import java.util.Map;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface SalesOrderItemStatusDao {

	public String saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault;

	public List<SalesOrderItemStatusDto> listAllSalesOrderItemStatuses();

	public SalesOrderItemStatusDto getSalesOrderItemStatusById(String salesOrderItemStatusId);

	public String deleteSalesOrderItemStatusById(String salesOrderItemStatusId) throws ExecutionFault;

	public Map<TaskItemDto, SalesOrderItemStatusDto> batchFetchByTaskSerIdItemId(List<TaskItemDto> taskItemList);

	public SalesOrderItemStatusDto getItemStatusDataUsingTaskSerialIdAndItemNum(String taskSerialId, String itemNum);

	public List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId);

	public List<SalesOrderItemStatusDto> getItemStatusDataUsingSalesOrderItemNum(String itemNum);

	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevelAndItemNum(String decisionSetId,
			String levelNum, String itemNum);

	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevel(String decisionSetId, String levelNum);

	public List<SalesOrderItemStatusDto> getItemsBySapTaskId(String sapTaskId);

	public SalesOrderItemStatusDto getItemStatusFromDecisionSetAndLevel(String decisionSetId, String level,
			String itemNum);

	public String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault;

	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevelList(String decisionSetId, String level,
			String itemNum);

	public List<SalesOrderItemStatusDto> getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
			String decisionSet);

	public List<SalesOrderItemStatusDto> getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String workflowId, String salesOrderItemNum);

	public List<SalesOrderItemStatusDto> getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String salesOrderItemNum);
	
	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionsetId(String decisionSetId);
}
