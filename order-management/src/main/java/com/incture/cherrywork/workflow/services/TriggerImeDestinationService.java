package com.incture.cherrywork.workflow.services;

import java.io.IOException;
import java.net.URISyntaxException;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface TriggerImeDestinationService {

	public String triggerIme(String decisionSetId);

	public ResponseEntity triggerImeForBlockWorkflow(String requestId);
	public ResponseEntity triggerImePostDS(String salesOrderNumber)  throws URISyntaxException, IOException;
}
