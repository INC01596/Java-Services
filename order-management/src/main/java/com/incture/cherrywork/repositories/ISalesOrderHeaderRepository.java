package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.SalesOrderHeader;

public interface ISalesOrderHeaderRepository extends JpaRepository<SalesOrderHeader, String>,
QuerydslPredicateExecutor<SalesOrderHeader>{
    }
