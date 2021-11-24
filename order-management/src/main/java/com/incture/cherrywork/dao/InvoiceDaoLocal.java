package com.incture.cherrywork.dao;

import java.time.LocalDate;

public interface InvoiceDaoLocal {

	String getStatusByInvoiceNo(String invoiceNo);

	int updateInvoiceStatus(String transactionId, String status);

	LocalDate getFirstOfCreditMonth(String custId);

}
