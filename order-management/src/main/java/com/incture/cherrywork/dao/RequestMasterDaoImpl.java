package com.incture.cherrywork.dao;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.entities.RequestMasterDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Repository
@Component
public class RequestMasterDaoImpl extends BaseDao<RequestMasterDo, RequestMasterDto> implements RequestMasterDao {
    
	 @Lazy
	 @Autowired
	 private CommentDao commentRepo;

	@Autowired
	private SessionFactory sessionfactory;

	@Override
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

	@Override
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

	@Override
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

	@Override
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
		try (Session session = sessionfactory.openSession()) {
			Transaction tx = session.beginTransaction();

			RequestMasterDo requestDo = importDto(reqMasterDto);
			getSession().saveOrUpdate(requestDo);
			// getSession().flush();
			// getSession().clear();
			tx.commit();
			session.close();

			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RequestMasterDto> listAllRequests() {
		return exportList(getSession().createQuery("from RequestMasterDo", RequestMasterDo.class).list());
	}

	@Override
	public List<RequestMasterDto> getRequestMasterById(String reqId) {
		return exportList(
				getSession().createQuery("from RequestMasterDo r where r.requestId = :reqId", RequestMasterDo.class)
						.setParameter("reqId", reqId).getResultList());
	}

	// fetch single record based on the
	// refDocNumber/salesDocNumber/salesHeaderId
	@Override
	public RequestMasterDto getRequestMasterByRefDocNumber(String refDocNumebr) {
		return exportDto(
				getSession().createQuery("from RequestMasterDo r where r.refDocNum = :refDocNum", RequestMasterDo.class)
						.setParameter("refDocNum", refDocNumebr).getSingleResult());
	}

	@Override
	public String deleteRequestMasterById(String reqId) throws ExecutionFault {
		try {
			RequestMasterDo reqMasterDo = getSession().byId(RequestMasterDo.class).load(reqId);
			if (reqMasterDo != null) {
				getSession().delete(reqMasterDo);
				return "Request master is completedly removed";
			} else {
				return "Request master is not found on Request id : " + reqId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public String deleteRequestMasterByRefDocNum(String salesOrderNum) throws ExecutionFault {
		try {
			List<RequestMasterDo> reqMasterDo = getSession()
					.createQuery("from RequestMasterDo r where r.refDocNum = :refDocNum", RequestMasterDo.class)
					.setParameter("refDocNum", salesOrderNum).getResultList();
			if (reqMasterDo != null && !reqMasterDo.isEmpty()) {
				reqMasterDo.forEach(req -> getSession().delete(req));
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
			getSession().merge(requestDo);
			getSession().flush();
			getSession().clear();
			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String UpdateRequestMaster(RequestMasterDto reqMasterDto) throws ExecutionFault {
		try (Session session = sessionfactory.openSession()) {
			Transaction tx = session.beginTransaction();

			RequestMasterDo requestDo = importDto(reqMasterDto);
			getSession().saveOrUpdate(requestDo);
			// getSession().flush();
			// getSession().clear();
			tx.commit();
			session.close();

			return requestDo.getRequestId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

}
