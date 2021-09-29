package com.incture.cherrywork.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.PermissionObjectDto;
import com.incture.cherrywork.entities.PermissionObjectDo;
import com.incture.cherrywork.services.PermissionObjectsServices;



@RestController
@RequestMapping("/permissionObject")
public class PermissionObjectController {

	@Autowired
	private PermissionObjectsServices services;

	@PostMapping("/saveOrUpdate")
	public ResponseEntity<?> saveOrUpdate(@RequestBody PermissionObjectDo model) {
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

	@PostMapping("/createPermisionObject")
	public ResponseEntity<?> createPermisionObject(@RequestBody PermissionObjectDto permissionObjectDto) {
		return services.createPermisionObject(permissionObjectDto);
	}

	@GetMapping("/findByPermName/{permName}")
	public ResponseEntity<?> findByPermName(@PathVariable String permName) {
		return services.findByPermName(permName);
	}

	@GetMapping("/findByDomainCode/{domainCode}")
	public ResponseEntity<?> findByDomainCode(@PathVariable String domainCode) {
		return services.findByDomainCode(domainCode);
	}

	@GetMapping("/findAllPermissionObjects")
	public ResponseEntity<?> loadAllPermissionObjectsWithPagination(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "updatedAt") String sortBy) {
		return services.loadAllPermissionObjectsWithPagination(pageNo, pageSize, sortBy);
	}

	@GetMapping("/findByIdWithDetails/{id}")
	public ResponseEntity<?> findByIdWithDetails(@PathVariable String id) {
		return services.findByIdWithDetails(id);
	}

	@GetMapping("/findUsersById/{id}")
	public ResponseEntity<?> findUsersById(@PathVariable String id) {
		return services.findUsersById(id);
	}

	@GetMapping("/findUserDetailsById/{id}")
	public ResponseEntity<?> findUserDetailsById(@PathVariable String id) {
		return services.findUserDetailsById(id);
	}

	@GetMapping("/findUsersCountById/{id}")
	public ResponseEntity<?> findUsersCountById(@PathVariable String id) {
		return services.findUsersCountById(id);
	}

	@PostMapping("/findEmptyAttributeForPermObjects")
	public ResponseEntity<?> findEmptyAttributeForPermObjects(@RequestBody List<String> permissionObjectGuidLists) {
		return services.findEmptyAttributeForPermObjects(permissionObjectGuidLists);
	}

	@GetMapping("/migrateOldDacPermissionObjectsToNew")
	public ResponseEntity<?> migrateOldDacPermissionObjectsToNew() {
		return services.migrateOldDacPermissionObjectsToNew();
	}

}
