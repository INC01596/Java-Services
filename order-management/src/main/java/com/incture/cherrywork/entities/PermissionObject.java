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
@Table(name = "PERMISSION_OBJECT_NAME")
@EqualsAndHashCode(callSuper=false)
public class PermissionObject extends CommonPostfix {
	
	@Id
	@Column(name = "PERMISSION_OBJECT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permissionId;
	
	@Column(name = "PERMISSION_DESCRIPTION")
	private String desc;

}
