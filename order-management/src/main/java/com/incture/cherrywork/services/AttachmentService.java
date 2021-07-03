package com.incture.cherrywork.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.repositories.IAttachmentRepository;

@Service("Attachment Service")
@Transactional
public class AttachmentService implements IAttachmentService {

	@Autowired
	private IAttachmentRepository attachmentRepo;

	@Override
	public ResponseEntity<Object> getFileByReturnReqNum(String returnReqNum) {

		try {
			List<Attachment> attachment = attachmentRepo.findByReturnReqNum(returnReqNum);

			if (attachment != null && !attachment.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).header("message", "OK").body(attachment);

			} else {
				return ResponseEntity.status(HttpStatus.OK).header("message", " Not found ").body(" Not found ");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).header("message", " Not found " + e).body(null);

		}

	}

	@Override
	public Attachment getFileByDocId(String docId) {
		Attachment attachment = null;
		try {
			attachment = attachmentRepo.findByDocId(docId);
			return attachment;

		} catch (Exception e) {
			return attachment;

		}

	}

	@Override
	public ResponseEntity<Object> deleteByDocId(String docId) {

		int result = attachmentRepo.deleteByDocId(docId);

		return ResponseEntity.status(HttpStatus.OK).header("message", "Removed Attachement successfully").body(result + "Removed Attachement successfully");

	}
	
	

}
