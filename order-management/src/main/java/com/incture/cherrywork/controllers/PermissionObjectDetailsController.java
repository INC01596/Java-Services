package com.incture.cherrywork.controllers;


import java.util.List;

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

import com.incture.cherrywork.entities.AttributeDetailsDto;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;
import com.incture.cherrywork.services.PermissionObjectDetailsServices;



@RestController
@RequestMapping("/permissionObjectDetails")
public class PermissionObjectDetailsController {

	@Autowired
	private PermissionObjectDetailsServices services;

	@PostMapping("/saveOrUpdate")
	public ResponseEntity<?> saveOrUpdate(@RequestBody PermissionObjectDetailsDo model) {
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

	@PutMapping("/updatePermisionObjectDetails")
	public ResponseEntity<?> modifyPermisionObject(@RequestBody List<AttributeDetailsDto> listOfAttributes) {
		return services.modifyPermisionObjectDetails(listOfAttributes);
	}

}

