package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

public interface IUsersDetailService {
	
	ResponseEntity<Object> findAllRightsForUserInDomain(String userPid, String domainCode);

}
