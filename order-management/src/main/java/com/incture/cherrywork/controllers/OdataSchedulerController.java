package com.incture.cherrywork.controllers;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.incture.cherrywork.services.SalesOrderOdataServices;



@RestController
@RequestMapping(value = "/odata")
public class OdataSchedulerController {

	@Autowired
	private SalesOrderOdataServices salesOrderodataServices;
	
	@GetMapping("/msg")
	public String welcomeMessage() {
		return "Welcome message from Odata Scheduler Controller...!!!";
	}
	
	@PostMapping("/pricingSet")
	public String pricingSet(@RequestBody String request) {
		
	return salesOrderodataServices.pricingSet(request);
	}
	

	@PostMapping("/user")
	public String usersBySoldToParty(@RequestBody String soldToParty) {
		
	return salesOrderodataServices.usersBySoldToParty(soldToParty);
	
		
	}

}
