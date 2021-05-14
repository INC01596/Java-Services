<<<<<<< HEAD
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

=======
//package com.incture.cherrywork.services.new_workflow;
//
//
//
//import com.incture.dto.ResponseEntity;
//import com.incture.dto.new_workflow.SalesOrderLevelStatusDto;
//
//public interface SalesOrderLevelStatusService {
//
//	public ResponseEntity<Object> saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto);
//
//	public ResponseEntity<Object> listAllSalesOrderLevelStatuses();
//
//	public ResponseEntity<Object> deleteSalesOrderLevelStatusById(String salesOrderLevelStatusId);
//
//	public ResponseEntity<Object> getSalesOrderLevelStatusById(String salesOrderLevelStatusId);
//
//	public ResponseEntity<Object> getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSet, String level);
//
//	public ResponseEntity<Object> checkLevelStatus(String decisionSet, String level);
//
//}
//
>>>>>>> refs/remotes/origin/master
