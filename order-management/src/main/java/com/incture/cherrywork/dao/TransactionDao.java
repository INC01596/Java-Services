package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.AttachmentDto;
import com.incture.cherrywork.dtos.InvoiceDto;
import com.incture.cherrywork.dtos.StatusDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.entities.AttachmentDo;
import com.incture.cherrywork.entities.InvoiceDo;
import com.incture.cherrywork.entities.StatusDo;
import com.incture.cherrywork.entities.TransactionDo;
import com.incture.cherrywork.util.ServicesUtil;



/**
 * @author Polireddy.M
 *
 */
@Repository
@Transactional
public class TransactionDao extends BaseDao<TransactionDo, TransactionDto> implements TransactionDaoLocal {

	/*
	 * TransactionDo transactionDo; TransactionDto transactionDto;
	 */

	@Override
	public TransactionDo importDto(TransactionDto dto) {

		TransactionDo transactionDo = new TransactionDo();

		if (dto != null) {

			transactionDo.setTransactionId(dto.getTransactionId());
			transactionDo.setSalesRep(dto.getSalesRep());
			transactionDo.setCustomerId(dto.getCustomerId());
			transactionDo.setChequeNumber(dto.getChequeNumber());
			transactionDo.setCustomerName(dto.getCustomerName());
			transactionDo.setSalesRepName(dto.getSalesRepName());
			transactionDo.setCurrency(dto.getCurrency());
			transactionDo.setSalesRepPhoneNo(dto.getSalesRepPhoneNo());
			transactionDo.setCustomerPhoneNo(dto.getCustomerPhoneNo());
			transactionDo.setOutstandingAmount(dto.getOutstandingAmount());

			if (dto.getDateOfPayment() != null) {
				transactionDo.setDateOfPayment(ServicesUtil.dateConverterForPlanner(dto.getDateOfPayment()));
			}

			transactionDo.setModeOfPayment(dto.getModeOfPayment());

			if (dto.getTaskAllocatedDate() != null) {
				transactionDo.setTaskAllocatedDate(ServicesUtil.dateConverterForPlanner(dto.getTaskAllocatedDate()));
			}
			if (dto.getChequeDate() != null) {
				transactionDo.setChequeDate(ServicesUtil.dateConverterForPlanner(dto.getChequeDate()));
			}
			transactionDo.setAmount(dto.getAmount());
			transactionDo.setComment(dto.getComment());
			transactionDo.setBankName(dto.getBankName());

			List<StatusDo> statusList = new ArrayList<>();

			if (dto.getStatusList() != null) {

				for (StatusDto statusDto1 : dto.getStatusList()) {

					StatusDo statusDo = new StatusDo();

					statusDo.setStatusId(statusDto1.getStatusId());
					statusDo.setUpdatedBy(statusDto1.getUpdatedBy());
					if (statusDto1.getUpdateDate() != null) {
						statusDo.setUpdateDate(ServicesUtil.convertDate(statusDto1.getUpdateDate()));
					}
					statusDo.setApproverComments(statusDto1.getApproverComments());
					statusDo.setPendingWith(statusDto1.getPendingWith());
					statusDo.setStatus(statusDto1.getStatus());
					statusDo.setTransaction(transactionDo);

					statusList.add(statusDo);
				}

			}

			transactionDo.setStatusList(statusList);

			List<AttachmentDo> attachmentList = new ArrayList<>();

//			if (dto.getAttachmentList() != null) {
//
//				for (AttachmentDto attachmentDto1 : dto.getAttachmentList()) {
//
//					AttachmentDo attachmentDo = new AttachmentDo();
//
//					attachmentDo.setAttachmentId(attachmentDto1.getAttachmentId());
//					attachmentDo.setAttachmentReference(attachmentDto1.getAttachmentReference());
//					attachmentDo.setTransaction(transactionDo);
//
//					attachmentList.add(attachmentDo);
//				}
//
//			}

//			transactionDo.setAttachmentList(attachmentList);

			List<InvoiceDo> invoiceList = new ArrayList<>();

			if (dto.getInvoiceList() != null) {

				for (InvoiceDto invoiceDto1 : dto.getInvoiceList()) {

					InvoiceDo invoiceDo = new InvoiceDo();

					invoiceDo.setIsCreditNote(invoiceDto1.getIsCreditNote());
					invoiceDo.setInvoiceId(invoiceDto1.getInvoiceId());
					invoiceDo.setSapInvoiceNumber(invoiceDto1.getSapInvoiceNumber());
					invoiceDo.setInvoiceAmount(invoiceDto1.getInvoiceAmount());
					invoiceDo.setInvoiceCurrency(invoiceDto1.getInvoiceCurrency());
					invoiceDo.setPendingAmount(invoiceDto1.getPendingAmount());

					if (invoiceDto1.getCollectionDueDate() != null) {
						invoiceDo.setCollectionDueDate(
								ServicesUtil.dateConverterForPlanner(invoiceDto1.getCollectionDueDate()));

					}
					if (invoiceDto1.getInvoiceCreatedDate() != null) {
						invoiceDo.setInvoiceCreatedDate(
								ServicesUtil.dateConverterForPlanner(invoiceDto1.getInvoiceCreatedDate()));
					}
					invoiceDo.setCustomerId(invoiceDto1.getCustomerId());
					invoiceDo.setStatus(invoiceDto1.getStatus());
					invoiceDo.setTransaction(transactionDo);
					invoiceList.add(invoiceDo);

				}

			}

			transactionDo.setInvoiceList(invoiceList);

		}

		return transactionDo;
	}

