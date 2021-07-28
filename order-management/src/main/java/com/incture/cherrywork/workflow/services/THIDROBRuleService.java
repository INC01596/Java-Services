package com.incture.cherrywork.workflow.services;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.incture.cherrywork.rules.ApproverDataOutputDto;
import com.incture.cherrywork.rules.RuleConstants;
import com.incture.cherrywork.rules.RuleInputDto;
import com.incture.cherrywork.rules.RuleServiceDestination;


public class THIDROBRuleService extends RuleServiceDestination{
	

	public THIDROBRuleService(){	
	}
	
	@Override
	public List<?> getResultList(RuleInputDto input) throws ClientProtocolException, IOException, URISyntaxException {

		System.err.println("RuleINputDto "+ input);
		String node = execute(input, RuleConstants.thIDBRORuleService);
		System.err.println("[THIDROBRuleService][getResultList] node: "+node);
		ApproverDataOutputDto dto = new ApproverDataOutputDto();
		
		if(node!=null && !node.isEmpty())
		    return dto.convertFromJSonNodeRo(node);
		return null;
	}

}
