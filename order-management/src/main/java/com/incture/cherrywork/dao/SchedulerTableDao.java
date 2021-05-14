package com.incture.cherrywork.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.incture.cherrywork.dtos.SchedulerTableDto;



public interface SchedulerTableDao {

	public String save(SchedulerTableDto schedulerTableDto) throws Exception;

	public List<SchedulerTableDto> listAllLogs();

	public List<SchedulerTableDto> listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate);

}
