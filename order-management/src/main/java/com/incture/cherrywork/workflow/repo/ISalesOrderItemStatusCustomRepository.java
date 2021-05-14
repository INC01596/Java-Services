package com.incture.cherrywork.workflow.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;

@Repository
public interface ISalesOrderItemStatusCustomRepository {
	SalesOrderItemStatusDto getItemStatusFromDecisionSetAndLevel(String decisionSetId, String level,String itemNum);
	List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId);

}
