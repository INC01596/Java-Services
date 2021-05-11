package com.incture.cherrywork.workflow;


import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;


public interface SalesOrderTaskStatusesService {

	public ResponseEntity<Object> saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto);

	public ResponseEntity<Object> listAllSalesOrderTaskStatus();

	public ResponseEntity<Object> getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	public ResponseEntity<Object> deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key);

	public ResponseEntity<Object> getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level);

}

