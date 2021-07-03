package com.incture.cherrywork.workflow.services;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface TriggerImeDestinationService {

	public String triggerIme(String decisionSetId);

	public ResponseEntity triggerImeForBlockWorkflow(String requestId);
}
