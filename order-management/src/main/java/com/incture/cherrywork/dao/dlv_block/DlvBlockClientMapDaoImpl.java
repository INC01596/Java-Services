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
import com.incture.cherrywork.dtos.DlvBlockClientMapDo;
import com.incture.cherrywork.dtos.DlvBlockClientMapDto;
import com.incture.cherrywork.exceptions.ExecutionFault;


@Service
@Transactional
public class DlvBlockClientMapDaoImpl extends BaseDao<DlvBlockClientMapDo, DlvBlockClientMapDto>
		implements DlvBlockClientMapDao {

	
	public DlvBlockClientMapDo importDto(DlvBlockClientMapDto dto) {
		DlvBlockClientMapDo dlvBlockClientMapDo = null;
		if (dto != null) {
			dlvBlockClientMapDo = new DlvBlockClientMapDo();

			dlvBlockClientMapDo.setClientId(dto.getClientId());
			dlvBlockClientMapDo.setReleaseMapId(dto.getReleaseMapId());

			if (dto.getClientKey() != null) {
				dlvBlockClientMapDo.setClientKey(dto.getClientKey());
			}

		}
		return dlvBlockClientMapDo;
	}

	
	public DlvBlockClientMapDto exportDto(DlvBlockClientMapDo entity) {
		DlvBlockClientMapDto dlvBlockClientMapDto = null;
		if (entity != null) {
			dlvBlockClientMapDto = new DlvBlockClientMapDto();

			dlvBlockClientMapDto.setClientId(entity.getClientId());
			dlvBlockClientMapDto.setReleaseMapId(entity.getReleaseMapId());

			if (entity.getClientKey() != null) {
				dlvBlockClientMapDto.setClientKey(entity.getClientKey());
			}
		}
		return dlvBlockClientMapDto;
	}

	
	public List<DlvBlockClientMapDo> importList(List<DlvBlockClientMapDto> list) {
		if (list != null && !list.isEmpty()) {
			List<DlvBlockClientMapDo> dtoList = new ArrayList<>();
			for (DlvBlockClientMapDto entity : list) {

				dtoList.add(importDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	
	public List<DlvBlockClientMapDto> exportList(List<DlvBlockClientMapDo> list) {
		if (list != null && !list.isEmpty()) {
			List<DlvBlockClientMapDto> dtoList = new ArrayList<>();
			for (DlvBlockClientMapDo entity : list) {

				dtoList.add(exportDto(entity));
			}
			return dtoList;
		}
		return Collections.emptyList();
	}

	@Override
	public String saveOrUpdateDlvBlockClientMap(DlvBlockClientMapDto dlvBlockClientMapDto) throws ExecutionFault {
		try {
			DlvBlockClientMapDo dlvBlockClientMapDo = importDto(dlvBlockClientMapDto);
			getSession().saveOrUpdate(dlvBlockClientMapDo);
			getSession().flush();
			getSession().clear();
			return "DlvBlockClientMap is successfully created with =" + dlvBlockClientMapDo.getPrimaryKey();
		} catch (NoResultException | NullPointerException e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<DlvBlockClientMapDto> listAllDlvBlockClientMaps() {
		return exportList(getSession().createQuery("from DlvBlockClientMapDo", DlvBlockClientMapDo.class).list());

	}

	@Override
	public DlvBlockClientMapDto getDlvBlockClientMapById(String clientKey) {
		return exportDto(getSession().get(DlvBlockClientMapDo.class, clientKey));
	}

	@Override
	public String deleteDlvBlockClientMapById(String clientKey) throws ExecutionFault {
		try {
			DlvBlockClientMapDo dlvBlockClientMapDo = getSession().byId(DlvBlockClientMapDo.class).load(clientKey);
			if (dlvBlockClientMapDo != null) {
				getSession().delete(dlvBlockClientMapDo);
				return "DlvBlockClientMap is completedly removed";
			} else {
				return "DlvBlockClientMap is not found on client id : " + clientKey;
			}
		} catch (Exception e) {
			throw new ExecutionFault(e + " on " + e.getStackTrace()[1]);
		}
	}

	@Override
	public List<String> getDlvBlockClientMapByReleaseMapId(String releaseMapId) {
		return getSession().createQuery(
				"select client.clientId from DlvBlockClientMapDo client where client.releaseMapId = :releaseMapId",
				String.class).setParameter("releaseMapId", releaseMapId).list();

	}

}

