package com.incture.cherrywork.services;



import java.time.LocalDateTime;
import java.util.List;

import com.incture.cherrywork.dtos.AbbyySchedulerLogsDto;
import com.incture.cherrywork.dtos.ResponseEntity;

import com.incture.cherrywork.entities.AbbyySchedulerLogs;

public interface AbbyySchedulerService {
		
		public ResponseEntity saveInDB(AbbyySchedulerLogsDto AbbyySchedulerLogsDto);
		
		public ResponseEntity listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate);
		
}
