package com.incture.cherrywork.repositories;



import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.AttributeDetailsDo;



@Repository
public interface AttributeDetailsRepository extends JpaRepository<AttributeDetailsDo, String> {

	AttributeDetailsDo findByAttributeIdAndDomainCodeAndAttributeDesc(String attributeId, String domainCode,
			String attributeDesc);

	List<AttributeDetailsDo> findByDomainCodeOrderByAttributeIdAsc(String domainCode);

	List<AttributeDetailsDo> findByAttributeDetailsGuidIn(Set<String> attributeDetailsDoList);

	List<AttributeDetailsDo> findByDomainCodeAndAttributeDetailsGuidIn(String domainCode,
			Set<String> attributeDetailsDoList);

	Long countByDomainCode(String domainCode);

	AttributeDetailsDo findByAttributeId(String attributeId);

}

