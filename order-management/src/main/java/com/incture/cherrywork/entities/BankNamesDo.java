package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Column(name = "CONTROLLING_AREA")
	private String controllingArea;

	@Id
	@Column(name = "BANK_NAME")
	private String bankName;


	@Override
	public Object getPrimaryKey() {
		return null;
	}

	public BankNamesDo() {
		super();
	}

}
