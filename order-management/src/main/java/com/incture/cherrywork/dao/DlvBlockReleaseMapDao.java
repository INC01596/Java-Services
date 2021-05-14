package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface DlvBlockReleaseMapDao {

	public String saveOrUpdateDlvBlockReleaseMap(DlvBlockReleaseMapDto dlvBlockReleaseMapDto) throws ExecutionFault;

	public List<DlvBlockReleaseMapDto> listAllDlvBlockReleaseMaps();

	public DlvBlockReleaseMapDto getDlvBlockReleaseMapById(String releaseMapId);

	public DlvBlockReleaseMapDto getDlvBlockReleaseMapBydlvBlockCode(String dlvBlockCode) throws ExecutionFault;

	public String deleteDlvBlockReleaseMapById(String releaseMapId) throws ExecutionFault;

}
