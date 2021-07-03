package com.incture.cherrywork.dtos;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class ItemDataInReturnOrderDto {

	private String returnReqNum;
	private String exchReqNum;
	private String orderNum;
	private String orderItemNum;
	private String itemSerialId;
	private String level;
	private String decisionSetId;
	private String approverType;
	private String taskId;
	private String taskStatusSerialId;
	private Integer itemStatus;
	private Integer visiblity;
	private String higherLvlItem;
	private String itemDlvBlock;
	private String itemDlvBlockText;
	private String itemStagingStatus;
	private String refDocNum;
	private String refDocItem;
	private String materialNum;
	private String materialText;
	private Double salesQty;
	private String salesUom;
	private Double unitPrice;
	private Double itemAmount;
	private String materialGroup;
	private String materialGroup4;
	private String plant;
	private String splPrice;
	private String itemCategory;
	private String docCurrency;
	private String reasonForRejection;
	private String reasonForRejectionText;
	private String baseUnit;
	private String itemCategText;
	private Double convDen;
	private Double convNum;
	private String batchNum;
	private String acceptOrReject;
	private String serialNumber;
	private BigInteger materialExpiryDate;

	public ItemDataInReturnOrderDto(String sales_order_num, String so_item_num, String item_status_serial_id,
			String level, String decision_set_id, String approver_type, String task_id, String task_status_serial_id,
			Integer item_status, Integer visiblity, String spl_price, BigInteger material_expiry_date,
			String serial_number, String sap_material_num, String material_group, String short_text,
			String item_category, String sales_unit, String item_dlv_block, String relfordel_text, String ref_doc_num,
			String ref_doc_item, String plant, Double net_price, String doc_currency, Double net_worth,
			String reason_for_rejection, String material_group_for, String base_unit, Double ordered_qty_sales,
			String item_categ_text, Double conv_den, Double conv_num, String higher_level_item,
			String item_staging_status, String batch_num) {
		super();
		this.orderNum = sales_order_num;
		this.orderItemNum = so_item_num;
		this.itemSerialId = item_status_serial_id;
		this.level = level;
		this.decisionSetId = decision_set_id;
		this.approverType = approver_type;
		this.taskId = task_id;
		this.itemStatus = item_status;
		this.visiblity = visiblity;
		this.splPrice = spl_price;
		this.materialNum = sap_material_num;
		this.materialGroup = material_group;
		this.materialText = short_text;
		this.itemCategory = item_category;
		this.salesUom = sales_unit;
		this.itemDlvBlock = item_dlv_block;
		this.serialNumber = serial_number;
		this.materialExpiryDate = material_expiry_date;
		this.itemDlvBlockText = relfordel_text;
		this.refDocNum = ref_doc_num;
		this.refDocItem = ref_doc_item;
		this.plant = plant;
		this.taskStatusSerialId = task_status_serial_id;
		this.unitPrice = net_price;
		this.docCurrency = doc_currency;
		this.itemAmount = net_worth;
		this.reasonForRejection = reason_for_rejection;
		this.materialGroup4 = material_group_for;
		this.baseUnit = base_unit;
		this.salesQty = ordered_qty_sales;
		this.itemCategText = item_categ_text;
		this.convDen = conv_den;
		this.convNum = conv_num;
		this.higherLvlItem = higher_level_item;
		this.itemStagingStatus = item_staging_status;
		this.batchNum = batch_num;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDataInReturnOrderDto other = (ItemDataInReturnOrderDto) obj;
		if (decisionSetId == null) {
			if (other.decisionSetId != null)
				return false;
		} else if (!decisionSetId.equals(other.decisionSetId))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decisionSetId == null) ? 0 : decisionSetId.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		return result;
	}

}