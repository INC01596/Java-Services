package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Entity
@Table(name = "CustomerContact")
public class CustomerContact {

	@Id
	@Column(name = "customerContactId")
	private String customerContactId;

	@Getter(value = AccessLevel.NONE)
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

	@Column(name = "custName")
	private String custName;

	@Column(name = "custPhone")
	private String custPhone;

	@Column(name = "customerAddress")
	private String customerAddress;

	@Column(name = "custEmail")
	private String custEmail;

}
