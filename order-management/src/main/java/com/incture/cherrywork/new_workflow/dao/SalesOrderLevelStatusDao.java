package com.incture.cherrywork.new_workflow.dao;



import java.util.List;
import java.util.Set;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface SalesOrderLevelStatusDao {

	public String saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault;

	public List<SalesOrderLevelStatusDto> listAllSalesOrderLevelStatuses();

	public SalesOrderLevelStatusDto getSalesOrderLevelStatusById(String salesOrderLevelStatusId);

	public SalesOrderLevelStatusDo getSalesOrderLevelStatusDoById(String salesOrderLevelStatusId);

	public String deleteSalesOrderLevelStatusById(String salesOrderLevelStatusId) throws ExecutionFault;

	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSet, String level);

	public String persistSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto) throws ExecutionFault;

	public String saveSalerOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto);

	public String saveOrUpdateSalesOrderLevelStatusSynchronized(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault;

	public String saveOrUpdateSalesOrderLevelStatusSynchronizedDo(SalesOrderLevelStatusDo salesOrderLevelStatusDo)
			throws ExecutionFault;

	public SalesOrderLevelStatusDo getSalesOrderLevelStatusByDecisionSetAndLevelDo(String decisionSetId, String level);

	public List<SalesOrderLevelStatusDto> getSalesOrderLevelStatusByDecisionSet(String decisionSetId);

	public List<SalesOrderLevelStatusDto> getSalesOrderLevelStatusByDecisionSetWithLevelInProgress(
			String decisionSetId);

	public List<SalesOrderLevelStatusDto> getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(String decisionSetId,
			String level);

	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevelSession(String decisionSetId,
			String level);

	public String saveOrUpdateSalesOrderLevelStatusSave(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault;

	public List<SalesOrderLevelStatusDto> getAllTheUpcomingLevelStatusesForPerticularDecisionSet(String decisionSet,
			List<String> pastLevelList);

	public Set<Integer> getLevelStatusIdByDS(List<String> decisionSetIDs);

}
