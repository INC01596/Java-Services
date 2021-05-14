package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusKeyDto;
import com.incture.cherrywork.dto.workflow.SalesOrderTaskStatusesDto;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusPrimaryKey;
import com.incture.cherrywork.entities.workflow.SalesOrderTaskStatusesDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Repository
@Component
public class SalesOrderTaskStatusesDaoImpl extends BaseDao<SalesOrderTaskStatusesDo, SalesOrderTaskStatusesDto>
		implements SalesOrderTaskStatusesDao {

	@Override
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

	@Override
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

	@Override
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

	@Override
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
			getSession().saveOrUpdate(salesOrderTaskStatusDo);
			getSession().flush();
			getSession().clear();
			return "Sales Order Task Status is successfully created with key : " + salesOrderTaskStatusDo.getKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<SalesOrderTaskStatusesDto> listAllSalesOrderTaskStatus() {
		return exportList(getSession().createQuery("from SalesOrderTaskStatusDo", SalesOrderTaskStatusesDo.class).list());
	}

	@Override
	public SalesOrderTaskStatusesDto getSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) {
		return exportDto(getSession().get(SalesOrderTaskStatusesDo.class, new SalesOrderTaskStatusPrimaryKey(
				key.getRequestId(), key.getDecisionSetId(), key.getLevel(), key.getUserGroup())));
	}

	@Override
	public String deleteSalesOrderTaskStatusById(SalesOrderTaskStatusKeyDto key) throws ExecutionFault {
		try {
			SalesOrderTaskStatusesDo salesOrderTaskStatusDo = getSession().byId(SalesOrderTaskStatusesDo.class)
					.load(new SalesOrderTaskStatusPrimaryKey(key.getRequestId(), key.getDecisionSetId(), key.getLevel(),
							key.getUserGroup()));
			if (salesOrderTaskStatusDo != null) {
				getSession().delete(salesOrderTaskStatusDo);
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
		return exportList(getSession()
				.createQuery("from SalesOrderTaskStatusDo s where s.key.decisionSetId = :dsId and s.key.level = :level",
						SalesOrderTaskStatusesDo.class)
				.setParameter("dsId", decisionSet).setParameter("level", level).list());
	}

}
