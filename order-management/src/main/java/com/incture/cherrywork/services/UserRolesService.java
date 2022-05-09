package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.Response;

public interface UserRolesService {
	public ResponseEntity<Response> getRole(String userId);
}
