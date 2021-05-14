package com.incture.cherrywork.dao;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SchedulerTableDto;

import com.incture.cherrywork.entities.SchedulerTableDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.workflow.repo.ISchedulerRepository;



/**
 * @author Mohit.Basak
 *
 */
@SuppressWarnings("unused")
@Repository
@Component
public class SchedulerTableDaoImpl extends BaseDao<SchedulerTableDo, SchedulerTableDto> implements SchedulerTableDao {

	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ISchedulerRepository repo;
	
	@Override
	public SchedulerTableDo importDto(SchedulerTableDto fromDto) {
		SchedulerTableDo schedulerTableDo = null;
		if (fromDto != null) {
			schedulerTableDo = new SchedulerTableDo();
			// since its primary key so not set here
			schedulerTableDo.setLoggedMessage(fromDto.getLoggedMessage());
			schedulerTableDo.setTimeStamp(fromDto.getTimeStamp());
			schedulerTableDo.setIstTimeStamp(fromDto.getIstTimeStamp());

		}

		return schedulerTableDo;
	}

	@Override
	public SchedulerTableDto exportDto(SchedulerTableDo entity) {
		SchedulerTableDto schedulerTableDto = null;
		if (entity != null) {
			schedulerTableDto = new SchedulerTableDto();

			schedulerTableDto.setId(entity.getId());
			schedulerTableDto.setLoggedMessage(entity.getLoggedMessage());
			schedulerTableDto.setTimeStamp(entity.getTimeStamp());
			// setting only indian time not istTimestamp
			// schedulerTableDto.setIstTimeStamp(entity.getIstTimeStamp());
			schedulerTableDto
					.setIndianTime(entity.getIstTimeStamp() != null ? entity.getIstTimeStamp().toString() : null);
		}

		return schedulerTableDto;
	}

	@Override
	public List<SchedulerTableDo> importList(List<SchedulerTableDto> list) {
		if (list != null && !list.isEmpty()) {
			List<SchedulerTableDo> doList = new ArrayList<>();
			for (SchedulerTableDto entity : list) {

				doList.add(importDto(entity));
			}
			return doList;
		}
		return Collections.emptyList();
	}

	@Override
	public List<SchedulerTableDto> exportList(List<SchedulerTableDo> list) {
		if (list != null && !list.isEmpty()) {
			List<SchedulerTableDto> dtoList = new ArrayList<>();
			for (SchedulerTableDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String save(SchedulerTableDto schedulerTableDto) throws Exception {
		try {
			SchedulerTableDo schedulerTableDo = ObjectMapperUtils.map(schedulerTableDto, SchedulerTableDo.class);
			
			SchedulerTableDo save=repo.save(schedulerTableDo);
			
			return  "save to hana successfully";

			
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerTableDto> listAllLogs() {
		
		Query q=em.createQuery("from SchedulerTableDo st order by st.istTimeStamp desc");
		
		List<SchedulerTableDo>list=q.getResultList();
		return ObjectMapperUtils.mapAll(list, SchedulerTableDto.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerTableDto> listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate) {
		return exportList(em
				.createQuery(
						"from SchedulerTableDo st where istTimeStamp between :startDate and :endDate order by st.istTimeStamp desc")
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList());

	}
}
