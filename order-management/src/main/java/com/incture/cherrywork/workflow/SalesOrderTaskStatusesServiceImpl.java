/*package com.incture.cherrywork.workflow;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;

import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusesDo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.workflow.repo.ISalesOrderTaskStatusesRepository;



@Service
@Transactional
public class SalesOrderTaskStatusesServiceImpl implements SalesOrderTaskStatusesService {

	
	
	@Autowired
	private ISalesOrderTaskStatusesRepository salesOrderTaskStatusRepo;
	
	
	@Override
	public ResponseEntity<Object> saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		
		SalesOrderTaskStatusesDo salesOrdertaskStatuses= ObjectMapperUtils.map(salesOrderTaskStatusDto, SalesOrderTaskStatusesDo.class);
		SalesOrderTaskStatusesDo savedSalesOrdertaskStatuses=salesOrderTaskStatusRepo.save(salesOrdertaskStatuses);
		return ResponseEntity.ok(ObjectMapperUtils.map(savedSalesOrdertaskStatuses, SalesOrderTaskStatusesDto.class));
	}
		
		/*try {
			// Checking Primary Key here
			if (!checkString(salesOrderTaskStatusDto.getDecisionSetId())
					&& !checkString(salesOrderTaskStatusDto.getUserGroup())
					&& !checkString(salesOrderTaskStatusDto.getLevel())
					&& !checkString(salesOrderTaskStatusDto.getRequestId())) {

				String msg = salesOrderTaskStatusRepo.saveOrUpdateSalesOrderTaskStatus(salesOrderTaskStatusDto);

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, CREATION_FAILED, ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}*/
/*
	@Override
	public ResponseEntity<Object> listAllSalesOrderTaskStatus() {
		try {
			List<SalesOrderTaskStatusesDo> list = salesOrderTaskStatusRepo.findAll();
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
		
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Bad Request");
		}
	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!checkString(key.getDecisionSetId()) && !checkString(key.getLevel()) && !checkString(key.getRequestId())
					&& !checkString(key.getUserGroup())) {
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
			HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}
	/*
	@Override
	public ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!checkString(key.getDecisionSetId()) && !checkString(key.getLevel()) && !checkString(key.getRequestId())
					&& !checkString(key.getUserGroup())) {
				String msg = salesOrderTaskStatusRepo.deleteSalesOrderTaskStatusById(key);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level) {
		try {
			if (!checkString(decisionSet) && !checkString(level)) {
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
					return new ResponseEntity("", HttpStatus.NO_CONTENT, DATA_NOT_FOUND, ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set and Level both fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public com.incture.cherrywork.dtos.ResponseEntity saveOrUpdateSalesOrderTaskStatus(
			SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.incture.cherrywork.dtos.ResponseEntity listAllSalesOrderTaskStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.incture.cherrywork.dtos.ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.incture.cherrywork.dtos.ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.incture.cherrywork.dtos.ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet,
			String level) {
		// TODO Auto-generated method stub
		return null;
	}

}*/

