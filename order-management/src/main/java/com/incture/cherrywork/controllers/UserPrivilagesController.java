package com.incture.cherrywork.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.UserPrivilagesDto;
import com.incture.cherrywork.entities.UserPrivilagesDo;
import com.incture.cherrywork.services.UserPrivilagesServices;



@RestController
@RequestMapping("/userPrivilages")
public class UserPrivilagesController {

	@Autowired
	private UserPrivilagesServices services;

	@PostMapping("/saveOrUpdate")
	public ResponseEntity<?> saveOrUpdate(@RequestBody UserPrivilagesDo model) {
		return services.saveOrUpdate(model);
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
		return services.listAll();
	}

	@PostMapping("/assignPermissionObjects")
	public ResponseEntity<?> assignMultiplePermissionObject(@RequestBody UserPrivilagesDto userPrivilagesDto) {
		return services.assignMultiplePermissionObject(userPrivilagesDto);
	}

	@PostMapping("/unassignPermissionObjects")
	public ResponseEntity<?> unassignPermissionObjects(@RequestBody UserPrivilagesDto userPrivilagesDto) {
		return services.unassignPermissionObjects(userPrivilagesDto);
	}

}
