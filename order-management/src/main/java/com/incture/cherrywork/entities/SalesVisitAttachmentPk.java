package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Embeddable
@Data
public class SalesVisitAttachmentPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "attachmentId")
	private String attachmentId;

	@ToString.Exclude
	@JsonBackReference("attachment")
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

	public SalesVisitAttachmentPk(String id, SalesVisit visit) {
		this.attachmentId = id;
		this.salesVisit = visit;
	}

}
