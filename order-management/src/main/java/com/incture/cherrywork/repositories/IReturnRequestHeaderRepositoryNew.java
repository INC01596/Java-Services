package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.entities.ReturnRequestHeader;

@Repository
public class IReturnRequestHeaderRepositoryNew  {

	
	@Autowired 
	private IReturnRequestHeaderRepository repo;

	public Page<ReturnRequestHeader> findAll(ReturnFilterDto dto,Pageable pageable) {
		
		
		
		if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
				{
			      System.err.println("hello2");
			      return repo.findAll(pageable);
				}
		return null; 
	}
	
	
	
}
