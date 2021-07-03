package com.incture.cherrywork.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class SalesOrderDto {
	private String customerPo;
	private String salesOrderNum;

	public SalesOrderDto(String sales_order_num, String customer_po) {
		super();
		this.salesOrderNum = sales_order_num;
		this.customerPo = customer_po;
	}
	

}
