package com.incture.cherrywork.new_workflow.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
import com.incture.cherrywork.workflow.repositories.ISalesOrderTaskStatusRepository;

@Service
@Transactional
public class SalesOrderTaskStatusDaoImpl implements SalesOrderTaskStatusDao {

	// @Autowired
	// private SalesOrderItemStatusDaoImpl soItemStatusDao;

	@Autowired
	private SalesOrderLevelStatusDao soLevelStatusDao;

	@Autowired
	private ISalesOrderTaskStatusRepository salesOrderTaskStatusRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Lazy
	@Autowired
	private SalesOrderItemStatusDao soItemStatusRepo;

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
				salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);

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

				salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);

				return salesOrderTaskStatusDo.getTaskStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException | IllegalStateException e) {
			System.err.println("Excepton while saving sales order task status: "+e.getMessage());
			throw e;
			// throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			System.err.println("Excepton while saving sales order task status: "+e.getMessage());
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

				salesOrderTaskStatusRepository.save(salesOrderTaskStatusDo);

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
		return exportList(
				entityManager.createQuery("from SalesOrderTaskStatusDo", SalesOrderTaskStatusDo.class).getResultList());
	}

	@Override
	public SalesOrderTaskStatusDto getSalesOrderTaskStatusById(String salesOrderTaskStatusId) {
		return exportDto(salesOrderTaskStatusRepository.getOne(salesOrderTaskStatusId));
	}

	@Override
	public SalesOrderTaskStatusDo getSalesOrderTaskStatusDoById(String salesOrderTaskStatusId) {

		return salesOrderTaskStatusRepository.findById(salesOrderTaskStatusId).get();
	}

	@Override
	public String deleteSalesOrderTaskStatusById(String salesOrderTaskStatusId) throws ExecutionFault {
		try {
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = salesOrderTaskStatusRepository
					.getOne(salesOrderTaskStatusId);
			if (salesOrderTaskStatusDo != null) {
				salesOrderTaskStatusRepository.delete(salesOrderTaskStatusDo);
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

		return exportList(entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task join SalesOrderLevelStatusDo l on "
								+ "l.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getResultList());
	}

	@Override
	public SalesOrderTaskStatusDto getTasksFromDecisionSetAndLevel(String decisionSet, String level) {

		return exportDto(entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl",
						SalesOrderTaskStatusDo.class)
				.setParameter("decisionSet", decisionSet).setParameter("level", level).getSingleResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTasksIdFromDecisionSetAndLevel(String decisionSet) {
		return entityManager
				.createQuery(
						"select task.taskId from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :decisionSetId and task.salesOrderLevelStatus.levelStatus = :levelStatus")
				.setParameter("decisionSetId", decisionSet)
				.setParameter("levelStatus", StatusConstants.LEVEL_IN_PROGRESS).getResultList();
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromSapTaskId(String taskId) {

		return exportList(entityManager.createQuery("from SalesOrderTaskStatusDo task where task.taskId = :taskId",
				SalesOrderTaskStatusDo.class).setParameter("taskId", taskId).getResultList());
	}

	@Override
	public List<SalesOrderTaskStatusDo> getAllTasksFromSapTaskIdDo(String taskId) {
		return entityManager.createQuery("from SalesOrderTaskStatusDo task where task.taskId = :taskId",
				SalesOrderTaskStatusDo.class).setParameter("taskId", taskId).getResultList();
	}

	@Override
	public SalesOrderTaskStatusDto getAllTasksFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportDto((SalesOrderTaskStatusDo)entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
						SalesOrderTaskStatusDo.class)
				.setParameter("levelStatusSerialId", levelStatusSerialId).getSingleResult());
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksListFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportList(entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
						SalesOrderTaskStatusDo.class)
				.setParameter("levelStatusSerialId", levelStatusSerialId).getResultList());
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSet(String decisionSetId) {

		return exportList((entityManager
				.createQuery("from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :dsId ",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).getResultList()));
	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevelAndItemNum(String decisionSetId,
			String level, String itemNum) {

		return exportList(entityManager
				.createQuery(
						"select task from SalesOrderTaskStatusDo task join SalesOrderItemStatusDo item on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderTaskStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).setParameter("itemNum", itemNum)
				.getResultList());

	}

	@Override
	public List<SalesOrderTaskStatusDto> getAllTaskByTaskSerialId(List<String> taskSerialId) {

		return exportList(entityManager
				.createQuery("from SalesOrderTaskStatusDo task where task.taskStatusSerialId in (:taskStatusSerialId)",
						SalesOrderTaskStatusDo.class)
				.setParameter("taskStatusSerialId", taskSerialId).getResultList());

	}

	@Override
	public List<SalesOrderTaskStatusDto> getListOfAllTasksFromLevelStatusSerialId(String levelStatusSerialId) {

		return exportList(entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
						SalesOrderTaskStatusDo.class)
				.setParameter("levelStatusSerialId", levelStatusSerialId).getResultList());
	}

}
