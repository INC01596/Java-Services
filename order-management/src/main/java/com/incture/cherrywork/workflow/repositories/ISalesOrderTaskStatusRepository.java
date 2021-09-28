package com.incture.cherrywork.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusesDo;




@Repository
public interface ISalesOrderTaskStatusRepository extends JpaRepository<SalesOrderTaskStatusDo, String>, QuerydslPredicateExecutor<SalesOrderTaskStatusDo>{

}
