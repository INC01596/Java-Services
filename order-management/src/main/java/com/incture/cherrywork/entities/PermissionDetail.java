package com.incture.cherrywork.entities;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PERMISSION_DETAILS")
public class PermissionDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long slNo;
//	private String permissionId;
	
	@JoinColumn(name="PERMISSION_OBJECT_ID")
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private PermissionObject permissionId;
	
	@JoinColumn(name="ATTRIBUTE_ID")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private AttributeDetail attributeId;
	
	@Column(name="ATTRIBUTE_VALUE")
	private String attributeValue;
	
	@Column(name="ATTRIBUTE_VALUE_DESC")
	private String attributeValueDesc;
	
	@Column(name="HEADER_ITEM")
	private String headerOrItem;
	
	@Column(name="INCLUSION")
	private boolean isInclusion;
		
	@Column(name="TEXT")
	private String text;
}

