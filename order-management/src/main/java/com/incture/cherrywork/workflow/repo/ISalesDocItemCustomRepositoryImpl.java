package com.incture.cherrywork.workflow.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.workflow.SalesDocItemDto;
import com.incture.cherrywork.entities.workflow.SalesDocItemDo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;

@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesDocItemCustomRepositoryImpl implements ISalesDocItemCustomRepository{

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId) {
		String query = "from SalesDocItemDo item where item.decisionSetId = :dsId";
		Query q1 = entityManager.createQuery(query);
		List<SalesDocItemDo> docItem = q1.getResultList();
		List<SalesDocItemDto> docItemDto = new ArrayList<>();
		for(SalesDocItemDo docItem1:docItem)
			docItemDto.add(ObjectMapperUtils.map(docItem1, SalesDocItemDto.class));
		return docItemDto;
	}

	
	@Override
	public List<String> getDSBySalesHeaderID(String salesHeaderId) {

		return entityManager.createQuery(
				"select distinct ds.decisionSetId from SalesDocItemDo ds where ds.salesDocItemKey.salesDocHeader.salesOrderNum = :salesOrderNum and ds.decisionSetId is not null",
				String.class).setParameter("salesOrderNum", salesHeaderId).getResultList();
	}

}
