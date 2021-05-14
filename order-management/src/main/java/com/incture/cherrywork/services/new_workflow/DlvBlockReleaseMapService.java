package com.incture.cherrywork.services.new_workflow;

import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface DlvBlockReleaseMapService {

	public ResponseEntity saveOrUpdateDlvBlockReleaseMap(DlvBlockReleaseMapDto dlvBlockReleaseMapDto);

	public ResponseEntity listAllDlvBlockReleaseMaps();

	public ResponseEntity getDlvBlockReleaseMapById(String releaseMapId);

	public ResponseEntity getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(String dlvBlockCode);

	public ResponseEntity getDlvBlockReleaseMapBydlvBlockCodeWithSpecialClients(String dlvBlockCode);

	public ResponseEntity deleteDlvBlockReleaseMapById(String releaseMapId);

}