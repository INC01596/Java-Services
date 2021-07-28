package com.incture.cherrywork.services;

import java.io.IOException;
import java.net.URISyntaxException;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;

public interface ISalesDocItemService {
	public ResponseEntity getInputDtoDataSet(SalesOrderHeaderInput soInput);
	public ResponseEntity triggerImePostSoDsCompletion(String salesOrderNumber, String decisionSetId) throws URISyntaxException, IOException ;

}
