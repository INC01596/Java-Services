package com.incture.cherrywork.rules;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.ReturnOrderRuleInputDto;

@Service
@Transactional
public class THIDBRuleService extends RuleServiceDestination {

	public THIDBRuleService() {

	}

	@Override
	public List<?> getResultListRuleService(RuleInputDto input)
			throws ClientProtocolException, IOException, URISyntaxException {

		System.err.println("RuleINputDto " + input);

		String node = execute(input, RuleConstants.thIDBRuleService);

		ApproverDataOutputDto dto = new ApproverDataOutputDto();

		if (node != null && !node.isEmpty())
			return dto.convertFromJSonNode(node);
		if (node != null && !node.isEmpty())
			return dto.convertFromJSonNodeWorkruleOutput(node);
		return null;
	}

}
