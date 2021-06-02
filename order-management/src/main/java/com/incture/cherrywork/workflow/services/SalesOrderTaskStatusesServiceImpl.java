package com.incture.cherrywork.workflow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.worflow.dao.SalesOrderTaskStatusesDao;

@Service
@Transactional
public class SalesOrderTaskStatusesServiceImpl implements SalesOrderTaskStatusesService {

	@Autowired
	private SalesOrderTaskStatusesDao salesOrderTaskStatusRepo;

	@Override
	public ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		try {
			// Checking Primary Key here
			if (!HelperClass.checkString(salesOrderTaskStatusDto.getDecisionSetId())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getUserGroup())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getLevel())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getRequestId())) {

				String msg = salesOrderTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrderTaskStatus() {
		try {
			List<SalesOrderTaskStatusesDto> list = salesOrderTaskStatusRepo.listAllSalesOrderTaskStatus();
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
	public ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!HelperClass.checkString(key.getDecisionSetId()) && !HelperClass.checkString(key.getLevel()) && !HelperClass.checkString(key.getRequestId())
					&& !HelperClass.checkString(key.getUserGroup())) {
				SalesOrderTaskStatusesDto salesOrderTaskStatusDto = salesOrderTaskStatusRepo
						.getSalesOrderTaskStatusById(key);
				if (salesOrderTaskStatusDto != null) {
					return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.ACCEPTED,
							"Sales Order Task Status is found for Key : " + key, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Task Status is not available for Key : " + key, ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!HelperClass.checkString(key.getDecisionSetId()) && !HelperClass.checkString(key.getLevel()) && !HelperClass.checkString(key.getRequestId())
					&& !HelperClass.checkString(key.getUserGroup())) {
				String msg = salesOrderTaskStatusRepo.deleteSalesOrderTaskStatusById(key);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level) {
		try {
			if (!HelperClass.checkString(decisionSet) && !HelperClass.checkString(level)) {
				List<SalesOrderTaskStatusesDto> list = salesOrderTaskStatusRepo
						.getSalesOrderTaskStatusAccToDsAndLevel(decisionSet, level);
				if (list != null && !list.isEmpty()) {

					// flag to count the completed task status
					int flagForTotalCompletedStatus = 0;

					// Checking list task status and count completed task
					for (SalesOrderTaskStatusesDto salesOrderTaskStatusDto : list) {
						// if
						// (salesOrderTaskStatusDto.getTaskStatus().equals(TaskStatus.APPROVED.toString())
						// || salesOrderTaskStatusDto.getTaskStatus()
						// .equals(TaskStatus.PARTIALLY_APPROVED.toString())) {
						// flagForTotalCompletedStatus++;
						// }
					}

					// Checking list size with status which are completed
					if (flagForTotalCompletedStatus == list.size()) {

						// If size of list and status as completed are same then
						// sending TRUE
						return new ResponseEntity(list, HttpStatus.ACCEPTED, Boolean.TRUE.toString(),
								ResponseStatus.SUCCESS);

					} else {

						// If size of list and status as completed are not same
						// then sending FALSE
						return new ResponseEntity(list, HttpStatus.ACCEPTED, Boolean.FALSE.toString(),
								ResponseStatus.SUCCESS);

					}
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "DATA_NOT_FOUND", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set and Level both fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
