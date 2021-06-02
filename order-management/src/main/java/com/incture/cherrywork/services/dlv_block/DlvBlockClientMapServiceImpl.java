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
import com.incture.cherrywork.dtos.DlvBlockClientMapDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;



@Service
@Transactional
public class DlvBlockClientMapServiceImpl implements DlvBlockClientMapService {

	@Autowired
	private DlvBlockClientMapDao dlvBlockClientMapRepo;

	@Override
	public ResponseEntity saveOrUpdateDlvBlockClientMap(DlvBlockClientMapDto dlvBlockClientMapDto) {
		try {
			if (dlvBlockClientMapDto.getReleaseMapId() != null && !HelperClass.checkString(dlvBlockClientMapDto.getClientId())) {
				String msg = dlvBlockClientMapRepo.saveOrUpdateDlvBlockClientMap(dlvBlockClientMapDto);
				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, CREATION_FAILED, ResponseStatus.FAILED);
				}
				return new ResponseEntity(dlvBlockClientMapDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Release Map Id and Client id fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllDlvBlockClientMaps() {
		try {
			List<DlvBlockClientMapDto> list = dlvBlockClientMapRepo.listAllDlvBlockClientMaps();
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
	public ResponseEntity getDlvBlockClientMapById(String clientKey) {
		try {
			if (!HelperClass.checkString(clientKey)) {
				DlvBlockClientMapDto dlvBlockClientMapDto = dlvBlockClientMapRepo.getDlvBlockClientMapById(clientKey);
				if (dlvBlockClientMapDto != null) {
					return new ResponseEntity(dlvBlockClientMapDto, HttpStatus.ACCEPTED,
							"Client map is found for client key : " + clientKey, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Client map is not available for client id : " + clientKey, ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Client key field is mandatory",
						ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteDlvBlockClientMapById(String clientKey) {
		try {
			if (!HelperClass.checkString(clientKey)) {
				String msg = dlvBlockClientMapRepo.deleteDlvBlockClientMapById(clientKey);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Client key field is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getDlvBlockClientMapByReleaseMapId(String releaseMapId) {
		try {
			if (!HelperClass.checkString(releaseMapId)) {

				List<String> listOfClients = dlvBlockClientMapRepo.getDlvBlockClientMapByReleaseMapId(releaseMapId);

				if (listOfClients != null && !listOfClients.isEmpty()) {

					return new ResponseEntity(listOfClients, HttpStatus.ACCEPTED, DATA_FOUND, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"No result found for clients : " + releaseMapId, ResponseStatus.SUCCESS);
				}

			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Delivery Block Code is mandatory field",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}

}
