package com.incture.cherrywork.dto.new_workflow;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class SalesOrderItemStatusDto {

	private String itemStatusSerialId;
	private String salesOrderItemNum;
	private Integer itemStatus;
	private Integer visiblity;
	private String taskStatusSerialId;

	public SalesOrderItemStatusDto(String item_status_serial_id, Integer item_status, String so_item_num,
			Integer visiblity, String task_status_serial_id) {
		super();
		this.itemStatusSerialId = item_status_serial_id;
		this.salesOrderItemNum = so_item_num;
		this.itemStatus = item_status;
		this.visiblity = visiblity;
		this.taskStatusSerialId = task_status_serial_id;
	}

}
