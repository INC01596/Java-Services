package com.incture.cherrywork.util;

import java.math.BigInteger;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.workflow.ItemStatusTrackingLevelMsgDto;
import com.incture.cherrywork.dtos.SalesOrderDto;
import com.incture.cherrywork.dtos.SupportUserFunctionTaskItemDataDto;

@MappedSuperclass
@SqlResultSetMapping(name = NativeSqlResultMapping.ITEM_RESULT, classes = {
		@ConstructorResult(targetClass = com.incture.cherrywork.dtos.ItemDataInReturnOrderDto.class, columns = {
				@ColumnResult(name = "sales_order_num"), @ColumnResult(name = "so_item_num"),
				@ColumnResult(name = "item_status_serial_id"), @ColumnResult(name = "level"),
				@ColumnResult(name = "decision_set_id"), @ColumnResult(name = "approver_type"),
				@ColumnResult(name = "task_id"), @ColumnResult(name = "task_status_serial_id"),
				@ColumnResult(name = "item_status", type = Integer.class),
				@ColumnResult(name = "visiblity", type = Integer.class), @ColumnResult(name = "spl_price"),
				@ColumnResult(name = "material_expiry_date", type = BigInteger.class),
				@ColumnResult(name = "serial_number"), @ColumnResult(name = "sap_material_num"),
				@ColumnResult(name = "material_group"), @ColumnResult(name = "short_text"),
				@ColumnResult(name = "item_category"), @ColumnResult(name = "sales_unit"),
				@ColumnResult(name = "item_dlv_block"), @ColumnResult(name = "relfordel_text"),
				@ColumnResult(name = "ref_doc_num"), @ColumnResult(name = "ref_doc_item"),
				@ColumnResult(name = "plant"), @ColumnResult(name = "net_price", type = Double.class),
				@ColumnResult(name = "doc_currency"), @ColumnResult(name = "net_worth", type = Double.class),
				@ColumnResult(name = "reason_for_rejection"), @ColumnResult(name = "material_group_for"),
				@ColumnResult(name = "base_unit"), @ColumnResult(name = "ordered_qty_sales", type = Double.class),
				@ColumnResult(name = "item_categ_text"), @ColumnResult(name = "conv_den", type = Double.class),
				@ColumnResult(name = "conv_num", type = Double.class), @ColumnResult(name = "higher_level_item"),
				@ColumnResult(name = "item_staging_status"), @ColumnResult(name = "batch_num")

		}) })
