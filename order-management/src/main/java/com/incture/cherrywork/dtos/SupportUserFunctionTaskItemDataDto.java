package com.incture.cherrywork.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class SupportUserFunctionTaskItemDataDto {

	private String orderNum;
	private String itemNum;
	private String itemStatus;
	private String itemStatusText;
	private String itemDeliveryBlockCode;
	private String itemDeliveryBlockText;
	private String splPrice;
	private String sapMaterialNum;
	private String batchNum;
	private String materialGroup;
	private String shortText;
	private String itemCategory;
	private String salesUnit;
	private String refDocNum;
	private String refDocItem;
	private String plant;
	private String storageLoc;
	private String oldMatCode;
	private String netPrice;
	private String docCurrency;
	private String netWorth;
	private String reasonForRejection;
	private String reasonForRejectionText;
	private String materialGroup4;
	private String baseUnit;
	private String higherLevelItem;
	private String itemCategText;
	private String salesTeam;
	private String salesArea;
	private String serialNumber;
	private String matExpiryDate;
	private String orderedQtySales;
	private String cuConfQtyBase;
	private String cuConfQtySales;
	private String cuReqQtySales;
	private String taxAmount;

	public SupportUserFunctionTaskItemDataDto(String sales_order_num, String sales_order_item_num,
			String sap_material_num, Double ordered_qty_sales, String spl_price, String sales_unit, Double net_price,
			String doc_currency, Double net_worth, String reason_for_rejection, String reason_for_rejection_text,
			String material_group_for, String base_unit, String material_group, String item_dlv_block,
			String relfordel_text, String ref_doc_num, String ref_doc_item, String short_text) {

		this.orderNum = sales_order_num;
		this.itemNum = sales_order_item_num;
		this.sapMaterialNum = sap_material_num;
		this.orderedQtySales = String.format("%.2f", ordered_qty_sales);
		this.splPrice = spl_price;
		this.salesUnit = sales_unit;
		this.netPrice = String.format("%.2f", net_price);
		this.docCurrency = doc_currency;
		this.netWorth = String.format("%.2f", net_worth);
		this.reasonForRejection = reason_for_rejection;
		this.reasonForRejectionText = reason_for_rejection_text;
		this.materialGroup4 = material_group_for;
		this.baseUnit = base_unit;
		this.materialGroup = material_group;
		this.itemDeliveryBlockCode = item_dlv_block;
		this.itemDeliveryBlockText = relfordel_text; // schedule lines
		this.refDocNum = ref_doc_num;
		this.refDocItem = ref_doc_item;
		this.shortText = short_text;

	}

}
