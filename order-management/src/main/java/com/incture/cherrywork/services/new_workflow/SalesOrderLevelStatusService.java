package com.incture.cherrywork.services.new_workflow;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface SalesOrderLevelStatusService {

	public ResponseEntity saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto);

	public ResponseEntity listAllSalesOrderLevelStatuses();

	public ResponseEntity deleteSalesOrderLevelStatusById(String salesOrderLevelStatusId);

	public ResponseEntity getSalesOrderLevelStatusById(String salesOrderLevelStatusId);

	public ResponseEntity getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSet, String level);

	public ResponseEntity checkLevelStatus(String decisionSet, String level);

}

