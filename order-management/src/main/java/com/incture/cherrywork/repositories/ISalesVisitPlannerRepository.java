package com.incture.cherrywork.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesVisit;

@Repository
public interface ISalesVisitPlannerRepository extends JpaRepository<SalesVisit, String> {

	@Query("from SalesVisit where visitId=?1")
	public SalesVisit findByVisitId(String visitId);

	@Query("from SalesVisit where visitId in (?1)")
	public Optional<List<SalesVisit>> findAllByVisitId(List<String> visitIdList);
}
