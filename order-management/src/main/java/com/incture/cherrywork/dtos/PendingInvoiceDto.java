package com.incture.cherrywork.dtos;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOperation;
import com.incture.cherrywork.util.DB_Operation;
import com.incture.cherrywork.util.InvalidInputFault;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PendingInvoiceDto extends BaseDto {

	private Long documentNumber;
	private String custId;
	private String accountingDocument;
	private String documentType;
	private String billingDocumentNo;
	private String currency;
	private BigDecimal amountInLocalCurrency;
	private BigDecimal amount;
	private BigDecimal pendingAmount;
	private Date postingDate;
	private Date documentDate;
	private String invoiceNumber;
	private Date billDate;
	private String soldToParty;
	private String payer;
	private BigDecimal netValue;
	private BigDecimal taxAmount;
	private List<PendingInvoiceItemDto> invoiceItemList;

	@Override
	public void validate(DB_Operation enOperation) throws InvalidInputFault {
	}

	@Override
	public Object getPrimaryKey() {
		return null;
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
