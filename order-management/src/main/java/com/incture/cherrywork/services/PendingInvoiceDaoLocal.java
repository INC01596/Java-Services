package com.incture.cherrywork.services;

import java.time.LocalDate;
import java.util.List;

import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.entities.PendingInvoiceDo;

public interface PendingInvoiceDaoLocal {

	void savePendingInvoices(List<PendingInvoiceDo> pendingInvList);

	List<PendingInvoiceDto> getPendingInvoices(List<String> cutomersList);

	LocalDate getFirstOfCreditMonth(String custId);

	List<PendingInvoiceDto> getPendingInvoicesNew(List<String> list);

}