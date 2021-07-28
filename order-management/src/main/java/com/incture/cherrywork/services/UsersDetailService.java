package com.incture.cherrywork.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.AttributeDetailsDo;
import com.incture.cherrywork.entities.DomainTextDo;
import com.incture.cherrywork.entities.PermissionObjectDetailsDo;
import com.incture.cherrywork.entities.UserPrivilagesDo;
import com.incture.cherrywork.entities.UsersDetailDo;
import com.incture.cherrywork.repositories.IAttributeDetailsRepository;
import com.incture.cherrywork.repositories.IDomainTextRepository;
import com.incture.cherrywork.repositories.IPermissionObjectDetailsRepository;
import com.incture.cherrywork.repositories.IUserPrivilagesRepository;
import com.incture.cherrywork.repositories.IUsersDetailRepository;
import com.incture.cherrywork.util.AppErrorMsgConstants;
import com.incture.cherrywork.util.HelperClass;


@Service
@Transactional
public class UsersDetailService implements IUsersDetailService{
	
	
	@Autowired 
	private IUsersDetailRepository userDetailRepository;
	
	@Autowired
	private IDomainTextRepository domainTextRepository;
	
	@Autowired
	private IUserPrivilagesRepository userPrivilagesRepository;
	
	@Autowired
	private IPermissionObjectDetailsRepository permissionObjectDetailsRepository;
	
	@Autowired
	private IAttributeDetailsRepository attributeDetailsRepository;
	
	@Override
	public ResponseEntity<Object> findAllRightsForUserInDomain(String userPid, String domainCode) {
		try {
			if (!HelperClass.checkString(userPid) && !HelperClass.checkString(domainCode)) {

				UsersDetailDo usersDetailDo = userDetailRepository.findByUserId(userPid);

				if (usersDetailDo != null) {

					Optional<DomainTextDo> domainTextDo = domainTextRepository.findById(domainCode);

					if (domainTextDo.isPresent()) {

						List<UserPrivilagesDo> userPrivilagesList = userPrivilagesRepository
								.findByUserGuidAndDomainCode(usersDetailDo.getUserGuid(), domainCode);
						System.err.println("[UsersDetailService][findAllRightsForUserInDomain]userPrivilagesList: "+userPrivilagesList);

						if (!userPrivilagesList.isEmpty()) {

							Set<String> distinctPermissionObjectGuid = userPrivilagesList.stream()
									.map(UserPrivilagesDo::getPermissionObjectGuid).collect(Collectors.toSet());
							
							System.err.println("[UsersDetailService][findAllRightsForUserInDomain]distinctPermissionObjectGuid: "+distinctPermissionObjectGuid);

							List<PermissionObjectDetailsDo> permissionObjectDetailsList = permissionObjectDetailsRepository
									.findByPermissionObjectGuidIn(distinctPermissionObjectGuid);
							

							System.err.println("[UsersDetailService][findAllRightsForUserInDomain]permissionObjectDetailsList: "+permissionObjectDetailsList);
							if (!permissionObjectDetailsList.isEmpty()) {

								Map<String, AttributeDetailsDo> attributeDetailsDoMap = attributeDetailsRepository
										.findByDomainCodeOrderByAttributeIdAsc(domainCode).stream()
										.collect(Collectors.toMap(AttributeDetailsDo::getAttributeDetailsGuid,
												attrDetails -> attrDetails, (oldValue, newValue) -> newValue));

								System.err.println("attributeDetailsDoMap size: "+attributeDetailsDoMap.size());
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
				return new ResponseEntity<>(AppErrorMsgConstants.INVALID_INPUT + "Please provide user details id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





}
