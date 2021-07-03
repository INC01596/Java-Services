package com.incture.cherrywork.workflow.services;

import java.time.LocalDateTime;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTableDto;


public interface SchedulerTableService {

	public ResponseEntity saveInDB(SchedulerTableDto schedulerTableDto);

	public ResponseEntity listAllLog();

	public ResponseEntity listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate);

}
