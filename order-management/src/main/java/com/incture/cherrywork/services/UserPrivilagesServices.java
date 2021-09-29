package com.incture.cherrywork.services;



import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.UserPrivilagesDto;
import com.incture.cherrywork.entities.UserPrivilagesDo;



public interface UserPrivilagesServices {

	ResponseEntity<?> findById(String id);

	ResponseEntity<?> saveOrUpdate(UserPrivilagesDo model);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> listAll();

	ResponseEntity<?> assignMultiplePermissionObject(UserPrivilagesDto userPrivilagesDto);

	ResponseEntity<?> unassignPermissionObjects(UserPrivilagesDto userPrivilagesDto);
}
