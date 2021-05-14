package com.incture.cherrywork.workflow.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.WConstants.DkshStatusConstants;


@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesOrderLevelStatusCustomRepositoryImpl implements ISalesOrderLevelStatusCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevel(String decisionSetId, String level) {
		System.err.println("decisiion = " + decisionSetId + level);
		String query = "from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSetId);
		q1.setParameter("lvl", level);
		SalesOrderLevelStatusDo result = (SalesOrderLevelStatusDo) q1.getSingleResult();

		return ObjectMapperUtils.map(result, SalesOrderLevelStatusDto.class);
	}

	@Override
	public SalesOrderLevelStatusDo getSalesOrderLevelStatusByDecisionSetAndLevelDo(String decisionSetId, String level) {
		System.err.println("decisiion = " + decisionSetId + level);

		return entityManager
				.createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getSingleResult();
	}

	@Override
	public SalesOrderLevelStatusDo getSalesOrderLevelStatusDoById(String salesOrderLevelStatusId) {

		Session session = entityManager.unwrap(Session.class);
		return session.get(SalesOrderLevelStatusDo.class, salesOrderLevelStatusId);
	}
	
	@Override
	public SalesOrderLevelStatusDto getSalesOrderLevelStatusByDecisionSetAndLevelSession(String decisionSetId,
			String level) {
		System.err.println("decisiion = " + decisionSetId + level);
		Session session = entityManager.unwrap(Session.class);
//		Session session = sessionfactory.openSession();
//		Transaction transaction = session.beginTransaction();
		SalesOrderLevelStatusDto salesOrderLevelStatusDto = ObjectMapperUtils.map(entityManager
				.createQuery("from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level = :lvl",
						SalesOrderLevelStatusDo.class)
				.setParameter("dsId", decisionSetId).setParameter("lvl", level).getSingleResult(),SalesOrderLevelStatusDto.class);

//		transaction.commit();
//		session.close();

		return salesOrderLevelStatusDto;
	}


	@Override
	public List<SalesOrderLevelStatusDto> getsalesOrderLevelStatusByDecisionSetAndLevelNewStatus(String decisionSetId,
			String level) {
		String query = "from SalesOrderLevelStatusDo l where l.decisionSetId = :dsId and l.level != :level and l.levelStatus = :levelStatus";
		Query q1 = entityManager.createQuery(query);
		q1.setParameter("dsId", decisionSetId);
		q1.setParameter("level", level);
		q1.setParameter("levelStatus", DkshStatusConstants.LEVEL_NEW);
		List<SalesOrderLevelStatusDo> list = q1.getResultList();
		List<SalesOrderLevelStatusDto> dtoList = new ArrayList<>();
		for(SalesOrderLevelStatusDo levelStatus:list)
			dtoList.add(ObjectMapperUtils.map(levelStatus, SalesOrderLevelStatusDto.class));
		return dtoList;
	}

}
