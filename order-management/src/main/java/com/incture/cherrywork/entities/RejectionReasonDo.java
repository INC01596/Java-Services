package com.incture.cherrywork.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "REJECTION_REASON")
public class RejectionReasonDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REJECTION_REASON")
	private String rejectReason;

	@Override
	public Object getPrimaryKey() {

		return rejectReason;
	}

}
