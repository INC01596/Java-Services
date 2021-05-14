package com.incture.cherrywork.rules;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;



public interface THIDBRuleServiceInterface {
	
	public List<?> getResultList(RuleInputDto input) throws ClientProtocolException, IOException;

}