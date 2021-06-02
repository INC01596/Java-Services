package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.dtos.RequestMasterInsertDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.RequestMasterService;


@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/reqMaster")
public class RequestMasterController {

	public RequestMasterController() {
		System.err.println("inside request master controller");
	}

	@Autowired
	private RequestMasterService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createRequest(@RequestBody RequestMasterDto requestMasterDto) {
		System.err.println("Request Created Successfully");
		return services.saveOrUpdateRequestMaster(requestMasterDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateRequest(@RequestBody RequestMasterDto requestMasterDto) {
		System.err.println("Request Updated Successfully");
		if (requestMasterDto.getRequestId() != null) {
			return services.saveOrUpdateRequestMaster(requestMasterDto);
		} else {
			return new ResponseEntity(requestMasterDto, HttpStatus.BAD_REQUEST, "Request Master Id is mandatory",
					ResponseStatus.FAILED);
		}
	}

	@GetMapping("/list")
	public ResponseEntity listAllRequests() {
		System.err.println("Inside Requests List");
		return services.listAllRequests();
	}

	// Not working
	@DeleteMapping("/deleteById/{reqId}")
	public ResponseEntity deleteRequestMasterById(@PathVariable("reqId") String reqId) {
		System.err.println("Inside Delete Request by Id");
		return services.deleteRequestMasterById(reqId);
	}

	@GetMapping("/findById/{reqId}")
	public ResponseEntity findRequestMasterById(@PathVariable("reqId") String reqId) {
		System.err.println("Inside Request Master find by Id method");
		return services.getRequestMasterById(reqId);
	}
	
	
	//Rest service to update REQUEST_MASTER TABLE with complete status
		@PostMapping(path = "/updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
		public ResponseEntity updateRequestStatus(@RequestBody RequestMasterInsertDto requestMasterInsertDto) {
			if (requestMasterInsertDto.getSalesOrderNo()!= null) {
				return services.updateStatusPostBlock(requestMasterInsertDto.getSalesOrderNo(), requestMasterInsertDto.isNoItemBlock());
			} else {
				return new ResponseEntity(requestMasterInsertDto.getSalesOrderNo(), HttpStatus.BAD_REQUEST, "SalesOrder Number is mandatory",
						ResponseStatus.FAILED);
			}
		} 
	 


	
}