@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_DOC_HEADER_DATA, classes = {
		@ConstructorResult(targetClass = com.incture.cherrywork.dtos.SalesDocHeaderDto.class, columns = {
				@ColumnResult(name = "requested_by"), @ColumnResult(name = "order_remark"),
				@ColumnResult(name = "order_reason_text"), @ColumnResult(name = "order_category"),
				@ColumnResult(name = "order_type"), @ColumnResult(name = "doc_type_text"),
				@ColumnResult(name = "sales_org"), @ColumnResult(name = "distribution_channel"),
				@ColumnResult(name = "division"), @ColumnResult(name = "customer_po"),
				@ColumnResult(name = "sold_to_party"), @ColumnResult(name = "sold_to_party_text"),
				@ColumnResult(name = "ship_to_party"), @ColumnResult(name = "ship_to_party_text"),
				@ColumnResult(name = "doc_currency"), @ColumnResult(name = "delivery_block_code"),
				@ColumnResult(name = "dlv_block_text"), @ColumnResult(name = "cond_group5"),
				@ColumnResult(name = "cond_group5_text"),
				@ColumnResult(name = "sales_order_date", type = BigInteger.class), @ColumnResult(name = "order_reason"),
				@ColumnResult(name = "orderer_na"), @ColumnResult(name = "created_by"),
				@ColumnResult(name = "attachment_url"), @ColumnResult(name = "approval_status"),
				@ColumnResult(name = "overall_status"), @ColumnResult(name = "sales_org_text"),
				@ColumnResult(name = "distribution_channel_text"), @ColumnResult(name = "division_text"),
				@ColumnResult(name = "payer"), @ColumnResult(name = "payer_text"),
				@ColumnResult(name = "bill_to_party"), @ColumnResult(name = "bill_to_party_text") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_ORDER_DATA, classes = {
		@ConstructorResult(targetClass = SalesOrderDto.class, columns = {
				@ColumnResult(name = "sales_order_num"), @ColumnResult(name = "customer_po") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_DOC_ITEM_LEVEL_STATUS_DATA, classes = {
		@ConstructorResult(targetClass = ItemStatusTrackingLevelMsgDto.class, columns = {
				@ColumnResult(name = "so_item_num"), @ColumnResult(name = "decision_set_id"),
				@ColumnResult(name = "level"), @ColumnResult(name = "level_status") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_DOC_TASK_STATUS_DATA, classes = {
		@ConstructorResult(targetClass = SalesOrderTaskStatusDto.class, columns = {
				@ColumnResult(name = "task_status_serial_id"), @ColumnResult(name = "approver"),
				@ColumnResult(name = "task_id"), @ColumnResult(name = "task_status", type = Integer.class),
				@ColumnResult(name = "level_status_serial_id"), @ColumnResult(name = "completed_by") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA, classes = {
		@ConstructorResult(targetClass = SalesOrderItemStatusDto.class, columns = {
				@ColumnResult(name = "item_status_serial_id"),
				@ColumnResult(name = "item_status", type = Integer.class), @ColumnResult(name = "so_item_num"),
				@ColumnResult(name = "visiblity", type = Integer.class),
				@ColumnResult(name = "task_status_serial_id") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.SALES_ORDER_ITEM_DATA, classes = {
		@ConstructorResult(targetClass = SupportUserFunctionTaskItemDataDto.class, columns = {
				@ColumnResult(name = "sales_order_num"), @ColumnResult(name = "sales_order_item_num"),
				@ColumnResult(name = "sap_material_num"),
				@ColumnResult(name = "ordered_qty_sales", type = Double.class), @ColumnResult(name = "spl_price"),
				@ColumnResult(name = "sales_unit"), @ColumnResult(name = "net_price", type = Double.class),
				@ColumnResult(name = "doc_currency"), @ColumnResult(name = "net_worth", type = Double.class),
				@ColumnResult(name = "reason_for_rejection"), @ColumnResult(name = "reason_for_rejection_text"),
				@ColumnResult(name = "material_group_for"), @ColumnResult(name = "base_unit"),
				@ColumnResult(name = "material_group"), @ColumnResult(name = "item_dlv_block"),
				@ColumnResult(name = "relfordel_text"), @ColumnResult(name = "ref_doc_num"),
				@ColumnResult(name = "ref_doc_item"), @ColumnResult(name = "short_text") }) })

@SqlResultSetMapping(name = NativeSqlResultMapping.LEVEL_STATUS_DATA, classes = {
		@ConstructorResult(targetClass = SalesOrderLevelStatusDto.class, columns = {
				@ColumnResult(name = "level_status_serial_id"), @ColumnResult(name = "approver_type"),
				@ColumnResult(name = "decision_set_id"), @ColumnResult(name = "level"),
				@ColumnResult(name = "level_status", type = Integer.class), @ColumnResult(name = "user_group") }) })

public abstract class NativeSqlResultMapping {

	public static final String ITEM_RESULT = "itemResult";
	public static final String SALES_ORDER_DATA = "salesOrderData";
	public static final String SALES_ORDER_ITEM_DATA = "salesOrderItemData";
	public static final String SALES_DOC_HEADER_DATA = "salesDocHeaderData";
	public static final String SALES_DOC_ITEM_LEVEL_STATUS_DATA = "salesDocItemLevelStatusDataForOnSubmit";
	public static final String LEVEL_STATUS_DATA = "levelStatusData";
	public static final String SALES_DOC_TASK_STATUS_DATA = "salesDocTaskStatusData";
	public static final String SALES_DOC_ITEM_STATUS_DATA = "salesDocItemStatusData";

}