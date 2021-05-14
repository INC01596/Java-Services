package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.entities.DlvBlockReleaseMapDo;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;


@Repository
@Component
public class DlvBlockReleaseMapDaoImpl extends BaseDao<DlvBlockReleaseMapDo, DlvBlockReleaseMapDto>
		implements DlvBlockReleaseMapDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	
 public DlvBlockReleaseMapDo importDto(DlvBlockReleaseMapDto dto) {
		DlvBlockReleaseMapDo dlvBlockReleaseMapDo =ObjectMapperUtils.map(dto, DlvBlockReleaseMapDo.class);
		
		return dlvBlockReleaseMapDo;
	}

	
	public DlvBlockReleaseMapDto exportDto(DlvBlockReleaseMapDo entity) {
		DlvBlockReleaseMapDto dlvBlockReleaseMapDto = ObjectMapperUtils.map(entity, DlvBlockReleaseMapDto.class);
		
		return dlvBlockReleaseMapDto;
	}

	
	public List<DlvBlockReleaseMapDo> importList(List<DlvBlockReleaseMapDto> list) {
		 List<DlvBlockReleaseMapDo> list1=ObjectMapperUtils.mapAll(list, DlvBlockReleaseMapDo.class);
		 return list1;
	}

	
	public List<DlvBlockReleaseMapDto> exportList(List<DlvBlockReleaseMapDo> list) {
		 List<DlvBlockReleaseMapDto> list1=ObjectMapperUtils.mapAll(list, DlvBlockReleaseMapDto.class);
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateDlvBlockReleaseMap(DlvBlockReleaseMapDto dlvBlockReleaseMapDto) throws ExecutionFault {
		try {
			DlvBlockReleaseMapDo dlvBlockReleaseMapDo = importDto(dlvBlockReleaseMapDto);
			Session session = entityManager.unwrap(Session.class);
		  session.saveOrUpdate(dlvBlockReleaseMapDo);
		session.flush();
			session.clear();
			return "DlvBlockReleaseMap is successfully created with =" + dlvBlockReleaseMapDo.getPrimaryKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DlvBlockReleaseMapDto> listAllDlvBlockReleaseMaps() {
		return exportList(entityManager.createQuery("from DlvBlockReleaseMapDo").getResultList());
	}

	@Override
	public DlvBlockReleaseMapDto getDlvBlockReleaseMapById(String releaseMapId) {
		Session session = entityManager.unwrap(Session.class);
		return exportDto(session.get(DlvBlockReleaseMapDo.class, releaseMapId));
	}

	@Override
	public String deleteDlvBlockReleaseMapById(String releaseMapId) throws ExecutionFault {
		try {
			Session session = entityManager.unwrap(Session.class);
			DlvBlockReleaseMapDo dlvBlockReleaseMapDo = session.byId(DlvBlockReleaseMapDo.class)
					.load(releaseMapId);
			if (dlvBlockReleaseMapDo != null) {
				
				session.delete(dlvBlockReleaseMapDo);
				return "DlvBlockReleaseMap is completedly removed";
			} else {
				return "DlvBlockReleaseMap is not found on release map id : " + releaseMapId;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public DlvBlockReleaseMapDto getDlvBlockReleaseMapBydlvBlockCode(String dlvBlockCode) throws ExecutionFault {
		Session session = entityManager.unwrap(Session.class);
		return exportDto(
				(DlvBlockReleaseMapDo) entityManager.createQuery("from DlvBlockReleaseMapDo dlv where dlv.dlvBlockCode = :dlvBlockCode")
						.setParameter("dlvBlockCode", dlvBlockCode).getSingleResult());

	}

}
