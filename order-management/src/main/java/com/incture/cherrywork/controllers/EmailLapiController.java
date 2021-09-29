package com.incture.cherrywork.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.services.EmailDefinitionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v1/mail-template")
@Api(value = "Email Lapi API", tags = { "Email Lapi API" }, description = "  ")
public class EmailLapiController {
	@Autowired
	private EmailDefinitionService emailDefinitionService;
	@GetMapping
	@ApiOperation(value = "Get Email Templates ")
	public List<Map<String, Object>> getEmailTemplate(
			@ApiParam(value = "Application") @RequestParam(value = "application", required = false) String application
			) {
		return emailDefinitionService.getEmailTemplate(application);
	}

}

