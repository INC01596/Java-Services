package com.incture.cherrywork.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.rules.VisitPlanRuleInputDto;
import com.incture.cherrywork.rules.VisitPlannerRuleService;

@RestController
@RequestMapping("/RuleController")
public class RuleController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private VisitPlannerRuleService ruleService;

	@PostMapping("/invokeVisitPlanRules")
	public Object invokeRules(@RequestBody VisitPlanRuleInputDto input) throws InterruptedException {
		try {
			return ruleService.getVisitApprover(input);
		} catch (IOException e) {
			logger.error("[RuleController][invokeRules] Exception: " + e);
			return null;
		}
	}

}
