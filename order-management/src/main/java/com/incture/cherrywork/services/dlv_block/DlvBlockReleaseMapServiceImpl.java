package com.incture.cherrywork.services.dlv_block;



import static com.incture.cherrywork.WConstants.Constants.CREATION_FAILED;
import static com.incture.cherrywork.WConstants.Constants.DATA_FOUND;
import static com.incture.cherrywork.WConstants.Constants.EMPTY_LIST;
import static com.incture.cherrywork.WConstants.Constants.EXCEPTION_FAILED;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.dlv_block.DlvBlockClientMapDao;
import com.incture.cherrywork.dao.dlv_block.DlvBlockReleaseMapDao;
import com.incture.cherrywork.dtos.DlvBlockReleaseMapDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class DlvBlockReleaseMapServiceImpl implements DlvBlockReleaseMapService {

	@Autowired
	private DlvBlockReleaseMapDao dlvBlockReleaseMapRepo;

	@Autowired
	private DlvBlockClientMapDao dlvBlockClientMapRepo;

	@Override
	public ResponseEntity saveOrUpdateDlvBlockReleaseMap(DlvBlockReleaseMapDto dlvBlockReleaseMapDto) {

		try {
			if (!HelperClass.checkString(dlvBlockReleaseMapDto.getDlvBlockCode())
					&& !HelperClass.checkString(dlvBlockReleaseMapDto.getCountryCode())) {
				String msg = dlvBlockReleaseMapRepo.saveOrUpdateDlvBlockReleaseMap(dlvBlockReleaseMapDto);

				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, CREATION_FAILED, ResponseStatus.FAILED);
				}
				return new ResponseEntity(dlvBlockReleaseMapDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Delivery Block code and Country code fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllDlvBlockReleaseMaps() {
		try {
			List<DlvBlockReleaseMapDto> list = dlvBlockReleaseMapRepo.listAllDlvBlockReleaseMaps();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, DATA_FOUND, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, EMPTY_LIST, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getDlvBlockReleaseMapById(String releaseMapId) {
		try {
			if (!HelperClass.checkString(releaseMapId)) {
				DlvBlockReleaseMapDto dlvBlockReleaseMapDto = dlvBlockReleaseMapRepo
						.getDlvBlockReleaseMapById(releaseMapId);
				if (dlvBlockReleaseMapDto != null) {
					return new ResponseEntity(dlvBlockReleaseMapDto, HttpStatus.ACCEPTED,
							"Block is found for release map id : " + releaseMapId, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Block is not available for release map id : " + releaseMapId, ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Comment Id field is mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getDlvBlockReleaseMapBydlvBlockCodeForDisplayOnly(String dlvBlockCode) {
		try {
			if (!HelperClass.checkString(dlvBlockCode)) {
				DlvBlockReleaseMapDto dlvBlockReleaseMapDto = dlvBlockReleaseMapRepo
						.getDlvBlockReleaseMapBydlvBlockCode(dlvBlockCode);
				if (dlvBlockReleaseMapDto != null && dlvBlockReleaseMapDto.getDisplay() != false
						&& !HelperClass.checkString(dlvBlockReleaseMapDto.getDlvBlockCode())) {
					return new ResponseEntity(dlvBlockReleaseMapDto, HttpStatus.ACCEPTED, DATA_FOUND,
							ResponseStatus.SUCCESS);

				} else {
					return new ResponseEntity(null, HttpStatus.NO_CONTENT,
							"No result found for delivery block code : " + dlvBlockCode, ResponseStatus.SUCCESS);

				}
			} else {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Delivery Block Code is mandatory field",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity getDlvBlockReleaseMapBydlvBlockCodeWithSpecialClients(String dlvBlockCode) {

		try {
			if (!HelperClass.checkString(dlvBlockCode)) {
				DlvBlockReleaseMapDto dlvBlockReleaseMapDto = dlvBlockReleaseMapRepo
						.getDlvBlockReleaseMapBydlvBlockCode(dlvBlockCode);
				if (dlvBlockReleaseMapDto != null && dlvBlockReleaseMapDto.getEdit() != false
						&& dlvBlockReleaseMapDto.getSpecialClient() != false
						&& dlvBlockReleaseMapDto.getDisplay() != false
						&& !HelperClass.checkString(dlvBlockReleaseMapDto.getDlvBlockCode())) {

					List<String> listOfClients = dlvBlockClientMapRepo
							.getDlvBlockClientMapByReleaseMapId(dlvBlockReleaseMapDto.getReleaseMapId());

					if (listOfClients != null && !listOfClients.isEmpty()) {
						dlvBlockReleaseMapDto.setClientMapDtoList(listOfClients);
						return new ResponseEntity(dlvBlockReleaseMapDto, HttpStatus.ACCEPTED, DATA_FOUND,
								ResponseStatus.SUCCESS);
					} else {
						return new ResponseEntity(null, HttpStatus.NO_CONTENT,
								"No result found for clients : " + dlvBlockReleaseMapDto.getReleaseMapId(),
								ResponseStatus.SUCCESS);
					}

				} else {
					return new ResponseEntity(null, HttpStatus.NO_CONTENT,
							"No result found for delivery block code : " + dlvBlockCode, ResponseStatus.SUCCESS);

				}
			} else {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Delivery Block Code is mandatory field",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity deleteDlvBlockReleaseMapById(String releaseMapId) {
		try {
			if (!HelperClass.checkString(releaseMapId)) {
				String msg = dlvBlockReleaseMapRepo.deleteDlvBlockReleaseMapById(releaseMapId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Release Map Id field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

}
