package com.incture.cherrywork.rules;


import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface RuleOutputDto {
	
	//public RuleOutputDto convertFromJSonNode(JsonNode node);

	public List<ApproverDataOutputDto> convertFromJSonNode(String node);
	List<ApproverDataOutputDto> convertFromJSonNodeWorkruleOutput(String node);

	public List<ApproverDataOutputDto> convertFromJSonNodeRo(String node);
}

