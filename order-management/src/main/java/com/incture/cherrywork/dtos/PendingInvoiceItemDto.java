package com.incture.cherrywork.dtos;


import java.math.BigDecimal;

import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.util.DB_Operation;
import com.incture.cherrywork.util.InvalidInputFault;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PendingInvoiceItemDto extends BaseDto {
	
	
	private Long pendInvItmPrimaryId;
	
	
	private String itemNumber;
	
	
	private String matID;
	
	
	private String description;
	
	
	private BigDecimal highItem;
	
	
	private BigDecimal billedQty;
	
	
	private String salesUnit;
	

	private BigDecimal netPrice;
	
	
	private BigDecimal taxAmount;
	

	private String salesDoc;
	

	private String itemCateg;
	
	
	private String freegoodInd;
	
	
	private String invoiceNumber;
	
	
	private PendingInvoiceDto pendingInvoicedto;
	
	@Override
	public void validate(DB_Operation enOperation) throws InvalidInputFault {	
		
	}

	@Override
	public Object getPrimaryKey() {		
		return pendInvItmPrimaryId;
	}

	@Override
	public void validate(EnOperation enOperation) throws com.incture.cherrywork.exceptions.InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
