package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.workflow.services.BlockTypeDeterminationService;


@RestController
@RequestMapping("/btd")
public class BlockTypeDeterController {

	@Autowired
	private BlockTypeDeterminationService services;

	public BlockTypeDeterController() {
		System.err.println("Inside Block Type Determination controller");
	}

	@GetMapping("/findBtdBySoId/{salesOrderHeaderNo}")
	public ResponseEntity findBtdById(@PathVariable("salesOrderHeaderNo") String salesOrderHeaderNo) {
		System.err.println("Inside find btd by Id method");
		return services.blockTypeFilterBasedOnSoId(salesOrderHeaderNo);
	}

}