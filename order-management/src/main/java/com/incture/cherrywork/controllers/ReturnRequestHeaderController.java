package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.services.ReturnRequestHeaderService;



@RestController
@RequestMapping("/retReqHead")

public class ReturnRequestHeaderController {

	@Autowired
	private ReturnRequestHeaderService services;

	@GetMapping("/test")
	public String test() {
		return "Hello";
	}

	
	@PostMapping("/list")
	public ResponseEntity<Object> listAllReturnRequestHeaders(@RequestBody ReturnFilterDto dto) {
		System.err.println("Inside ReturnRequestHeaders List");
		System.err.println(dto.getReturnReqNumber());
		System.err.println(dto.getPageNo());
   return services.listAllReturnReqHeaders(dto);
	}



}

