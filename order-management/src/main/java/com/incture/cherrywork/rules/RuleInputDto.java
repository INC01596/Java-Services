package com.incture.cherrywork.rules;

public interface RuleInputDto {
	
	public String toRuleInputString(String rulesServiceId);
	public String WorkRuleEnginePayload(String decisonTableId);

}