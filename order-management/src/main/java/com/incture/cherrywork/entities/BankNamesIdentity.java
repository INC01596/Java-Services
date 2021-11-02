package com.incture.cherrywork.entities;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class BankNamesIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankNamesIdentity() {
		super();
	}

	@Column(name = "CONTROLLING_AREA")
	private String controllingArea;

	@Column(name = "BANK_NAME")
	private String bankName;

}
