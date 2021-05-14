package com.incture.cherrywork.workflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusesDo;


@Repository
public interface ISalesOrderTaskStatusRepository extends JpaRepository<SalesOrderTaskStatusDo, String>,
QuerydslPredicateExecutor<SalesOrderTaskStatusDo>,ISalesOrderTaskStatusCustomRepository{

}
