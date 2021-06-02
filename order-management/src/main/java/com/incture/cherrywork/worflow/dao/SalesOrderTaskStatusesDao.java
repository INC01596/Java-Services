package com.incture.cherrywork.worflow.dao;

import java.util.List;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.exceptions.ExecutionFault;


public interface SalesOrderTaskStatusesDao {

	public String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto)
			throws ExecutionFault;

	public List<SalesOrderTaskStatusesDto> listAllSalesOrderTaskStatus();

	public SalesOrderTaskStatusesDto getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	public String deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) throws ExecutionFault;

	public List<SalesOrderTaskStatusesDto> getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level);

}
