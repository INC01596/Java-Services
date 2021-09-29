package com.incture.cherrywork.repositories;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.dtos.EmailDefinitionProcessMappingDto;
import com.incture.cherrywork.entities.EmailDefinitionProcessMappingDo;
import com.incture.cherrywork.entities.EmailDefinitionProcessMappingDoPk;



public interface EmailDefinitionProcessMappingRepo
		extends JpaRepository<EmailDefinitionProcessMappingDo, EmailDefinitionProcessMappingDoPk> {

	/**
	 * @param dto
	 * @return EmailDefinitionProcessMappingDo
	 */
	public default EmailDefinitionProcessMappingDo importDto(EmailDefinitionProcessMappingDto dto) {
		EmailDefinitionProcessMappingDo entity = null;
		EmailDefinitionProcessMappingDoPk pk;
		if (dto != null) {
			entity = new EmailDefinitionProcessMappingDo();
			pk = new EmailDefinitionProcessMappingDoPk();
			BeanUtils.copyProperties(dto, entity);
			pk.setEmailDefinitionId(dto.getEmailDefinitionId());
			pk.setProcess(dto.getProcess());
			entity.setId(pk);
		}
		return entity;
	}

	/**
	 * @param entity
	 * @return EmailDefinitionProcessMappingDto
	 */
	public default EmailDefinitionProcessMappingDto exportDto(EmailDefinitionProcessMappingDo entity) {
		EmailDefinitionProcessMappingDto dto = null;
		if (entity != null) {
			dto = new EmailDefinitionProcessMappingDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setEmailDefinitionId(entity.getId().getEmailDefinitionId());
			dto.setProcess(entity.getId().getProcess());
		}
		return dto;
	}

	/**
	 * @param dtoList
	 * @return List<EmailDefinitionProcessMappingDo>
	 */
	public default List<EmailDefinitionProcessMappingDo> importDtoList(List<EmailDefinitionProcessMappingDto> dtoList) {
		List<EmailDefinitionProcessMappingDo> EmailDefinitionProcessMappingDos = null;
		if (dtoList != null && !dtoList.isEmpty()) {
			EmailDefinitionProcessMappingDos = dtoList.stream().filter(dto -> dto != null).map(dto -> {
				EmailDefinitionProcessMappingDo entity = new EmailDefinitionProcessMappingDo();
				EmailDefinitionProcessMappingDoPk pk = new EmailDefinitionProcessMappingDoPk();
				BeanUtils.copyProperties(dto, entity);
				pk.setEmailDefinitionId(dto.getEmailDefinitionId());
				pk.setProcess(dto.getProcess());
				entity.setId(pk);
				return entity;
			}).collect(Collectors.toList());
		}
		return EmailDefinitionProcessMappingDos;
	}

	/**
	 * @param entity
	 * @return List<EmailDefinitionProcessMappingDto>
	 */
	public default List<EmailDefinitionProcessMappingDto> exportDtoList(
			List<EmailDefinitionProcessMappingDo> entityList) {
		List<EmailDefinitionProcessMappingDto> EmailDefinitionProcessMappingDtos = null;
		if (entityList != null && !entityList.isEmpty()) {
			EmailDefinitionProcessMappingDtos = entityList.stream().filter(entity -> entity != null).map(entity -> {
				EmailDefinitionProcessMappingDto dto = new EmailDefinitionProcessMappingDto();
				BeanUtils.copyProperties(entity, dto);
				dto.setEmailDefinitionId(entity.getId().getEmailDefinitionId());
				dto.setProcess(entity.getId().getProcess());
				return dto;
			}).collect(Collectors.toList());
		}
		return EmailDefinitionProcessMappingDtos;
	}

	@Modifying
	@Query("delete from EmailDefinitionProcessMappingDo where id.emailDefinitionId=:emailDefinitionId ")
	public void deleteByEmailDefinitionId(@Param("emailDefinitionId") String emailDefinitionId);

	@Query("select mapping from EmailDefinitionProcessMappingDo mapping where mapping.id.emailDefinitionId=:emailDefinitionId ")

	public List<EmailDefinitionProcessMappingDo> getByEmailDefinitionId(String emailDefinitionId);

}
