package com.incture.cherrywork.rules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;


//@Component
public class THIDBRuleService extends RuleServiceDestination{


	public THIDBRuleService(){
		
		
	}
	
	@Override
	public List<?> getResultList(RuleInputDto input) throws ClientProtocolException, IOException, URISyntaxException {

		
		
		System.err.println("RuleINputDto "+ input);
		String node = execute(input, RuleConstants.thIDBRuleService);
		
		ApproverDataOutputDto dto = new ApproverDataOutputDto();
		
		if(node!=null && !node.isEmpty())
		    return dto.convertFromJSonNode(node);
		return null;
	}
}
