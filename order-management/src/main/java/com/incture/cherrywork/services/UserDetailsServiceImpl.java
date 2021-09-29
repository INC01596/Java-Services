package com.incture.cherrywork.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.UserDetailsWithRightsForExcel;
import com.incture.cherrywork.dtos.UserInfo;
import com.incture.cherrywork.dtos.UserList;
import com.incture.cherrywork.entities.AttributeDetailsDo;
import com.incture.cherrywork.entities.AttributeDetailsDto;
import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;
import com.incture.cherrywork.entities.PermissionObjectDetailsRepository;
import com.incture.cherrywork.entities.PermissionObjectDo;
import com.incture.cherrywork.entities.UserPrivilagesDo;
import com.incture.cherrywork.entities.UsersDetailDo;
import com.incture.cherrywork.repositories.AttributeDetailsRepository;
import com.incture.cherrywork.repositories.DomainTextRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.PermissionObjectsRepository;
import com.incture.cherrywork.repositories.UserDetailsRepository;
import com.incture.cherrywork.repositories.UserPrivilagesRepository;
import com.incture.cherrywork.sales.constants.ApplicationConstants;
import com.incture.cherrywork.util.DateUtils;
import com.incture.cherrywork.util.ExcelUtils;
import com.incture.cherrywork.util.HelperClass;


@Service
public class UserDetailsServiceImpl implements UserDetailsServices {

	private static final String AppErrorMsgConstants = null;

	@Autowired
	private UserDetailsRepository repo;

	@Autowired
	private UserPrivilagesRepository userPrivilagesRepo;

	@Autowired
	private PermissionObjectsRepository permObjectsRepository;

	@Autowired
	private AttributeDetailsRepository attributeDetailsRepo;

	@Autowired
	private DomainTextRepository domainTextRepo;

