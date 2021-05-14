package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.exceptions.ExecutionFault;

public interface ScheduleLineDao {

	public String saveOrUpdateScheduleLine(ScheduleLineDto scheduleLineDto) throws ExecutionFault;

	public List<ScheduleLineDto> listAllScheduleLines();

	public ScheduleLineDto getScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum);

	public String deleteScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum)
			throws ExecutionFault;

}

