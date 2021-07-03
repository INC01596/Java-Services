package com.incture.cherrywork.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "USER_DETAILS_GUID")
@EqualsAndHashCode(callSuper = false)
public class UsersDetailDo extends CommonPostfix {

	@Id
	@Column(name = "USER_GUID_NUM")
	private String userGuid = UUID.randomUUID().toString();

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "USER_GROUP",columnDefinition = "TEXT")
	private String userGroup;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

}
