package com.incture.cherrywork.workflow.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.workflow.SalesDocItemDto;

@Repository
public interface ISalesDocItemCustomRepository {

	List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId);
	List<String> getDSBySalesHeaderID(String salesHeaderId);
}
