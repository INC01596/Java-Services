package com.incture.cherrywork.workflow.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;

@Repository
public interface ISalesOrderLevelStatusCustomRepository {
	SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSetId, String level);
	SalesOrderLevelStatusDo getSalesOrderLevelStatusByDecisionSetAndLevelDo(String decisionSetId, String level);
	SalesOrderLevelStatusDo getSalesOrderLevelStatusDoById(String salesOrderLevelStatusId);
	SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevelSession(String decisionSetId,
			String level);
	List<SalesOrderLevelStatusDto> getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(String decisionSetId,
			String level);

}
