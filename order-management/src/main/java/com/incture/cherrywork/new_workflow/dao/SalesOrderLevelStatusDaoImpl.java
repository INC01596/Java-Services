package com.incture.cherrywork.new_workflow.dao;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Service
@Transactional
public class SalesOrderLevelStatusDaoImpl extends BaseDao<SalesOrderLevelStatusDo, SalesOrderLevelStatusDto>
		implements SalesOrderLevelStatusDao {

	@Autowired
	private SessionFactory sessionfactory;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public SalesOrderLevelStatusDo importDto(SalesOrderLevelStatusDto dto) {

		SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

		if (dto != null) {
			salesOrderLevelStatusDo = new SalesOrderLevelStatusDo();

			salesOrderLevelStatusDo.setApproverType(dto.getApproverType());
			salesOrderLevelStatusDo.setDecisionSetId(dto.getDecisionSetId());
			salesOrderLevelStatusDo.setLevel(dto.getLevel());
			salesOrderLevelStatusDo.setLevelStatus(dto.getLevelStatus());
			salesOrderLevelStatusDo.setUserGroup(dto.getUserGroup());

			// Setting Primary Key
			if (dto.getLevelStatusSerialId() != null) {
				salesOrderLevelStatusDo.setLevelStatusSerialId(dto.getLevelStatusSerialId());
			}

			// Converting list level to entity using import List method and
			// checking the content of it
			// if (dto.getTaskStatusList() != null &&
			// !dto.getTaskStatusList().isEmpty()) {
			// List<SalesOrderTaskStatusDo> soTaskStatusDoList =
			// soTaskStatusDao.importList(dto.getTaskStatusList());//
			// if (soTaskStatusDoList != null && !soTaskStatusDoList.isEmpty())
			// {
			// salesOrderLevelStatusDo.setTaskStatusList(soTaskStatusDoList);
			// }
			// }
		}

		return salesOrderLevelStatusDo;
	}

	
	public SalesOrderLevelStatusDto exportDto(SalesOrderLevelStatusDo entity) {
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = null;

		if (entity != null) {
			salesOrderLevelStatusDto = new SalesOrderLevelStatusDto();

			salesOrderLevelStatusDto.setApproverType(entity.getApproverType());
			salesOrderLevelStatusDto.setDecisionSetId(entity.getDecisionSetId());
			salesOrderLevelStatusDto.setLevel(entity.getLevel());
			salesOrderLevelStatusDto.setLevelStatus(entity.getLevelStatus());
			salesOrderLevelStatusDto.setUserGroup(entity.getUserGroup());

			// Setting Primary Key
			if (entity.getLevelStatusSerialId() != null) {
				salesOrderLevelStatusDto.setLevelStatusSerialId(entity.getLevelStatusSerialId());
			}

			// Converting list level to dto using export List method and
			// checking the content of it
			// if (entity.getTaskStatusList() != null &&
			// !entity.getTaskStatusList().isEmpty()) {
			// List<SalesOrderTaskStatusDto> soTaskStatusDtoList =
			// soTaskStatusDao.exportList(entity.getTaskStatusList());//
			// if (soTaskStatusDtoList != null &&
			// !soTaskStatusDtoList.isEmpty()) {
			// salesOrderLevelStatusDto.setTaskStatusList(soTaskStatusDtoList);
			// }
			// }
		}

		return salesOrderLevelStatusDto;
	}

	
	public List<SalesOrderLevelStatusDo> importList(List<SalesOrderLevelStatusDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderLevelStatusDo> doList = new ArrayList<>();
			for (SalesOrderLevelStatusDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	
	public List<SalesOrderLevelStatusDto> exportList(List<SalesOrderLevelStatusDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderLevelStatusDto> dtoList = new ArrayList<>();
			for (SalesOrderLevelStatusDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderLevelStatusDo salesOrderLevelStatusDo = importDto(salesOrderLevelStatusDto);
			getSession().merge(salesOrderLevelStatusDo);
			getSession().flush();
			getSession().clear();

			return salesOrderLevelStatusDo.getLevelStatusSerialId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	// changed to using current session
	@Override
	public String saveOrUpdateSalesOrderLevelStatusSynchronized(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		try {

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

			salesOrderLevelStatusDo = importDto(salesOrderLevelStatusDto);
			if (salesOrderLevelStatusDo != null) {
				Session session = sessionfactory.openSession();
				Transaction tx1 = session.beginTransaction();
				session.saveOrUpdate(salesOrderLevelStatusDo);
				tx1.commit();
				session.clear();
				session.close();
				return salesOrderLevelStatusDo.getLevelStatusSerialId();
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	@Override
	public String saveOrUpdateSalesOrderLevelStatusSave(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		try {

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = null;

			salesOrderLevelStatusDo = importDto(salesOrderLevelStatusDto);
			if (salesOrderLevelStatusDo != null) {
				getSession().save(salesOrderLevelStatusDo);
				getSession().flush();
				getSession().clear();

				return salesOrderLevelStatusDo.getLevelStatusSerialId();
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	@Override
	public String saveOrUpdateSalesOrderLevelStatusSynchronizedDo(SalesOrderLevelStatusDo salesOrderLevelStatusDo)
			throws ExecutionFault {

		try {

			if (salesOrderLevelStatusDo != null) {

				getSession().saveOrUpdate(salesOrderLevelStatusDo);
				getSession().flush();
				getSession().clear();

				return salesOrderLevelStatusDo.getLevelStatusSerialId();
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
		return null;

	}

	@Override
	public List<SalesOrderLevelStatusDto> listAllSalesOrderLevelStatuses() {
		return exportList(
				getSession().createQuery("from SalesOrderLevelStatusDo", SalesOrderLevelStatusDo.class).list());
	}

	@Override
	public SalesOrderLevelStatusDto getSalesOrderLevelStatusById(String salesOrderLevelStatusId) {
		return exportDto(getSession().get(SalesOrderLevelStatusDo.class, salesOrderLevelStatusId));
	}

	@Override
	public SalesOrderLevelStatusDo getSalesOrderLevelStatusDoById(String salesOrderLevelStatusId) {

		return getSession().get(SalesOrderLevelStatusDo.class, salesOrderLevelStatusId);
	}

	@Override
	public String deleteSalesOrderLevelStatusById(String salesOrderLevelStatusId) throws ExecutionFault {
		try {
			SalesOrderLevelStatusDo salesOrderLevelStatusDo = getSession().byId(SalesOrderLevelStatusDo.class)
					.load(salesOrderLevelStatusId);
			if (salesOrderLevelStatusDo != null) {
				getSession().delete(salesOrderLevelStatusDo);
				return "Sales Order Level Status is completedly removed";
			} else {
				return "Sales Order Level Status is not found on Order id : " + salesOrderLevelStatusId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public String persistSalesOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderLevelStatusDo salesOrderLevelStatusDo = importDto(salesOrderLevelStatusDto);
			getSession().merge(salesOrderLevelStatusDo);
			getSession().flush();
			getSession().clear();

			return salesOrderLevelStatusDo.getLevelStatusSerialId();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSetId, String level) {
		System.err.println("decisiion = " + decisionSetId + level);
		return exportDto(getSession()
				.createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getSingleResult());
	}

	@Override
	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevelSession(String decisionSetId,
			String level) {
		System.err.println("decisiion = " + decisionSetId + level);
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = exportDto(getSession()
				.createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getSingleResult());

		transaction.commit();
		session.close();

		return salesOrderLevelStatusDto;
	}

	@Override
	public SalesOrderLevelStatusDo getSalesOrderLevelStatusByDecisionSetAndLevelDo(String decisionSetId, String level) {
		System.err.println("decisiion = " + decisionSetId + level);

		return getSession()
				.createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getSingleResult();
	}

	@Override
	public String saveSalerOrderLevelStatus(SalesOrderLevelStatusDto salesOrderLevelStatusDto) {

		SalesOrderLevelStatusDo salesOrderLevelStatusDo = new SalesOrderLevelStatusDo();

		salesOrderLevelStatusDo.setApproverType(salesOrderLevelStatusDto.getApproverType());

		salesOrderLevelStatusDo.setDecisionSetId(salesOrderLevelStatusDto.getDecisionSetId());

		salesOrderLevelStatusDo.setLevel(salesOrderLevelStatusDto.getLevel());

		salesOrderLevelStatusDo.setUserGroup(salesOrderLevelStatusDto.getUserGroup());

		salesOrderLevelStatusDo.setLevelStatus(salesOrderLevelStatusDto.getLevelStatus());

		Session session = sessionfactory.openSession();

		session.save(salesOrderLevelStatusDo);

		session.close();

		return salesOrderLevelStatusDo.getLevelStatusSerialId();

	}

	@Override
	public List<SalesOrderLevelStatusDto> getSalesOrderLevelStatusByDecisionSet(String decisionSetId) {

		return exportList(getSession().createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId",
				SalesOrderLevelStatusDo.class).setParameter("dsId", decisionSetId).list());
	}

	@Override
	public List<SalesOrderLevelStatusDto> getSalesOrderLevelStatusByDecisionSetWithLevelInProgress(
			String decisionSetId) {

		return exportList(getSession().createQuery(
				"from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.levelStatus not in ('"
						+StatusConstants.LEVEL_NEW + "')",
				SalesOrderLevelStatusDo.class).setParameter("dsId", decisionSetId).list());
	}

	@Override
	public List<SalesOrderLevelStatusDto> getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(String decisionSetId,
			String level) {

		return exportList(getSession()
				.createQuery(
						"from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level != :level and l.levelStatus = :levelStatus",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("level", level)
				.setParameter("levelStatus", StatusConstants.LEVEL_NEW).list());
	}

	@Override
	public List<SalesOrderLevelStatusDto> getAllTheUpcomingLevelStatusesForPerticularDecisionSet(String decisionSet,
			List<String> pastLevelList) {

		StringBuilder queryForExcludeLevel = new StringBuilder();
		int count = 0;
		for (String levelData : pastLevelList) {
			count++;
			queryForExcludeLevel.append("'" + levelData + "'");
			if (count != pastLevelList.size()) {
				queryForExcludeLevel.append(",");
			}

		}
		return exportList(getSession().createQuery(
				"from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.levelStatus = '"
						+ StatusConstants.LEVEL_IN_PROGRESS + "' and l.level NOT IN (" + queryForExcludeLevel + ")",
				SalesOrderLevelStatusDo.class).setParameter("dsId", decisionSet).list());
	}

	@Override
	public Set<Integer> getLevelStatusIdByDS(List<String> decisionSetIDs) {
		Set<Integer> levelStatusIdList = new HashSet<>();
		try {
			for (int i = 0; i < decisionSetIDs.size(); i++) {
				String decisionSetId = decisionSetIDs.get(i);
				List<Integer> soLevelDoList = getSession().createQuery(
						"select distinct l.levelStatus from SalesOrderLevelStatusDo l where l.decisionSetId =:decisionSetId",
						Integer.class).setParameter("decisionSetId", decisionSetId).list();
				levelStatusIdList.addAll(soLevelDoList);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return levelStatusIdList;

	}
}

