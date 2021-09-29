package com.incture.cherrywork.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.AttributeDetail;



@Repository
public interface AttributeDetailRepository extends JpaRepository<AttributeDetail, Integer> {

	AttributeDetail findByAttributeId(String id);
	
	AttributeDetail findByAttributeDesc(String attributeDesc);
}
