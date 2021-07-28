package com.incture.cherrywork.dao.dlv_block;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.entities.DlvBlockReleaseMapDo;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.repositories.IDlvBlockReleaseMapRepository;



@Service
@Transactional
public class DlvBlockReleaseMapDaoImpl implements DlvBlockReleaseMapDao {

	@Autowired
	private IDlvBlockReleaseMapRepository dlvBlockReleaseMapRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public DlvBlockReleaseMapDo importDto(DlvBlockReleaseMapDto dto) {
		DlvBlockReleaseMapDo dlvBlockReleaseMapDo = null;
		if (dto != null) {
			dlvBlockReleaseMapDo = new DlvBlockReleaseMapDo();
			if (dto.getReleaseMapId() != null) {
				dlvBlockReleaseMapDo.setReleaseMapId(dto.getReleaseMapId());
			}
			dlvBlockReleaseMapDo.setCountryCode(dto.getCountryCode());
			dlvBlockReleaseMapDo.setDisplay(dto.getDisplay());
			dlvBlockReleaseMapDo.setDlvBlockCode(dto.getDlvBlockCode());
			dlvBlockReleaseMapDo.setEdit(dto.getEdit());
			dlvBlockReleaseMapDo.setHeaderLevel(dto.getHeaderLevel());
			dlvBlockReleaseMapDo.setItemLevel(dto.getItemLevel());
			dlvBlockReleaseMapDo.setSpecialClient(dto.getSpecialClient());

		}
		return dlvBlockReleaseMapDo;
	}

	
	public DlvBlockReleaseMapDto exportDto(DlvBlockReleaseMapDo entity) {
		DlvBlockReleaseMapDto dlvBlockReleaseMapDto = null;
		if (entity != null) {
			dlvBlockReleaseMapDto = new DlvBlockReleaseMapDto();

			dlvBlockReleaseMapDto.setCountryCode(entity.getCountryCode());
			dlvBlockReleaseMapDto.setDisplay(entity.getDisplay());
			dlvBlockReleaseMapDto.setDlvBlockCode(entity.getDlvBlockCode());
			dlvBlockReleaseMapDto.setEdit(entity.getEdit());
			dlvBlockReleaseMapDto.setHeaderLevel(entity.getHeaderLevel());
			dlvBlockReleaseMapDto.setItemLevel(entity.getItemLevel());
			dlvBlockReleaseMapDto.setSpecialClient(entity.getSpecialClient());

			if (entity.getReleaseMapId() != null) {
				dlvBlockReleaseMapDto.setReleaseMapId(entity.getReleaseMapId());
			}
		}
		return dlvBlockReleaseMapDto;
	}

	
	public List<DlvBlockReleaseMapDo> importList(List<DlvBlockReleaseMapDto> list) {
		if (list != null && !list.isEmpty()) {
			List<DlvBlockReleaseMapDo> dtoList = new ArrayList<>();
			for (DlvBlockReleaseMapDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	
	public List<DlvBlockReleaseMapDto> exportList(List<DlvBlockReleaseMapDo> list) {
		if (list != null && !list.isEmpty()) {
			List<DlvBlockReleaseMapDto> dtoList = new ArrayList<>();
			for (DlvBlockReleaseMapDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateDlvBlockReleaseMap(DlvBlockReleaseMapDto dlvBlockReleaseMapDto) throws ExecutionFault {
		try {
			DlvBlockReleaseMapDo dlvBlockReleaseMapDo = importDto(dlvBlockReleaseMapDto);
			dlvBlockReleaseMapRepository.save(dlvBlockReleaseMapDo);
			return "DlvBlockReleaseMap is successfully created with =" + dlvBlockReleaseMapDo.getPrimaryKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DlvBlockReleaseMapDto> listAllDlvBlockReleaseMaps() {
		return exportList(dlvBlockReleaseMapRepository.findAll());
	}

	@Override
	public DlvBlockReleaseMapDto getDlvBlockReleaseMapById(String releaseMapId) {

		return exportDto(dlvBlockReleaseMapRepository.getOne(releaseMapId));
	}

	@Override
	public String deleteDlvBlockReleaseMapById(String releaseMapId) throws ExecutionFault {
		try {
			DlvBlockReleaseMapDo dlvBlockReleaseMapDo = dlvBlockReleaseMapRepository.getOne(releaseMapId);
			if (dlvBlockReleaseMapDo != null) {
				dlvBlockReleaseMapRepository.delete(dlvBlockReleaseMapDo);
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

		dlvBlockCode = dlvBlockCode.substring(0,2)+dlvBlockCode.substring(2,dlvBlockCode.length());
		System.err.println("[getDlvBlockReleaseMapBydlvBlockCode] dlvBlockCode: "+dlvBlockCode);
		String query = "from DlvBlockReleaseMapDo dlv where dlv.dlvBlockCode=\'"+dlvBlockCode+"\'";
		Query q1 = entityManager.createQuery(query);
		return exportDto((DlvBlockReleaseMapDo)q1.getSingleResult());
		
	}

}
