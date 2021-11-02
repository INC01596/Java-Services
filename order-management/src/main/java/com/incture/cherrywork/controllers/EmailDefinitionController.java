package com.incture.cherrywork.controllers;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.DefDto;
import com.incture.cherrywork.dtos.EmailUiDto;
import com.incture.cherrywork.dtos.GetTemplateDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.services.EmailDefinitionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v1/mail-definition")
@Api(value = "Mail Definition", tags = { "Mail Definition" }, description = "  ")
public class EmailDefinitionController {
	@Autowired
	private EmailDefinitionService emailDefinitionService;

	@PostMapping("/create")
	public ResponseDto createEmailTemplate (@RequestBody EmailUiDto emailUiDto) {
		System.err.println("HEY CONTROLLER");
		return emailDefinitionService.createEmailTemplate(emailUiDto);
	}

	@PatchMapping("/{emailDefinitionId}")
	public ResponseDto updateEmailTemplate(
		 @PathVariable(value = "emailDefinitionId", required = true) String emailDefinitionId, @RequestBody EmailUiDto emailUiDto) {
		return emailDefinitionService.updateEmailTemplate(emailDefinitionId, emailUiDto);
	}

	@DeleteMapping("/{emailDefinitionId}")
	public ResponseDto deleteEmailTemplate(
			 @PathVariable(value = "emailDefinitionId", required = true) String emailDefinitionId) {
		return emailDefinitionService.deleteEmailTemplate(emailDefinitionId);
	}

	@PostMapping("/get")
	@ApiOperation(value = "Get Email Definition Template by  emailDefinitionId ")
	public ResponseDto getEmailTemplate(@RequestBody GetTemplateDto dto) {
		JSONObject inputObject = new JSONObject();

		JSONObject getInvoiceDetails = new JSONObject();

		getInvoiceDetails.put("CustomerNumber","get" );

		inputObject.put("GetInvoiceDetails", "getInvoiceDetails");
		System.err.println(inputObject.toString());

		Pageable pageable;
		if (!(dto.getPageIndex() != null && dto.getLimit() != null)) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		} else {
			pageable = PageRequest.of(dto.getPageIndex(), dto.getLimit());
		}

		return emailDefinitionService.getEmailTemplate(dto.getEmailDefinitionId(), dto.getApplication(), dto.getProcess(), dto.getEntityName(),pageable,dto.getSearchString());
	}

	@GetMapping("/validate")
	@ApiOperation(value = "Check For Active Template")
	public ResponseDto checkActiveTemplate(
			@ApiParam(value = "Application") @RequestParam(value = "application", required = false) String application,
			@ApiParam(value = "Entity") @RequestParam(value = "entity", required = false) String entity,

			@ApiParam(value = "Process") @RequestParam(value = "process", required = false) String process) {
		return emailDefinitionService.validateActiveTemplate(application, entity, process);
	}
	@PostMapping("/getEmailDefi")
	public String getDefId(@RequestBody DefDto defDto)
	{
		System.err.println("hey1 "+defDto.toString());
		
		return emailDefinitionService.getDefId(defDto);
	}
	

}
