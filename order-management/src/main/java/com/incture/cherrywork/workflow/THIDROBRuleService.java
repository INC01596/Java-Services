/*package com.incture.cherrywork.workflow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.incture.rule.constants.RuleConstants;
import com.incture.rule.idb.dto.ApproverDataOutputDto;
import com.incture.rule.interfaces.RuleInputDto;
import com.incture.rule.interfaces.RuleServiceDestination;

public class THIDROBRuleService extends RuleServiceDestination{
	

	public THIDROBRuleService(){	
	}
	
	@Override
	public List<?> getResultList(RuleInputDto input) throws ClientProtocolException, IOException, URISyntaxException {

		System.err.println("RuleINputDto "+ input);
		String node = execute(input, RuleConstants.thIDBRORuleService);
		
		ApproverDataOutputDto dto = new ApproverDataOutputDto();
		
		if(node!=null && !node.isEmpty())
		    return dto.convertFromJSonNodeRo(node);
		return null;
	}

}*/

