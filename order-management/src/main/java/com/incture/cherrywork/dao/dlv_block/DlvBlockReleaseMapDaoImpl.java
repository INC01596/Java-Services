package com.incture.cherrywork.dao.dlv_block;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.entities.DlvBlockReleaseMapDo;
import com.incture.cherrywork.exceptions.ExecutionFault;



@Service
@Transactional
public class DlvBlockReleaseMapDaoImpl extends BaseDao<DlvBlockReleaseMapDo, DlvBlockReleaseMapDto>
		implements DlvBlockReleaseMapDao {

	
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

	@Override
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

	@Override
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

	@Override
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
			getSession().saveOrUpdate(dlvBlockReleaseMapDo);
			getSession().flush();
			getSession().clear();
			return "DlvBlockReleaseMap is successfully created with =" + dlvBlockReleaseMapDo.getPrimaryKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DlvBlockReleaseMapDto> listAllDlvBlockReleaseMaps() {
		return exportList(getSession().createQuery("from DlvBlockReleaseMapDo", DlvBlockReleaseMapDo.class).list());
	}

	@Override
	public DlvBlockReleaseMapDto getDlvBlockReleaseMapById(String releaseMapId) {

		return exportDto(getSession().get(DlvBlockReleaseMapDo.class, releaseMapId));
	}

	@Override
	public String deleteDlvBlockReleaseMapById(String releaseMapId) throws ExecutionFault {
		try {
			DlvBlockReleaseMapDo dlvBlockReleaseMapDo = getSession().byId(DlvBlockReleaseMapDo.class)
					.load(releaseMapId);
			if (dlvBlockReleaseMapDo != null) {
				getSession().delete(dlvBlockReleaseMapDo);
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

		return exportDto(
				getSession()
						.createQuery("from DlvBlockReleaseMapDo dlv where dlv.dlvBlockCode = :dlvBlockCode",
								DlvBlockReleaseMapDo.class)
						.setParameter("dlvBlockCode", dlvBlockCode).getSingleResult());

	}

}
