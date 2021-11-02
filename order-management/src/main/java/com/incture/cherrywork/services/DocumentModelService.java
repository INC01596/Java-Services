package com.incture.cherrywork.services;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.incture.cherrywork.dtos.AttachmentDto;
import com.incture.cherrywork.dtos.ResponseDto;


public interface DocumentModelService {

	ResponseDto getDocumentByID(String documentID);

	ResponseDto uploadDocument(String entityId, String entityType, CommonsMultipartFile file);

	ResponseDto getAttachmentsByEntity(String entityId, String entityType);

	void saveAttachments(List<AttachmentDto> list);

	void deleteAttatchment(String documentId, String fileName);

	AttachmentDto getAttachmentsByDocmentId(String documentId);

	ResponseDto uploadDocumentToStore(String entityId, String entityType, CommonsMultipartFile[] files);

	ResponseDto deleteFileByDocumentId(String documentId);

}

