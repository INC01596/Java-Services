package com.incture.cherrywork.workflow.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import com.incture.cherrywork.dao.SchedulerTableDao;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTableDto;
import com.incture.cherrywork.entities.SchedulerTableDo;
import com.incture.cherrywork.repositories.ISchedulerTableRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.ServicesUtil;

@Service("SchedulerTableServiceImpl")
@Transactional
public class SchedulerTableServiceImpl implements SchedulerTableService {

	@Lazy
	@Autowired
	private SchedulerTableDao schedulerTableDao;

	@Autowired
	private ISchedulerTableRepository schedulerTableRepository;

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity saveInDB(SchedulerTableDto schedulerTableDto) {
		try {
			schedulerTableDto.setId(ServicesUtil.randomId());
			SchedulerTableDo toSave = ObjectMapperUtils.map(schedulerTableDto, SchedulerTableDo.class);
			System.err.println("[SchedulerTableServiceImpl][saveInDB] toSave" + toSave.toString());
			SchedulerTableDo savedSchedulertableDo = schedulerTableRepository.save(toSave);

			System.err.println(
					"[SchedulerTableServiceImpl][saveInDB] savedSchedulertableDo" + savedSchedulertableDo.toString());
			if (savedSchedulertableDo == null) {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			String msg = "Successfully updated in hana";
			return new ResponseEntity(null, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		} catch (Exception e) {
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			e.printStackTrace();
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
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
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
					return new ResponseEntity(list, HttpStatus.OK, " DATA_FOUND", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter start date and end date",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}
}
