package com.incture.cherrywork.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.services.FTPServiceLocal;



@RestController
@ComponentScan("com.incture")
@RequestMapping(value = "rest/valueHelp", produces = "application/json")
public class ValueHelpController {

	@Autowired
	private FTPServiceLocal service;



	@RequestMapping(value = "/getAllCustomerDetails/{salesRep}", method = RequestMethod.GET)
	public ResponseDto getAllCustomerDetails(@PathVariable String salesRep) {
		System.err.println("Controller get All Customers");
		salesRep= "177";
		return service.getAllCustDetailsBySalesRep(salesRep);
	}

	@RequestMapping(value = "/getAllCutomerWithInvoices/{salesRep}", method = RequestMethod.GET)
	public ResponseDto getAllCutomerWithInvoices(@PathVariable String salesRep) {
		//salesRep="1000123";
		return service.getAllCustomerDetailsWithInvoices(salesRep);
	}
}

