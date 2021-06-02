package com.incture.cherrywork.tasksubmit;

import java.io.IOException;
import java.net.URISyntaxException;

import com.incture.cherrywork.dtos.ResponseEntity;


public interface TriggerImeForWorkflowService {
	
	public ResponseEntity triggerImePostDS(String salesOrderNumber) throws URISyntaxException, IOException;

}

