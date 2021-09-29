package com.incture.cherrywork.repositories;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.cherrywork.dtos.ApplicationMasterDto;
import com.incture.cherrywork.entities.ApplicationMasterDo;
import com.incture.cherrywork.util.ServicesUtil;


public interface ApplicationMasterRepo extends JpaRepository<ApplicationMasterDo, String> {

	public default ApplicationMasterDo importDto(ApplicationMasterDto dto) {
		ApplicationMasterDo entity = null;
		if (!ServicesUtil.isEmpty(dto)) {
			entity = new ApplicationMasterDo();
			BeanUtils.copyProperties(dto, entity);
		}
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 */
	public default ApplicationMasterDto exportDto(ApplicationMasterDo entity) {
		ApplicationMasterDto dto = null;

		if (!ServicesUtil.isEmpty(entity)) {
			dto = new ApplicationMasterDto();
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	@Query("select  distinct  p.application ,p.applicationDesc  from ApplicationMasterDo p ")
	public List<Object[]> listAllApplications();

	@Query("select  distinct  p.entity,p.entityDesc  from ApplicationMasterDo p where (:application is null or p.application =:application)")
	public List<Object[]> listEntitiesByApplication(@Param("application") String application);

	@Query("select   distinct p.process,p.processDesc  from ApplicationMasterDo p where (:application is null or p.application =:application ) and (:entity is null or p.entity=:entity )")
	public List<Object[]> listProcessByAppAndEntity(@Param("application") String application,
			@Param("entity") String entity);
}
