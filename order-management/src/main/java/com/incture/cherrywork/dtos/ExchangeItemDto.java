
package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class ExchangeItemDto {
	private String exchangeReqNum;
	private String exchangeReqItemid;
	private String returnReqNum;
	private String refReturnReqNum;
	private String refDocNum;
	private String refDocItem;
	private String sapMaterialNum;
	private String higherLevelItem;
	private String materialGroup;
	private String materialGroup4;
	private String shortText;
	private String itemCategory;
	private String itemType;
	private String batchNum;
	private Double orderedQtySales;
	private Double cuConfQtyBase;
	private Double cuConfQtySales;
	private Double cuReqQtySales;
	private String salesUnit;
	private String baseUnit;
	private Double convDen;
	private Double convNum;
	private String itemBillingBlock;
	private String sapSalesOrderNum;
	private String sapSalesOrderItemNum;
	
	
	
	private String refDocItm;//os 
	private String material;//os
	private String unitPrice;
	private String ltp;
	private String amount;
	private String higherLevel;//OS
	private String batch;//OS
	private String serialNum;//os
	private String returnUom;//OS
	private Double returnQty;//OS
	private Double unitPriceCc;//os
	private String unitPriceInv;// add in Do
	private String invoiceTotalAmount; // add in Do
	private String returnReqItemid;
	private String plant;//os
	private Date pricingDate;
	private Date serviceRenderedDate;
	private String storageLocation;
	private Date referenceInvDate;
	private Date expiryDate;//add in do
	private String billingType;//os
	private String manualFoc;
	 private boolean itemVisibility;
	

}
