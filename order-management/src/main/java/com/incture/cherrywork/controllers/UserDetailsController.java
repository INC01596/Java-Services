package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.services.IUsersDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userDetails")
@Api(value = "User Details Controller", tags = { "User Details Controller" })
public class UserDetailsController {
	
	@Autowired
	private IUsersDetailService usersDetailService;
	
	@GetMapping("/findAllRightsForUserInDomain/{userPid}&{domainCode}")
	@ApiOperation(value = "find All Rights For User In Domain")
	public ResponseEntity<Object> findAllRightsForUserInDomain(@PathVariable String userPid,
			@PathVariable String domainCode) {
		return usersDetailService.findAllRightsForUserInDomain(userPid, domainCode);
	}
	


}
