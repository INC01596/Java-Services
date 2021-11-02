//
//package com.incture.cherrywork.controllers;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//
//import javax.servlet.http.HttpServletRequest;
//import  org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
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
//
//import org.springframework.web.bind.annotation.RestController;
//
//
//import com.google.common.io.ByteStreams;
//import com.incture.cherrywork.entities.BlobFile;
//
//import com.incture.cherrywork.services.LocalAWSObjectStoreService;
//import com.incture.cherrywork.services.ObjectStoreService;
//import com.incture.cherrywork.util.ObjectStoreUtil;
//
//@RestController
//@RequestMapping("api/v1/document")
//public class DocumentController {
//	public DocumentController() {
//		//HelperClass.getLogger(this.getClass().getName()).info("inside comment controller");
//	
//		System.err.println("Inside Document controller");
//	}
//
//	@Autowired
//	private ObjectStoreService objectStoreService;
//	
//	 
//
//	/**
//	 * @return list of blobfiles Function to get the list of objects in the
//	 *         objectStore.
//	 /*
//	@GetMapping("/storage")
//	@ResponseBody
//	public ResponseEntity<List<BlobFile>> listFiles() {
//
//		List<BlobFile> blobFiles = this.objectStoreService.listObjects();
//		return new ResponseEntity<>(blobFiles, HttpStatus.OK);
//	}
//
//	*/
//	 /* @param request
//	 * @return Message indicating if the file has been uploaded Function to
//	 *         upload objects to objectStore.
//	 */
//	@PostMapping("/storage")
//	public ResponseEntity<String> uploadFile(HttpServletRequest request) throws IOException, FileUploadException {
//
//		byte[] byteArray = null;
//		StringBuffer message = new StringBuffer();
//		Optional<FileItemStream> fileItemStream = Optional.empty();
//
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		// message.append(isMultipart);
//		if (isMultipart) {
//			// message.append("Is multipart");
//			ServletFileUpload upload = new ServletFileUpload();
//			FileItemIterator fileItemIterator = upload.getItemIterator(request);
//
//			while (fileItemIterator.hasNext()) {
//				// message.append("Inside while");
//				fileItemStream = Optional.of(fileItemIterator.next());
//				InputStream inputStream = fileItemStream.get().openStream();
//				byteArray = ByteStreams.toByteArray(inputStream);
//				// message.append(byteArray.toString());
//				if (!fileItemStream.get().isFormField()) {
//					// message.append("Inside if loop");
//					final String contentType = fileItemStream.get().getContentType();
//					message.append(
//							objectStoreService.uploadFile(byteArray, fileItemStream.get().getName(), contentType));
//				}
//			}
//
//		}
//		return new ResponseEntity<>(message.toString(), HttpStatus.ACCEPTED);
//	}
//
//	/**
//	 * @param fileName
//	 * @return inputStream containing the file Function to get a particular
//	 *         objects from objectStore.
//	 */
//	@SuppressWarnings("unchecked")
//	@GetMapping(value = "/storage/{name:.*}")
//	public ResponseEntity<InputStreamResource> getFile(@PathVariable(value = "name") String fileName) {
//
//		if (fileName != null) {
//			HttpHeaders respHeaders = new HttpHeaders();
//
//			if (this.objectStoreService.isBlobExist(fileName)) {
//				respHeaders.setContentDispositionFormData("attachment", fileName);
//				InputStreamResource inputStreamResource = new InputStreamResource(
//						this.objectStoreService.getFile(fileName));
//				return new ResponseEntity<InputStreamResource>(inputStreamResource, respHeaders, HttpStatus.OK);
//			} else {
//				return errorMessage(fileName + ObjectStoreUtil.FILE_DOESNOT_EXIST, HttpStatus.NOT_FOUND);
//			}
//		}
//
//		// Default to 200, when input is missing
//		return new ResponseEntity<InputStreamResource>(HttpStatus.OK);
//	}
//
//	/**
//	 * @param fileName
//	 * @return Message indicating if the file has been deleted Function to
//	 *         delete an object
//	 */
//	@SuppressWarnings("unchecked")
//	@DeleteMapping("/storage/{name}")
//	public ResponseEntity<String> deleteFile(@PathVariable(value = "name") String fileName) {
//		String msg = ObjectStoreUtil.CANNOT_DELETE_NULL;
//		if (fileName != null) {
//			if (this.objectStoreService.isBlobExist(fileName)) {
//				if (this.objectStoreService.deleteFile(fileName)) {
//					msg = fileName + ObjectStoreUtil.DELETE_SUCCESSFUL;
//				} else {
//					msg = ObjectStoreUtil.DELETE_FAILED + fileName;
//					return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//			} else {
//				msg = fileName + ObjectStoreUtil.FILE_DOESNOT_EXIST;
//				return errorMessage(msg, HttpStatus.NOT_FOUND);
//			}
//
//		}
//
//		return new ResponseEntity<>(msg, HttpStatus.OK);
//	}
//
//	/**
//	 * @param message
//	 * @param status
//	 * @return ResponseEntity with HTTP status,headers and body helper function
//	 *         to form the responseEntity
//	 **/
//	@SuppressWarnings("rawtypes")
//	private static ResponseEntity errorMessage(String message, HttpStatus status) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);
//		return ResponseEntity.status(status).headers(headers).body(message);
//	}
//
//}
//
