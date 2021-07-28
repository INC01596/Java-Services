package com.incture.cherrywork.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.dtos.TriggerImePostDSDto;
import com.incture.cherrywork.services.ISalesDocItemService;


@RestController
@RequestMapping("/salesDocItem")
public class SalesDocItemController {

	@Autowired
	private ISalesDocItemService services;

	
	
	@PostMapping(path = "/getDataStructureSet", consumes = "application/json", produces = "application/json")
	public ResponseEntity dataStructureSet(@RequestBody SalesOrderHeaderInput salesOrderHeaderInput) {
		System.err.println("Sales Document Item List inside controller classs");

		System.err.println("Sales Document Item List inside controller classs " + salesOrderHeaderInput.toString());

		return services.getInputDtoDataSet(salesOrderHeaderInput);
	}
	@PostMapping(path = "/triggerImePostDSCompletion", consumes = "application/json", produces = "application/json")
	public ResponseEntity triggerImePostDSCompletion(@RequestBody TriggerImePostDSDto triggerPostDsDto)
			throws URISyntaxException, IOException {
		return services.triggerImePostSoDsCompletion(triggerPostDsDto.getSalesOrderNo(), triggerPostDsDto.getDataSet());
	}


}
