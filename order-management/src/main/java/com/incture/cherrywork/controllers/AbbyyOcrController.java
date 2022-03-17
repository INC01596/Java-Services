package com.incture.cherrywork.controllers;



import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.services.AbbyOcrService;
import com.incture.cherrywork.services.AttachEmail;
import com.incture.cherrywork.services.AttachmentService;




@RestController
@RequestMapping("/abbyy")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1024, maxRequestSize = 1024 * 1024 * 1024)
public class AbbyyOcrController {
	@Autowired
	private AbbyOcrService abbyOcrService;
	
	
	
	@Autowired
	private AttachEmail email;
	

	@PostMapping(value = "/upload")
	public ResponseEntity<Object> upload(@RequestBody Attachment  array)
			throws IOException {
		
			return abbyOcrService.uploadDocToTrans(array.getDocData());
		}
	

	@PostMapping(value = "/getAttachments")
	public Object getAttachments()
			throws IOException, MessagingException {
		
			return email.receiveEmail();
		}
	}


