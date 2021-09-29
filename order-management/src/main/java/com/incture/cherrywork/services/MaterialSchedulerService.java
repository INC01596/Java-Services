package com.incture.cherrywork.services;

import java.time.LocalDateTime;

import com.incture.cherrywork.dtos.MaterialSchedulerLogsDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.entities.MaterialSchedulerLogs;

public interface MaterialSchedulerService {
		//nischal -- Methods for all the db operations on Material Table
		public ResponseEntity saveInDB(MaterialSchedulerLogsDto materialSchedulerLogsDto);
		
		public ResponseEntity listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate);
}
