package com.incture.cherrywork.workflow.services;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;


public interface ImeTrigger {

	
	
	
	
		
		
		public String triggerIme(String salesOrderNo);
		
		public String execute( String salesOrderNo) throws ClientProtocolException, IOException;



}

