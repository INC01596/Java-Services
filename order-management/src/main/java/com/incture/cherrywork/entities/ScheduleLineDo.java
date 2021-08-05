package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCHEDULE_LINE")
public @Data class ScheduleLineDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private ScheduleLinePrimaryKeyDo scheduleLineKey;

	@Column(precision = 3, name = "REQ_QTY_SALES")
	private Double reqQtySales;

	@Column(precision = 3, name = "CONF_QTY_SALES")
	private Double confQtySales;

	@Column(name = "SALES_UNIT", length = 3)
	private String salesUnit;

	@Column(precision = 3, name = "REQ_QTY_BASE")
	private Double reqQtyBase;

	@Column(name = "BASE_UNIT", length = 3)
	private String baseUnit;

	@Column(name = "SCHLINE_DELIVERY_BLOCK", length = 3)
	private String schlineDeliveryBlock;

	@Column(name = "RELFORDEL_TEXT", length = 100)
	private String relfordelText;

	@Override
	public Object getPrimaryKey() {
		return scheduleLineKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
