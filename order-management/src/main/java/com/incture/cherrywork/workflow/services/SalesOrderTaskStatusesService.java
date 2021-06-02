package com.incture.cherrywork.workflow.services;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface SalesOrderTaskStatusesService {

	public ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto);

	public ResponseEntity listAllSalesOrderTaskStatus();

	public ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	public ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	public ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level);

}