	@Autowired
	private PermissionObjectDetailsRepository permissionObjectDetailsRepo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<UsersDetailDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("INVALID_INPUT" + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> saveOrUpdate(UsersDetailDo model) {
		try {
			if (!HelperClass.checkString(model.getEmail()) && !HelperClass.checkString(model.getUserId())) {

				UsersDetailDo userDetailsDo = repo.findByUserId(model.getUserId());

				if (userDetailsDo == null) {
					model.setCreatedAt(DateUtils.getCurrentTimeInEPOCHSec());
					model.setUpdatedAt(DateUtils.getCurrentTimeInEPOCHSec());
					UsersDetailDo entity = repo.saveAndFlush(model);
					return new ResponseEntity<>(entity, HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>("Current User pid " + model.getUserId()
							+ " already exist. Please create user in IAS first.", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("INVALID_INPUT" + "Please provide User Pid and Email.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	

	
	@Override
	public ResponseEntity<?> getAll() {
		
		return ResponseEntity.ok(repo.findAll());
	}



	@Override
	public ResponseEntity<?> saveAllUsersFromIdp() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	

	
	

	@Override
	public ResponseEntity<Object> create(UsersDetailDo dos) {
		// TODO Auto-generated method stub
		return null;
	}

//
	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				repo.deleteById(id);
				return new ResponseEntity<>("User Details for " + id + " is successfully deleted.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG "+ e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<?> listAll() {
		try {
			List<UsersDetailDo> list = repo.findAll();
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
	public ResponseEntity<?> findPermissionObjectAndAttributeDetails(String userId, String domainCode) {
		try {
			if (!HelperClass.checkString(userId) && !HelperClass.checkString(domainCode)) {

				UsersDetailDo usersDetailDo = repo.findByUserId(userId);

				if (usersDetailDo != null) {

					Optional<DomainTextDo> domainTextDo = domainTextRepo.findById(domainCode);

					if (domainTextDo.isPresent()) {

						List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepo
								.findByUserGuidAndDomainCode(usersDetailDo.getUserGuid(), domainCode);

						if (!userPrivilagesList.isEmpty()) {

							Set<String> distinctPermissionObjectGuid = userPrivilagesList.stream()
									.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toSet());

							List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepo
									.findByPermissionObjectGuidIn(distinctPermissionObjectGuid);

							if (!permissionObjectDetailsList.isEmpty()) {

								List<AttributeDetailsDo> attributeDetailsDoList = attributeDetailsRepo
										.findByDomainCodeOrderByAttributeIdAsc(domainCode);

								if (!attributeDetailsDoList.isEmpty()) {

									Map<String, List<PermissionObjectDetailsDo>> mapOfAttributesGuidWithValues = permissionObjectDetailsList
											.stream()
											.collect(Collectors.groupingBy(
													PermissionObjectDetailsDo::getAttributeDetailsGuid,
													Collectors.mapping(permObjectDetails -> permObjectDetails,
															Collectors.toList())));

									List<AttributeDetailsDto> attributeDetailsDtoList = ObjectMapperUtils
											.mapAll(attributeDetailsDoList, AttributeDetailsDto.class);

									attributeDetailsDtoList.stream().forEach(attrDetailsDto -> {
										if (mapOfAttributesGuidWithValues
												.containsKey(attrDetailsDto.getAttributeDetailsGuid())) {
											if (mapOfAttributesGuidWithValues
													.get(attrDetailsDto.getAttributeDetailsGuid()).stream()
													.anyMatch(k -> k.getAttributeValue().contains("*"))) {
												PermissionObjectDetailsDo permObjDetail = new PermissionObjectDetailsDo();
												permObjDetail.setAttributeValue("*");
												attrDetailsDto.setPermissionObjectDetailsDos(
														Stream.of(permObjDetail).collect(Collectors.toList()));
											} else {

												List<PermissionObjectDetailsDo> listOfAttributeValues = mapOfAttributesGuidWithValues
														.get(attrDetailsDto.getAttributeDetailsGuid()).stream()
														.collect(Collectors
																.toCollection(() -> new TreeSet<>(Comparator.comparing(
																		PermissionObjectDetailsDo::getAttributeValue))))
														.stream().collect(Collectors.toList());
												attrDetailsDto.setPermissionObjectDetailsDos(listOfAttributeValues);
											}
										} else {
											attrDetailsDto.setPermissionObjectDetailsDos(null);
										}
									});
									return new ResponseEntity<>(attributeDetailsDtoList, HttpStatus.OK);
								} else {
									return new ResponseEntity<>("No Attributes found for domain code : " + domainCode,
											HttpStatus.CONFLICT);
								}
							} else {
								return new ResponseEntity<>(
										"Permission object is assigned but doesn't have any assignment to attributes for user : "
												+ userId,
										HttpStatus.CONFLICT);
							}
						} else {
							return new ResponseEntity<>("Permission object is not assigned to user : " + userId,
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Domain Code not found with the id : " + domainCode,
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User not found with the id : " + userId, HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ResponseEntity<?> saveAllUsersFromIdp() {
//		try {
//			Integer count = DkshApplicationConstants.IAS_USERS_LIST_COUNT;
//			Integer startIndex = DkshApplicationConstants.IAS_USERS_LIST_START_INDEX;
//
//			Double countD = new Double(count);
//
//			Double startIndexD = new Double(startIndex);
//			ResponseEntity<?> responseFromIas = HelperClass.findAllidpUsersForExcel(startIndexD.intValue(),
//					countD.intValue());
//
//			if (responseFromIas.getStatusCodeValue() == HttpStatus.OK.value()) {
//
//				IasUserList ias = (IasUserList) responseFromIas.getBody();
//				List<IasResourceDto> listOfUsers = ias.getListOfUsers();
//
//				System.err.println("listOfUsers : " + listOfUsers);
//				Double totalResult = ias.getTotalResults().doubleValue();
//
//				if (totalResult.intValue() != 0) {
//
//					ResponseEntity<?> responseFromFetchingCountryMap = HelperClass.findAllCountryTexts();
//
//					if (responseFromFetchingCountryMap.getStatusCodeValue() == HttpStatus.OK.value()) {
//
//						List<UsersDetailDo> listOfUsersInHana = new ArrayList<>();
//
//						Map<String, Object> mapOfCountryText = (Map<String, Object>) responseFromFetchingCountryMap
//								.getBody();
//
//						System.err.println("mapOfCountryText : " + mapOfCountryText);
//
//						if (totalResult > (startIndexD * countD)) {
//							for (int i = 0; i < Math.ceil(totalResult / (startIndex * count) - 1); i++) {
//								startIndexD = startIndexD + countD;
//								IasUserList iasTemp = (IasUserList) HelperClass
//										.findAllidpUsersForExcel(startIndexD.intValue(), countD.intValue()).getBody();
//								listOfUsers.addAll(iasTemp.getListOfUsers());
//							}
//
//						}
//
//						for (IasResourceDto iasResourceDto : listOfUsers) {
//
//							UsersDetailDo userDetailsDo = repo.findByUserId(iasResourceDto.getPid());
//
//							if (userDetailsDo == null) {
//								userDetailsDo = new UsersDetailDo();
//								userDetailsDo.setCreatedAt(DateUtils.getCurrentTimeInEPOCHSec());
//								userDetailsDo.setCreatedBy("ADMIN");
//							}
//
//							userDetailsDo.setUpdatedAt(DateUtils.getCurrentTimeInEPOCHSec());
//							userDetailsDo.setEmail(iasResourceDto.getEmail());
//							userDetailsDo.setUserId(iasResourceDto.getPid());
//							userDetailsDo.setFirstName(iasResourceDto.getFirstName());
//							userDetailsDo.setLastName(iasResourceDto.getLastName());
//							userDetailsDo.setUserGroup(iasResourceDto.getUserGroup());
//							if (!HelperClass.checkString(iasResourceDto.getCountry())) {
//								userDetailsDo.setCountry(mapOfCountryText.get(iasResourceDto.getCountry()).toString());
//							}
//							listOfUsersInHana.add(userDetailsDo);
//
//						}
//
//						repo.saveAll(listOfUsersInHana);
//
//						return new ResponseEntity<>("Users updated successfully", HttpStatus.CREATED);
//					} else {
//						return responseFromFetchingCountryMap;
//					}
//				} else {
//					return new ResponseEntity<>("No data found", HttpStatus.NO_CONTENT);
//				}
//
//			} else {
//				return responseFromIas;
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
	@Override
	public ResponseEntity<?> findAllPermissionObjects(String userPid) {
		try {
			if (!HelperClass.checkString(userPid)) {

				UsersDetailDo usersDetailDo = repo.findByUserId(userPid);

				if (usersDetailDo != null) {

					List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepo
							.findByUserGuid(usersDetailDo.getUserGuid());

					if (userPrivilagesList != null && !userPrivilagesList.isEmpty()) {

						List<String> permissionObjectGuidList = userPrivilagesList.stream()
								.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toList());

						List<PermissionObjectDo> permissionObjectList = permObjectsRepository
								.findByPermissionObjectGuidInOrderByCreatedAtDesc(permissionObjectGuidList);

						if (permissionObjectList != null && !permissionObjectList.isEmpty()) {

							return new ResponseEntity<>(permissionObjectList, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(
									"Permission object is assigned but data of permission object doesn't exist for user : "
											+ userPid,
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Permission object is not assigned to user : " + userPid,
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User not found with the id : " + userPid, HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT "+ "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteByUserPid(String userPid) {
		try {
			if (!HelperClass.checkString(userPid)) {
				UsersDetailDo usersDetailDo = repo.findByUserId(userPid);
				if (usersDetailDo != null) {
					repo.delete(usersDetailDo);
					return new ResponseEntity<>(userPid + " is successfully deleted.", HttpStatus.ACCEPTED);
				} else {
					return new ResponseEntity<>(userPid + " is not found.", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide User Pid.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateUser(UsersDetailDo model) {
		try {
			if (!HelperClass.checkString(model.getEmail()) && !HelperClass.checkString(model.getUserId())) {

				UsersDetailDo userDetailsDo = repo.findByUserId(model.getUserId());

				if (userDetailsDo != null) {
					model.setUpdatedAt(DateUtils.getCurrentTimeInEPOCHSec());
					model.setUserGuid(userDetailsDo.getUserGuid());
				UsersDetailDo entity = repo.saveAndFlush(model);
		return new ResponseEntity<>(entity, HttpStatus.ACCEPTED);
		} else {
				return new ResponseEntity<>("Current User pid " + model.getUserId()
						+ " doesn't exist. Please create user in IAS first.", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("INVALID_INPUT "+ "Please provide User Pid and Email.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

	


	@Override
	public ResponseEntity<?> findAllRightsForUserInDomain(String userPid, String domainCode) {
		try {
			if (!HelperClass.checkString(userPid) && !HelperClass.checkString(domainCode)) {

				UsersDetailDo usersDetailDo = repo.findByUserId(userPid);

				if (usersDetailDo != null) {

					Optional<DomainTextDo> domainTextDo = domainTextRepo.findById(domainCode);

					if (domainTextDo.isPresent()) {

						List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepo
								.findByUserGuidAndDomainCode(usersDetailDo.getUserGuid(), domainCode);

						if (!userPrivilagesList.isEmpty()) {

							Set<String> distinctPermissionObjectGuid = userPrivilagesList.stream()
									.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toSet());

							List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepo
									.findByPermissionObjectGuidIn(distinctPermissionObjectGuid);

							if (!permissionObjectDetailsList.isEmpty()) {

								Map<String, AttributeDetailsDo> attributeDetailsDoMap = attributeDetailsRepo
										.findByDomainCodeOrderByAttributeIdAsc(domainCode).stream()
										.collect(Collectors.toMap(AttributeDetailsDo::getAttributeDetailsGuid,
												attrDetails -> attrDetails, (oldValue, newValue) -> newValue));

								if (!attributeDetailsDoMap.isEmpty()) {

									Map<String, Set<String>> mapOfAttributesGuidWithValues = permissionObjectDetailsList
											.stream()
											.collect(Collectors.groupingBy(
													PermissionObjectDetailsDo::getAttributeDetailsGuid,
													Collectors.mapping(PermissionObjectDetailsDo::getAttributeValue,
															Collectors.toSet())));

									TreeMap<String, String> mapOfAttributeValues = new TreeMap<>();

									for (Map.Entry<String, AttributeDetailsDo> entry : attributeDetailsDoMap
											.entrySet()) {

										StringJoiner sj = new StringJoiner("@");
										if (mapOfAttributesGuidWithValues.containsKey(entry.getKey())) {
											if (mapOfAttributesGuidWithValues.get(entry.getKey()).stream()
													.anyMatch(k -> k.contains("*"))) {
												sj.add("*");
											} else {
												mapOfAttributesGuidWithValues.get(entry.getKey()).stream()
														.forEach(attrValue -> sj.add(attrValue));
											}
										}
										mapOfAttributeValues.put(entry.getValue().getAttributeId(),
												sj.length() != 0 ? sj.toString() : null);
									}

									mapOfAttributeValues.put("userId", userPid);
									mapOfAttributeValues.put("email", usersDetailDo.getEmail());
									mapOfAttributeValues.put("domainCode", domainCode);
									mapOfAttributeValues.put("domainCodeText", domainTextDo.get().getDomainText());
									mapOfAttributeValues.put("message",
											mapOfAttributesGuidWithValues.size() != attributeDetailsDoMap.size()
													? "User is assigned to " + mapOfAttributesGuidWithValues.size()
															+ " out of " + attributeDetailsDoMap.size()
															+ " attributes for domain " + domainCode
													: null);

									return new ResponseEntity<>(mapOfAttributeValues, HttpStatus.OK);
								} else {
									return new ResponseEntity<>("No Attributes found for domain code : " + domainCode,
											HttpStatus.CONFLICT);
								}
							} else {
								return new ResponseEntity<>(
										"Permission object is assigned but doesn't have any assignment to attributes for user : "
												+ userPid,
										HttpStatus.CONFLICT);
							}
						} else {
							return new ResponseEntity<>("Permission object is not assigned to user : " + userPid,
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Domain Code not found with the id : " + domainCode,
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User not found with the id : " + userPid, HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MS" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> downloadExcelForAllUsersRightsInDomain(String domainCode) {
		try {
			if (!HelperClass.checkString(domainCode)) {

				List<UsersDetailDo> usersDetailDoList = repo.findAll();

				if (!usersDetailDoList.isEmpty()) {

					Optional<DomainTextDo> domainTextDo = domainTextRepo.findById(domainCode);

					if (domainTextDo.isPresent()) {

						Map<String, AttributeDetailsDo> attributeDetailsDoMap = attributeDetailsRepo
								.findByDomainCodeOrderByAttributeIdAsc(domainCode).stream()
								.collect(Collectors.toMap(AttributeDetailsDo::getAttributeDetailsGuid,
										attrDetails -> attrDetails, (oldValue, newValue) -> newValue));

						if (!attributeDetailsDoMap.isEmpty()) {

							List<UserDetailsWithRightsForExcel> listOfUsers = new ArrayList<>();

							for (UsersDetailDo usersDetailDo : usersDetailDoList) {

								UserDetailsWithRightsForExcel userDetail = new UserDetailsWithRightsForExcel();

								List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepo
										.findByUserGuidAndDomainCode(usersDetailDo.getUserGuid(), domainCode);

								userDetail.setUSERID(usersDetailDo.getUserId());
								userDetail.setEMAIL(usersDetailDo.getEmail());
								userDetail.setFIRST_NAME(usersDetailDo.getFirstName());
								userDetail.setLAST_NAME(usersDetailDo.getLastName());
								userDetail.setUSER_COUNTRY(usersDetailDo.getCountry());
								userDetail.setGROUP_NAMES(usersDetailDo.getUserGroup());
								userDetail.setDOMAIN_NAME(domainTextDo.get().getDomainCode() + "("
										+ domainTextDo.get().getDomainText() + ")");

								if (!userPrivilagesList.isEmpty()) {

									Set<String> distinctPermissionObjectGuid = userPrivilagesList.stream()
											.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toSet());

									StringJoiner sjForPermissionObjectNames = new StringJoiner(",");
									permObjectsRepository
											.findByPermissionObjectGuidIn(
													distinctPermissionObjectGuid.stream().collect(Collectors.toList()))
											.stream().forEach(permObj -> sjForPermissionObjectNames
													.add(permObj.getPermissionObjectText()));

									userDetail.setASSIGNED_PERMISSION_OBJECTS(sjForPermissionObjectNames.toString());

									List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepo
											.findByPermissionObjectGuidIn(distinctPermissionObjectGuid);

									if (!permissionObjectDetailsList.isEmpty()) {

										Map<String, Set<String>> mapOfAttributesGuidWithValues = permissionObjectDetailsList
												.stream()
												.collect(Collectors.groupingBy(
														PermissionObjectDetailsDo::getAttributeDetailsGuid,
														Collectors.mapping(PermissionObjectDetailsDo::getAttributeValue,
																Collectors.toSet())));

										for (Map.Entry<String, AttributeDetailsDo> entry : attributeDetailsDoMap
												.entrySet()) {

											StringJoiner sj = new StringJoiner("@");
											if (mapOfAttributesGuidWithValues.containsKey(entry.getKey())) {
												if (mapOfAttributesGuidWithValues.get(entry.getKey()).stream()
														.anyMatch(k -> k.contains("*"))) {
													sj.add("*");
												} else {
													mapOfAttributesGuidWithValues.get(entry.getKey()).stream()
															.forEach(attrValue -> sj.add(attrValue));
												}
											}

											userDetail = settingUserRights(entry.getValue().getAttributeId(),
													sj.length() != 0 ? sj.toString() : null, userDetail);
										}
									}
								}
								listOfUsers.add(userDetail);
							}

							File file = new File(ApplicationConstants.IAS_USERS_LIST_FILE_NAME + ".csv");

							FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(ExcelUtils.writeToExcel(
							ApplicationConstants.IAS_USERS_LIST_EXCEL_SHEET_NAME, listOfUsers)));

							return ResponseEntity.ok()
									.header("Content-Disposition",
											"attachment; filename=" + ApplicationConstants.IAS_USERS_LIST_FILE_NAME
													+ ".csv")
									.contentLength(file.length()).contentType(MediaType.parseMediaType("text/csv"))
									.body(new FileSystemResource(file));
						} else {
							return new ResponseEntity<>("Attributes not found for domain : " + domainCode,
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Domain Code not found for : " + domainCode, HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("Users not found", HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public UserDetailsWithRightsForExcel settingUserRights(String key, String value,
			UserDetailsWithRightsForExcel userDetail) {
		switch (key) {
		case "ATR01":
			userDetail.setSALES_ORG(value);
			break;
		case "ATR02":
			userDetail.setDIST_CHANNEL(value);
			break;
		case "ATR03":
			userDetail.setDIVISION(value);
			break;
		case "ATR04":
			userDetail.setMATERIAL_GROUP(value);
			break;
		case "ATR05":
			userDetail.setMATERIAL_GROUP_4(value);
			break;
		case "ATR06":
			userDetail.setCUSTOMER_CODE(value);
			break;
		case "ATR07":
			userDetail.setMATERIAL(value);
			break;
		case "ATR08":
			userDetail.setORDER_TYPE(value);
			break;
		case "ATR09":
			userDetail.setPLANT(value);
			break;
		case "ATR10":
			userDetail.setSTORAGE_LOCATION(value);
			break;
		case "ATR11":
			userDetail.setMATERIAL_GROUP_1(value);
			break;
		case "ATR12":
			userDetail.setMATERIAL_GROUP_2(value);
			break;
		case "ATR13":
			userDetail.setMATERIAL_GROUP_3(value);
			break;
		case "ATR14":
			userDetail.setMATERIAL_GROUP_5(value);
			break;
		case "ATR15":
			userDetail.setPRODUCT_HIERARCHY_1(value);
			break;
		case "ATR16":
			userDetail.setPRODUCT_HIERARCHY_2(value);
			break;
		case "ATR17":
			userDetail.setPRODUCT_HIERARCHY_3(value);
			break;
		case "ATR18":
			userDetail.setPRODUCT_HIERARCHY_4(value);
			break;
		case "ATR19":
			userDetail.setPRODUCT_HIERARCHY_5(value);
			break;
		case "ATR20":
			userDetail.setPRODUCT_HIERARCHY_6(value);
			break;
		case "ATR21":
			userDetail.setPRODUCT_HIERARCHY_7(value);
			break;
		case "ATR22":
			userDetail.setCUSTOMER_GROUP(value);
			break;
		case "ATR23":
			userDetail.setCUSTOMER_GROUP_1(value);
			break;
		case "ATR24":
			userDetail.setCUSTOMER_GROUP_2(value);
			break;
		case "ATR25":
			userDetail.setCUSTOMER_GROUP_3(value);
			break;
		case "ATR26":
			userDetail.setCUSTOMER_GROUP_4(value);
			break;
		case "ATR27":
			userDetail.setCUSTOMER_GROUP_5(value);
			break;
		case "ATR28":
			userDetail.setDISTRICT(value);
			break;
		case "ATR29":
			userDetail.setCOUNTRY(value);
			break;
		}
		return userDetail;
	}

	@Override
	public ResponseEntity<?> findAllAvailablePermObjects(String userPid) {
		try {
			if (!HelperClass.checkString(userPid)) {

				UsersDetailDo usersDetailDo = repo.findByUserId(userPid);

				if (usersDetailDo != null) {

					List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepo
							.findByUserGuid(usersDetailDo.getUserGuid());
					List<PermissionObjectDo> permissionObjectList = null;
					if (userPrivilagesList != null && !userPrivilagesList.isEmpty()) {

						List<String> permissionObjectGuidList = userPrivilagesList.stream()
								.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toList());

						permissionObjectList = permObjectsRepository.findByPermissionObjectGuidNotIn(
								permissionObjectGuidList, Sort.by("updatedAt").descending());
					} else {
						permissionObjectList = permObjectsRepository.findAll(Sort.by("updatedAt").descending());
					}
					return new ResponseEntity<>(permissionObjectList, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("User not found with the id : " + userPid, HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdateList(List<UsersDetailDo> model) {
		try {

			List<UsersDetailDo> entityList = repo.saveAll(model);
			if (entityList != null && !entityList.isEmpty()) {
				return new ResponseEntity<>("Created Successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT" + "Please provide valid input check User Pid and Email.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> findAllidpUsersAndSyncWithHana() {
		try {
			Integer count = ApplicationConstants.IAS_USERS_LIST_COUNT;
			Integer startIndex = ApplicationConstants.IAS_USERS_LIST_START_INDEX;

			Double countD = new Double(count);

			Double startIndexD = new Double(startIndex);
			ResponseEntity<?> responseFromIas = HelperClass.findAllidpUsers(startIndexD.intValue(), countD.intValue());

			if (responseFromIas.getStatusCodeValue() == HttpStatus.OK.value()) {

				UserList ias = (UserList) responseFromIas.getBody();
				List<UserInfo> listOfUsers = ias.getResources();

				System.err.println("listOfUsers : " + listOfUsers);
				Double totalResult = Double.valueOf(ias.getTotalResults());

				if (totalResult.intValue() != 0) {

					ResponseEntity<?> responseFromFetchingCountryMap = HelperClass.findAllCountryTexts();

					if (responseFromFetchingCountryMap.getStatusCodeValue() == HttpStatus.OK.value()) {

						List<UsersDetailDo> listOfUsersInHana = new ArrayList<>();

						Map<String, Object> mapOfCountryText = (Map<String, Object>) responseFromFetchingCountryMap
								.getBody();

						System.err.println("mapOfCountryText : " + mapOfCountryText);

						if (totalResult > (startIndexD * countD)) {
							for (int i = 0; i < Math.ceil(totalResult / (startIndex * count) - 1); i++) {
								startIndexD = startIndexD + countD;
								UserList iasTemp = (UserList) HelperClass
										.findAllidpUsers(startIndexD.intValue(), countD.intValue()).getBody();
								listOfUsers.addAll(iasTemp.getResources());
							}

						}

						for (UserInfo iasResourceDto : listOfUsers) {

							UsersDetailDo userDetailsDo = repo.findByUserId(iasResourceDto.getId());

							if (userDetailsDo == null) {
								userDetailsDo = new UsersDetailDo();
								userDetailsDo.setCreatedAt(DateUtils.getCurrentTimeInEPOCHSec());
								userDetailsDo.setCreatedBy("ADMIN");
							}

							userDetailsDo.setUpdatedAt(DateUtils.getCurrentTimeInEPOCHSec());
							if (iasResourceDto.getEmails() != null && !iasResourceDto.getEmails().isEmpty()) {
								userDetailsDo.setEmail(iasResourceDto.getEmails().get(0).getValue());
							}
							userDetailsDo.setUserId(iasResourceDto.getId());
							if (iasResourceDto.getName() != null) {
								userDetailsDo.setFirstName(iasResourceDto.getName().getGivenName());
								userDetailsDo.setLastName(iasResourceDto.getName().getFamilyName());
							}
							if (iasResourceDto.getGroups() != null && !iasResourceDto.getGroups().isEmpty()) {
								StringJoiner sj = new StringJoiner(",");
								iasResourceDto.getGroups().stream().forEach(group -> sj.add(group.getDisplay()));
								userDetailsDo.setUserGroup(sj.toString());
							}
							if (iasResourceDto.getAddresses() != null && !iasResourceDto.getAddresses().isEmpty()
									&& !HelperClass.checkString(iasResourceDto.getAddresses().get(0).getCity())) {
								userDetailsDo.setCountry(mapOfCountryText
										.get(iasResourceDto.getAddresses().get(0).getCity()).toString());
							}
							listOfUsersInHana.add(userDetailsDo);

						}

						repo.saveAll(listOfUsersInHana);

						return new ResponseEntity<>(ias, HttpStatus.CREATED);
					} else {
						return responseFromFetchingCountryMap;
					}
				} else {
					return new ResponseEntity<>("No data found", HttpStatus.NO_CONTENT);
				}

			} else {
				return responseFromIas;
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}


//}*/
