package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.ReturnRequestHeaderService;



@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/retReqHead")
public class ReturnRequestHeaderController {

	@Autowired
	private ReturnRequestHeaderService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createReturnRequestHeader(@RequestBody ReturnRequestHeaderDto returnRequestHeaderDto) {
		System.err.println("ReturnRequestHeader Created Successfully");
		return services.saveOrUpdateReturnReqHeader(returnRequestHeaderDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateReturnRequestHeader(@RequestBody ReturnRequestHeaderDto returnRequestHeaderDto) {
		if (returnRequestHeaderDto.getReturnReqNum() != null) {
			System.err.println("ReturnRequestHeader Updated Successfully");
			return services.saveOrUpdateReturnReqHeader(returnRequestHeaderDto);
		} else {
			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Header Id is mandatory",
					ResponseStatus.FAILED);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllReturnRequestHeaders() {
		System.err.println("Inside ReturnRequestHeaders List");
		return services.listAllReturnReqHeaders();
	}

	@DeleteMapping("/deleteById/{returnReqNum}")
	public ResponseEntity deleteReturnRequestHeaderById(@PathVariable("returnReqNum") String returnReqNum) {
		System.err.println("Inside Delete ReturnRequestHeader by Id");
		return services.deleteReturnReqHeaderById(returnReqNum);
	}

	@GetMapping("/findById/{returnReqNum}")
	public ResponseEntity findReturnRequestHeaderById(@PathVariable("returnReqNum") String returnReqNum) {
		System.err.println("Inside ReturnRequestHeader find by Id method");
		return services.getReturnReqHeaderById(returnReqNum);
	}

}