	@Override
	public TransactionDto exportDto(TransactionDo entity) {

		TransactionDto transactionDto = new TransactionDto();

		if (entity != null) {

			transactionDto.setOutstandingAmount(entity.getOutstandingAmount());
			transactionDto.setTransactionId(entity.getTransactionId());
			transactionDto.setSalesRep(entity.getSalesRep());
			transactionDto.setCustomerId(entity.getCustomerId());
			transactionDto.setChequeNumber(entity.getChequeNumber());
			transactionDto.setCustomerName(entity.getCustomerName());
			transactionDto.setSalesRepName(entity.getSalesRepName());
			transactionDto.setCurrency(entity.getCurrency());
			transactionDto.setSalesRepPhoneNo(entity.getSalesRepPhoneNo());
			transactionDto.setCustomerPhoneNo(entity.getCustomerPhoneNo());
			if (entity.getDateOfPayment() != null) {
				transactionDto.setDateOfPayment(ServicesUtil.dateConverterForPlanner(entity.getDateOfPayment()));
			}
			transactionDto.setModeOfPayment(entity.getModeOfPayment());
			if (entity.getTaskAllocatedDate() != null) {
				transactionDto
						.setTaskAllocatedDate(ServicesUtil.dateConverterForPlanner(entity.getTaskAllocatedDate()));
			}
			if (entity.getChequeDate() != null) {
				transactionDto.setChequeDate(ServicesUtil.dateConverterForPlanner(entity.getChequeDate()));
			}
			transactionDto.setAmount(entity.getAmount());
			transactionDto.setComment(entity.getComment());
			transactionDto.setBankName(entity.getBankName());

			List<StatusDto> statusList = new ArrayList<>();

			if (entity.getStatusList() != null) {

				for (StatusDo statusDo1 : entity.getStatusList()) {

					StatusDto statusDto = new StatusDto();

					statusDto.setStatusId(statusDo1.getStatusId());

					statusDto.setUpdatedBy(statusDo1.getUpdatedBy());
					if (statusDo1.getUpdateDate() != null) {
						statusDto.setUpdateDate(ServicesUtil.convertDate(statusDo1.getUpdateDate()));
					}
					statusDto.setApproverComments(statusDo1.getApproverComments());
					statusDto.setPendingWith(statusDo1.getPendingWith());
					statusDto.setStatus(statusDo1.getStatus());

					statusList.add(statusDto);
				}
			}

			transactionDto.setStatusList(statusList);

			List<AttachmentDto> attachmentList = new ArrayList<>();

//			if (entity.getAttachmentList() != null) {
//
//				for (AttachmentDo attachmentDo1 : entity.getAttachmentList()) {
//
//					AttachmentDto attachmentDto = new AttachmentDto();
//
//					attachmentDto.setAttachmentId(attachmentDo1.getAttachmentId());
//					attachmentDto.setAttachmentReference(attachmentDo1.getAttachmentReference());
//
//					attachmentList.add(attachmentDto);
//				}
//
//			}

			transactionDto.setAttachmentList(attachmentList);

			List<InvoiceDto> invoiceList = new ArrayList<>();

			if (entity.getInvoiceList() != null) {

				for (InvoiceDo invoiceDo1 : entity.getInvoiceList()) {

					InvoiceDto invoiceDto = new InvoiceDto();

					invoiceDto.setIsCreditNote(invoiceDo1.getIsCreditNote());
					invoiceDto.setInvoiceId(invoiceDo1.getInvoiceId());
					invoiceDto.setSapInvoiceNumber(invoiceDo1.getSapInvoiceNumber());
					invoiceDto.setInvoiceAmount(invoiceDo1.getInvoiceAmount());
					invoiceDto.setInvoiceCurrency(invoiceDo1.getInvoiceCurrency());
					invoiceDto.setPendingAmount(invoiceDo1.getPendingAmount());
					if (invoiceDo1.getCollectionDueDate() != null) {
						invoiceDto.setCollectionDueDate(
								ServicesUtil.dateConverterForPlanner(invoiceDo1.getCollectionDueDate()));
					}
					if (invoiceDo1.getInvoiceCreatedDate() != null) {
						invoiceDto.setInvoiceCreatedDate(
								ServicesUtil.dateConverterForPlanner(invoiceDo1.getInvoiceCreatedDate()));
					}
					invoiceDto.setCustomerId(invoiceDo1.getCustomerId());
					invoiceDto.setStatus(invoiceDo1.getStatus());

					invoiceList.add(invoiceDto);

				}
			}

			transactionDto.setInvoiceList(invoiceList);

		}

		return transactionDto;
	}

