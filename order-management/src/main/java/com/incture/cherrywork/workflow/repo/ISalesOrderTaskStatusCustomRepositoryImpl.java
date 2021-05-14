package com.incture.cherrywork.workflow.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.DkshStatusConstants;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusDo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;


@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesOrderTaskStatusCustomRepositoryImpl implements ISalesOrderTaskStatusCustomRepository{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SalesOrderTaskStatusDto> getAllTaskByTaskSerialId(List<String> taskSerialId) {

		String query = "from SalesOrderTaskStatusDo task where task.taskStatusSerialId in "+taskSerialId;
		Query q1 = entityManager.createQuery(query);
		List<SalesOrderTaskStatusDo> list = q1.getResultList();
		List<SalesOrderTaskStatusDto> taskDto = new ArrayList<>();
		for(SalesOrderTaskStatusDo task:list)
			taskDto.add(ObjectMapperUtils.map(task, SalesOrderTaskStatusDto.class));
		return taskDto;

	}
	
	@Override
	public SalesOrderTaskStatusDo getSalesOrderTaskStatusDoById(String salesOrderTaskStatusId) {
		Session session = entityManager.unwrap(Session.class);

		return session.get(SalesOrderTaskStatusDo.class, salesOrderTaskStatusId);
	}
	
	@Override
	public List<SalesOrderTaskStatusDto> getAllTasksFromDecisionSetAndLevel(String decisionSetId, String level) {

		
		String query = "select task from SalesOrderTaskStatusDo task join SalesOrderLevelStatusDo l on "
				+ "l.levelStatusSerialId = task.salesOrderLevelStatus.levelStatusSerialId where l.decisionSetId = :dsId and l.level = :lvl";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSetId);
		q1.setParameter("lvl", level);
		List<SalesOrderTaskStatusDo> taskList = q1.getResultList();
		List<SalesOrderTaskStatusDto> resList = new ArrayList<>();
		for(SalesOrderTaskStatusDo task:taskList)
			resList.add(ObjectMapperUtils.map(task, SalesOrderTaskStatusDto.class));
		return resList;
	}


	@Override
	public SalesOrderTaskStatusDto getAllTasksFromLevelStatusSerialId(String levelStatusSerialId) {

		return ObjectMapperUtils.map(entityManager
				.createQuery(
						"from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.levelStatusSerialId = :levelStatusSerialId",
						SalesOrderTaskStatusDo.class)
				.setParameter("levelStatusSerialId", levelStatusSerialId).getSingleResult(),SalesOrderTaskStatusDto.class);
	}
	
	@Override
	public List<String> getTasksIdFromDecisionSetAndLevel(String decisionSet) {
		
		String query = "select task.taskId from SalesOrderTaskStatusDo task where task.salesOrderLevelStatus.decisionSetId = :decisionSetId and task.salesOrderLevelStatus.levelStatus = :levelStatus";
		Query q1= entityManager.createQuery(query);
		q1.setParameter("decisionSetId", decisionSet);
		q1.setParameter("levelStatus", DkshStatusConstants.LEVEL_IN_PROGRESS);
		List<String> list = q1.getResultList();
		return list;
	}



}
