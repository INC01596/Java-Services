package com.incture.cherrywork.dtos;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.incture.cherrywork.entities.BaseDo;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "PAYMENT_TERMS")
public class PaymentTermDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "TERM")
	private String term;

	@Override
	public Object getPrimaryKey() {
		return null;
	}

}
