package com.incture.cherrywork.controllers;
//<-----------------Sandeep Kumar-------------------------------------->






import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.MaterialContainerDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;
import com.incture.cherrywork.services.MaterialMasterServices;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping(value = "/search")
public class MaterialMasterController {

	@Autowired
	private MaterialMasterServices materialMasterServices;
	
	
	
	@GetMapping("/msg")
	public String welcomeMessage() {
		return "Welcome message from Material Controller...!!!";
	}
	
	@PostMapping("/getMaterialByDesc")
	public ResponseEntity<Object> getMaterialByDesc(@RequestBody MaterialContainerDto  dto) {
		return materialMasterServices.getMaterialByDesc(dto);
	}
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody SalesOrderMaterialMasterDto  dto) {
		return materialMasterServices.create(dto);
	}
	
	

	
	@GetMapping("/getMaterialByName/{material}")
	public ResponseEntity<Object> getMaterialByName(@PathVariable("material") String material) {
		return materialMasterServices.getMaterialByName(material);
	}
	
	@GetMapping("/getMaterialNames")
	public ResponseEntity<Object> getMaterialNames() {
		return materialMasterServices.getMaterialNames();
	}
	/*
	
	@PostMapping("/manualSearchResult")
	public Response manualSearchResult(@RequestBody SearchHeaderDto searchDto) {
		return materialMasterServices.manualSearchResult(searchDto);
	}
	
	@PostMapping("/getSearchDropdown")
	public Response getSearchDropdown(@RequestBody SearchHeaderDto searchDto) {
		return materialMasterServices.getSearchDropdown(searchDto);
	}
	*/
	
	@GetMapping("/scheduler")
	public ResponseEntity<Object> materialScheduler(){
		return materialMasterServices.materialScheduler();
	}
}