	@Override
	public String createTransaction(TransactionDto dto) {

		return save(importDto(dto));
	}

	@Override
	public void updateTransaction(TransactionDto dto) {
		update((importDto(dto)));
	}

	@Override
	public void deleteTransaction(TransactionDto dto) {
		delete((importDto(dto)));
	}

	@Override
	public TransactionDto getTransactionById(String transactionId) {

		TransactionDto transactionDto = new TransactionDto();

		if (transactionId != null && !transactionId.trim().isEmpty()) {

			transactionDto = exportDto((TransactionDo) getSession().get(TransactionDo.class, transactionId));
		}
		return transactionDto;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TransactionDto> getTransactionsBySalesRepAndCustId(String salesRep, String customerId) {

		List<TransactionDto> transactionDtoList = new ArrayList<>();

		if (!salesRep.trim().isEmpty() && !customerId.trim().isEmpty()) {

			String hql = " from TransactionDo where salesRep=:salesRep and customerId=:customerId ";

			Query q = getSession().createQuery(hql);
			q.setParameter("salesRep", salesRep);
			q.setParameter("customerId", customerId);

			List<TransactionDo> transactionDoList = q.list();

			for (TransactionDo entity : transactionDoList) {

				transactionDtoList.add(exportDto(entity));

			}

		}
		return transactionDtoList;
	}

	@Override
	public Long checkTransactionId(String transactionId) {

		String hql = " select count(salesRep) from TransactionDo where transactionId=:transactionId ";

		Query q = getSession().createQuery(hql);

		q.setParameter("transactionId", transactionId);

		Long count = (Long) q.uniqueResult();

		return count;

	}

	@Override
	public List<TransactionDo> importList(List<TransactionDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionDto> exportList(List<TransactionDo> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
