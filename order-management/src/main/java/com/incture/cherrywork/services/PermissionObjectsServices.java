package com.incture.cherrywork.services;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.PermissionObjectDto;
import com.incture.cherrywork.entities.PermissionObjectDo;



public interface PermissionObjectsServices {

	ResponseEntity<?> findById(String id);

	ResponseEntity<?> saveOrUpdate(PermissionObjectDo model);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> listAll();

	ResponseEntity<?> createPermisionObject(PermissionObjectDto permissionObjectDto);

	ResponseEntity<?> findByPermName(String permName);

	ResponseEntity<?> findByDomainCode(String domainCode);

	ResponseEntity<?> loadAllPermissionObjectsWithPagination(Integer pageNo, Integer pageSize, String sortBy);

	ResponseEntity<?> findByIdWithDetails(String id);

	ResponseEntity<?> findUsersById(String id);

	ResponseEntity<?> findEmptyAttributeForPermObjects(List<String> permissionObjectGuidList);

	ResponseEntity<?> migrateOldDacPermissionObjectsToNew();

	ResponseEntity<?> findUserDetailsById(String id);

	ResponseEntity<?> findUsersCountById(String id);

}
