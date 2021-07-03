package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "USER_DETAILS")
@EqualsAndHashCode(callSuper = false)
public class UserDetail extends CommonPostfix {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PROJECT")
	private String project;

	@Column(name = "PERMISSION_OBJECT_ID")
	private int permissionId;

	@Column(name = "VARIANT_ID")
	private String variantId = "Default";
}
