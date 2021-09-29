package com.incture.cherrywork.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.PermissionObject;
import com.incture.cherrywork.entities.PermissionObjectDo;



@Repository
public interface PermissionObjectRepository extends JpaRepository<PermissionObject, Integer> {

	PermissionObjectDo findByDescEqualsIgnoreCase(String desc);

	PermissionObjectDo findByPermissionId(int id);
	
	
}

