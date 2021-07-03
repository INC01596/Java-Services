package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

public interface IDataAccessService {

	
	ResponseEntity<Object> fetchPermisionDetailsForUser(String userId, String projectName);
}
