package com.incture.cherrywork.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.ScheduleLineDao;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class ScheduleLineServiceImpl implements ScheduleLineService {

	@Autowired
	private ScheduleLineDao scheduleLineRepo;

	@Override
	public ResponseEntity saveOrUpdateScheduleLine(ScheduleLineDto scheduleLineDto) {
		try {
			if (!HelperClass.checkString(scheduleLineDto.getScheduleLineNum()) && !HelperClass.checkString(scheduleLineDto.getSalesHeaderNo())
					&& !HelperClass.checkString(scheduleLineDto.getSalesItemOrderNo())) {
				String msg = scheduleLineRepo.saveOrUpdateScheduleLine(scheduleLineDto);

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
				}
				return new ResponseEntity(scheduleLineDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Schedule Line Id, Sales Header Id and Sales Item Id fields are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllScheduleLines() {
		try {
			List<ScheduleLineDto> list = scheduleLineRepo.listAllScheduleLines();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum) {
		try {
			if (!HelperClass.checkString(soItemNum) && !HelperClass.checkString(soHeadNum) && !HelperClass.checkString(scheduleLineId)) {
				String msg = scheduleLineRepo.deleteScheduleLineById(scheduleLineId, soHeadNum, soItemNum);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED}",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Schedule Line Id, Sales Header Id and Sales Item Id fields are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR," EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getScheduleLineById(String scheduleLineId, String soHeadNum, String soItemNum) {
		try {
			if (!HelperClass.checkString(soItemNum) && !HelperClass.checkString(soHeadNum) && !HelperClass.checkString(scheduleLineId)) {
				ScheduleLineDto commentDto = scheduleLineRepo.getScheduleLineById(scheduleLineId, soHeadNum, soItemNum);
				if (commentDto != null) {
					return new ResponseEntity(commentDto, HttpStatus.ACCEPTED,
							"Schedule Line is found for Schedule Line id : " + scheduleLineId, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Schedule Line is not available for Schedule Line id : " + scheduleLineId,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Schedule Line Id, Sales Header Id and Sales Item Id fields are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
