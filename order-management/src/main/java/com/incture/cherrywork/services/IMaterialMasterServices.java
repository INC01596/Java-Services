//<----------------------------Sandeep----------------------------------->

package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.MaterialContainerDto;

import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;

public interface IMaterialMasterServices {
	
	ResponseEntity<Object> getMaterialByDesc(MaterialContainerDto dto);
	ResponseEntity<Object> getMaterialByName(String material);
	ResponseEntity<Object> create(SalesOrderMaterialMasterDto  Dto);
	ResponseEntity<Object> getMaterialNames();
}

