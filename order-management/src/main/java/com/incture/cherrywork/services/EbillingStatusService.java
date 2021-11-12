package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.WConstants.Message;
import com.incture.cherrywork.dao.StatusDaoLocal;
import com.incture.cherrywork.dtos.ResponseDto;



@Service("EbillingStatusService")
@Transactional
public class EbillingStatusService implements EbillingStatusServiceLocal {

	//private static final Logger logger = LoggerFactory.getLogger(EbillingStatusService.class);

	@Autowired
	private StatusDaoLocal statusDaoLocal;

//	@Autowired
//	InvoiceDaoLocal invoiceDao;
//
//	@Autowired
//	private TransactionDaoLocal transactionDaoLocal;
//
//	/*@Autowired
//	private CustomerDaoLocal customerDaoLocal;*/
//
//	@Autowired
//	private UserDaoLocal userDaoLocal;
//
//	@Autowired
//	JavaMailSender notifyMail;

//	@Autowired
//	private PushNotificationUtil pushNotificationUtil;

	ResponseDto responseDto;

//	@Override
//	public ResponseDto updateStatus(BillingApprovalDto approvalDto) {
//
//		Map<String, String> settingMap = new HashMap<>();
//		responseDto = new ResponseDto();
//		try {
//			StatusDto statusDto = new StatusDto();
//			statusDto.setApproverComments(approvalDto.getComments());
//			statusDto.setStatus(approvalDto.getApprovalStatus());
//			if (approvalDto.getApprovalStatus().equalsIgnoreCase("Acknowledged")) {
//				statusDto.setPendingWith("AccountExecutive");
//
//			} else {
//				statusDto.setPendingWith("");
//			}
//			TransactionDto transactionDto = new TransactionDto();
//			transactionDto.setTransactionId(approvalDto.getTransactionId());
//			statusDto.setTransaction(transactionDto);
//			statusDto.setUpdateDate(new Date());
//			statusDto.setUpdatedBy(approvalDto.getApproverId());
//			statusDto.setRejectionReason(approvalDto.getRejectionReason());
//			statusDto.setApproverName(approvalDto.getApproverName());
//			statusDaoLocal.createStatus(statusDto);
//
//			invoiceDao.updateInvoiceStatus(approvalDto.getTransactionId(), approvalDto.getApprovalStatus());
//
//			TransactionDto transactionDtoForMail = new TransactionDto();
//			transactionDtoForMail = transactionDaoLocal.getTransactionById(approvalDto.getTransactionId());
//			/*
//			 * CustomerDto customerDto = new CustomerDto();
//			 * customerDto.setCustId(transactionDtoForMail.getCustomerId());
//			 * customerDto = customerDaoLocal.getCustomer(customerDto);
//			 */
//
//			settingMap = buildMail(approvalDto.getApprovalStatus(), approvalDto.getRequester(),
//					approvalDto.getTransactionId(), approvalDto.getApproverId(), approvalDto.getComments(),
//					approvalDto.getRejectionReason(), transactionDtoForMail);
//
//			notifyMail.sendMailCloud(settingMap.get("recipient"), settingMap.get("subject"),
//					settingMap.get("mailBody"));
//          
////			if (flag) {
////
////				logger.error("[EbillingStatusService][updateStatus]: Mail sent Successfully to :"
////						+ approvalDto.getRequester());
////			} else {
////
////				logger.error("[EbillingStatusService][updateStatus]: Mail send failed to :" + approvalDto.getRequester()
////						+ "Transaction id:" + approvalDto.getTransactionId());
////			}
//
////			String mes = approvalDto.getTransactionId() + " is " + approvalDto.getApprovalStatus();
//
//			List<String> userList = new ArrayList<>();
//			userList.add(approvalDto.getRequester());
//			//pushNotificationUtil.sendPushNotification(mes, userList);
//
//			responseDto.setCode(HttpStatus.SC_OK);
//			responseDto.setStatus(Boolean.TRUE);
//			responseDto.setMessage(Message.SUCCESS.toString());
//
//		} catch (Exception e) {
//			responseDto.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//			responseDto.setStatus(Boolean.FALSE);
//			responseDto.setMessage(Message.FAILED + " " + e.getMessage() + ":" + approvalDto.getTransactionId());
//		}
//		return responseDto;
//	}
//
//	@Override
//	public ResponseDto getTrackingDetails(String transactionId) {
//		responseDto = new ResponseDto();
//		List<StatusDto> ebillingStatusList = new ArrayList<StatusDto>();
//		List<TrackingDto> resultList = new ArrayList<TrackingDto>();
//		TrackingDto trackingDto;
//
//		ebillingStatusList = statusDaoLocal.getTrackingDetails(transactionId);
//		try {
//			int counter = 0;
//			for (StatusDto dto : ebillingStatusList) {
//				trackingDto = new TrackingDto();
//				if (counter == 0) {
//					trackingDto.setCreatedBy(dto.getUpdatedBy());
//					trackingDto.setCreatedDate(dto.getUpdateDate());
//					trackingDto.setPendingWith(dto.getPendingWith());
//					trackingDto.setTitle("Request Created By :" + dto.getTransaction().getSalesRepName());
//					trackingDto.setCreatedByName(dto.getTransaction().getSalesRepName());
//					trackingDto.setOrderStatus(dto.getStatus());
//					trackingDto.setOrderStatusId(Long.parseLong(dto.getStatusId() + ""));
//					trackingDto.setApproverComments(dto.getTransaction().getComment());
//
//					resultList.add(trackingDto);
//					counter++;
//				} else {
//					trackingDto.setApprovedDate(dto.getUpdateDate());
//					trackingDto.setOrderStatus(dto.getStatus());
//					trackingDto.setPendingWith(dto.getPendingWith());
//					trackingDto.setOrderStatusId(Long.parseLong(dto.getStatusId() + ""));
//					if (dto.getStatus().equalsIgnoreCase("Acknowledged")) {
//						trackingDto.setTitle("Request Acknowledged by :" + dto.getUpdatedBy());
//					} else {
//						if (dto.getStatus().equalsIgnoreCase("Approved")) {
//							trackingDto.setTitle("Request Approved by :" + dto.getUpdatedBy());
//						} else {
//							trackingDto.setTitle("Request Rejected by :" + dto.getUpdatedBy());
//
//						}
//						trackingDto.setApproverId(dto.getUpdatedBy());
//						trackingDto.setApproverComments(dto.getApproverComments());
//						resultList.add(trackingDto);
//						counter++;
//					}
//				}
//			}
//			responseDto.setData(resultList);
//			responseDto.setCode(HttpStatus.SC_OK);
//			responseDto.setStatus(true);
//			responseDto.setMessage(Message.SUCCESS.toString());
//
//		} catch (Exception e) {
//			responseDto.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//			responseDto.setStatus(false);
//			responseDto.setMessage(Message.FAILED + " " + e.getMessage());
//		}
//		return responseDto;
//	}

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

//	private Map<String, String> buildMail(String status, String requester, String transactionId, String approverId,
//			String comments, String rejectionReason, TransactionDto transactionDto) {
//
//		UserDto userDto = userDaoLocal.getUserById(approverId);
//
//		Map<String, String> mailMap = new HashMap<>();
//		mailMap.put("subject", "Collections: Transaction with ID : " + transactionId + " of "
//				+ transactionDto.getCustomerName() + " is " + status);
//		mailMap.put("recipient", requester);
//
//		StringBuffer html = new StringBuffer();
//
//		html.append("<p>Dear Requestor,");
//		html.append("<br><br>");
//		html.append("Transaction with  ID: <b>");
//		html.append(transactionId);
//		html.append("</b> of Customer : <b> " + transactionDto.getCustomerName());
//		html.append(" </b> is ");
//		html.append(status);
//		html.append(" by " + userDto.getUserName());
//		html.append("<br><br> <b>Comments :</b>" + comments);
//
//		if (status != null & status.equalsIgnoreCase("REJECTED")) {
//
//			html.append("<br> <b> Rejection Reason : </b>" + rejectionReason);
//		}
//
//		html.append("<br> <b> Mode of Payment : </b> " + transactionDto.getModeOfPayment());
//		html.append("<br> <b> Paid Amount :</b>" + transactionDto.getAmount());
//
//		if (transactionDto.getModeOfPayment().equalsIgnoreCase("cheque")) {
//
//			html.append("<br> <b> Cheque Bank :</b> " + transactionDto.getBankName());
//			html.append("<br> <b> Cheque Date :</b> " + ServicesUtil.convertDate(transactionDto.getChequeDate()));
//
//		}
//
//		if (transactionDto.getModeOfPayment().equalsIgnoreCase("wire transfer")) {
//
//			html.append("<br> <b> Bank Name :</b> " + transactionDto.getBankName());
//			html.append("<br> <b> Payment Date :</b> " + ServicesUtil.convertDate(transactionDto.getChequeDate()));
//
//		}
//
//		html.append("<br><br> <a href=\"" + HciApiConstants.WEB_URL + "\">Visit Delfi Web</a>");
//
//		html.append("<br><br>");
//		html.append("Best regards,");
//		html.append("<br>");
//		html.append("<font color='EE104C'><i><b>Delfi Team</b></i></font>");
//		html.append("<br><br>");
//		html.append("<font color='5D90AC'>");
//		html.append("Note: This is auto generated email please do not reply.</font>");
//		html.append("<br><br><br>");
//		html.append("</p>");
//
//		mailMap.put("mailBody", new String(html));
//		return mailMap;
//	}

}
