package com.incture.cherrywork.services;



import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.entities.DomainTextDo;



public interface DomainTextServices {

	ResponseEntity<?> findById(String id);

	ResponseEntity<?> saveOrUpdate(DomainTextDo model);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> listAll();
}

