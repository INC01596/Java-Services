package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.incture.cherrywork.WConstants.Message;

import com.incture.cherrywork.dao.StatusDaoLocal;
import com.incture.cherrywork.dao.TransactionDaoLocal;
import com.incture.cherrywork.dtos.BillingApprovalDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.StatusDto;
import com.incture.cherrywork.dtos.TrackingDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.repositories.InvoiceRepo;



@Service("EbillingStatusService")
@Transactional
public class EbillingStatusService implements EbillingStatusServiceLocal {

	
	@Autowired
	private StatusDaoLocal statusDaoLocal;

	@Autowired
	 private InvoiceRepo invRepo;

	@Autowired
	private TransactionDaoLocal transactionDaoLocal;

	ResponseDto responseDto;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDto updateStatus(BillingApprovalDto approvalDto) {
		System.err.println("dto"+approvalDto);
		responseDto = new ResponseDto();
		try {
			StatusDto statusDto = new StatusDto();
			statusDto.setApproverComments(approvalDto.getComments());
			statusDto.setStatus(approvalDto.getApprovalStatus());
			if (approvalDto.getApprovalStatus().equalsIgnoreCase("Acknowledged")) {
				statusDto.setPendingWith("AccountExecutive");

			} else {
				statusDto.setPendingWith("");
			}
			System.err.println("statusDtodto"+statusDto.getPendingWith());
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setTransactionId(approvalDto.getTransactionId());
			statusDto.setTransaction(transactionDto);
			statusDto.setUpdateDate(new Date());
			statusDto.setUpdatedBy(approvalDto.getApproverId());
			statusDto.setRejectionReason(approvalDto.getRejectionReason());
			statusDto.setApproverName(approvalDto.getApproverName());
			statusDaoLocal.createStatus(statusDto);
			
			System.err.println("helloInvoicebefore");
				String query = "Update InvoiceDo i set i.status=:status where i.transaction.transactionId=:transactionId";
				Query q =entityManager.createQuery(query);
				 q.setParameter("transactionId", approvalDto.getTransactionId());
			    q.setParameter("status", approvalDto.getApprovalStatus());

				int a= q.executeUpdate();
				System.err.println("a "+a);
				String query1 = "Update StatusDo i set i.status=:status where i.transaction.transactionId=:transactionId";
				Query q1 =entityManager.createQuery(query1);
				 q1.setParameter("transactionId", approvalDto.getTransactionId());
			    q1.setParameter("status", approvalDto.getApprovalStatus());
			    int b= q1.executeUpdate();
			    System.err.println("b "+b);

			
			//invRepo.updateInvoiceStatus(approvalDto.getTransactionId(), approvalDto.getApprovalStatus());
			System.err.println("helloInvoiceafter");

		

			List<String> userList = new ArrayList<>();
			userList.add(approvalDto.getRequester());
			
			responseDto.setStatusCode(HttpStatus.SC_OK);
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setMessage(Message.SUCCESS.toString());

		} catch (Exception e) {
			responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setMessage(Message.FAILED + " " + e.getMessage() + ":" + approvalDto.getTransactionId());
		}
		return responseDto;
	}

	@Override
	public ResponseDto getTrackingDetails(String transactionId) {
		responseDto = new ResponseDto();
		List<StatusDto> ebillingStatusList = new ArrayList<StatusDto>();
		List<TrackingDto> resultList = new ArrayList<TrackingDto>();
		TrackingDto trackingDto;

		ebillingStatusList = statusDaoLocal.getTrackingDetails(transactionId);
		try {
			int counter = 0;
			for (StatusDto dto : ebillingStatusList) {
				trackingDto = new TrackingDto();
				if (counter == 0) {
					trackingDto.setCreatedBy(dto.getUpdatedBy());
					trackingDto.setCreatedDate(dto.getUpdateDate());
					trackingDto.setPendingWith(dto.getPendingWith());
					trackingDto.setTitle("Request Created By :" + dto.getTransaction().getSalesRepName());
					trackingDto.setCreatedByName(dto.getTransaction().getSalesRepName());
					trackingDto.setOrderStatus(dto.getStatus());
					trackingDto.setOrderStatusId(Long.parseLong(dto.getStatusId() + ""));
					trackingDto.setApproverComments(dto.getTransaction().getComment());

					resultList.add(trackingDto);
					counter++;
				} else {
					trackingDto.setApprovedDate(dto.getUpdateDate());
					trackingDto.setOrderStatus(dto.getStatus());
					trackingDto.setPendingWith(dto.getPendingWith());
					trackingDto.setOrderStatusId(Long.parseLong(dto.getStatusId() + ""));
					if (dto.getStatus().equalsIgnoreCase("Acknowledged")) {
						trackingDto.setTitle("Request Acknowledged by :" + dto.getUpdatedBy());
					} else {
						if (dto.getStatus().equalsIgnoreCase("Approved")) {
							trackingDto.setTitle("Request Approved by :" + dto.getUpdatedBy());
						} else {
							trackingDto.setTitle("Request Rejected by :" + dto.getUpdatedBy());

						}
						trackingDto.setApproverId(dto.getUpdatedBy());
						trackingDto.setApproverComments(dto.getApproverComments());
						resultList.add(trackingDto);
						counter++;
					}
				}
			}
			responseDto.setData(resultList);
			responseDto.setStatusCode(HttpStatus.SC_OK);
			responseDto.setStatus(true);
			responseDto.setMessage(Message.SUCCESS.toString());

		} catch (Exception e) {
			responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseDto.setStatus(false);
			responseDto.setMessage(Message.FAILED + " " + e.getMessage());
		}
		return responseDto;
	}

	@Override
	public ResponseDto getPendingApprovals(String pendingWith) {
		responseDto = new ResponseDto();
		try {
			responseDto.setData(statusDaoLocal.getPendingApprovals(pendingWith));
			//responseDto.setData(statusDaoLocal.getPendingApprovalsFromView(pendingWith));
			responseDto.setStatusCode(HttpStatus.SC_OK);
			responseDto.setStatus(true);
			responseDto.setMessage(Message.SUCCESS.toString());

		} catch (Exception e) {
			responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseDto.setStatus(false);
			responseDto.setMessage(Message.FAILED + " " + e.getMessage());
		}
		return responseDto;
	}


}
