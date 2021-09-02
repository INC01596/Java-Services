package com.incture.cherrywork.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.rules.RuleInputDto;
import com.incture.cherrywork.rules.THIDBRuleService;
import com.incture.cherrywork.workflow.services.THIDROBRuleService;

@RestController
@RequestMapping("/WorkRuleController")
public class WorkRuleController {
	
	@Autowired
	private THIDROBRuleService tHIDROBRuleService;
	
	@GetMapping("/setTableId/{tableId}")
	public String setDecisonSetTableId(@PathVariable String tableId){
		return tHIDROBRuleService.setDecisonTableId(tableId);
	}
	
//	@GetMapping("/getPayload/{tableId}")
//	public String getPayloadByDecisonTableId(@PathVariable String tableId){
//		return tHIDBRuleService.getPayloadByDecisionTable(tableId);
//	}
	@PostMapping("/invoke/rules")
	public Object invokeRules(@RequestBody RuleInputDto input){
		try {
			return tHIDROBRuleService.getResultListRuleService(input);
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
