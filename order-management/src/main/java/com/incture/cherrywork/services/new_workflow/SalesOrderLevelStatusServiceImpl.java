package com.incture.cherrywork.services.new_workflow;



import java.util.List;


import static com.incture.cherrywork.WConstants.Constants.CREATION_FAILED;
import static com.incture.cherrywork.WConstants.Constants.DATA_FOUND;
import static com.incture.cherrywork.WConstants.Constants.EMPTY_LIST;
import static com.incture.cherrywork.WConstants.Constants.EXCEPTION_FAILED;
import static com.incture.cherrywork.WConstants.Constants.INVALID_INPUT;
import static com.incture.cherrywork.WConstants.StatusConstants.LEVEL_COMPLETE;
import static com.incture.cherrywork.WConstants.StatusConstants.LEVEL_IN_PROGRESS;
import static com.incture.cherrywork.WConstants.StatusConstants.TASK_COMPLETE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.SalesOrderLevelStatusDao;
import com.incture.cherrywork.dao.SalesOrderTaskStatusDao;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class SalesOrderLevelStatusServiceImpl implements SalesOrderLevelStatusService {

	@Autowired
	private SalesOrderLevelStatusDao soLevelStatusRepo;

	@Autowired
	private SalesOrderTaskStatusDao soTaskStatusRepo;

	@Override
	public ResponseEntity saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto) {
		try {
			if (salesOrderLevelStatusDto != null) {
				String msg = soLevelStatusRepo.saveOrUpdateSalesOrderLevelStatus(salesOrderLevelStatusDto);
				

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesOrderLevelStatusDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "INVALID_INPUT", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrderLevelStatuses() {
		try {
			List<SalesOrderLevelStatusDto> list = soLevelStatusRepo.listAllSalesOrderLevelStatuses();
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
	public ResponseEntity deleteSalesOrderLevelStatusById(String salesOrderLevelStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderLevelStatusId)) {
				String msg = soLevelStatusRepo.deleteSalesOrderLevelStatusById(salesOrderLevelStatusId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Level Status Id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesOrderLevelStatusById(String salesOrderLevelStatusId) {
		try {
			if (!HelperClass.checkString(salesOrderLevelStatusId)) {
				SalesOrderLevelStatusDto salesOrderLevelStatusDto = soLevelStatusRepo
						.getSalesOrderLevelStatusById(salesOrderLevelStatusId);
				if (salesOrderLevelStatusDto != null) {
					return new ResponseEntity(salesOrderLevelStatusDto, HttpStatus.ACCEPTED,
							"Sales Order Level Status is found for Sales Order Level Status id : "
									+ salesOrderLevelStatusId,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Level Status is not available for Sales Order Level Status id : "
									+ salesOrderLevelStatusId,
							ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Level Status Id field is mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity checkLevelStatus(String decisionSetId, String level) {
		try {
			if (!HelperClass.checkString(decisionSetId) && !HelperClass.checkString(level)) {
				// Fetch tasks from decision set id and level
				List<SalesOrderTaskStatusDto> soTaskStatusDtoList = soTaskStatusRepo
						.getAllTasksFromDecisionSetAndLevel(decisionSetId, level);

				if (!soTaskStatusDtoList.isEmpty()) {
					// Getting the level status serial id for first task
					String levelStatusSerialId = soTaskStatusDtoList.get(0).getLevelStatusSerialId();

					// Checking task status for each entity
					int countFlag = 0;
					for (SalesOrderTaskStatusDto taskDto : soTaskStatusDtoList) {
						// Checking all task for same level status serial id
						if (levelStatusSerialId.equals(taskDto.getLevelStatusSerialId())
								&& taskDto.getTaskStatus() == TASK_COMPLETE) {
							countFlag++;
						}
					}

					// Get the level entity from serial id of level
					SalesOrderLevelStatusDto soLevelStatusDto = soLevelStatusRepo
							.getSalesOrderLevelStatusById(levelStatusSerialId);

					// Checking each task status flag if all tasks have
					// completed then Level is completed
					if (countFlag == soTaskStatusDtoList.size()) {
						// Setting status of level here
						soLevelStatusDto.setLevelStatus(LEVEL_COMPLETE);
						// Updating the DB for level status acc to task status
						soLevelStatusRepo.saveOrUpdateSalesOrderLevelStatus(soLevelStatusDto);
						return new ResponseEntity(soTaskStatusDtoList, HttpStatus.OK, LEVEL_COMPLETE.toString(),
								ResponseStatus.SUCCESS);
					} else {
						// Setting status of level here
						soLevelStatusDto.setLevelStatus(LEVEL_IN_PROGRESS);
						// Updating the DB for level status acc to task status
						soLevelStatusRepo.saveOrUpdateSalesOrderLevelStatus(soLevelStatusDto);
						return new ResponseEntity(soTaskStatusDtoList, HttpStatus.OK, LEVEL_IN_PROGRESS.toString(),
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Task Status is not available for Decision Set Id : " + decisionSetId
									+ " and  level : " + level,
							ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Decision set id and Level fields are mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}

	}


@Override
	public ResponseEntity getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSet, String level) {
		try {
			if (!HelperClass.checkString(decisionSet) && !HelperClass.checkString(level)) {
				SalesOrderLevelStatusDto salesOrderLevelStatusDto = soLevelStatusRepo
						.getSalesOrderLevelStatusByDecisionSetAndLevel(decisionSet, level);
				if (salesOrderLevelStatusDto != null) {
					return new ResponseEntity(salesOrderLevelStatusDto, HttpStatus.ACCEPTED,
							"Sales Order Level Status is found for Sales Order Level Status id : " + decisionSet
									+ " and " + level,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Sales Order Level Status is not available for Sales Order Level Status id : " + decisionSet
									+ " and " + level,
							ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Sales Order Level Status Id field is mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	} 

}
