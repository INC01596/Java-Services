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
@Table(name = "ATTRIBUTE_DETAILS_GUID")
@EqualsAndHashCode(callSuper = false)
public class AttributeDetailsDo extends CommonPostfix {

	@Id
	@Column(name = "ATTRIBUTE_DETAILS_GUID_NUM")
	private String attributeDetailsGuid = UUID.randomUUID().toString();

	@Column(name = "ATTRIBUTE_ID")
	private String attributeId;

	@Column(name = "DOMAIN_CODE")
	private String domainCode;

	@Column(name = "ATTRIBUTE_DESC")
	private String attributeDesc;

	@Column(name = "ALIAS_NAME")
	private String aliasName;

	@Column(name = "INCLUSION")
	private Boolean isInclusion = true;

}
