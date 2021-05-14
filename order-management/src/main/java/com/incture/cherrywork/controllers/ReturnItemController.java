package com.incture.cherrywork.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.services.ReturnItemService;



@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/returnItem")
public class ReturnItemController {

	@Autowired
	private ReturnItemService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createReturnItem(@RequestBody ReturnItemDto returnItemDto) {
		System.err.println("ReturnItem Created Successfully");
		return services.saveOrUpdateReturnItem(returnItemDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateReturnItem(@RequestBody ReturnItemDto returnItemDto) {
		System.err.println("ReturnItem Updated Successfully");
		return services.saveOrUpdateReturnItem(returnItemDto);
	}

	@GetMapping("/list")
	public ResponseEntity listAllReturnItems() {
		System.err.println("Inside ReturnItem List");
		return services.listAllReturnItems();
	}

	@DeleteMapping("/deleteById/{returnReqNum}/{returnReqItemid}")
	public ResponseEntity deleteReturnItemById(@PathVariable("returnReqNum") String returnReqNum,
			@PathVariable("returnReqItemid") String returnReqItemid) {
		System.err.println("Inside Delete ReturnItem by Id");
		return services.deleteReturnItemById(returnReqNum, returnReqItemid);
	}

	@GetMapping("/findById/{returnReqNum}/{returnReqItemid}")
	public ResponseEntity findReturnItemById(@PathVariable("returnReqNum") String returnReqNum,
			@PathVariable("returnReqItemid") String returnReqItemid) {
		System.err.println("Inside ReturnItem find by Id method");
		return services.getReturnItemById(returnReqNum, returnReqItemid);
	}

}

