package com.incture.cherrywork.workflow.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.workflow.DlvBlockReleaseMapDto;
import com.incture.cherrywork.entities.workflow.DlvBlockReleaseMapDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.sap.db.jdbc.Session;

@SuppressWarnings("unused")
@Transactional
@Repository
public class IDlvBlockReleaseMapCustomRepositoryImpl implements IDlvBlockReleaseMapCustomRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public DlvBlockReleaseMapDto getDlvBlockReleaseMapBydlvBlockCode(String dlvBlockCode) {

		return ObjectMapperUtils.map(
				entityManager.createQuery("from DlvBlockReleaseMapDo dlv where dlv.dlvBlockCode = :dlvBlockCode",
								DlvBlockReleaseMapDo.class)
						.setParameter("dlvBlockCode", dlvBlockCode).getSingleResult(), DlvBlockReleaseMapDto.class);

	}



}
