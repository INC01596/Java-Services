package com.incture.cherrywork.repositories;



import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.dtos.RecipientDto;
import com.incture.cherrywork.entities.RecipientDo;
import com.incture.cherrywork.entities.RecipientDoPK;
import com.incture.cherrywork.util.ServicesUtil;



public interface RecipientRepo extends JpaRepository<RecipientDo, RecipientDoPK> {

	public default RecipientDo importDto(RecipientDto dto) {
		RecipientDo entity = null;
		RecipientDoPK pk = null;

		if (dto != null) {
			pk = new RecipientDoPK();
			entity = new RecipientDo();
			pk.setEmailDefinitionId(dto.getEmailDefinitionId());
			pk.setRecipientType(dto.getRecipientType());
			pk.setUserEmail(dto.getUserEmail());
			entity.setId(pk);
		}

		return entity;
	}

	public default List<RecipientDo> importDto(List<RecipientDto> recipientDtoList) {
		if (!ServicesUtil.isEmpty(recipientDtoList)) {
			List<RecipientDo> recipientDoList = new ArrayList<RecipientDo>();
			recipientDtoList.forEach(dto -> {

				RecipientDoPK pk = new RecipientDoPK();
				RecipientDo entity = new RecipientDo();
				pk.setEmailDefinitionId(dto.getEmailDefinitionId());
				pk.setRecipientType(dto.getRecipientType());
				pk.setUserEmail(dto.getUserEmail());
				entity.setId(pk);
				recipientDoList.add(entity);
			});
			return recipientDoList;
		}

		return null;
	}

	@Query("select p from RecipientDo p where p.id.emailDefinitionId=:emailDefinitionId")
	public List<RecipientDo> findByIdEmailDefinitionId(@Param("emailDefinitionId") String emailDefinitionId);

	public void deleteByIdEmailDefinitionId(String emailDefinitionId);

	/**
	 * @param entity
	 * @return
	 */
	// public default CustomAttributeDefinitionDto
	// exportDto(CustomAttributeDefinitionDo entity) {
	// CustomAttributeDefinitionDto dto = null;
	//
	// if (!ServicesUtil.isEmpty(entity)) {
	// dto = new CustomAttributeDefinitionDto();
	// BeanUtils.copyProperties(entity, dto);
	// dto.setCustomAttributeDefinition(entity.getId().getCustomAttributeDefinition());
	// dto.setProcessDefinitionId(entity.getId().getProcessDefinitionId());
	// }
	// return dto;
	// }

}
