package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.UserRolesDto;
import com.incture.cherrywork.entities.UserRoles;
import com.incture.cherrywork.repositories.IUserRolesRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;

@Service("UserRolesServiceImpl")
public class UserRolesServiceImpl implements UserRolesService {

	@Autowired
	private IUserRolesRepository urRolesRepository;

	public ResponseEntity<Response> getRole(String userId) {
		List<UserRoles> rolesList = urRolesRepository.getRole(userId);
		List<UserRolesDto> rolesListDto = new ArrayList<>();
		if (!rolesList.isEmpty())
			rolesListDto = ObjectMapperUtils.mapAll(rolesList, UserRolesDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(rolesListDto).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Roles fethched successfully!").build());
	}

}
