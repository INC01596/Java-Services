package com.incture.cherrywork.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.OutBoundDeliveryDto;
import com.incture.cherrywork.services.IOutBoundDeliveryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Out Bound Odata", tags = {"Out Bound Odata"})
@RequestMapping("/api/v1")
public class OutBoundOdataController {
	
	
	@Autowired
	private IOutBoundDeliveryService outBoundDeliveryService;
	
	@PostMapping("/OutBoundCreate")
	@ApiOperation(value = "Create a OutBoundCreate Dataset")
	public ResponseEntity<?> createOdata(@Valid @RequestBody OutBoundDeliveryDto inputDto) throws URISyntaxException, IOException {
		return outBoundDeliveryService.createOutBoundDeliveryOnSubmit(inputDto);
    }
	
	@PostMapping("/OutDelivery")
    @ApiOperation(value = "Create a OutBoundCreate Dataset")
	public ResponseEntity<Object> create(@Valid @RequestBody OutBoundDeliveryDto outBoundDeliveryDto) {
			return outBoundDeliveryService.create(outBoundDeliveryDto);
	}
	@GetMapping("/OutDelivery/{obdNumber}/")
	@ApiOperation(value = "Read OutBoundCreate Dataset")
	public ResponseEntity<Object> read(@PathVariable String obdNumber) { 
			return outBoundDeliveryService.read(obdNumber);
	}
	@PutMapping("/OutDelivery/{obdNumber}/")
	@ApiOperation(value = "Update OutBoundCreate Dataset")
	public ResponseEntity<Object> update(@PathVariable String obdNumber, @Valid @RequestBody OutBoundDeliveryDto outBoundDeliveryDto) { 
			return outBoundDeliveryService.update(obdNumber, outBoundDeliveryDto);
	}
	@DeleteMapping("/OutDelivery/{obdNumber}/")
	@ApiOperation(value = "Delete OutBoundCreate Dataset")
	public ResponseEntity<Object> delete(@PathVariable String obdNumber) { 
			return outBoundDeliveryService.delete(obdNumber);
	}
	@GetMapping("/OutDelivery")
	@ApiOperation(value = "List all OutBoundCreate Datasets")
	public ResponseEntity<Object> readAll(@RequestParam(value = "search") String search) {
			return outBoundDeliveryService.readAll(search);
	}

}
