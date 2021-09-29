package com.incture.cherrywork.repositories;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.dtos.EmailDefinitionDto;
import com.incture.cherrywork.entities.EmailDefinitionDo;
import com.incture.cherrywork.util.ServicesUtil;


public interface EmailDefinitionRepo extends JpaRepository<EmailDefinitionDo, String> {

	public default EmailDefinitionDo importDto(EmailDefinitionDto dto) {
		EmailDefinitionDo entity = null;
		if (!ServicesUtil.isEmpty(dto)) {
			entity = new EmailDefinitionDo();
			BeanUtils.copyProperties(dto, entity);
		}
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 */
	public default EmailDefinitionDto exportDto(EmailDefinitionDo entity) {
		EmailDefinitionDto dto = null;

		if (!ServicesUtil.isEmpty(entity)) {
			dto = new EmailDefinitionDto();
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	@Query("select p from EmailDefinitionDo p  where (:application is null or p.application =:application) and (:process is null or p.process =:process) and (:entity is null or p.entity =:entity) and (:name is null or p.name =:name) and (:status is null or p.status =:status)")
	public Page<EmailDefinitionDo> getEmailDefinitionBasedOnDetails(@Param("application") String application,
			@Param("process") String process, @Param("entity") String entity, @Param("name") String name,
			@Param("status") String status, Pageable pageable);

//	@Query("select  p from EmailDefinitionDo p   left join EmailDefinitionProcessMappingDo mapping on p.emailDefinitionId = mapping.id.emailDefinitionId where (:application is null or p.application =:application) and (:process is null or mapping.id.process =:process) and (:entity is null or p.entity =:entity) and (:name is null or p.name =:name) and (:status is null or p.status =:status)")
//	public List<EmailDefinitionDo> getEmailDefinitionBasedOnDetails(@Param("application") String application,
//			@Param("process") String process, @Param("entity") String entity, @Param("name") String name,
//			@Param("status") String status);

	@Query("select   p from EmailDefinitionDo p where p.emailDefinitionId =:emailDefinitionId")
	public List<EmailDefinitionDo> findByEmailDefinitionId(@Param("emailDefinitionId") String emailDefinitionId);

	@Query("select p from EmailDefinitionDo p  where p.application =:application and   p.process =:process and  p.entity =:entity  and p.status='Active'")
	public List<EmailDefinitionDo> getActiveEmailDefinition(@Param("application") String application,
			@Param("process") String process, @Param("entity") String entity);
//	@Query("select   p from EmailDefinitionDo p  left join EmailDefinitionProcessMappingDo mapping on p.emailDefinitionId = mapping.id.emailDefinitionId  where p.application =:application and   mapping.id.process =:process and  p.entity =:entity and p.name=:name ")
//	public List<EmailDefinitionDo> getEmailDefinition(@Param("application") String application,
//			@Param("process") String process, @Param("entity") String entity, @Param("name") String name);

	@Query("select p from EmailDefinitionDo p  where (:application is null or p.application =:application) and (:process is null or p.process =:process) and (:entity is null or p.entity =:entity) and (:name is null or p.name =:name)  and (:emailDefinitionId is null or p.emailDefinitionId!=:emailDefinitionId )")
	public List<EmailDefinitionDo> validateName(@Param("application") String application,
			@Param("process") String process, @Param("entity") String entity, @Param("name") String name,
			@Param("emailDefinitionId") String emailDefinitionId);
//	@Query("select   p from EmailDefinitionDo p  left join EmailDefinitionProcessMappingDo mapping on p.emailDefinitionId = mapping.id.emailDefinitionId where (:application is null or p.application =:application) and (:process is null or mapping.id.process =:process) and (:entity is null or p.entity =:entity) and (:name is null or p.name =:name)  and (:emailDefinitionId is null or p.emailDefinitionId!=:emailDefinitionId )")
//	public List<EmailDefinitionDo> validateName(@Param("application") String application,
//			@Param("process") String process, @Param("entity") String entity, @Param("name") String name,
//			@Param("emailDefinitionId") String emailDefinitionId);

	@Modifying
	@Query(" update EmailDefinitionDo e set e.status=:newStatus where e.status=:oldStatus and   e.application =:application and  e.process =:process and  e.entity =:entity")
	public void updateStatus(@Param("application") String application, @Param("process") String process,
			@Param("entity") String entity, @Param("newStatus") String newStatus, @Param("oldStatus") String oldStatus);

	public Page<EmailDefinitionDo> findByNameContainingIgnoreCase(String searchString, Pageable pageable);
}
