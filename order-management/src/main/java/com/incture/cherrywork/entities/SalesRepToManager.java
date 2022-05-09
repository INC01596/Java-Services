package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SalesRepToManager")
public class SalesRepToManager {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "salesRepId")
	private String salesRepId;

	@Column(name = "salesRepEmail")
	private String salesRepEmail;

	@Column(name = "managerId")
	private String managerId;

	@Column(name = "managerEmail")
	private String managerEmail;
}
