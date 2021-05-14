package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.TaskItemDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Repository
@Component
public class SalesOrderItemStatusDaoImpl extends BaseDao<SalesOrderItemStatusDo, SalesOrderItemStatusDto>
		implements SalesOrderItemStatusDao {

	@Autowired
	private SalesOrderTaskStatusDaoImpl soTaskStatusRepo;

	@Autowired
	private SalesOrderLevelStatusDaoImpl soLevelstatusDao;

	@Autowired
	private SessionFactory sessionfactory;

	
	public SalesOrderItemStatusDo importDto(SalesOrderItemStatusDto dto) {
		SalesOrderItemStatusDo salesOrderItemStatusDo = null;
		if (dto != null) {
			salesOrderItemStatusDo = new SalesOrderItemStatusDo();

			salesOrderItemStatusDo.setItemStatus(dto.getItemStatus());
			salesOrderItemStatusDo.setSalesOrderItemNum(dto.getSalesOrderItemNum());
			salesOrderItemStatusDo.setVisiblity(dto.getVisiblity());

			// PK
			if (dto.getItemStatusSerialId() != null) {
				salesOrderItemStatusDo.setItemStatusSerialId(dto.getItemStatusSerialId());
			}
			// Field is mandatory as in foreign key here
			SalesOrderTaskStatusDo salesOrderTaskStatusDo = soTaskStatusRepo
					.importDto(soTaskStatusRepo.getSalesOrderTaskStatusById(dto.getTaskStatusSerialId()));

			if (salesOrderTaskStatusDo != null) {
				salesOrderItemStatusDo.setSalesOrderTaskStatus(salesOrderTaskStatusDo);
			} else {
				return null;
			}
		}

		return salesOrderItemStatusDo;
	}

	
	public SalesOrderItemStatusDto exportDto(SalesOrderItemStatusDo entity) {
		SalesOrderItemStatusDto salesOrderItemStatusDto = null;
		if (entity != null) {
			salesOrderItemStatusDto = new SalesOrderItemStatusDto();

			salesOrderItemStatusDto.setItemStatus(entity.getItemStatus());
			salesOrderItemStatusDto.setSalesOrderItemNum(entity.getSalesOrderItemNum());
			salesOrderItemStatusDto.setVisiblity(entity.getVisiblity());

			// Setting Primary Key here
			if (entity.getItemStatusSerialId() != null) {
				salesOrderItemStatusDto.setItemStatusSerialId(entity.getItemStatusSerialId());
			}

			// Field is mandatory as in foreign key here
			salesOrderItemStatusDto.setTaskStatusSerialId(entity.getSalesOrderTaskStatus().getTaskStatusSerialId());
		}

		return salesOrderItemStatusDto;
	}

	@Override
	public List<SalesOrderItemStatusDo> importList(List<SalesOrderItemStatusDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderItemStatusDo> doList = new ArrayList<>();
			for (SalesOrderItemStatusDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	
	public List<SalesOrderItemStatusDto> exportList(List<SalesOrderItemStatusDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SalesOrderItemStatusDto> dtoList = new ArrayList<>();
			for (SalesOrderItemStatusDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateSalesOrderItemStatus(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault {
		try {
			SalesOrderItemStatusDo salesOrderItemStatusDo = importDto(salesOrderItemStatusDto);
			if (salesOrderItemStatusDo != null) {
				getSession().merge(salesOrderItemStatusDo);
				getSession().flush();
				getSession().clear();

				return salesOrderItemStatusDo.getItemStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesOrderItemStatusDto> listAllSalesOrderItemStatuses() {
		return exportList(getSession().createQuery("from SalesOrderItemStatusDo", SalesOrderItemStatusDo.class).list());
	}

	@Override
	public SalesOrderItemStatusDto getSalesOrderItemStatusById(String salesOrderItemStatusId) {
		return exportDto(getSession().get(SalesOrderItemStatusDo.class, salesOrderItemStatusId));
	}

	@Override
	public String deleteSalesOrderItemStatusById(String salesOrderItemStatusId) throws ExecutionFault {
		try {
			SalesOrderItemStatusDo salesOrderItemStatusDo = getSession().byId(SalesOrderItemStatusDo.class)
					.load(salesOrderItemStatusId);
			if (salesOrderItemStatusDo != null) {
				getSession().delete(salesOrderItemStatusDo);
				return "Sales Order Item Status is completedly removed";
			} else {
				return "Sales Order Item Status is not found on Order id : " + salesOrderItemStatusId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	// public SalesOrderItemStatusDto getItemsDetailsByTaskIdItemId(String
	// taskSerId, String itemId) {
	//
	// return exportDto(
	// (SalesOrderItemStatusDo) getSession()
	// .createQuery(
	// "from SalesOrderItemStatusDo item where
	// item.salesOrderTaskStatus.taskStatusSerialId = :sId and
	// item.salesOrderItemNum = :itId",
	// SalesOrderTaskStatusDo.class)
	// .setParameter("sId", taskSerId).setParameter("itId", itemId));
	//
	// }

	@Override
	public Map<TaskItemDto, SalesOrderItemStatusDto> batchFetchByTaskSerIdItemId(List<TaskItemDto> taskItemList) {
		Map<TaskItemDto, SalesOrderItemStatusDto> itemStatusDoList = new HashMap<>();
		StringBuilder strErr = new StringBuilder();
		int count = 0;
		for (int i = 0; i < taskItemList.size(); i++) {
			try {
				count++;
				if (count == taskItemList.size()) {
					getSession().flush();
					getSession().clear();
				}
				SalesOrderItemStatusDo itemStatusDo = getSession()
						.createQuery(
								"from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.taskStatusSerialId = :sId and item.salesOrderItemNum = :itId",
								SalesOrderItemStatusDo.class)
						.setParameter("sId", taskItemList.get(i).getTaskSerId())
						.setParameter("itId", taskItemList.get(i).getItemId()).getSingleResult();
				itemStatusDoList.put(taskItemList.get(i), exportDto(itemStatusDo));
			} catch (RuntimeException e) {
				//logger.info("Error occur at : " + e + " on " + e.getStackTrace()[1]);
				strErr.append("Error occur at : " + e + " on index of " + i + " because " + e.getStackTrace() + "\n"
						+ e.getCause().getCause().getLocalizedMessage());
			} catch (Exception e) {
				//logger.info("Error occur at : " + e + " on " + e.getStackTrace()[1]);
				strErr.append("Error occur at : " + e + " on index of " + i + " because " + e.getStackTrace() + "\n"
						+ e.getCause().getCause().getLocalizedMessage());
			}
		}

		return itemStatusDoList;
	}

	@Override
	public SalesOrderItemStatusDto getItemStatusDataUsingTaskSerialIdAndItemNum(String taskSerialId, String itemNum) {
		return exportDto(getSession()
				.createQuery(
						"from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.taskStatusSerialId = :taskSerialId and item.salesOrderItemNum = :itemId",
						SalesOrderItemStatusDo.class)
				.setParameter("taskSerialId", taskSerialId).setParameter("itemId", itemNum).getSingleResult());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId) {
		return exportList(getSession().createQuery(
				"from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.taskStatusSerialId = :taskSerialId",
				SalesOrderItemStatusDo.class).setParameter("taskSerialId", taskSerialId).list());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingSalesOrderItemNum(String itemNum) {

		return exportList(getSession().createQuery(
				"from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.salesOrderItemNum = :itemNum",
				SalesOrderItemStatusDo.class).setParameter("itemNum", itemNum).list());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevelAndItemNum(String decisionSetId,
			String levelNum, String itemNum) {
		return exportList(getSession()
				.createQuery(
						"select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", levelNum).setParameter("itemNum", itemNum)
				.list());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemsBySapTaskId(String sapTaskId) {

		String query = "select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
				+ "task.taskId = :sapTaskId";

		return exportList(getSession().createQuery(query, SalesOrderItemStatusDo.class)
				.setParameter("sapTaskId", sapTaskId).list());
	}

	@Override
	public SalesOrderItemStatusDto getItemStatusFromDecisionSetAndLevel(String decisionSetId, String level,
			String itemNum) {

		return exportDto(getSession()
				.createQuery(
						"select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).setParameter("itemNum", itemNum)
				.uniqueResult());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevelList(String decisionSetId, String level,
			String itemNum) {

		return exportList(getSession()
				.createQuery(
						"select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).setParameter("itemNum", itemNum)
				.list());
	}

	@Override
	public String saveOrUpdateSalesOrderItemStatusSynchronised(SalesOrderItemStatusDto salesOrderItemStatusDto)
			throws ExecutionFault {
		try {

			SalesOrderItemStatusDo salesOrderItemStatusDo = new SalesOrderItemStatusDo();

			SalesOrderTaskStatusDo salesOrderTaskStatusDo = soTaskStatusRepo
					.getSalesOrderTaskStatusDoById(salesOrderItemStatusDto.getTaskStatusSerialId());

			SalesOrderLevelStatusDo salesOrderLevelStatusDo = soLevelstatusDao.getSalesOrderLevelStatusDoById(
					salesOrderTaskStatusDo.getSalesOrderLevelStatus().getLevelStatusSerialId());

			salesOrderTaskStatusDo.setSalesOrderLevelStatus(salesOrderLevelStatusDo);
			salesOrderItemStatusDo.setSalesOrderTaskStatus(salesOrderTaskStatusDo);
			salesOrderItemStatusDo.setSalesOrderItemNum(salesOrderItemStatusDto.getSalesOrderItemNum());
			salesOrderItemStatusDo.setItemStatus(salesOrderItemStatusDto.getItemStatus());
			salesOrderItemStatusDo.setVisiblity(salesOrderItemStatusDto.getVisiblity());

			if (salesOrderItemStatusDo != null) {
				Session session = sessionfactory.openSession();
				Transaction tx1 = session.beginTransaction();
				session.saveOrUpdate(salesOrderItemStatusDo);
				tx1.commit();
				session.clear();
				session.close();
				return salesOrderItemStatusDo.getItemStatusSerialId();
			} else {
				return null;
			}
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionSetAndLevel(String decisionSetId, String levelNum) {
		return exportList(getSession()
				.createQuery(
						"select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl",
						SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", levelNum).list());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String workflowTaskId, String salesOrderItemNum) {

		String query = "select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId"
				+ " join SalesOrderLevelStatusDo level on level.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where"
				+ " level.decisionSetId = :dsId and item.salesOrderItemNum = :itemNum and task.taskId != :workflowTaskId and item.itemStatus IN ('"
				+ StatusConstants.BLOCKED + "')";

		// String query = "select item from SalesOrderItemStatusDo item where
		// item.salesOrderItemNum = :itemNum and "
		// + "item.salesOrderTaskStatus.salesOrderLevelStatus.decisionSetId =
		// :dsId and item.salesOrderTaskStatus.taskId != :workflowTaskId";
		return exportList(getSession().createQuery(query, SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("workflowTaskId", workflowTaskId)
				.setParameter("itemNum", salesOrderItemNum).list());

	}

	@Override
	public List<SalesOrderItemStatusDto> getAllTheUpcomingItemStatusesForPerticularDecisionSetAndItemNotBlocked(
			String decisionSet) {

		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();

		// String query = "select item from SalesOrderItemStatusDo item join
		// SalesOrderTaskStatusDo task on task.taskStatusSerialId =
		// item.salesOrderTaskStatus.taskStatusSerialId"
		// + " join SalesOrderLevelStatusDo l on l.levelStatusSerialId =
		// task.salesOrderLevelStatus.levelStatusSerialId where"
		// + " l.decisionSetId = :dsId and item.itemStatus = '" +
		// DkshStatusConstants.BLOCKED
		// + "' and l.levelStatus != '" + DkshStatusConstants.LEVEL_COMPLETE +
		// "' and l.level IN ("
		// + queryForExcludeLevel + ") and task.taskStatus = '" +
		// DkshStatusConstants.TASK_IN_PROGRESS + "'";

		String query = "select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId"
				+ " join SalesOrderLevelStatusDo l on l.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where"
				+ " l.decisionSetId = :dsId and item.itemStatus = '" + StatusConstants.BLOCKED + "'";

		List<SalesOrderItemStatusDo> list = getSession().createQuery(query, SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSet).list();

		tx.commit();
		session.close();

		System.err.println("List of Blocked items : " + list.size());
		return exportList(list);
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemsStatusFromDecisionSetAndItemNumForAllLevels(String decisionSetId,
			String salesOrderItemNum) {
		String query = "select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId"
				+ " join SalesOrderLevelStatusDo level on level.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where"
				+ " level.decisionSetId = :dsId and item.salesOrderItemNum = :itemNum and item.itemStatus = '9'";
		return exportList(getSession().createQuery(query, SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("itemNum", salesOrderItemNum).list());
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusFromDecisionsetId(String decisionSetId) {

		String query = "select item from  SalesOrderItemStatusDo"
        +" item where item.taskStatusSerialId in(select task.taskStatusSerialId from SalesOrderTaskStatusDo task where task.levelStatusSerialId in("
        +"select level.levelStatusSerialId from SalesOrderLevelStatusDo level where level.level =(select max(level.level) from SalesOrderLevelStatusDo level"
        +" where level.decisionSetId = :dsId  and level.levelStatus = '4') and level.decisionSetId = :dsId))";
		
		System.err.println("queryToFetchItem "+query);

		return exportList(getSession().createQuery(query, SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).list());
		
	}
	
	
	
	
	

}
