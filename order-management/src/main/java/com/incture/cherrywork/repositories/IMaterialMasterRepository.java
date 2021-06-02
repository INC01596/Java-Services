package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.MaterialMaster;

public interface IMaterialMasterRepository extends JpaRepository<MaterialMaster, String>,
QuerydslPredicateExecutor<MaterialMaster>{
	
}

