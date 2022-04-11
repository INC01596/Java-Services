package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesVisit;

@Repository
public interface ISalesVisitPlannerRepository extends JpaRepository<SalesVisit, String> {

	public SalesVisit findByVisitId(String visitId);

}
