package com.incture.cherrywork.workflow.services;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.rules.ApproverDataOutputDto;
import com.incture.cherrywork.rules.RuleConstants;
import com.incture.cherrywork.rules.RuleInputDto;
import com.incture.cherrywork.rules.RuleServiceDestination;


@Service
@Transactional
public class THIDROBRuleService extends RuleServiceDestination{
	
	private String tableId = "DT_000202";

	public String setDecisonTableId(String id) {
		this.tableId = id;
		return this.tableId;
	}

	

	public THIDROBRuleService(){	
	}
	
	@Override
	public List<?> getResultListRuleService(RuleInputDto input) throws ClientProtocolException, IOException, URISyntaxException {

		System.err.println("RuleINputDto "+ input);
		//Commented below line for using workrule api call.
//		String node = execute(input, RuleConstants.thIDBRuleService);
		String node = callWorkRulesRuleEngine(input, tableId);

		System.err.println("[THIDROBRuleService][getResultList] node: "+node);
		ApproverDataOutputDto dto = new ApproverDataOutputDto();
		
		if(node!=null && !node.isEmpty()){
			
		//Below line commented due to above workrule call.
//		    return dto.convertFromJSonNodeRo(node);
			return dto.convertFromJSonNodeRoFromWorkRule(node);
		}
		return null;
	}

}
