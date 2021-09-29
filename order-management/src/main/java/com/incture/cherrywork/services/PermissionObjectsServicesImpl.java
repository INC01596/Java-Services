package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.PermissionObjectDto;
import com.incture.cherrywork.dtos.PermissionObjectWithDomainTextDto;
import com.incture.cherrywork.entities.AttributeDetail;
import com.incture.cherrywork.entities.AttributeDetailsDo;
import com.incture.cherrywork.entities.AttributeDetailsDto;
import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.entities.PermissionDetail;
import com.incture.cherrywork.entities.PermissionObject;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;
import com.incture.cherrywork.entities.PermissionObjectDetailsRepository;
import com.incture.cherrywork.entities.PermissionObjectDo;
import com.incture.cherrywork.entities.UserPrivilagesDo;
import com.incture.cherrywork.entities.UsersDetailDo;
import com.incture.cherrywork.repositories.AttributeDetailRepository;
import com.incture.cherrywork.repositories.AttributeDetailsRepository;
import com.incture.cherrywork.repositories.DomainTextRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.PermissionDetailRepository;
import com.incture.cherrywork.repositories.PermissionObjectRepository;
import com.incture.cherrywork.repositories.PermissionObjectsRepository;
import com.incture.cherrywork.repositories.UserDetailsRepository;
import com.incture.cherrywork.repositories.UserPrivilagesRepository;
import com.incture.cherrywork.util.DateUtils;
import com.incture.cherrywork.util.HelperClass;



@Service
public class PermissionObjectsServicesImpl implements PermissionObjectsServices {

	@Autowired
	private PermissionObjectsRepository repo;

	@Autowired
	private PermissionObjectRepository oldPermObjectRepo;

	@Autowired
	private PermissionDetailRepository oldPermObjectDetailRepo;

	@Autowired
	private AttributeDetailRepository oldAttrDetailRepo;

	@Autowired
	private DomainTextRepository domainRepo;
//
	@Autowired
	private UserPrivilagesRepository userPrivilagesRepo;

	@Autowired
	private UserDetailsRepository userDetailsRepo;

	@Autowired
	private AttributeDetailsRepository attributeDetailsRepo;

	@Autowired
	private PermissionObjectDetailsServices permissionObjectDetailsServices;

