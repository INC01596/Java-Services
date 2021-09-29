package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.AttributeDetailsDo;
import com.incture.cherrywork.entities.AttributeDetailsDto;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;
import com.incture.cherrywork.entities.PermissionObjectDetailsRepository;
import com.incture.cherrywork.entities.PermissionObjectDo;
import com.incture.cherrywork.repositories.AttributeDetailsRepository;
import com.incture.cherrywork.repositories.PermissionObjectsRepository;
import com.incture.cherrywork.util.HelperClass;


@Service
public class PermissionObjectDetailsServicesImpl implements PermissionObjectDetailsServices {

	@Autowired
	private PermissionObjectDetailsRepository repo;

	@Autowired
	private PermissionObjectsRepository permissionObjectsRepo;

	@Autowired
	private AttributeDetailsRepository attributeDetailsRepo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<PermissionObjectDetailsDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT "+ "Please provide Permission Object details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdate(PermissionObjectDetailsDo model) {
		try {
			if (!HelperClass.checkString(model.getAttributeValue())
					&& !HelperClass.checkString(model.getPermissionObjectGuid())
					&& !HelperClass.checkString(model.getAttributeDetailsGuid())) {

				Optional<PermissionObjectDo> permissionObjectDo = permissionObjectsRepo
						.findById(model.getPermissionObjectGuid());

				if (permissionObjectDo.isPresent()) {
					Optional<AttributeDetailsDo> attributeDetailsDo = attributeDetailsRepo
							.findById(model.getAttributeDetailsGuid());

					if (attributeDetailsDo.isPresent()) {
						PermissionObjectDetailsDo entity = repo.saveAndFlush(model);
						return new ResponseEntity<>(entity, HttpStatus.CREATED);
					} else {
						return new ResponseEntity<>(
								"Attribute details guid doesn't exist. Please provide valid Attribute guid.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>(
							"Permission Object guid doesn't exist. Please provide valid Permission Object guid.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide Attribute value, Permission Object guid and Attribute guid.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				repo.deleteById(id);
				return new ResponseEntity<>("Permission Object Details " + id + " is successfully deleted.",
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT "+ "Please provide Permission Object details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> listAll() {
		try {
			List<PermissionObjectDetailsDo> list = repo.findAll();
			if (!list.isEmpty()) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveAll(List<PermissionObjectDetailsDo> list) {
		try {
			Optional<PermissionObjectDo> permissionObjectDo = permissionObjectsRepo
					.findById(list.get(0).getPermissionObjectGuid());
			if (permissionObjectDo.isPresent()) {
				Set<String> setOfAttributeGuids = list.stream().map(PermissionObjectDetailsDo::getAttributeDetailsGuid)
						.collect(Collectors.toSet());
				List<AttributeDetailsDo> attributeDetailsDoList = attributeDetailsRepo
						.findByAttributeDetailsGuidIn(setOfAttributeGuids);
				if (!attributeDetailsDoList.isEmpty() && setOfAttributeGuids.size() == attributeDetailsDoList.size()) {
					List<PermissionObjectDetailsDo> entityList = repo.saveAll(list);
					return new ResponseEntity<>(entityList, HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>(
							"AppErrorMsgConstants.INVALID_INPUT"
									+ "Attribute detail guids doesn't exist. Please provide valid Attribute guids.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"Permission Object guid doesn't exist. Please provide valid Permission Object guid.",
						HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> modifyPermisionObjectDetails(List<AttributeDetailsDto> listOfAttributes) {
		try {
			if (listOfAttributes != null && !listOfAttributes.isEmpty()) {

				List<PermissionObjectDetailsDo> permObjectDetailsDoList = new ArrayList<>();

				listOfAttributes.stream().forEach(attribute -> {
					if (attribute.getPermissionObjectDetailsDos() != null
							&& !attribute.getPermissionObjectDetailsDos().isEmpty()) {
						permObjectDetailsDoList.addAll(attribute.getPermissionObjectDetailsDos());
					}
				});

				if (!permObjectDetailsDoList.isEmpty()) {

					String permissionObjectGuid = permObjectDetailsDoList.get(0).getPermissionObjectGuid();

					List<PermissionObjectDetailsDo> permissionObjectDetailsInDb = repo
							.findByPermissionObjectGuid(permissionObjectGuid);

					if (permissionObjectDetailsInDb != null && !permissionObjectDetailsInDb.isEmpty()) {

						repo.deleteInBatch(permissionObjectDetailsInDb);
					}

					ResponseEntity<?> responseFromPermissionObjectDetails = saveAll(permObjectDetailsDoList);
					if (responseFromPermissionObjectDetails.getStatusCodeValue() == HttpStatus.CREATED.value()) {
						return new ResponseEntity<>(permObjectDetailsDoList, HttpStatus.ACCEPTED);
					} else {
						return responseFromPermissionObjectDetails;
					}
				} else {
					return new ResponseEntity<>("No Attribute values are assigned to Permission Object.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide list of attributes.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
}

