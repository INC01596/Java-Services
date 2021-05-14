package com.incture.cherrywork.entities.workflow;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incture.cherrywork.entities.BaseDo;
import com.incture.cherrywork.entities.workflow.ScheduleLineDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALES_DOC_ITEM")
public @Data class SalesDocItemDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private SalesDocItemPrimaryKeyDo salesDocItemKey;

	@JsonBackReference("task-soItem")
	@OneToMany(mappedBy = "scheduleLineKey.soItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<ScheduleLineDo> scheduleLineList;

	@Column(name = "SAP_MATERIAL_NUM", length = 18)
	private String sapMaterialNum;

	@Column(name = "BATCH_NUM", length = 10)
	private String batchNum;

	@Column(name = "SPL_PRICE")
	private String splPrice;

	@Column(name = "MATERIAL_GROUP", length = 20)
	private String materialGroup;

	@Column(name = "SHORT_TEXT", length = 1000)
	private String shortText;

	@Column(name = "ITEM_CATEGORY", length = 20)
	private String itemCategory;

	@Column(name = "ITEM_TYPE", length = 20)
	private String itemType;

	@Column(name = "OLD_MAT_CODE", length = 20)
	private String oldMatCode;

	@Column(name = "SALES_UNIT", length = 20)
	private String salesUnit;

	@Column(name = "ITEM_BILLING_BLOCK", length = 20)
	private String itemBillingBlock;

	@Column(name = "ITEM_DLV_BLOCK", length = 100)
	private String itemDlvBlock;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_DELIVERY_DATE")
	private Date firstDeliveryDate;

	@Column(name = "PARTIAL_DLV", length = 20)
	private String partialDlv;

	@Column(name = "REF_DOC_NUM", length = 20)
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM")
	private String refDocItem;

	@Column(name = "PLANT", length = 20)
	private String plant;

	@Column(name = "STORAGE_LOC", length = 20)
	private String storageLoc;

	@Column(precision = 3, name = "NET_PRICE")
	private Double netPrice;

	@Column(name = "DOC_CURRENCY", length = 20)
	private String docCurrency;

	@Column(name = "PRICING_UNIT", length = 20)
	private String pricingUnit;

	@Column(name = "COUD_UNIT", length = 20)
	private String coudUnit;

	@Column(precision = 3, name = "NET_WORTH")
	private Double netWorth;

	@Column(name = "OVERALL_STATUS", length = 20)
	private String overallStatus;

	@Column(name = "DELIVERY_STATUS", length = 20)
	private String deliveryStatus;

	@Column(name = "REASON_FOR_REJECTION", length = 255)
	private String reasonForRejection;

	@Column(name = "REASON_FOR_REJECTION_TEXT")
	private String reasonForRejectionText;

	@Column(name = "MATERIAL_GROUP_FOR", length = 20)
	private String materialGroupFor;

	@Column(name = "BASE_UNIT", length = 20)
	private String baseUnit;

	@Column(name = "HIGHER_LEVEL_ITEM", length = 100)
	private String higherLevelItem;

	@Column(precision = 3, name = "TAX_AMOUNT")
	private Double taxAmount;

	@Column(precision = 3, name = "CONV_DEN")
	private Double convDen;

	@Column(precision = 3, name = "CONV_NUM")
	private Double convNum;

	@Column(precision = 3, name = "CU_CONF_QTY_BASE")
	private Double cuConfQtyBase;

	@Column(precision = 3, name = "CU_CONF_QTY_SALES")
	private Double cuConfQtySales;

	@Column(precision = 3, name = "CU_REQ_QTY_SALES")
	private Double cuReqQtySales;

	@Column(precision = 3, name = "ORDERED_QTY_SALES")
	private Double orderedQtySales;

	@Column(name = "DECISION_SET_ID", length = 100)
	private String decisionSetId;

	@Column(name = "ITEM_STAGING_STATUS", length = 20)
	private String itemStagingStatus;

	@Column(name = "ITEM_CATEG_TEXT", length = 100)
	private String itemCategText;

	@Column(name = "SALES_AREA")
	private String salesArea;

	@Column(name = "SALES_TEAM")
	private String salesTeam;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "MATERIAL_EXPIRY_DATE")
	private BigInteger matExpiryDate;

	@Override
	public Object getPrimaryKey() {
		return salesDocItemKey;
	}

}
