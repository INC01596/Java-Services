package com.incture.cherrywork.entities.workflow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incture.cherrywork.entities.workflow.SalesDocHeaderDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public @Data class SalesDocItemPrimaryKeyDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "SALES_ORDER_ITEM_NUM", length = 100)
	private String salesItemOrderNo;

	@JsonBackReference("task_list-task")
	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_NUM", nullable = false, referencedColumnName = "SALES_ORDER_NUM")
	private SalesDocHeaderDo salesDocHeader;
}
