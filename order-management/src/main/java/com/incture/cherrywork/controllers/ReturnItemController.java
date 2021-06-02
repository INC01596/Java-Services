package com.incture.cherrywork.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



import com.incture.cherrywork.services.ReturnItemService;



@RestController
@RequestMapping("/returnItem")
public class ReturnItemController {

	@Autowired
	private ReturnItemService returnServices;

//	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
//	public ResponseEntity createReturnItem(@RequestBody ReturnItemDto returnItemDto) {
//		System.err.println("ReturnItem Created Successfully");
//		return services.saveOrUpdateReturnItem(returnItemDto);
//	}
//
//	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
//	public ResponseEntity updateReturnItem(@RequestBody ReturnItemDto returnItemDto) {
//		System.err.println("ReturnItem Updated Successfully");
//		return services.saveOrUpdateReturnItem(returnItemDto);
//	}

	@GetMapping("/list")
	public ResponseEntity<Object> listAllReturnItems() {
		System.err.println("Inside ReturnItem List");
		return returnServices.listAllReturnItems();
	}

//	@DeleteMapping("/deleteById/{returnReqNum}/{returnReqItemid}")
//	public ResponseEntity deleteReturnItemById(@PathVariable("returnReqNum") String returnReqNum,
//			@PathVariable("returnReqItemid") String returnReqItemid) {
//		System.err.println("Inside Delete ReturnItem by Id");
//		return services.deleteReturnItemById(returnReqNum, returnReqItemid);
//	}
//
//	@GetMapping("/findById/{returnReqNum}/{returnReqItemid}")
//	public ResponseEntity findReturnItemById(@PathVariable("returnReqNum") String returnReqNum,
//			@PathVariable("returnReqItemid") String returnReqItemid) {
//		System.err.println("Inside ReturnItem find by Id method");
//		return services.getReturnItemById(returnReqNum, returnReqItemid);
//	}

}

