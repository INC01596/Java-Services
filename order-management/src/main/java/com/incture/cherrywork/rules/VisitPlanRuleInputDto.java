package com.incture.cherrywork.rules;

import lombok.Data;

@Data
public class VisitPlanRuleInputDto {
	private String visitId;

	public String toRuleInputString(String rulesServiceId, String salesRepEmail, String custCode) {
		return "{ \"RuleServiceId\" : \"" + rulesServiceId + "\", \"Vocabulary\" : [ {"
				+ "\"SalesVisitPlannerInput\" : { \"salesRepId\":\"" + salesRepEmail + "\",\"customerCode\":\""
				+ custCode + "\"} } ] }";
	}

}
