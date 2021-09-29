package com.incture.cherrywork.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.MaterialSchedulerLogs;

@Repository
public interface IMaterialSchedulerLogs extends JpaRepository<MaterialSchedulerLogs,String>{
	@Query("SELECT m from MaterialSchedulerLogs m where istTimeStamp between ?1 and ?2 order by m.istTimeStamp desc")
	List<MaterialSchedulerLogs> findMaterialLogsBetweenDate(LocalDateTime startDate, LocalDateTime endDate);
}
