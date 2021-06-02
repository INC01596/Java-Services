package com.incture.cherrywork.dtos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class ReturnItemDto {

	private String returnReqItemid;
	private String returnReqNum;
	private String refDocNum;
	private String refDocItem;
	private String materialGroup;
	private String materialGroup4;
	private String material;
	private String shortText;
	private Double avlReturnQty;
	private String avlUom;
	private Double returnQty;
	private String returnUom;
	private Double unitPriceInv;
	private Double unitPriceCc;
	private Double invoiceTotalAmount;
	private String storageLocation;
	private String higherLevel;
	private String billingType;
	private String batch;
	private String serialNum;
	private Date expiryDate;
	private String sapReturnOrderNum;
	private String sapReturnOrderItemNum;
	private String overallItemWorkflowStatus;
	private String plant;//os
	private Date referenceInvDate;//os
	private String itemText;//os
	private Date pricingDate;// os
	private Date serviceRenderedDate;//os
	private String paymentTerms;//os 
	private String conditionGroup4;//os
	private boolean itemVisibility;

}
