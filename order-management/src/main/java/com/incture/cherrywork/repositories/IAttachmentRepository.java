package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.entities.AttachmentPk;

@Repository
public interface IAttachmentRepository extends JpaRepository<Attachment, AttachmentPk>{
	
Attachment findByDocId(String docId);
	
	List<Attachment> findByReturnReqNum(String returnReqNum);
	
	
	@Modifying(clearAutomatically = true)
	@Query(value ="delete from attachments at where at.DOC_ID =?1 ", nativeQuery = true)
	int  deleteByDocId(String docId);


}
