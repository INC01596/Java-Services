package com.incture.cherrywork.new_workflow.dao;




import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;


@Service
@Transactional
public class SalesOrderTaskStatusDaoImpl extends BaseDao<SalesOrderTaskStatusDo, SalesOrderTaskStatusDto>
		implements SalesOrderTaskStatusDao {

	// @Autowired
	// private SalesOrderItemStatusDaoImpl soItemStatusDao;

	@Autowired
	private SalesOrderLevelStatusDao soLevelStatusDao;

	@Autowired
	private SessionFactory sessionfactory;

	@Lazy
	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

	@Override
	public SalesOrderTaskStatusDo importDto(SalesOrderTaskStatusDto dto) {
		SalesOrderTaskStatusDo salesOrderTaskStatusDo = null;
		if (dto != null) {
			salesOrderTaskStatusDo = new SalesOrderTaskStatusDo();

			salesOrderTaskStatusDo.setApprover(dto.getApprover());
			salesOrderTaskStatusDo.setTaskId(dto.getTaskId());
			salesOrderTaskStatusDo.setTaskStatus(dto.getTaskStatus());
			salesOrderTaskStatusDo.setCompletedBy(dto.getCompletedBy());

			// Setting primary key
			if (dto.getTaskStatusSerialId() != null) {
				salesOrderTaskStatusDo.setTaskStatusSerialId(dto.getTaskStatusSerialId());
			}

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = soLevelStatusDao
					.getSalesOrderLevelStatusDoById(dto.getLevelStatusSerialId());

			if (salesOrderLevelStatusDo != null) {
				salesOrderTaskStatusDo.setSalesOrderLevelStatus(salesOrderLevelStatusDo);
			} else {
				return null;
			}
		}

		return salesOrderTaskStatusDo;
	}

	public SalesOrderTaskStatusDo importDtoSalesTask(SalesOrderTaskStatusDto dto) {
		SalesOrderTaskStatusDo salesOrderTaskStatusDo = null;
		if (dto != null) {
			salesOrderTaskStatusDo = new SalesOrderTaskStatusDo();

			salesOrderTaskStatusDo.setApprover(dto.getApprover());
			salesOrderTaskStatusDo.setTaskId(dto.getTaskId());
			salesOrderTaskStatusDo.setTaskStatus(dto.getTaskStatus());
			salesOrderTaskStatusDo.setCompletedBy(dto.getCompletedBy());

			// Setting primary key
			if (dto.getTaskStatusSerialId() != null) {
				salesOrderTaskStatusDo.setTaskStatusSerialId(dto.getTaskStatusSerialId());
			}

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = dto.getSalesOrderLevelStatus();
			// Field is mandatory as in foreign key here
			salesOrderLevelStatusDo.setLevelStatusSerialId(dto.getLevelStatusSerialId());
			if (salesOrderLevelStatusDo != null) {
				salesOrderTaskStatusDo.setSalesOrderLevelStatus(salesOrderLevelStatusDo);

			} else {
				return null;
			}

			// Converting list level to entity using import List method and
			// checking the content of it
			// if (dto.getItemStatusList() != null &&
			// !dto.getItemStatusList().isEmpty()) {
			// List<SalesOrderItemStatusDo> soItemStatusDoList =
			// soItemStatusDao.importList(dto.getItemStatusList());
			// if (soItemStatusDoList != null && !soItemStatusDoList.isEmpty())
			// {
			// salesOrderTaskStatusDo.setItemStatusList(soItemStatusDoList);
			// }
			// }
		}
		return salesOrderTaskStatusDo;
	}

	@Override
	public SalesOrderTaskStatusDto exportDto(SalesOrderTaskStatusDo entity) {
		SalesOrderTaskStatusDto salesOrderTaskStatusDto = null;
		if (entity != null) {
			salesOrderTaskStatusDto = new SalesOrderTaskStatusDto();

			salesOrderTaskStatusDto.setApprover(entity.getApprover());
			salesOrderTaskStatusDto.setTaskId(entity.getTaskId());
			salesOrderTaskStatusDto.setTaskStatus(entity.getTaskStatus());
			salesOrderTaskStatusDto.setCompletedBy(entity.getCompletedBy());

			// Setting primary key
			if (entity.getTaskStatusSerialId() != null) {
				salesOrderTaskStatusDto.setTaskStatusSerialId(entity.getTaskStatusSerialId());
			}

			// Field is mandatory as in foreign key here
			salesOrderTaskStatusDto.setLevelStatusSerialId(entity.getSalesOrderLevelStatus().getLevelStatusSerialId());

			// Converting list level to dto using export List method and
			// checking the content of it
			// List<SalesOrderItemStatusDto> soItemStatusDoList =
			// soItemStatusDao.exportList(entity.getItemStatusList());//
			// if (soItemStatusDoList != null && !soItemStatusDoList.isEmpty())
			// {
			// salesOrderTaskStatusDto.setItemStatusList(soItemStatusDoList);
			// }

		}
		return salesOrderTaskStatusDto;

	}

	@Override
	public List<SalesOrderTaskStatusDo> importList(List<SalesOrderTaskStatusDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderTaskStatusDo> doList = new ArrayList<>();
			for (SalesOrderTaskStatusDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<SalesOrderTaskStatusDto> exportList(List<SalesOrderTaskStatusDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderTaskStatusDto> dtoList = new ArrayList<>();
			for (SalesOrderTaskStatusDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesOrderTaskStatus(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = importDto(salesOrderTaskStatusDto);
			if (salesOrderTaskStatusDo != null) {
				getSession().merge(salesOrderTaskStatusDo);
				getSession().flush();
				getSession().clear();
				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String saveOrUpdateSalesOrderTaskStatusSynchronized(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = null;
			salesOrderTaskStatusDo = importDto(salesOrderTaskStatusDto);
			if (salesOrderTaskStatusDo != null) {
				Session session = sessionfactory.openSession();
				Transaction tx1 = session.beginTransaction();
				session.saveOrUpdate(salesOrderTaskStatusDo);
				tx1.commit();
				session.clear();
				session.close();

				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String saveOrUpdateSalesOrderTaskStatusUpdate(SalesOrderTaskStatusDto salesOrderTaskStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = null;
			salesOrderTaskStatusDo = importDto(salesOrderTaskStatusDto);
			if (salesOrderTaskStatusDo != null) {

				getSession().update(salesOrderTaskStatusDo);
				getSession().flush();
				getSession().clear();

				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesOrderTaskStatusDto> listAllSalesOrderTaskStatuses() {
		return exportList(getSession().createQuery("from SalesOrderTaskStatusDo", SalesOrderTaskStatusDo.class).list());
	}

	@Override
	public SalesOrderTaskStatusDto getSalesOrderTaskStatusById(String salesOrderTaskStatusId) {
		return exportDto(getSession().get(SalesOrderTaskStatusDo.class, salesOrderTaskStatusId));
	}

	@Override
	public SalesOrderTaskStatusDo getSalesOrderTaskStatusDoById(String salesOrderTaskStatusId) {

		return getSession().get(SalesOrderTaskStatusDo.class, salesOrderTaskStatusId);
	}

	@Override
	public String deleteSalesOrderTaskStatusById(String salesOrderTaskStatusId) throws ExecutionFault {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = getSession().byId(SalesOrderTaskStatusDo.class)
					.load(salesOrderTaskStatusId);
			if (salesOrderTaskStatusDo != null) {
				getSession().delete(salesOrderTaskStatusDo);
				return "Sales Order Task Status is completedly removed";
			} else {
				return "Sales Order Task Status is not found on Order id : " + salesOrderTaskStatusId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevel(String decisionSetId, String level) {

		return exportList(getSession()
				.createQuery(
						"select task from SalesOrderTaskStatusDo task join SalesOrderLevelStatusDo l on "
								+ "l.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).list());
	}

	@Override
	public SalesOrderTaskStatusDto getTasksFromDecisionSetAndLevel(String decisionSet, String level) {

		return exportDto(getSession()
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl",
						SalesOrderTaskStatusDo.class)
				.setParameter("decisionSet", decisionSet).setParameter("level", level).getSingleResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTasksIdFromDecisionSetAndLevel(String decisionSet) {
		return getSession()
				.createQuery(
						"select task.taskId from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :decisionSetId and task.salesOrderLevelStatus.levelStatus = :levelStatus")
				.setParameter("decisionSetId", decisionSet)
				.setParameter("levelStatus", StatusConstants.LEVEL_IN_PROGRESS).list();
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromSapTaskId(String taskId) {

		return exportList(getSession().createQuery("from SalesOrderTaskStatusDo task where task.taskId = :taskId",
				SalesOrderTaskStatusDo.class).setParameter("taskId", taskId).list());
	}

	@Override
	public List<SalesOrderTaskStatusDo> getAllTasksFromSapTaskIdDo(String taskId) {
		return getSession().createQuery("from SalesOrderTaskStatusDo task where task.taskId = :taskId",
				SalesOrderTaskStatusDo.class).setParameter("taskId", taskId).list();
	}

	@Override
	public SalesOrderTaskStatusDto getAllTasksFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportDto(getSession()
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
						SalesOrderTaskStatusDo.class)
				.setParameter("levelStatusSerialId", levelStatusSerialId).getSingleResult());
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksListFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportList(
				getSession()
						.createQuery(
								"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
								SalesOrderTaskStatusDo.class)
						.setParameter("levelStatusSerialId", levelStatusSerialId).list());
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSet(String decisionSetId) {

		return exportList((getSession()
				.createQuery("from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :dsId ",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).list()));
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevelAndItemNum(String decisionSetId,
			String level, String itemNum) {

		return exportList(getSession()
				.createQuery(
						"select task from SalesOrderTaskStatusDo task join SalesOrderItemStatusDo item on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).setParameter("itemNum", itemNum)
				.list());

	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTaskByTaskSerialId(List<String> taskSerialId) {

		return exportList(getSession()
				.createQuery("from SalesOrderTaskStatusDo task where task.taskStatusSerialId in (:taskStatusSerialId)",
						SalesOrderTaskStatusDo.class)
				.setParameterList("taskStatusSerialId", taskSerialId).list());

	}

	@Override
	public List<SalesOrderTaskStatusDto> getListOfAllTasksFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportList(
				getSession()
						.createQuery(
								"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
								SalesOrderTaskStatusDo.class)
						.setParameter("levelStatusSerialId", levelStatusSerialId).list());
	}

}

