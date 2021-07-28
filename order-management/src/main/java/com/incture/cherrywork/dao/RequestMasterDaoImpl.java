package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.entities.RequestMasterDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.workflow.repositories.IRequestMasterRepository;

@Service
@Transactional
public class RequestMasterDaoImpl implements RequestMasterDao {

	@Lazy
	@Autowired
	private CommentDao commentRepo;

	@Autowired
	private SessionFactory sessionfactory;

	@Autowired
	private IRequestMasterRepository requestMasterRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public RequestMasterDo importDto(RequestMasterDto fromDto) {
		RequestMasterDo reqMasterDo = null;
		if (fromDto != null) {
			reqMasterDo = new RequestMasterDo();

			reqMasterDo.setRefDocNum(fromDto.getRefDocNum());
			reqMasterDo.setReferenceDocType(fromDto.getReferenceDocType());
			reqMasterDo.setRequestCategory(fromDto.getRequestCategory());
			reqMasterDo.setRequestedBy(fromDto.getRequestedBy());
			reqMasterDo.setRequestShortText(fromDto.getRequestShortText());
			reqMasterDo.setRequestStatusCode(fromDto.getRequestStatusCode());
			reqMasterDo.setRequestType(fromDto.getRequestType());

			// Converting Date from String
			reqMasterDo.setRequestDate(fromDto.getRequestDate());

			// Setting Primary Key
			if (fromDto.getRequestId() != null) {
				reqMasterDo.setRequestId(fromDto.getRequestId());
			}
		}
		return reqMasterDo;
	}

	public RequestMasterDto exportDto(RequestMasterDo entity) {
		RequestMasterDto reqMasterDto = null;
		if (entity != null) {
			reqMasterDto = new RequestMasterDto();

			reqMasterDto.setRefDocNum(entity.getRefDocNum());
			reqMasterDto.setReferenceDocType(entity.getReferenceDocType());
			reqMasterDto.setRequestCategory(entity.getRequestCategory());
			reqMasterDto.setRequestedBy(entity.getRequestedBy());
			reqMasterDto.setRequestShortText(entity.getRequestShortText());
			reqMasterDto.setRequestStatusCode(entity.getRequestStatusCode());
			reqMasterDto.setRequestType(entity.getRequestType());

			// Converting String from Date
			reqMasterDto.setRequestDate(entity.getRequestDate());

			// Setting Primary Key
			if (entity.getRequestId() != null) {
				reqMasterDto.setRequestId(entity.getRequestId());
			}

			// Setting Comment List from comment table
			// if (entity.getRequestId() != null) {
			//
			// // Getting comment Data from the Primary Key of Request ID
			// reqMasterDto.setCommentsList(commentRepo.getCommentListByRefId(entity.getRequestId()));
			// }

		}
		return reqMasterDto;
	}

	public List<RequestMasterDo> importList(List<RequestMasterDto> list) {
		if (list != null && !list.isEmpty()) {
			List<RequestMasterDo> doList = new ArrayList<>();
			for (RequestMasterDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	public List<RequestMasterDto> exportList(List<RequestMasterDo> list) {
		if (list != null && !list.isEmpty()) {
			List<RequestMasterDto> dtoList = new ArrayList<>();
			for (RequestMasterDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateRequestMaster(RequestMasterDto reqMasterDto) throws ExecutionFault {
		try {

			RequestMasterDo requestDo = importDto(reqMasterDto);
			requestMasterRepository.save(requestDo);

			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RequestMasterDto> listAllRequests() {
		return exportList(requestMasterRepository.findAll());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RequestMasterDto> getRequestMasterById(String reqId) {

		String query = "from RequestMasterDo r where r.requestId = :reqId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("reqId", reqId);
		List<RequestMasterDo> list = q1.getResultList();
		if (list == null)
			return null;
		return ObjectMapperUtils.mapAll(list, RequestMasterDto.class);
	}

	// fetch single record based on the
	// refDocNumber/salesDocNumber/salesHeaderId
	@Override
	public RequestMasterDto getRequestMasterByRefDocNumber(String refDocNumebr) {
		String query = "from RequestMasterDo r where r.refDocNum = :refDocNum";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("refDocNum", refDocNumebr);
		return exportDto((RequestMasterDo) q1.getSingleResult());
	}

	@Override
	public String deleteRequestMasterById(String reqId) throws ExecutionFault {
		try {
			requestMasterRepository.deleteById(reqId);
			return "Request master is completedly removed";
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public String deleteRequestMasterByRefDocNum(String salesOrderNum) throws ExecutionFault {
		try {
			String query = "from RequestMasterDo r where r.refDocNum = :refDocNum";
			Query q1 = entityManager.createQuery(query);
			q1.setParameter("refDocNum", salesOrderNum);

			List<RequestMasterDo> reqMasterDo = q1.getResultList();
			if (reqMasterDo != null && !reqMasterDo.isEmpty()) {
				reqMasterDo.forEach(req -> requestMasterRepository.delete(req));
				return "Request master is completedly removed";
			} else {
				return "Request master is not found on Sales Order Num : " + salesOrderNum;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public String saveOrUpdateRequestMasterStatus(RequestMasterDto reqMasterDto) throws ExecutionFault {
		try {
			RequestMasterDo requestDo = importDto(reqMasterDto);
			requestMasterRepository.save(requestDo);
			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String UpdateRequestMaster(RequestMasterDto reqMasterDto) throws ExecutionFault {
		try {
			RequestMasterDo requestDo = importDto(reqMasterDto);
			requestMasterRepository.save(requestDo);
			// getSession().flush();
			// getSession().clear();

			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

}
