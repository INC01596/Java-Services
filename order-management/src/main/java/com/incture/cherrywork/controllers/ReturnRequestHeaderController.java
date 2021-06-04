
package com.incture.cherrywork.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.services.IAttachmentService;
import com.incture.cherrywork.services.IReturnRequestHeaderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/returnRequest")
public class ReturnRequestHeaderController {

	
	@Autowired
	private IReturnRequestHeaderService service;
	
	@Autowired
	private IAttachmentService dbFileStorageService;
	
	
	
	@GetMapping("/test")
	public String test() {
		return "Hello";
	}


	@PostMapping("/createReturnRequest/saveAsDraft")
	@ApiOperation(value = "/createReturnRequest/saveAsDraft")
	public ResponseEntity<Object> saveAsDraft(@RequestBody ReturnOrderRequestPojo request) {

		return service.saveAsDraft(request);
	}

	@PostMapping("/createReturnRequest")
	@ApiOperation(value = "/createReturnRequest")
	public ResponseEntity<Object> create(@RequestBody ReturnOrderRequestPojo request) {
		return service.createReturnRequest(request);
	}

	@GetMapping("/downloadFile/{docId}")
	@ApiOperation(value = "/downloadFile/{docId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable(name = "docId") String docId) {
		// Load file from database
		Attachment dbFile = dbFileStorageService.getFileByDocId(docId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getDocName() + "\"")
				.body(new ByteArrayResource(dbFile.getDocData()));
	}

	@GetMapping(value = "/downloadFileByReturnReqNum/{returnReqNum}", produces = "application/zip")
	@ApiOperation(value = "/downloadFileByReturnReqNum/{returnReqNum}")
	public byte[] downloadFileByReturnReqNum(@PathVariable(name = "returnReqNum") String returnReqNum)
			throws IOException {
		// Load file from database
		ResponseEntity<Object> responseEnitty = dbFileStorageService.getFileByReturnReqNum(returnReqNum);

		@SuppressWarnings("unchecked")
		List<Attachment> attachement = (List<Attachment>) responseEnitty.getBody();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		for (int i = 0; i < attachement.size(); i++) {
			ZipEntry entry = new ZipEntry(returnReqNum + "-" + attachement.get(i).getDocName());
			entry.setSize(attachement.get(i).getDocData().length);
			zos.putNextEntry(entry);
			zos.write(attachement.get(i).getDocData());
		}
		zos.closeEntry();
		zos.close();
		/*
		 * File convFile = new File(returnReqNum);
		 * 
		 * OutputStream fos = new FileOutputStream( convFile );
		 * 
		 * fos.write(baos.toByteArray()); fos.close();
		 * 
		 * System.err.println("file "+convFile.length());
		 * 
		 * String message = sharePoint.putRecordInSharePoint(convFile);
		 * 
		 * System.err.println("message " + message );
		 */
		return baos.toByteArray();

	}
	
	@DeleteMapping(value = "/deleteAttachment/{docId}")
	@ApiOperation(value = "/deleteAttachment/{docId}")
	public ResponseEntity<?> deleteAttachement(@PathVariable(name = "docId") String docId) {
		System.err.println("delete");

		return dbFileStorageService.deleteByDocId(docId);
	}

	@GetMapping("getByReqNum/{reqNum}")
	public ResponseEntity<?> getByReturnReqNum(@PathVariable(name = "reqNum") String reqNum) {

		return service.findByReturnReqNum(reqNum);

	}





// Sandeep Kumar
	
	

	

	
	
	
	@PostMapping("/list")
	public ResponseEntity<?> listAllReturnRequestHeaders(@RequestBody ReturnFilterDto dto) {
		System.err.println("Inside ReturnRequestHeaders List");
		
        return service.listAllReturn(dto);
	}



}


