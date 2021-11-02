package com.incture.cherrywork.repositories;



import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.dtos.AttachmentDto;
import com.incture.cherrywork.entities.AttachmentDo;



public interface AttachmentRepo extends JpaRepository<AttachmentDo, String> {

	/**
	 * @param dto
	 * @return
	 */
	public default AttachmentDo importDto(AttachmentDto dto) {
		AttachmentDo entity = null;
		if (dto != null) {
			entity = new AttachmentDo();
			BeanUtils.copyProperties(dto, entity);
			if (dto.getFileContent() != null) {
				entity.setFileContent(dto.getFileContent().getBytes());
			}
		}
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 */
	public default AttachmentDto exportDto(AttachmentDo entity) {
		AttachmentDto dto = null;
		if (entity != null) {
			dto = new AttachmentDto();
			BeanUtils.copyProperties(entity, dto);
			if (entity.getFileContent() != null) {
				dto.setFileContent(new String(entity.getFileContent()));
			}
		}
		return dto;
	}

	@Query("select a from AttachmentDo a where a.documentName=:name")
	public AttachmentDo getAttachmentDetailsByName(@Param("name") String name);

	@Query("select a from AttachmentDo a where a.documentId=:documentId")
	public AttachmentDo getAttachmentDetailsByID(@Param("documentId") String documentId);

	@Query("select a from AttachmentDo a where a.entityId=:entityId and a.entityType=:entityType")
	public List<AttachmentDo> getAttachmentByEntityOld(@Param("entityId") String entityId,
			@Param("entityType") String entityType);
	
	@Query("select a.documentId , a.documentName, a.entityId , a.entityType , a.contentType "
			+ " from AttachmentDo a where a.entityId=:entityId and a.entityType=:entityType")
	public List<Object[]> getAttachmentByEntity(@Param("entityId") String entityId,
			@Param("entityType") String entityType);

	@Modifying
	@Query("delete from AttachmentDo where documentId=:documentId")
	public void deleteByAttachment(@Param("documentId") String documentId);

	public AttachmentDo findByDocumentId(String documentId);

}