	@Autowired
	private PermissionObjectDetailsRepository permissionObjectDetailsRepo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<PermissionObjectDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide permission object id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdate(PermissionObjectDo model) {
		try {
			if (!HelperClass.checkString(model.getDomainCode())
					&& !HelperClass.checkString(model.getPermissionObjectText())) {

				Optional<DomainTextDo> domainTextDo = domainRepo.findById(model.getDomainCode());
				if (domainTextDo.isPresent()) {

					PermissionObjectDo permissionObjectDo = repo
							.findByPermissionObjectText(model.getPermissionObjectText());

					if (permissionObjectDo == null) {
						Optional<PermissionObjectDo> permObjectFromDb = repo.findById(model.getPermissionObjectGuid());

						if (!permObjectFromDb.isPresent()) {
							// Added time in seconds
							model.setCreatedAt(DateUtils.getCurrentTimeInEPOCHSec());
						}
						model.setUpdatedAt(DateUtils.getCurrentTimeInEPOCHSec());

						PermissionObjectDo entity = repo.saveAndFlush(model);
						return new ResponseEntity<>(entity, HttpStatus.CREATED);
					} else {
						return new ResponseEntity<>("Permission Object already exists with "
								+ model.getPermissionObjectText() + " name. Please changes the name.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("Domain Code doesn't exist. Please save the domain code first.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"INVALID_INPUT" + "Please provide domain code and permission object name.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteById(String id) {
		try {
			if (!HelperClass.checkString(id)) {

				Optional<PermissionObjectDo> model = repo.findById(id);

				if (model.isPresent()) {
					List<PermissionObjectDetailsDo> permissionObjectDetails = permissionObjectDetailsRepo
							.findByPermissionObjectGuid(id);

					if (permissionObjectDetails != null && !permissionObjectDetails.isEmpty()) {

						permissionObjectDetailsRepo.deleteInBatch(permissionObjectDetails);
					}
					repo.deleteById(id);

					return new ResponseEntity<>("Permission Object with " + model.get().getPermissionObjectText()
							+ " is successfully deleted.", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Permission Object is not found for GUID : " + id, HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("INVALID_INPUT" + "Please provide permission object id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG "+ e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> listAll() {
		try {
			List<PermissionObjectDo> list = repo.findAll();
			if (!list.isEmpty()) {
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> createPermisionObject(PermissionObjectDto permissionObjectDto) {
		try {
			if (!HelperClass.checkString(permissionObjectDto.getDomainCode())
					&& !HelperClass.checkString(permissionObjectDto.getPermissionObjectText())) {

				PermissionObjectDo permissionObjectDo = new PermissionObjectDo();
				permissionObjectDo.setDomainCode(permissionObjectDto.getDomainCode());
				permissionObjectDo.setPermissionObjectText(permissionObjectDto.getPermissionObjectText());

				ResponseEntity<?> responseFromPermissionObject = saveOrUpdate(permissionObjectDo);

				if (responseFromPermissionObject.getStatusCodeValue() == HttpStatus.CREATED.value()) {

					if (permissionObjectDto.getListOfPermissionObjectDetails() != null
							&& !permissionObjectDto.getListOfPermissionObjectDetails().isEmpty()) {

						List<PermissionObjectDetailsDo> permObjectDetailsDoList = new ArrayList<>();

						permissionObjectDto.getListOfPermissionObjectDetails().stream()
								.forEach(permObjectDetailsDto -> {
									PermissionObjectDetailsDo permissionObjectDetailsDo = new PermissionObjectDetailsDo();
									permissionObjectDetailsDo
											.setPermissionObjectGuid(permissionObjectDo.getPermissionObjectGuid());
									permissionObjectDetailsDo
											.setAttributeDetailsGuid(permObjectDetailsDto.getAttributeDetailsGuid());
									permissionObjectDetailsDo
											.setAttributeValue(permObjectDetailsDto.getAttributeValue());
									permissionObjectDetailsDo.setAttributeText(permObjectDetailsDto.getAttributeText());
									permObjectDetailsDoList.add(permissionObjectDetailsDo);
								});

						ResponseEntity<?> responseFromPermissionObjectDetails = permissionObjectDetailsServices
								.saveAll(permObjectDetailsDoList);
						if (responseFromPermissionObjectDetails.getStatusCodeValue() == HttpStatus.CREATED.value()) {
							return new ResponseEntity<>(permObjectDetailsDoList, HttpStatus.CREATED);
						} else {
							return responseFromPermissionObjectDetails;
						}
					} else {
						return responseFromPermissionObject;
					}
				} else {
					return responseFromPermissionObject;
				}
			} else {
				return new ResponseEntity<>(
						"INVALID_INPUT" + "Please provide domain code and permission object name.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG "+ e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findByPermName(String permName) {
		try {
			if (!HelperClass.checkString(permName)) {
				PermissionObjectDo permissionObjectDo = repo.findByPermissionObjectText(permName);
				if (permissionObjectDo != null) {
					return new ResponseEntity<>(permissionObjectDo, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>(
						"INVALID_INPUT" + "Please provide Permission object name.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findByDomainCode(String domainCode) {
		try {
			if (!HelperClass.checkString(domainCode)) {
				List<PermissionObjectDo> permissionObjectDoList = repo.findByDomainCode(domainCode);
				if (!permissionObjectDoList.isEmpty()) {
					return new ResponseEntity<>(permissionObjectDoList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide Domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG "+ e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> loadAllPermissionObjectsWithPagination(Integer pageNo, Integer pageSize, String sortBy) {
		try {
			Page<PermissionObjectDo> pagedResult = repo
					.findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending()));

			if (pagedResult.hasContent()) {

				Map<String, DomainTextDo> mapOfDomainCodes = domainRepo
						.findByDomainCodeIn(pagedResult.getContent().stream().map(PermissionObjectDo::getDomainCode)
								.collect(Collectors.toSet()))
						.stream().collect(Collectors.toMap(DomainTextDo::getDomainCode, domainCode -> domainCode,
								(oldValue, newValue) -> oldValue));

				List<PermissionObjectWithDomainTextDto> listOfPermissionObjects = ObjectMapperUtils
						.mapAll(pagedResult.getContent(), PermissionObjectWithDomainTextDto.class).stream()
						.peek(permObject -> {
							if (mapOfDomainCodes.containsKey(permObject.getDomainCode())) {
								DomainTextDo domainCodeDo = mapOfDomainCodes.get(permObject.getDomainCode());
								permObject.setDomainText(domainCodeDo.getDomainText());
								permObject.setLanguageKey(domainCodeDo.getLanguageKey());
							}
						}).collect(Collectors.toList());

				return new ResponseEntity<>(listOfPermissionObjects, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findByIdWithDetails(String id) {
		try {
			if (!HelperClass.checkString(id)) {

				Optional<PermissionObjectDo> model = repo.findById(id);

				if (model.isPresent()) {

					List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepo
							.findByPermissionObjectGuid(id);

					if (!permissionObjectDetailsList.isEmpty()) {

						List<AttributeDetailsDo> attributeDetailsDoList = attributeDetailsRepo
								.findByDomainCodeOrderByAttributeIdAsc(model.get().getDomainCode());

						if (!attributeDetailsDoList.isEmpty()) {

							Map<String, List<PermissionObjectDetailsDo>> mapOfAttributesGuidWithValues = permissionObjectDetailsList
									.stream()
									.collect(Collectors.groupingBy(PermissionObjectDetailsDo::getAttributeDetailsGuid,
											Collectors.mapping(permObjectDetails -> permObjectDetails,
													Collectors.toList())));

							List<AttributeDetailsDto> attributeDetailsDtoList = ObjectMapperUtils
									.mapAll(attributeDetailsDoList, AttributeDetailsDto.class);

							attributeDetailsDtoList.stream().forEach(attrDetailsDto -> attrDetailsDto
									.setPermissionObjectDetailsDos(mapOfAttributesGuidWithValues
											.containsKey(attrDetailsDto.getAttributeDetailsGuid())
													? mapOfAttributesGuidWithValues
															.get(attrDetailsDto.getAttributeDetailsGuid())
													: null));

							return new ResponseEntity<>(attributeDetailsDtoList, HttpStatus.OK);

						} else {
							return new ResponseEntity<>(
									"No Attributes found for domain code : " + model.get().getDomainCode(),
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("No Attribute values are assigned to Permission Object "
								+ model.get().getPermissionObjectText(), HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("INVALID_INPUT "+ "Please provide permission object id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findUsersById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				List<UserPrivilagesDo> userPrivilagesDoList = userPrivilagesRepo.findByPermissionObjectGuid(id);
				if (!userPrivilagesDoList.isEmpty()) {

					List<UsersDetailDo> userDetailsList = userDetailsRepo.findByUserGuidIn(userPrivilagesDoList.stream()
							.map(UserPrivilagesDo::getUserGuid).collect(Collectors.toList()));

					StringJoiner sj = new StringJoiner(", ");
					userDetailsList.stream().map(UsersDetailDo::getUserId).forEach(userPid -> sj.add(userPid));

					return new ResponseEntity<>("Permission Object is assigned to " + sj.toString() + " users.",
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>("DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT" + "Please provide Permission Object Guid.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findEmptyAttributeForPermObjects(List<String> permissionObjectGuidList) {
		try {
			if (permissionObjectGuidList != null && !permissionObjectGuidList.isEmpty()) {

				List<PermissionObjectDo> permissionObjectDoList = repo
						.findByPermissionObjectGuidIn(permissionObjectGuidList);

				if (permissionObjectDoList != null && !permissionObjectDoList.isEmpty()) {

					Map<String, List<String>> distinctDomainCodesWithPermObjectGuids = permissionObjectDoList.stream()
							.collect(Collectors.groupingBy(PermissionObjectDo::getDomainCode, Collectors
									.mapping(PermissionObjectDo::getPermissionObjectGuid, Collectors.toList())));
					StringJoiner sj = new StringJoiner(", ");
					if (distinctDomainCodesWithPermObjectGuids != null
							&& !distinctDomainCodesWithPermObjectGuids.isEmpty()) {

						distinctDomainCodesWithPermObjectGuids.forEach((domainCode, permObjectGuidList) -> {
							ResponseEntity<?> resFromPermObj = checkDacAttributeForPermObjects(permissionObjectGuidList,
									domainCode);
							if (resFromPermObj.getStatusCodeValue() == HttpStatus.NOT_ACCEPTABLE.value()) {
								sj.add((String) resFromPermObj.getBody());
							}
						});
					}

					if (sj.length() != 0) {
						return new ResponseEntity<>(sj.toString(), HttpStatus.OK);
					} else {
						return new ResponseEntity<>("Validated Successfully.", HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<>(
							"Permission Object guid doesn't exist. Please use valid Permission Object guid.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide Permission Object Guid List and Domain Code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<?> checkDacAttributeForPermObjects(Collection<String> permissionObjectGuidList,
			String domainCode) {
		try {
			if (!HelperClass.checkString(domainCode) && permissionObjectGuidList != null
					&& !permissionObjectGuidList.isEmpty()) {

				List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepo
						.findByPermissionObjectGuidIn(permissionObjectGuidList.stream().collect(Collectors.toSet()));

				if (permissionObjectDetailsList != null && !permissionObjectDetailsList.isEmpty()) {

					Long attributesCountInDomain = attributeDetailsRepo.countByDomainCode(domainCode);

					if (attributesCountInDomain != null && attributesCountInDomain != 0) {

						Map<String, List<PermissionObjectDetailsDo>> mapOfAttributesGuidWithValues = permissionObjectDetailsList
								.stream()
								.collect(Collectors.groupingBy(PermissionObjectDetailsDo::getAttributeDetailsGuid,
										Collectors.mapping(permObjectDetails -> permObjectDetails,
												Collectors.toList())));

						if (attributesCountInDomain.intValue() == mapOfAttributesGuidWithValues.size()) {
							return new ResponseEntity<>("Validated Successfully.", HttpStatus.OK);
						} else {
							return new ResponseEntity<>("User is assigned to " + mapOfAttributesGuidWithValues.size()
									+ " out of " + attributesCountInDomain + " attributes for domain " + domainCode
									+ ", Would you like to continue", HttpStatus.NOT_ACCEPTABLE);
						}
					} else {
						return new ResponseEntity<>("No Attributes found for domain code : " + domainCode,
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>(
							"Permission object is assigned but doesn't have any assignment to attributes.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide Permission Object Guid List and Domain Code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> migrateOldDacPermissionObjectsToNew() {
		try {

			List<AttributeDetail> attrDetailsOldDac = oldAttrDetailRepo.findAll();
			Map<String, String> mapOfAttributes = new HashMap<>();

			if (!attrDetailsOldDac.isEmpty()) {

				attrDetailsOldDac.stream().forEach(attrDetailOld -> {

					AttributeDetailsDo attrDetailsDo = attributeDetailsRepo
							.findByAttributeId(attrDetailOld.getAttributeId());
					mapOfAttributes.put(attrDetailOld.getAttributeId(), attrDetailsDo.getAttributeDetailsGuid());

				});

				List<PermissionObject> permObjectList = oldPermObjectRepo.findAll();

				if (!permObjectList.isEmpty()) {

					permObjectList.stream().forEach(permObj -> {

						PermissionObjectDo permObjectDo = new PermissionObjectDo();
						permObjectDo.setDomainCode("cc");
						permObjectDo.setPermissionObjectText(permObj.getDesc());
						permObjectDo.setCreatedAt(permObj.getCreatedAt());
						permObjectDo.setCreatedBy(permObj.getCreatedBy());
						permObjectDo.setUpdatedAt(permObj.getUpdatedAt());
						permObjectDo.setUpdatedBy(permObj.getUpdatedBy());

						PermissionObjectDo permObjectDoFromDB = repo.save(permObjectDo);

						List<PermissionDetail> permObjectDetailsList = oldPermObjectDetailRepo
								.findByPermissionId(permObj);

						if (!permObjectDetailsList.isEmpty()) {

							List<PermissionObjectDetailsDo> listPermObjDetailsDo = new ArrayList<>();

							permObjectDetailsList.stream().forEach(permObjDetail -> {

								PermissionObjectDetailsDo permObjectDetailsDo = new PermissionObjectDetailsDo();
								permObjectDetailsDo.setAttributeDetailsGuid(
										mapOfAttributes.get(permObjDetail.getAttributeId().getAttributeId()));
								permObjectDetailsDo.setAttributeText(permObjDetail.getAttributeValueDesc());
								permObjectDetailsDo.setAttributeValue(permObjDetail.getAttributeValue());
								permObjectDetailsDo
										.setPermissionObjectGuid(permObjectDoFromDB.getPermissionObjectGuid());
								listPermObjDetailsDo.add(permObjectDetailsDo);
							});
							permissionObjectDetailsRepo.saveAll(listPermObjDetailsDo);
						} else {
							return;
						}

					});

				} else {
					return new ResponseEntity<>("Permission Objects not found in old dac app", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("Attributes not found in old dac app", HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>("Validated Successfully.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findUserDetailsById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				List<UserPrivilagesDo> userPrivilagesDoList = userPrivilagesRepo.findByPermissionObjectGuid(id);
				if (!userPrivilagesDoList.isEmpty()) {

					List<UsersDetailDo> userDetailsList = userDetailsRepo.findByUserGuidIn(userPrivilagesDoList.stream()
							.map(UserPrivilagesDo::getUserGuid).collect(Collectors.toList()));

					return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT" + "Please provide Permission Object Guid.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> findUsersCountById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				List<UserPrivilagesDo> userPrivilagesDoList = userPrivilagesRepo.findByPermissionObjectGuid(id);
				if (!userPrivilagesDoList.isEmpty()) {

					List<UsersDetailDo> userDetailsList = userDetailsRepo.findByUserGuidIn(userPrivilagesDoList.stream()
							.map(UserPrivilagesDo::getUserGuid).collect(Collectors.toList()));

					return new ResponseEntity<>(userDetailsList.size(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT" + "Please provide Permission Object Guid.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	
	

	

	

	
}

