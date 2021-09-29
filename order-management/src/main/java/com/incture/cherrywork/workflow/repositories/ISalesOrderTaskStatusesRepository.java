package com.incture.cherrywork.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusesDo;

@Repository
public interface ISalesOrderTaskStatusesRepository extends JpaRepository<SalesOrderTaskStatusesDo, String>{

}
