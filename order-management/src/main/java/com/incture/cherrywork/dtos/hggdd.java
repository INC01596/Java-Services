//package com.incture.cherrywork.dtos;
//
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//import com.incture.cherrywork.exceptions.InvalidInputFault;
//import com.incture.cherrywork.sales.constants.EnOperation;
//import com.incture.cherrywork.util.DB_Operation;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Setter
//@Getter
//@ToString
//public class TransactionDto extends BaseDto {
//
//	private String transactionId;
//
//	private String salesRep;
//
//	private String customerId;
//
//	private String chequeNumber;
//
//	private Date dateOfPayment;
//
//	private String modeOfPayment;
//
//	private Date taskAllocatedDate;
//
//	private BigDecimal amount;
//
//	private String comment;
//
//	private String bankName;
//
//	private List<StatusDto> statusList;
//
//	private List<AttachmentDto> attachmentList;
//
//	private List<InvoiceDto> invoiceList;
//
//	private Date chequeDate;
//
//	private String salesRepName;
//
//	private String customerName;
//
//	private String currency;
//
//	private String salesRepPhoneNo;
//
//	private String customerPhoneNo;
//	
//	private String outstandingAmount;
//
//	
//
//	@Override
//	public Object getPrimaryKey() {
//		return transactionId;
//	}
//
//	@Override
//	public void validate(EnOperation enOperation) throws InvalidInputFault {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Boolean getValidForUsage() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
