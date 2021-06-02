package com.incture.cherrywork.new_workflow.dao;


import java.util.List;

import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface SalesOrderTaskStatusDao {

	public String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault;

	public List<SalesOrderTaskStatusDto> getAllTasksListFromLevelStatusSerialId(String levelStatusSerialId);

	public List<SalesOrderTaskStatusDto> listAllSalesOrderTaskStatuses();

	public SalesOrderTaskStatusDo getSalesOrderTaskStatusDoById(String salesOrderTaskStatusId);

	public SalesOrderTaskStatusDto getSalesOrderTaskStatusById(String salesOrderTaskStatusId);

	public String deleteSalesOrderTaskStatusById(String salesOrderTaskStatusId) throws ExecutionFault;

	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevel(String decisionSet, String level);

	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSet(String decisionSetId);

	public List<SalesOrderTaskStatusDto> getAllTasksFromSapTaskId(String taskId);

	public SalesOrderTaskStatusDto getAllTasksFromLevelStatusSerialId(String levelStatusSerialId);

	public SalesOrderTaskStatusDto getTasksFromDecisionSetAndLevel(String decisionSet, String level);

	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevelAndItemNum(String decisionSet, String level,
			String itemNum);

	public String saveOrUpdateSalesOrderTaskStatusSynchronized(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault;

	public List<SalesOrderTaskStatusDo> getAllTasksFromSapTaskIdDo(String taskId);

	public List<SalesOrderTaskStatusDto> getAllTaskByTaskSerialId(List<String> taskSerialId);

	public List<SalesOrderTaskStatusDto> getListOfAllTasksFromLevelStatusSerialId(String levelStatusSerialId);

	public String saveOrUpdateSalesOrderTaskStatusUpdate(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault;

	public List<String> getTasksIdFromDecisionSetAndLevel(String decisionSet);



}

