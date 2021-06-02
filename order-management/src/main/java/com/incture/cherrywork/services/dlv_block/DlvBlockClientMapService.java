package com.incture.cherrywork.services.dlv_block;


import com.incture.cherrywork.dtos.DlvBlockClientMapDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface DlvBlockClientMapService {

	public ResponseEntity saveOrUpdateDlvBlockClientMap(DlvBlockClientMapDto dlvBlockClientMapDto);

	public ResponseEntity listAllDlvBlockClientMaps();

	public ResponseEntity getDlvBlockClientMapById(String clientKey);

	public ResponseEntity deleteDlvBlockClientMapById(String clientKey);

	public ResponseEntity getDlvBlockClientMapByReleaseMapId(String releaseMapId);

}
