package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.DlvBlockClientMapDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface DlvBlockClientMapDao {

	public String saveOrUpdateDlvBlockClientMap(DlvBlockClientMapDto dlvBlockClientMapDto) throws ExecutionFault;

	public List<DlvBlockClientMapDto> listAllDlvBlockClientMaps();

	public DlvBlockClientMapDto getDlvBlockClientMapById(String clientKey);

	public String deleteDlvBlockClientMapById(String clientKey) throws ExecutionFault;

	public List<String> getDlvBlockClientMapByReleaseMapId(String releaseMapId);

}

