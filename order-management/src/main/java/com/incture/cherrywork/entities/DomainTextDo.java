package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "DOMAIN_TEXT")
@EqualsAndHashCode(callSuper = false)
public class DomainTextDo extends CommonPostfix {

	@Id
	@Column(name = "DOMAIN_CODE")
	private String domainCode;

	@Column(name = "DOMAIN_TEXT")
	private String domainText;

	@Column(name = "LANGUAGE_KEY")
	private String languageKey;

}
