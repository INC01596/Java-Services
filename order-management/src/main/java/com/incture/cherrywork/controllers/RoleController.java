package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.services.UserRolesService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private UserRolesService userRolesService;

	@GetMapping("/getRole/{userId}")
	public ResponseEntity<Response> getRole(@PathVariable String userId) {
		return userRolesService.getRole(userId);
	}
}
