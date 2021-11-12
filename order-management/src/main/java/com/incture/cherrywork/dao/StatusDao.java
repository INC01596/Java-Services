package com.incture.cherrywork.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.BillingPendingTaskDto;
import com.incture.cherrywork.dtos.StatusDto;
import com.incture.cherrywork.entities.SalesManCustRelDo;
import com.incture.cherrywork.entities.StatusDo;
import com.incture.cherrywork.entities.TransactionDo;
import com.incture.cherrywork.util.ServicesUtil;



@Repository("StatusDao")
public class StatusDao extends BaseDao<StatusDo, StatusDto> implements StatusDaoLocal {

	//private static final Logger logger = LoggerFactory.getLogger(StatusDao.class);

	/*StatusDto statusDto;
	StatusDo statusDo;*/

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private SalesManCustRelDaoLocal salesManCustRelDaoLocal;

	@Override
	public StatusDo importDto(StatusDto dto) {

		StatusDo statusDo = new StatusDo();

		statusDo.setStatusId(dto.getStatusId());
		statusDo.setUpdatedBy(dto.getUpdatedBy());
		statusDo.setUpdateDate(ServicesUtil.convertDate(dto.getUpdateDate()));
		statusDo.setApproverComments(dto.getApproverComments());
		statusDo.setPendingWith(dto.getPendingWith());
		statusDo.setStatus(dto.getStatus());
		statusDo.setRejectionReason(dto.getRejectionReason());
		statusDo.setApproverName(dto.getApproverName());
		TransactionDo transactionDo = new TransactionDo();
		transactionDo.setTransactionId(dto.getTransaction().getTransactionId());

		statusDo.setTransaction(transactionDo);

		return statusDo;
	}

	@Override
	public StatusDto exportDto(StatusDo entity) {

		StatusDto statusDto = new StatusDto();

		statusDto.setStatusId(entity.getStatusId());
		statusDto.setUpdatedBy(entity.getUpdatedBy());
		statusDto.setUpdateDate(ServicesUtil.convertDate(entity.getUpdateDate()));
		statusDto.setApproverComments(entity.getApproverComments());
		statusDto.setPendingWith(entity.getPendingWith());
		statusDto.setStatus(entity.getStatus());
		statusDto.setRejectionReason(entity.getRejectionReason());
		statusDto.setApproverName(entity.getApproverName());
		statusDto.setTransaction(transactionDao.exportDto(entity.getTransaction()));

		return statusDto;
	}

	@Override
	public void createStatus(StatusDto dto) {
		persist(importDto(dto));
	}

	@Override
	public void updateStatus(StatusDto dto) {
		update((importDto(dto)));
	}

	@Override
	public void deleteStatus(StatusDto dto) {
		delete((importDto(dto)));
	}

