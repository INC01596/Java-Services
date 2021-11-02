//package com.incture.cherrywork.controllers;
//
//
//import java.io.IOException;
//
//import org.apache.commons.fileupload.FileUploadException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.incture.cherrywork.dtos.AttachmentDto;
//import com.incture.cherrywork.dtos.ResponseDto;
//import com.incture.cherrywork.services.DocumentModelService;
//import com.incture.cherrywork.services.ObjectStoreService;
//import com.incture.cherrywork.util.ObjectStoreUtil;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//
//@RestController
//@RequestMapping("v3/document")
//@Api(value = "Document Store", tags = { "Document Store-without security" }, description = "  ")
//public class DocumentModelControllerWithoutSecurity {
//
//	@Autowired
//	private DocumentModelService documentModelService;
//
//	@Autowired
//	private ObjectStoreService objectStoreService;
//
//	@GetMapping("/attachments")
//	@ApiOperation(value = "Retrieve all attachments by entity id and entity type")
//	public ResponseDto getAttachmentsByEntity(@ApiParam(value = "Entity ID") @RequestParam("entityId") String entityId,
//			@ApiParam(value = "Entity Type") @RequestParam("entityType") String entityType) {
//
//		return documentModelService.getAttachmentsByEntity(entityId, entityType);
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@GetMapping("/{documentId}")
//	@ApiOperation(value = "Retrieve document by document id")
//	public ResponseEntity<InputStreamResource> getDocumentById(
//			@ApiParam(value = "Document ID") @PathVariable(value = "documentId") String documentId) {
//
//		if (documentId != null) {
//
//			AttachmentDto attachmentDto = documentModelService.getAttachmentsByDocmentId(documentId);
//			if (attachmentDto != null) {
//
//				HttpHeaders respHeaders = new HttpHeaders();
//
//				if (this.objectStoreService.isBlobExist(documentId + "_" + attachmentDto.getDocumentName())) {
//					respHeaders.setContentDispositionFormData("attachment",
//							documentId + "_" + attachmentDto.getDocumentName());
//					InputStreamResource inputStreamResource = new InputStreamResource(
//							this.objectStoreService.getFile(documentId + "_" + attachmentDto.getDocumentName()));
//					return new ResponseEntity<InputStreamResource>(inputStreamResource, respHeaders, HttpStatus.OK);
//				} else {
//					return errorMessage(attachmentDto.getDocumentName() + ObjectStoreUtil.FILE_DOESNOT_EXIST,
//							HttpStatus.NOT_FOUND);
//				}
//			} else {
//				return errorMessage(ObjectStoreUtil.FILE_DOESNOT_EXIST, HttpStatus.NOT_FOUND);
//			}
//		}
//
//		return new ResponseEntity<InputStreamResource>(HttpStatus.OK);
//
//	}
//
//	// @PostMapping
//	// @ApiOperation(value = "Upload document to sharepoint ")
//	// public ResponseDto uploadDocument(@ApiParam(value = "Entity ID")
//	// @RequestParam("entityId") String entityId,
//	// @ApiParam(value = "Entity Type") @RequestParam("entityType") String
//	// entityType,
//	// @RequestParam("file") CommonsMultipartFile file) {
//	// return documentModelService.uploadDocument(entityId, entityType, file);
//	//
//	// }
//
//	@PostMapping
//	@ApiOperation(value = "Upload document ")
//	public ResponseDto uploadFile(@ApiParam(value = "Entity ID") @RequestParam("entityId") String entityId,
//			@ApiParam(value = "Entity Type") @RequestParam("entityType") String entityType,
//			@RequestParam CommonsMultipartFile[] files) throws IOException, FileUploadException {
//
//		return documentModelService.uploadDocumentToStore(entityId, entityType, files);
//
//	}
//
//	@DeleteMapping("/{documentId}")
//	public ResponseDto deleteFile(@ApiParam(value = "Document Id") @PathVariable("documentId") String documentId) {
//
//		return documentModelService.deleteFileByDocumentId(documentId);
//
//	}
//
////	*//**
////	 * @param message
////	 * @param status
////	 * @return ResponseEntity with HTTP status,headers and body helper function
////	 *         to form the responseEntity
////	 *//*
//	@SuppressWarnings("rawtypes")
//	private static ResponseEntity errorMessage(String message, HttpStatus status) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);
//		return ResponseEntity.status(status).headers(headers).body(message);
//	}
//
//}
