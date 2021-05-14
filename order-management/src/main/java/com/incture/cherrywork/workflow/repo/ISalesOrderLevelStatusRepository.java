package com.incture.cherrywork.workflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;

@Repository
public interface ISalesOrderLevelStatusRepository extends JpaRepository<SalesOrderLevelStatusDo, String>,
QuerydslPredicateExecutor<SalesOrderLevelStatusDo>, ISalesOrderLevelStatusCustomRepository{

}
