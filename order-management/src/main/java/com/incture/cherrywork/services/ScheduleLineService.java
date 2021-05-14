package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ScheduleLineDto;

public interface ScheduleLineService {

	public ResponseEntity saveOrUpdateScheduleLine(ScheduleLineDto scheduleLineDto);

	public ResponseEntity listAllScheduleLines();

	public ResponseEntity deleteScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum);

	public ResponseEntity getScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum);
}

