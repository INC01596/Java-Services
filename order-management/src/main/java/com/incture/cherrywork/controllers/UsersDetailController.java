package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.entities.UsersDetailDo;
import com.incture.cherrywork.services.UserDetailsServices;


@RestController
@RequestMapping("/userDetails")
public class UsersDetailController {

	@Autowired
	private UserDetailsServices services;

	@GetMapping("/getUsers")
	public ResponseEntity<?> findAllidpUsersAndSyncWithHana() {
		return services.findAllidpUsersAndSyncWithHana();
	}

	@PostMapping("/saveOrUpdate")
	public ResponseEntity<?> saveOrUpdate(@RequestBody UsersDetailDo model) {
		System.err.println("hello Controler");
		return services.saveOrUpdate(model);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody UsersDetailDo model) {
		return services.updateUser(model);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		return services.findById(id);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		return services.deleteById(id);
	}

	@GetMapping("/list")
	public ResponseEntity<?> listAll() {
		return services.getAll();
	}

	@GetMapping("/findPermissionDetails/{userId}&{domainCode}")
	public ResponseEntity<?> findPermissionObjectAndAttributeDetails(@PathVariable String userId,
			@PathVariable String domainCode) {
		return services.findPermissionObjectAndAttributeDetails(userId, domainCode);
	} 

	@GetMapping(path = "/saveAllUsersFromIdp")
	public ResponseEntity<?> saveAllUsersFromIdp() {
		return services.saveAllUsersFromIdp();
	}

	@GetMapping("/findAllPermissionObjects/{userPid}")
	public ResponseEntity<?> findAllPermissionObjects(@PathVariable String userPid) {
		return services.findAllPermissionObjects(userPid);
	}

	@GetMapping("/findAllAvailablePermObjects/{userPid}")
	public ResponseEntity<?> findAllAvailablePermObjects(@PathVariable String userPid) {
		return services.findAllAvailablePermObjects(userPid);
	}

	@DeleteMapping("/deleteByUserPid/{userPid}")
	public ResponseEntity<?> deleteByUserPid(@PathVariable String userPid) {
		return services.deleteByUserPid(userPid);
	}

	@GetMapping("/findAllRightsForUserInDomain/{userPid}&{domainCode}")
	public ResponseEntity<?> findAllRightsForUserInDomain(@PathVariable String userPid,
			@PathVariable String domainCode) {
		return services.findAllRightsForUserInDomain(userPid, domainCode);
	}

	@GetMapping("/downloadExcelForRights/{domainCode}")
	public ResponseEntity<?> downloadExcelForAllUsersRightsInDomain(@PathVariable String domainCode) {
		return services.downloadExcelForAllUsersRightsInDomain(domainCode);
	}

}
