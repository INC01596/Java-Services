package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesRepToManager;

@Repository
public interface ISalesRepToManagerRepository extends JpaRepository<SalesRepToManager, String> {

	public SalesRepToManager findBySalesRepId(String repId);

	public SalesRepToManager findBySalesRepEmail(String repEmail);

}
