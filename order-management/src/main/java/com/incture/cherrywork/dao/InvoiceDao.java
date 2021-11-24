package com.incture.cherrywork.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.InvoiceDto;
import com.incture.cherrywork.entities.InvoiceDo;
import com.incture.cherrywork.util.ServicesUtil;



@Repository("InvoiceDao")
public class InvoiceDao extends BaseDao<InvoiceDo, InvoiceDto> implements InvoiceDaoLocal {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDao.class);

	@Autowired
	private TransactionDao transactionDao;

	InvoiceDto invoiceDto;
	InvoiceDo invoiceDo;

	@Override
	public InvoiceDo importDto(InvoiceDto dto) {

		invoiceDo = new InvoiceDo();

		if (dto != null) {

			invoiceDo.setInvoiceId(dto.getInvoiceId());
			invoiceDo.setSapInvoiceNumber(dto.getSapInvoiceNumber());
			invoiceDo.setInvoiceAmount(dto.getInvoiceAmount());
			invoiceDo.setInvoiceCurrency(dto.getInvoiceCurrency());
			invoiceDo.setPendingAmount(dto.getPendingAmount());
			invoiceDo.setCollectionDueDate(ServicesUtil.convertDate(dto.getCollectionDueDate()));
			invoiceDo.setInvoiceCreatedDate(ServicesUtil.convertDate(dto.getInvoiceCreatedDate()));
			invoiceDo.setCustomerId(dto.getCustomerId());
			invoiceDo.setStatus(dto.getStatus());
			invoiceDo.setDocumentType(dto.getDocumentType());
			invoiceDo.setTransaction(transactionDao.importDto(dto.getTransaction()));
			invoiceDo.setIsCreditNote(dto.getIsCreditNote());

		}

		return invoiceDo;
	}

	@Override
	public InvoiceDto exportDto(InvoiceDo entity) {

		invoiceDto = new InvoiceDto();

		if (entity != null) {

			invoiceDto.setIsCreditNote(entity.getIsCreditNote());
			invoiceDto.setInvoiceId(entity.getInvoiceId());
			invoiceDto.setSapInvoiceNumber(entity.getSapInvoiceNumber());
			invoiceDto.setInvoiceAmount(entity.getInvoiceAmount());
			invoiceDto.setInvoiceCurrency(entity.getInvoiceCurrency());
			invoiceDto.setPendingAmount(entity.getPendingAmount());
			invoiceDto.setCollectionDueDate(ServicesUtil.convertDate(entity.getCollectionDueDate()));
			invoiceDto.setInvoiceCreatedDate(ServicesUtil.convertDate(entity.getInvoiceCreatedDate()));
			invoiceDto.setCustomerId(entity.getCustomerId());
			invoiceDto.setStatus(entity.getStatus());
			invoiceDto.setDocumentType(entity.getDocumentType());
			invoiceDto.setTransaction(transactionDao.exportDto(entity.getTransaction()));

		}

		return invoiceDto;

	}

	public void createInvoice(InvoiceDto dto) {
		persist(importDto(dto));
	}

	public void updateInvoice(InvoiceDto dto) {
		update((importDto(dto)));
	}

	public void deleteInvoice(InvoiceDto dto) {
		delete((importDto(dto)));
	}

	public InvoiceDto getInvoice(InvoiceDto dto) {
		return exportDto(get(importDto(dto)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getStatusByInvoiceNo(String invoiceNo) {

		String status = "";

		if (invoiceNo != null) {

			List<InvoiceDo> invoiceList = new ArrayList<InvoiceDo>();
			String query = "from InvoiceDo i where i.sapInvoiceNumber=:sapInvoiceNumber";
			Query q = getSession().createQuery(query);
			q.setParameter("sapInvoiceNumber", invoiceNo);

			invoiceList = q.list();

			if (!invoiceList.isEmpty()) {

				for (InvoiceDo i : invoiceList) {
					if (i.getStatus() != null && i.getStatus().equalsIgnoreCase("Inprogress")) {
						status = i.getStatus();
						break;
					}
				}

			}
		}
		return status;

	}

	@Override
	public int updateInvoiceStatus(String transactionId, String status) {
		String query = "Update InvoiceDo i set i.status=:status where i.transaction.transactionId=:transactionId";
		Query q = getSession().createQuery(query);
		q.setParameter("transactionId", transactionId);
		q.setParameter("status", status);

		return q.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public LocalDate getFirstOfCreditMonth(String custId) {

		LocalDate localDate;

		List<Date> listOfDate;
		String query = "select i.invoiceCreatedDate from InvoiceDo i where i.customerId=:customerId order by i.invoiceCreatedDate asc";
		Query q = getSession().createQuery(query);
		q.setParameter("customerId", custId);
		q.setFirstResult(0);
		q.setMaxResults(1);
		listOfDate = q.list();

		if (listOfDate != null && !listOfDate.isEmpty()) {

			localDate = listOfDate.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.with(TemporalAdjusters.firstDayOfNextMonth());
		} else {

			localDate = LocalDate.now();
		}

		logger.error("[InvoiceDao][getFirstOfCreditMonth]:" + localDate);

		return localDate;

	}

	@Override
	public List<InvoiceDo> importList(List<InvoiceDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoiceDto> exportList(List<InvoiceDo> list) {
		// TODO Auto-generated method stub
		return null;
	}

}
