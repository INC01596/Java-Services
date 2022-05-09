package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.UserRoles;

@Repository
public interface IUserRolesRepository extends JpaRepository<UserRoles, String> {

	@Query(value = "from UserRoles where userId=?1")
	public List<UserRoles> getRole(String userId);
}
