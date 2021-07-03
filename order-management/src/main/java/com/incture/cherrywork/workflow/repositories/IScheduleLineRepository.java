package com.incture.cherrywork.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.ScheduleLineDo;
import com.incture.cherrywork.entities.ScheduleLinePrimaryKeyDo;



public interface IScheduleLineRepository extends JpaRepository<ScheduleLineDo, ScheduleLinePrimaryKeyDo>{

	
}
