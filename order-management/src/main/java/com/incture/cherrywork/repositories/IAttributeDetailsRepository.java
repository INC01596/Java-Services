package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.AttributeDetailsDo;

public interface IAttributeDetailsRepository extends JpaRepository<AttributeDetailsDo, String>{
	
	List<AttributeDetailsDo> findByDomainCodeOrderByAttributeIdAsc(String domainCode);

}
