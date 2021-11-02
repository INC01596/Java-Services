package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.incture.cherrywork.entities.Attachment;

public interface IAttachmentService {

	public Attachment getFileByDocId(String docId);

	public ResponseEntity<Object> getFileByReturnReqNum(String returnReqNum);
	public ResponseEntity<?> deleteByDocId(String returnReqNum);

	String storeFile(MultipartFile file, String returnReqNum);

}
