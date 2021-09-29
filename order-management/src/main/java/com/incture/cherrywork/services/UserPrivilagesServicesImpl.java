package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.UserPrivilagesDto;
import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.entities.PermissionObjectDo;
import com.incture.cherrywork.entities.UserPrivilagesDo;
import com.incture.cherrywork.entities.UsersDetailDo;
import com.incture.cherrywork.repositories.DomainTextRepository;
import com.incture.cherrywork.repositories.PermissionObjectsRepository;
import com.incture.cherrywork.repositories.UserDetailsRepository;
import com.incture.cherrywork.repositories.UserPrivilagesRepository;
import com.incture.cherrywork.util.HelperClass;



@Service
public class UserPrivilagesServicesImpl implements UserPrivilagesServices {

	@Autowired
	private UserPrivilagesRepository repo;

	@Autowired
	private PermissionObjectsRepository permissionObjectsRepo;

	@Autowired
	private UserDetailsRepository userDetailsRepo;

	@Autowired
	private DomainTextRepository domainTextRepo;

	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			if (!HelperClass.checkString(id)) {
				Optional<UserPrivilagesDo> model = repo.findById(id);
				if (model.isPresent()) {
					return new ResponseEntity<>(model.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>("AppErrorMsgConstants.DATA_NOT_FOUND", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user privilages id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> saveOrUpdate(UserPrivilagesDo model) {
		try {
			if (!HelperClass.checkString(model.getDomainCode())
					&& !HelperClass.checkString(model.getPermissionObjectGuid())
					&& !HelperClass.checkString(model.getUserGuid())) {

				Optional<UsersDetailDo> userDetailsDo = userDetailsRepo.findById(model.getUserGuid());
				if (userDetailsDo.isPresent()) {
					Optional<DomainTextDo> domainTextDo = domainTextRepo.findById(model.getDomainCode());
					if (domainTextDo.isPresent()) {
						Optional<PermissionObjectDo> permissionObjectDo = permissionObjectsRepo
								.findById(model.getPermissionObjectGuid());
						if (permissionObjectDo.isPresent()) {
							if (permissionObjectDo.get().getDomainCode()
									.equalsIgnoreCase(domainTextDo.get().getDomainCode())) {
								UserPrivilagesDo entity = repo.saveAndFlush(model);
								return new ResponseEntity<>(entity, HttpStatus.CREATED);
							} else {
								return new ResponseEntity<>(
										"Entered Domain code is not available in Permission Object.",
										HttpStatus.CONFLICT);
							}
						} else {
							return new ResponseEntity<>(
									"Permission Object guid doesn't exist. Please use valid Permission Object guid.",
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Domain Code doesn't exist. Please use valid Domain Code.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User guid doesn't exist. Please use valid User guid.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide User guid, Permission Object guid and Domain code.",
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
				return new ResponseEntity<>("User Privilages " + id + " is successfully deleted.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("AppErrorMsgConstants.INVALID_INPUT" + "Please provide user privilages id.",
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
			List<UserPrivilagesDo> list = repo.findAll();
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
	public ResponseEntity<?> assignMultiplePermissionObject(UserPrivilagesDto userPrivilagesDto) {
		try {
			if (!HelperClass.checkString(userPrivilagesDto.getUserId())
					&& userPrivilagesDto.getPermissionObjectGuid() != null
					&& !userPrivilagesDto.getPermissionObjectGuid().isEmpty()) {

				UsersDetailDo userDetailsDo = userDetailsRepo.findByUserId(userPrivilagesDto.getUserId());
				if (userDetailsDo != null) {
					List<PermissionObjectDo> permissionObjectDoList = permissionObjectsRepo
							.findByPermissionObjectGuidIn(userPrivilagesDto.getPermissionObjectGuid());
					if (!permissionObjectDoList.isEmpty()
							&& permissionObjectDoList.size() == userPrivilagesDto.getPermissionObjectGuid().size()) {

						List<UserPrivilagesDo> userPrivilagesDoList = new ArrayList<>();
						permissionObjectDoList.stream().forEach(permObject -> {

							UserPrivilagesDo userPrivilagesDoInDb = repo
									.findByUserGuidAndDomainCodeAndPermissionObjectGuid(userDetailsDo.getUserGuid(),
											permObject.getDomainCode(), permObject.getPermissionObjectGuid());
							if (userPrivilagesDoInDb == null) {
								UserPrivilagesDo userPrivilagesDo = new UserPrivilagesDo();
								userPrivilagesDo.setDomainCode(permObject.getDomainCode());
								userPrivilagesDo.setUserGuid(userDetailsDo.getUserGuid());
								userPrivilagesDo.setPermissionObjectGuid(permObject.getPermissionObjectGuid());
								userPrivilagesDoList.add(userPrivilagesDo);
							}
						});

						if (!userPrivilagesDoList.isEmpty()) {
							List<UserPrivilagesDo> entityList = repo.saveAll(userPrivilagesDoList);
							return new ResponseEntity<>(entityList, HttpStatus.CREATED);
						} else {
							return new ResponseEntity<>("All entered Permission objects already assigned.",
									HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>(
								"Permission Object guid doesn't exist. Please use valid Permission Object guid.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User Id doesn't exist. Please use valid User Id.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide User guid, Permission Object guid and Domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> unassignPermissionObjects(UserPrivilagesDto userPrivilagesDto) {
		try {
			if (!HelperClass.checkString(userPrivilagesDto.getUserId())
					&& userPrivilagesDto.getPermissionObjectGuid() != null
					&& !userPrivilagesDto.getPermissionObjectGuid().isEmpty()) {

				UsersDetailDo userDetailsDo = userDetailsRepo.findByUserId(userPrivilagesDto.getUserId());
				if (userDetailsDo != null) {

					List<UserPrivilagesDo> userPrivilagesDoList = repo
							.findByUserGuidAndDomainCodeAndPermissionObjectGuidIn(userDetailsDo.getUserGuid(),
									userPrivilagesDto.getDomainId(), userPrivilagesDto.getPermissionObjectGuid());

					if (userPrivilagesDoList != null && !userPrivilagesDoList.isEmpty()) {
						repo.deleteInBatch(userPrivilagesDoList);
						return new ResponseEntity<>(userPrivilagesDoList.size()
								+ " permission objects have unassigned successfully for user "
								+ userPrivilagesDto.getUserId(), HttpStatus.ACCEPTED);
					} else {
						return new ResponseEntity<>(
								"Permission Object guid doesn't exist. Please use valid Permission Object guid or valid domain code.",
								HttpStatus.CONFLICT);
					}
				} else {
					return new ResponseEntity<>("User Id doesn't exist. Please use valid User Id.",
							HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<>(
						"AppErrorMsgConstants.INVALID_INPUT"
								+ "Please provide User guid, Permission Object guid and Domain code.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("AppErrorMsgConstants.EXCEPTION_POST_MSG" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
