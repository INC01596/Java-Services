package com.incture.cherrywork.workflow.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;

@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesOrderItemStatusCustomRepositoryImpl implements ISalesOrderItemStatusCustomRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public SalesOrderItemStatusDto getItemStatusFromDecisionSetAndLevel(String decisionSetId, String level,
			String itemNum) {

		return ObjectMapperUtils.map(entityManager.createQuery(
						"select item from SalesOrderItemStatusDo item join SalesOrderTaskStatusDo task on task.taskStatusSerialId = item.salesOrderTaskStatus.taskStatusSerialId where "
								+ "task.salesOrderLevelStatus.decisionSetId = :dsId and task.salesOrderLevelStatus.level = :lvl and item.salesOrderItemNum = :itemNum",
						SalesOrderItemStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).setParameter("itemNum", itemNum)
				.getSingleResult(),SalesOrderItemStatusDto.class);
	}

	@Override
	public List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId) {
		
		String query = "from SalesOrderItemStatusDo item where item.salesOrderTaskStatus.taskStatusSerialId = :taskSerialId";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("taskSerialId", taskSerialId);
		List<SalesOrderItemStatusDo> list = q1.getResultList();
		List<SalesOrderItemStatusDto> itemStatusDto = new ArrayList<>();
		for(SalesOrderItemStatusDo itemStatus:list)
			itemStatusDto.add(ObjectMapperUtils.map(itemStatus, SalesOrderItemStatusDto.class));
		return itemStatusDto;
	}


}
