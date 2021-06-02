package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.services.ISalesOrderHeaderService;
import com.incture.cherrywork.services.ISalesOrderItemService;

import io.swagger.annotations.Api;

@SuppressWarnings("unused")
@RestController
@Api(value = "Track SO", tags = { "Track SO" })
@RequestMapping("/api/v1/trackSo")
public class TrackSoController {
	
	
	@Autowired
	private ISalesOrderHeaderService salesOrderHeaderService;
	@Autowired
	private ISalesOrderItemService salesOrderItemService;

	
	@PostMapping("/getSOData")
	public ResponseEntity<Object> getSOData(@RequestBody HeaderIdDto dto)
	{
		return salesOrderHeaderService.getSOData(dto);
	}

}
