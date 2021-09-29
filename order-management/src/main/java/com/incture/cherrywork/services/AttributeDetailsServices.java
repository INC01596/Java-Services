package com.incture.cherrywork.services;



import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.entities.AttributeDetailsDo;


public interface AttributeDetailsServices {

	ResponseEntity<?> findById(String id);

	ResponseEntity<?> saveOrUpdate(AttributeDetailsDo model);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> listAll();

	ResponseEntity<?> findByDomainCode(String domainCode);

}
