package com.incture.cherrywork.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.RequestMasterDo;

public interface IRequestMasterRepository
		extends JpaRepository<RequestMasterDo, String>, QuerydslPredicateExecutor<RequestMasterDo> {

}
