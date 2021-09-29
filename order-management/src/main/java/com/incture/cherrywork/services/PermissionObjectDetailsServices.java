package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.entities.AttributeDetailsDto;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;



public interface PermissionObjectDetailsServices {

	ResponseEntity<?> findById(String id);

	ResponseEntity<?> saveOrUpdate(PermissionObjectDetailsDo model);

	ResponseEntity<?> saveAll(List<PermissionObjectDetailsDo> list);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> listAll();

	ResponseEntity<?> modifyPermisionObjectDetails(List<AttributeDetailsDto> listOfAttributes);
}
