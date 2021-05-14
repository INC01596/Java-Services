package com.incture.cherrywork.services;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.SchedulerTableDao;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTableDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;



@Service
@Transactional
public class SchedulerTableServiceImpl implements SchedulerTableService {

	@Autowired
	private SchedulerTableDao schedulerTableDao;

	@Override
	public ResponseEntity saveInDB(SchedulerTableDto schedulerTableDto) {
		try {
			String msg = schedulerTableDao.save(schedulerTableDto);

			if (msg == null) {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			return new ResponseEntity(null, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllLog() {
		try {
			List<SchedulerTableDto> list = schedulerTableDao.listAllLogs();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
		//	HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (startDate != null && endDate != null) {
				System.err.println("startDate : " + startDate);
				System.err.println("endDate : " + endDate);
				List<SchedulerTableDto> list = schedulerTableDao.listAllLogsInIst(startDate, endDate);
				if (list != null && !list.isEmpty()) {
					return new ResponseEntity(list, HttpStatus.OK," DATA_FOUND", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter start date and end date",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}
}
