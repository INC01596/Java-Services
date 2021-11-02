package com.incture.cherrywork.entities;



import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "BANK_NAMES")
public class BankNamesDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BankNamesIdentity bankName;

	@Override
	public Object getPrimaryKey() {
		return null;
	}

	public BankNamesDo() {
		super();
	}

}
