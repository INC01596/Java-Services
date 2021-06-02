package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.entities.Attachment;

public interface IAttachmentService {

	public Attachment getFileByDocId(String docId);

	public ResponseEntity<Object> getFileByReturnReqNum(String returnReqNum);
	public ResponseEntity<?> deleteByDocId(String returnReqNum);

}
