package com.incture.cherrywork.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusesDo;

public interface ISalesOrderTaskStatusesRepository extends JpaRepository<SalesOrderTaskStatusesDo, String>{

}
