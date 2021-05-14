package com.incture.cherrywork.tasksubmit;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface TriggerImeDestinationService {

	public String triggerIme(String decisionSetId);

	public ResponseEntity triggerImeForBlockWorkflow(String requestId);
}
