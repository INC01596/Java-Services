package com.incture.cherrywork.workflow.services;

import java.util.List;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface SalesOrderTaskStatusesService {

	ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto);

	ResponseEntity listAllSalesOrderTaskStatus();

	ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);
	String saveOrUpdateSalesOrderTaskStatus1(SalesOrderTaskStatusesDto salesOrderTaskStatusDto);
	List<SalesOrderTaskStatusesDto> listAllSalesOrderTaskStatus1();

	ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level);
	SalesOrderTaskStatusesDto getSalesOrderTaskStatusById1(SalesOrderTaskStatusKeyDto key);

}

