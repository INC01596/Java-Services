package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ApplicationMasterDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface ApplicationMasterService {

	public ResponseDto getAllApplications();

	public ResponseDto getEntitiesByApplication(String application);

	public ResponseDto getProcessByEntityAndApp(String application, String entity);

	public ResponseEntity<Object> createApp(ApplicationMasterDto dto);
}
