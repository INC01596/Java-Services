package com.incture.cherrywork.workflow.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusPrimaryKey;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.worflow.dao.SalesOrderTaskStatusesDao;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusesDo;
import com.incture.cherrywork.workflow.repositories.ISalesOrderTaskStatusRepository;

@Service
@Transactional
public class SalesOrderTaskStatusesServiceImpl implements SalesOrderTaskStatusesService {

	@Autowired
	private ISalesOrderTaskStatusRepository salesOrderTaskStatusRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseEntity saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		try {
			// Checking Primary Key here
			if (!HelperClass.checkString(salesOrderTaskStatusDto.getDecisionSetId())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getUserGroup())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getLevel())
					&& !HelperClass.checkString(salesOrderTaskStatusDto.getRequestId())) {

				String msg = saveOrUpdateSalesOrderTaskStatus1(salesOrderTaskStatusDto);

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
				}
				return new ResponseEntity(salesOrderTaskStatusDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Decision Set, User Group, Level, Request Id field are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesOrderTaskStatus() {
		try {
			List<SalesOrderTaskStatusesDto> list = listAllSalesOrderTaskStatus1();
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
	public List<SalesOrderTaskStatusesDto> listAllSalesOrderTaskStatus1() {
		String query = "from SalesOrderTaskStatusDo";
		Query q1 = entityManager.createQuery(query);
		return q1.getResultList();

	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!HelperClass.checkString(key.getDecisionSetId()) && !HelperClass.checkString(key.getLevel())
					&& !HelperClass.checkString(key.getRequestId()) && !HelperClass.checkString(key.getUserGroup())) {
				SalesOrderTaskStatusesDto salesOrderTaskStatusDto = getSalesOrderTaskStatusById1(key);
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
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public SalesOrderTaskStatusesDto getSalesOrderTaskStatusById1(SalesOrderTaskStatusKeyDto key) {
		String query = "from SalesOrderTaskStatusesDo where requestId=:reqId and decisionSetId=:dId and level=:level and userGroup=:ugroup";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("reqId", key.getRequestId());
		q1.setParameter("dId", key.getDecisionSetId());
		q1.setParameter("level", key.getLevel());
		q1.setParameter("ugroup", key.getUserGroup());
		return ObjectMapperUtils.map(q1.getSingleResult(), SalesOrderTaskStatusesDto.class);

	}

	@Override
	public ResponseEntity deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		try {
			if (!HelperClass.checkString(key.getDecisionSetId()) && !HelperClass.checkString(key.getLevel())
					&& !HelperClass.checkString(key.getRequestId()) && !HelperClass.checkString(key.getUserGroup())) {
				String msg = deleteSalesOrderTaskStatusById1(key);
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
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	public String deleteSalesOrderTaskStatusById1(SalesOrderTaskStatusKeyDto key) throws ExecutionFault {
		try {

			String query = "from SalesOrderTaskStatusesDo where requestId=:reqId and decisionSetId=:dId and level=:level and userGroup=:ugroup";
			Query q1 = entityManager.createQuery(query);
			q1.setParameter("reqId", key.getRequestId());
			q1.setParameter("dId", key.getDecisionSetId());
			q1.setParameter("level", key.getLevel());
			q1.setParameter("ugroup", key.getUserGroup());
			SalesOrderTaskStatusesDo salesOrderTaskStatusDo = (SalesOrderTaskStatusesDo) q1.getSingleResult();
			if (salesOrderTaskStatusDo != null) {
				salesOrderTaskStatusRepository.delete(salesOrderTaskStatusDo);
				return "Sales Order Task Status is completedly removed";
			} else {
				return "Sales Order Task Status is not found on Key : " + key;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();

		}
	}

	@Override
	public ResponseEntity getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level) {
		try {
			if (!HelperClass.checkString(decisionSet) && !HelperClass.checkString(level)) {
				List<SalesOrderTaskStatusesDto> list = getSalesOrderTaskStatusAccToDsAndLevel1(decisionSet, level);
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
			// HelperClass.getLogger(this.getClass().getName()).info(e + " on "
			// + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	public List<SalesOrderTaskStatusesDto> getSalesOrderTaskStatusAccToDsAndLevel1(String decisionSet, String level) {

		String query = "from SalesOrderTaskStatusDo s where s.key.decisionSetId = :dsId and s.key.level = :level";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSet);
		q1.setParameter("level", level);
		SalesOrderTaskStatusesDo s = null;
		return ObjectMapperUtils.mapAll(q1.getResultList(), SalesOrderTaskStatusesDto.class);

	}

	@Override
	public String saveOrUpdateSalesOrderTaskStatus1(SalesOrderTaskStatusesDto salesOrderTaskStatusDto) {
		try {
			SalesOrderTaskStatusesDo salesOrderTaskStatusDo = ObjectMapperUtils.map(salesOrderTaskStatusDto,
					SalesOrderTaskStatusesDo.class);

			salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);

			return "Sales Order Task Status is successfully created with key : " + salesOrderTaskStatusDo.getKey();
		} catch (NoResultException | NullPointerException e) {
			e.getStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
