package com.incture.cherrywork.util;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RuleEnginePayload {

	private String decisionTableId;
	private boolean executionStartsFromRight=false;
	private List<Map<String,Object>> conditions = new ArrayList<>();
}
