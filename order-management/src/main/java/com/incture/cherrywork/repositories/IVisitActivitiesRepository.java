package com.incture.cherrywork.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.VisitActivities;

@Repository
public interface IVisitActivitiesRepository extends JpaRepository<VisitActivities, String> {

	@Query("from VisitActivities where visitId=?1")
	public Optional<List<VisitActivities>> findByVisitId(String visitId);

}
