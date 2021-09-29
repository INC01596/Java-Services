package com.incture.cherrywork.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ATTRIBUTE_DETAILS")
public class AttributeDetail {
	
	@Id
	@Column(name = "ATTRIBUTE_ID")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String attributeId;
	
	@Column(name = "ATTRIBUTE_DESC")
	private String attributeDesc;
}

