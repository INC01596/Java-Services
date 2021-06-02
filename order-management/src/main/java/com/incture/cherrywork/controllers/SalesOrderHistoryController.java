package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;
import com.incture.cherrywork.services.SalesOrderHistoryService;


@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/salesOrder")
public class SalesOrderHistoryController {

	@Autowired
	private SalesOrderHistoryService services;

	public SalesOrderHistoryController() {
		System.err.println("Inside sales order history controller");
	}

	@PostMapping(path = "/saveSalesOrder", consumes = "application/json", produces = "application/json")
	public ResponseEntity saveSalesOrder(@RequestBody SalesOrderHistoryDto salesOrderHistoryDto) {
		return services.saveSalesOrderItem(salesOrderHistoryDto);
	}

	@GetMapping("/listAllSaleOrders")
	public ResponseEntity listAllSaleOrders() {
		return services.listAllSalesOrders();
	}

	@GetMapping("/listAllSaleOrderHistory/{salesOrderHeaderNum}&{salesOrderItemNum}")
	public ResponseEntity listAllSalesOrderHistoryOfItem(
			@PathVariable("salesOrderHeaderNum") String salesOrderHeaderNum,
			@PathVariable("salesOrderItemNum") String salesOrderItemNum) {
		return services.listHistoryOfSalesOrderBasedOnVersion(salesOrderHeaderNum, salesOrderItemNum);
	}

}
