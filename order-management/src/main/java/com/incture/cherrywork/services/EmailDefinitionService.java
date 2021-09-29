package com.incture.cherrywork.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.security.jwt.Jwt;

import com.incture.cherrywork.dtos.ApplicationVariablesMasterDto;
import com.incture.cherrywork.dtos.EmailUiDto;
import com.incture.cherrywork.dtos.MailTriggerDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface EmailDefinitionService {
		ResponseDto createEmailTemplate(EmailUiDto emailUiDto);

		ResponseDto updateEmailTemplate(String emailDefinitionId, EmailUiDto emailUiDto);

		ResponseDto deleteEmailTemplate(String emailDefinitionId);

		ResponseDto getEmailTemplate(String emailDefinitionId, String application, String process, String entityName,Pageable pageable,
				String searchString,Jwt jwt);

		ResponseDto validateActiveTemplate(String application, String entity, String process);

		List<Map<String, Object>> getEmailTemplate(String application);

		ResponseDto triggerMail(MailTriggerDto mailTriggerDto);

		ResponseDto createVariable(ApplicationVariablesMasterDto variablesMasterDto);

		ResponseDto getVariables(String application,String process,String entity);

	}


