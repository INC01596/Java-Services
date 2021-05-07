package com.incture.cherrywork.workflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusPrimaryKey;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusesDo;
import com.incture.cherrywork.repositories.ISalesOrderHeaderCustomRepository;

@Repository
public interface ISalesOrderTaskStatusesRepository extends JpaRepository<SalesOrderTaskStatusesDo, SalesOrderTaskStatusPrimaryKey>,
QuerydslPredicateExecutor<SalesOrderHeader>, ISalesOrderHeaderCustomRepository {


}