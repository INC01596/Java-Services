package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;

@Repository
public interface ISalesOrderItemStatusRepository extends JpaRepository<SalesOrderItemStatusDo, String>{

}
