package com.incture.cherrywork.dtos;



import java.util.Date;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InvoiceDto extends BaseDto {

	private Long invoiceId;

	private String sapInvoiceNumber;

	private Double invoiceAmount;

	private String invoiceCurrency;

	private Double pendingAmount;

	private Date invoiceCreatedDate;

	private Date collectionDueDate;

	private String customerId;

	private TransactionDto transaction;

	private String status;

	private String documentType;
	
	private Boolean isCreditNote;

	

	@Override
	public Object getPrimaryKey() {
		return invoiceId;
	}



	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
