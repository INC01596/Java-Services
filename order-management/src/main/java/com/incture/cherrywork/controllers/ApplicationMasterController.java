package com.incture.cherrywork.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ApplicationMasterDto;
import com.incture.cherrywork.dtos.ApplicationVariablesMasterDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.services.ApplicationMasterService;
import com.incture.cherrywork.services.EmailDefinitionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v1/mail-definition/application")
@Api(value = "Mail Application", tags = { "Mail Application" }, description = "  ")
public class ApplicationMasterController {

	@Autowired
	private ApplicationMasterService applicationMasterService;

	@Autowired
	private EmailDefinitionService emailDefinitionService;

	@PostMapping("/create")
	public ResponseEntity<Object> createApp(@RequestBody ApplicationMasterDto  dto){
	return applicationMasterService.createApp(dto);
	}
	
	@GetMapping
	@ApiOperation(value = "Get All Applications ")
	public ResponseDto getAllApplications() {
		return applicationMasterService.getAllApplications();
	}

	@GetMapping("/entity")
	@ApiOperation(value = "Get Entities ")
	public ResponseDto getEntities(
			@ApiParam(value = "Application") @RequestParam(value = "application", required = true) String application) {
		return applicationMasterService.getEntitiesByApplication(application);
	}

	@GetMapping("/process")
	@ApiOperation(value = "Get Process ")
	public ResponseDto geProcess(
			@ApiParam(value = "Application") @RequestParam(value = "application", required = true) String application,
			@ApiParam(value = "Entity") @RequestParam(value = "entity", required = true) String entity) {
		return applicationMasterService.getProcessByEntityAndApp(application, entity);
	}

	@PostMapping("/variable")
	@ApiOperation(value = "Add variable to application")
	public ResponseDto variableMaster(
			@ApiParam(value = "Create Variable in application") @RequestBody ApplicationVariablesMasterDto variablesMasterDto) {
		return emailDefinitionService.createVariable(variablesMasterDto);
	}

	@GetMapping("/variable")
	@ApiOperation(value = "Get variables for application ")
	public ResponseDto getVariables(
			@ApiParam(value = "Application") @RequestParam(value = "application", required = true) String application,
			@ApiParam(value = "Entity") @RequestParam(value = "entity",required = false) String entity,
			@ApiParam(value = "Process") @RequestParam(value = "process",required = false) String process) {
		return emailDefinitionService.getVariables(application,entity,process);
	}

}
