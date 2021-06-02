package com.incture.cherrywork.tasksubmit;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface TriggerImeService {
	
	public String triggerIme(String salesOrderNo);

	public ResponseEntity triggerImeForBlockWorkflow(String requestId);
	

}