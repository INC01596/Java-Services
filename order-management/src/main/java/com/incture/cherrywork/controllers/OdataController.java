package com.incture.cherrywork.controllers;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.incture.cherrywork.OdataS.OdataService;
import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dtos.ResponseEntity;

@RestController
@RequestMapping("/OdataServices")
public class OdataController {
 
	@Autowired
	private OdataService odataServices;

	@PostMapping("/updateOnSaveOrEdit")
	public ResponseEntity updateDataInEcc(@RequestBody OnSubmitTaskDto headerData) {
		String message=null;
		LocalTime entry = LocalTime.now();
		System.err.println("entry time for updateOnSaveOrEdit="+entry);
		
		ResponseEntity onSaveOrEdit = odataServices.onSaveOrEdit(headerData);
		
		if(onSaveOrEdit.getMessage()!=null){
			 message = onSaveOrEdit.getMessage();
		}
		
		LocalTime exit = LocalTime.now();
		System.err.println("exit time="+exit);
		System.err.println("total time taken= "+Duration.between(entry, exit));
		 message+= onSaveOrEdit.getMessage() +"total time taken= "+Duration.between(entry, exit);
		 
		 onSaveOrEdit.setMessage(message);
		 
		 return onSaveOrEdit;
	}
}

