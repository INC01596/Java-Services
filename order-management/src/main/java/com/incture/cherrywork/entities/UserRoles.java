package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "UserRoles")
@Data
public class UserRoles {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "userId")
	private String userId;

	@Column(name = "userEmail")
	private String userEmail;

	@Column(name = "role")
	private String role;

}
