package com.incture.cherrywork.repositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.AbbyySchedulerLogs;

@Repository
public interface IAbbyySchedulerLogs extends JpaRepository<AbbyySchedulerLogs,String>{
	@Query("SELECT m from AbbyySchedulerLogs m where istTimeStamp between ?1 and ?2 order by m.istTimeStamp desc")
	List<AbbyySchedulerLogs> findAbbyyLogsBetweenDate(LocalDateTime startDate, LocalDateTime endDate);
}