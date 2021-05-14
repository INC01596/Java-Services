package com.incture.cherrywork.workflow.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;

@Repository
public interface ISalesOrderTaskStatusCustomRepository {

	List<SalesOrderTaskStatusDto> getAllTaskByTaskSerialId(List<String> taskSerialId);
	SalesOrderTaskStatusDo getSalesOrderTaskStatusDoById(String salesOrderTaskStatusId);
	List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevel(String decisionSetId, String level);
	SalesOrderTaskStatusDto getAllTasksFromLevelStatusSerialId(String levelStatusSerialId);
	List<String> getTasksIdFromDecisionSetAndLevel(String decisionSet);
}
