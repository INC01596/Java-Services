package com.incture.cherrywork.repositories;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;


import org.springframework.stereotype.Repository;


import com.incture.cherrywork.entities.ReturnRequestHeader;

@Repository
public interface IReturnRequestHeaderRepository extends JpaRepository<ReturnRequestHeader, String>
{

	@Query("from ReturnRequestHeader r  ")
	public Page<ReturnRequestHeader> findAll(Pageable pageable);
	
}
