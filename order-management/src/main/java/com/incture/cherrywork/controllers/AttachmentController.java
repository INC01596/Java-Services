package com.incture.cherrywork.controllers;



import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.services.AttachmentService;




@RestController
@RequestMapping("/Attachment")
public class AttachmentController {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private AttachmentService dbFileStorageService;

	
	
 
	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("returnReqNum") String returnReqNum) {

		logger.debug("file " + file.toString() + "returnReqNum" + returnReqNum);
		if (file != null && returnReqNum != null) {

			String docId = dbFileStorageService.storeFile(file, returnReqNum);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("Attachment/downloadFile/").path(docId).toUriString();

			return new ResponseEntity<String>(fileDownloadUri, org.springframework.http.HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No file", org.springframework.http.HttpStatus.OK);
		}
	}

	@PostMapping("/uploadMultipleFiles")
	public List<ResponseEntity<?>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam("returnreqNum") String returnreqNum) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file, returnreqNum)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{docId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable(name = "docId") String docId) {
		// Load file from database
		Attachment dbFile = dbFileStorageService.getFileByDocId(docId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getDocName() + "\"")
				.body(new ByteArrayResource(dbFile.getDocData()));
	}

	@GetMapping(value = "/downloadFileByReturnReqNum/{returnReqNum}", produces = "application/zip")
	public ResponseEntity<Resource> downloadFileByReturnReqNum(@PathVariable(name = "returnReqNum") String returnReqNum)
			throws IOException {
		// Load file from database
		ResponseEntity<?> responseEnitty = dbFileStorageService.getFileByReturnReqNum(returnReqNum);

		@SuppressWarnings("unchecked")
		List<Attachment> attachement = (List<Attachment>) responseEnitty.getBody();
		Attachment dbFile=new Attachment();
		if(attachement.size()>0)
		{
			dbFile=attachement.get(0);
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getDocName() + "\"")
				.body(new ByteArrayResource(dbFile.getDocData()));



	}

	@DeleteMapping(value = "/deleteAttachment/{docId}")
	public ResponseEntity<?> deleteAttachement(@PathVariable(name = "docId") String docId) {
		System.err.println("delete");

		return dbFileStorageService.deleteByDocId(docId);
	}

	
}