	@Override
	public StatusDto getStatus(StatusDto dto) {
		return exportDto(get(importDto(dto)));
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<StatusDto> getTrackingDetails(String transactionId) {
//
//		List<StatusDto> statusDtoList = new ArrayList<>();
//
//		if (transactionId != null) {
//
//			String hql = "from StatusDo where transaction.transactionId=:transactionId ";
//
//			Query q = getSession().createQuery(hql);
//			q.setParameter("transactionId", transactionId);
//
//			List<StatusDo> statusDoList = q.list();
//
//			for (StatusDo status : statusDoList) {
//
//				statusDtoList.add(exportDto(status));
//			}
//
//		}
//		return statusDtoList;
//
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingPendingTaskDto> getPendingApprovals(String pendingWith) {

		List<BillingPendingTaskDto> taskList = new ArrayList<BillingPendingTaskDto>();
		// where pendingWith=:pendingWith and not ( status=:approved or
		// status=:rejected)
		Map<String, StatusDo> statusDoMap = new HashMap<String, StatusDo>();
		Map<String, String> acknowledgedMap = new HashMap<>();

		Set<String> custSet = new HashSet<>();

		if (pendingWith != null) {

			String hql = "from StatusDo";

			Query q = getSession().createQuery(hql);
			/*
			 * q.setParameter("pendingWith", pendingWith);
			 * q.setParameter("approved", "APPROVED");
			 * q.setParameter("rejected", "REJECTED");
			 */

			List<StatusDo> statusDoList = q.list();

			for (StatusDo s : statusDoList) {

				if (statusDoMap.containsKey(s.getTransaction().getTransactionId())) {

					if ((statusDoMap.get(s.getTransaction().getTransactionId())).getUpdateDate()
							.compareTo(s.getUpdateDate()) < 0) {
						statusDoMap.put(s.getTransaction().getTransactionId(), s);

						if (s.getStatus().equalsIgnoreCase("Acknowledged")) {
							acknowledgedMap.put(s.getTransaction().getTransactionId(), s.getApproverComments());
						}

					}
				} else {
					statusDoMap.put(s.getTransaction().getTransactionId(), s);

					if (s.getStatus().equalsIgnoreCase("Acknowledged")) {
						acknowledgedMap.put(s.getTransaction().getTransactionId(), s.getApproverComments());
					}
				}

				// making cust set
				custSet.add(s.getTransaction().getCustomerId());
			}

			/*logger.error("[E-StatusDao][getPendingApprovals]:CustomerList size formed for get Branch from Excel:"
					+ custSet.size());*/

			List<String> custList = new ArrayList<>(custSet);

			// getting branch code from excel
			List<SalesManCustRelDo> brachList = salesManCustRelDaoLocal.getSalesCustRelDtoByCustList(custList);

			Map<String, String> branchMap = new HashMap<>();

			if (brachList != null && !brachList.isEmpty()) {

				for (SalesManCustRelDo slaesDo : brachList) {

					if (slaesDo.getBranch() != null && !slaesDo.getBranch().trim().isEmpty()) {
						branchMap.put(slaesDo.getSapCustCode(), slaesDo.getBranch());
					}
				}

			}

			/*logger.error("[E-StatusDao][getPendingApprovals]:CustomerListMap size formed after get Branch from Excel:"
					+ branchMap.size());*/

			for (String statusId : statusDoMap.keySet()) {

				BillingPendingTaskDto taskDto = new BillingPendingTaskDto();

				StatusDto newStatusDto = new StatusDto();
				// statusDtoList.add(exportDto(statusDoMap.get(statusId)));
				newStatusDto = exportDto(statusDoMap.get(statusId));

				taskDto.setStatus(newStatusDto.getStatus());
				taskDto.setTransactionId(newStatusDto.getTransaction().getTransactionId());
				taskDto.setAmount(newStatusDto.getTransaction().getAmount());
				taskDto.setBankName(newStatusDto.getTransaction().getBankName());
				taskDto.setCustomerId(newStatusDto.getTransaction().getCustomerId());
				taskDto.setListInvoiceDto(newStatusDto.getTransaction().getInvoiceList());
				taskDto.setModeOfPayment(newStatusDto.getTransaction().getModeOfPayment());
				taskDto.setRequester(newStatusDto.getTransaction().getSalesRep());
				taskDto.setPaymentDate(newStatusDto.getTransaction().getDateOfPayment());
				taskDto.setComment(newStatusDto.getTransaction().getComment());
				taskDto.setSalesRepName(newStatusDto.getTransaction().getSalesRepName());
				taskDto.setCustomerName(newStatusDto.getTransaction().getCustomerName());
				taskDto.setCurrency(newStatusDto.getTransaction().getCurrency());
				taskDto.setCustomerPhoneNo(newStatusDto.getTransaction().getCustomerPhoneNo());
				taskDto.setSalesRepPhoneNo(newStatusDto.getTransaction().getSalesRepPhoneNo());
				taskDto.setChequeNumber(newStatusDto.getTransaction().getChequeNumber());
				taskDto.setAttachmentList(newStatusDto.getTransaction().getAttachmentList());
				taskDto.setApproverComments(newStatusDto.getApproverComments());
				taskDto.setRejectionReason(newStatusDto.getRejectionReason());
				taskDto.setAcknowledgeComments(acknowledgedMap.get(newStatusDto.getTransaction().getTransactionId()));

				// adding branch

				String branch = branchMap.get(newStatusDto.getTransaction().getCustomerId());

				taskDto.setBranch(branch != null ? branch : "");

				taskDto.setApproverName(newStatusDto.getApproverName());

				taskList.add(taskDto);

			}
			

		}

		return taskList;
	}

	@Override
	public List<StatusDo> importList(List<StatusDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatusDto> exportList(List<StatusDo> list) {
		// TODO Auto-generated method stub
		return null;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<BillingPendingTaskDto> getPendingApprovalsFromView(String pendingWith) {
//		
//		List<BillingPendingTaskDto> taskList = new ArrayList<BillingPendingTaskDto>();
//		Map<String, BillingViewDto> statusDoMap = new HashMap<String, BillingViewDto>();
//		Map<String, String> acknowledgedMap = new HashMap<>();
//		List<BillingViewDto> statusDoList = new ArrayList<>();
//
//		Set<String> custSet = new HashSet<>();
//
//		if (pendingWith != null) {
//
//			
//			List<Object[]> billingView = getSession().createSQLQuery(
//				    "SELECT * FROM COLLECTION_APPROVAL" )
//				.list();
//
//				for(Object[] billing : billingView) {
//					BillingViewDto viewObject = new BillingViewDto();
//					
//					viewObject.setTransactionId((String) billing[0]);
//					viewObject.setSalesRepName((String) billing[1]);
//					viewObject.setCustId((String) billing[2]);
//				    viewObject.setCustName((String) billing[3]);
//				    viewObject.setDateOfPayment((Date) billing[4]);
//				    viewObject.setAmount((BigDecimal) billing[5]);
//				    viewObject.setModeOfPayment((String) billing[6]);
//				    viewObject.setChequeNumber((String) billing[7]);
//				    viewObject.setComment((String) billing[8]);
//				    viewObject.setAckComments((String) billing[9]);
//				    viewObject.setAprComments((String) billing[10]);
//				    viewObject.setRejectionReason((String) billing[11]);
//				    viewObject.setApproverName((String) billing[12]);
//				    viewObject.setMainStatus((String) billing[13]);
//				    statusDoList.add(viewObject);
//				  
//				}
//			
//
//			for (BillingViewDto s : statusDoList) {
//
//				if (statusDoMap.containsKey(s.getTransactionId())) {
//
//					if ((statusDoMap.get(s.getTransactionId())).getDateOfPayment()
//							.compareTo(s.getDateOfPayment()) < 0) {
//						statusDoMap.put(s.getTransactionId(), s);
//
//						if (s.getMainStatus().equalsIgnoreCase("Acknowledged")) {
//							acknowledgedMap.put(s.getTransactionId(), s.getAprComments());
//						}
//
//					}
//				} else {
//					statusDoMap.put(s.getTransactionId(), s);
//
//					if (s.getMainStatus().equalsIgnoreCase("Acknowledged")) {
//						acknowledgedMap.put(s.getTransactionId(), s.getAprComments());
//					}
//				}
//
//				// making cust set
//				custSet.add(s.getCustId());
//			}
//
//			
//			List<String> custList = new ArrayList<>(custSet);
//
//			List<SalesManCustRelDo> brachList = salesManCustRelDaoLocal.getSalesCustRelDtoByCustList(custList);
//
//			Map<String, String> branchMap = new HashMap<>();
//
//			if (brachList != null && !brachList.isEmpty()) {
//
//				for (SalesManCustRelDo slaesDo : brachList) {
//
//					if (slaesDo.getBranch() != null && !slaesDo.getBranch().trim().isEmpty()) {
//						branchMap.put(slaesDo.getSapCustCode(), slaesDo.getBranch());
//					}
//				}
//
//			}
//
//			
//			for (String statusId : statusDoMap.keySet()) {
//
//				BillingPendingTaskDto taskDto = new BillingPendingTaskDto();
//
//				BillingViewDto billingStatus = new BillingViewDto();
//				billingStatus = statusDoMap.get(statusId);
//
//				taskDto.setStatus(billingStatus.getMainStatus());// main status
//				taskDto.setTransactionId(billingStatus.getTransactionId());
//				taskDto.setAmount(billingStatus.getAmount());
//				taskDto.setCustomerId(billingStatus.getCustId());
//				taskDto.setModeOfPayment(billingStatus.getModeOfPayment());
//				taskDto.setPaymentDate(billingStatus.getDateOfPayment());
//				taskDto.setComment(billingStatus.getComment());
//				taskDto.setSalesRepName(billingStatus.getSalesRepName());
//				taskDto.setCustomerName(billingStatus.getCustName());
//				taskDto.setChequeNumber(billingStatus.getChequeNumber());
//				taskDto.setApproverComments(billingStatus.getAprComments());
//				taskDto.setRejectionReason(billingStatus.getRejectionReason());
//				taskDto.setAcknowledgeComments(billingStatus.getAckComments());
//
//				// adding branch
//
//				String branch = branchMap.get(billingStatus.getCustId());
//				taskDto.setBranch(branch != null ? branch : "");
//				taskDto.setApproverName(billingStatus.getApproverName());
//				taskList.add(taskDto);
//
//			}
//			
//
//		}
//
//		return taskList;
//	
//		
//	
//	}
}
