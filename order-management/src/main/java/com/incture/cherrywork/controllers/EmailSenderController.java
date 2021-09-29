package com.incture.cherrywork.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.MailTriggerDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.services.EmailDefinitionService;
import com.incture.cherrywork.services.EmailSenderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/mail")
@Api(value = "Mail Notification", tags = { "Mail Notification" }, description = "  ")
public class EmailSenderController {

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private EmailDefinitionService emailDefinitionService;


	@PostMapping("/send")
	@ApiOperation(value = " Triggering a mail")
	public ResponseDto triggerMail(@RequestBody MailTriggerDto mailTriggerDto) {

		return emailDefinitionService.triggerMail(mailTriggerDto);
	}

}
