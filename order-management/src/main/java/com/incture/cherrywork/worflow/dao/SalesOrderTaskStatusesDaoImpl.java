package com.incture.cherrywork.worflow.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusPrimaryKey;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusesDo;
import com.incture.cherrywork.workflow.repositories.ISalesOrderTaskStatusRepository;
import com.incture.cherrywork.workflow.repositories.ISalesOrderTaskStatusesRepository;

@Service
@Transactional
public class SalesOrderTaskStatusesDaoImpl implements SalesOrderTaskStatusesDao {

	@Autowired
	private ISalesOrderTaskStatusesRepository salesOrderTaskStatusesRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public SalesOrderTaskStatusesDo importDto(SalesOrderTaskStatusesDto dto) {
		SalesOrderTaskStatusesDo salesOrderTaskStatusDo = null;
		if (dto != null) {
			salesOrderTaskStatusDo = new SalesOrderTaskStatusesDo();

			salesOrderTaskStatusDo.setTaskStatus(dto.getTaskStatus());
			salesOrderTaskStatusDo.setSalesOrderNum(dto.getSalesOrderNum());

			// Setting Primary Key
			salesOrderTaskStatusDo.setKey(new SalesOrderTaskStatusPrimaryKey(dto.getRequestId(), dto.getDecisionSetId(),
					dto.getLevel(), dto.getUserGroup()));
		}
		return salesOrderTaskStatusDo;
	}

	public SalesOrderTaskStatusesDto exportDto(SalesOrderTaskStatusesDo entity) {
		SalesOrderTaskStatusesDto salesOrderTaskStatusDto = null;
		if (entity != null) {
			salesOrderTaskStatusDto = new SalesOrderTaskStatusesDto();

			salesOrderTaskStatusDto.setTaskStatus(entity.getTaskStatus());
			salesOrderTaskStatusDto.setSalesOrderNum(entity.getSalesOrderNum());

			// Setting Primary Key
			salesOrderTaskStatusDto.setDecisionSetId(entity.getKey().getDecisionSetId());
			salesOrderTaskStatusDto.setLevel(entity.getKey().getLevel());
			salesOrderTaskStatusDto.setRequestId(entity.getKey().getRequestId());
			salesOrderTaskStatusDto.setUserGroup(entity.getKey().getUserGroup());
		}
		return salesOrderTaskStatusDto;
	}

	public List<SalesOrderTaskStatusesDo> importList(List<SalesOrderTaskStatusesDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderTaskStatusesDo> dtoList = new ArrayList<>();
			for (SalesOrderTaskStatusesDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	public List<SalesOrderTaskStatusesDto> exportList(List<SalesOrderTaskStatusesDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderTaskStatusesDto> dtoList = new ArrayList<>();
			for (SalesOrderTaskStatusesDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusesDto salesOrderTaskStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderTaskStatusesDo salesOrderTaskStatusDo = importDto(salesOrderTaskStatusDto);
			salesOrderTaskStatusesRepository.save(salesOrderTaskStatusDo);
			
			return "Sales Order Task Status is successfully created with key : " + salesOrderTaskStatusDo.getKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<SalesOrderTaskStatusesDto> listAllSalesOrderTaskStatus() {
		return exportList(
				salesOrderTaskStatusesRepository.findAll());
	}

	@Override
	public SalesOrderTaskStatusesDto getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		String query = "from SalesOrderTaskStatusesDo where key.requestId=:reqId and key.decisionSetId=:dId and key.userGroup=:ugroup";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("reqId", key.getRequestId());
		q1.setParameter("dId", key.getDecisionSetId());
		q1.setParameter("ugroup", key.getUserGroup());
		return exportDto((SalesOrderTaskStatusesDo)q1.getSingleResult());
	}

	@Override
	public String deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) throws ExecutionFault {
		try {
			String query = "from SalesOrderTaskStatusesDo where key.requestId=:reqId and key.decisionSetId=:dId and key.userGroup=:ugroup";
			Query q1 = entityManager.createQuery(query);
			q1.setParameter("reqId", key.getRequestId());
			q1.setParameter("dId", key.getDecisionSetId());
			q1.setParameter("ugroup", key.getUserGroup());
			SalesOrderTaskStatusesDo salesOrderTaskStatusDo = (SalesOrderTaskStatusesDo)q1.getSingleResult();
			if (salesOrderTaskStatusDo != null) {
				salesOrderTaskStatusesRepository.delete(salesOrderTaskStatusDo);
				return "Sales Order Task Status is completedly removed";
			} else {
				return "Sales Order Task Status is not found on Key : " + key;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<SalesOrderTaskStatusesDto> getSalesOrderTaskStatusAccToDsAndLevel(String decisionSet, String level) {
		return exportList(entityManager
				.createQuery("from SalesOrderTaskStatusDo s where s.key.decisionSetId = :dsId and s.key.level = :level",
						SalesOrderTaskStatusesDo.class)
				.setParameter("dsId", decisionSet).setParameter("level", level).getResultList());
	}

}
