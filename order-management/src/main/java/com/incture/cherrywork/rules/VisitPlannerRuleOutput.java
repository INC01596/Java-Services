package com.incture.cherrywork.rules;

import java.util.List;

import lombok.Data;

@Data
public class VisitPlannerRuleOutput {

	private List<?> result;
	private Integer levelCount;
	private String message;

	public VisitPlannerRuleOutput(String message) {
		this.message = message;
	}

	public VisitPlannerRuleOutput() {
		super();
	}

	public VisitPlannerRuleOutput(List<?> result, Integer levelCount, String message) {
		super();
		this.result = result;
		this.levelCount = levelCount;
		this.message = message;
	}

}
