package com.incture.cherrywork.dtos;

import java.util.Map;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class DecisionSetAndLevelDto {

	private DecisionSetAndLevelKeyDto key;
	private String approverType;
	private Boolean lastLevel = false;
	private SalesOrderLevelStatusDto nextLevelStatusDto;
	private Map<String, Integer> mapOfCumulativeItemsStatus;

	public DecisionSetAndLevelDto(String decisionSet, String level, String approverType) {
		super();
		this.key = new DecisionSetAndLevelKeyDto(decisionSet, level);
		this.approverType = approverType;
	}

}
