package com.incture.cherrywork.controllers;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.services.CustomerServiceLocal;
import com.incture.cherrywork.services.InvoiceServicesLocal;


/**
 * 
 * This class is used to run scheduler jobs in application
 * 
 * @author Polireddy.M
 * 
 * @since September 9 2018
 * 
 * 
 *
 */
@Component
@Configuration
@EnableScheduling
public class Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	private CustomerServiceLocal customerServiceLocal;

//	@Autowired
//	private SalesOrderServicesLocal salesOrderServicesLocal;

	@Autowired
	private InvoiceServicesLocal invoiceServicesLocal;

//	@Autowired
//	private IdpServiceLocal idpServiceLocal;
//
//	@Autowired
//	private JavaMailSender sendMail;

//	@Autowired
//	private HciPostSalesOrderLocal hciPostSalesOrderLocal;
//
//	@Autowired
//	private DeleteTempFilesInServerUtil deleteTempFilesInServerUtil;

	ResponseDto responseDto;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


	@Scheduled(cron = "0 0/5 * * * ?")
	public void cronJobForCustomers() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for Customers BulkUpload :" + dtf.format(startTime));

		responseDto = customerServiceLocal.saveAllCustomerFromRfcToHanaDb();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for Customers BulkUpload :" + dtf.format(endTime));

		logger.error("Total time taken By Customers BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + "status=" + responseDto.isStatus());

		String body = "<p>" + "Total time taken By Customers BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + "status=" + responseDto.isStatus() + "</p>";

		//sendMail.sendMailCloud("P000010", "Scheduler Information of Cutomers", body);

	}

	/*@Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForDrafts() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for Drafts deletion :" + dtf.format(startTime));

		responseDto = salesOrderServicesLocal.deleteDraftsWhichExceeds3days();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for Drafts deletion :" + dtf.format(endTime));

		logger.error("Total time taken By Drafts deletion(Sec)=" + Duration.between(startTime, endTime).getSeconds()
				+ ":::status=" + responseDto.isStatus() + "deletionCount::" + responseDto.getData());

		String body = "<p>" + "Total time taken By Drafts deletion(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus()
				+ "deletionCount::" + responseDto.getData() + "</p>";

		sendMail.sendMailCloud("P000010", "Scheduler Information of Drafts", body);
	}*/

	@Scheduled(cron = "0 0/5 * * * ?")
	public void cronJobForInvoices() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for Invoices BulkUpload :" + dtf.format(startTime));

		responseDto = invoiceServicesLocal.savePendingInvoices();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for Invoices BulkUpload :" + dtf.format(endTime));

		logger.error("Total time taken By Invoices BulkUpload(Sec)=" + Duration.between(startTime, endTime).getSeconds()
				+ ":status:" + responseDto.isStatus());

//		String body = "<p>" + "Total time taken By Invoices BulkUpload(Sec)="
//				+ Duration.between(startTime, endTime).getSeconds() + ":status:" + responseDto.isStatus() + "</p>";
//
//		sendMail.sendMailCloud("P000010", "Scheduler Information of Invoices", body);
	}

	/*@Scheduled(cron = "0 30 1 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForIdpUsers() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for IDP Users BulkUpload :" + dtf.format(startTime));

		responseDto = idpServiceLocal.saveAllUsersFromIdpToHana();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for IDP Users BulkUpload :" + dtf.format(endTime));

		logger.error("Total time taken By IDP Users BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus());

		String body = "<p>" + "Total time taken By IDP Users BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus() + "</p>";

		sendMail.sendMailCloud("P000010", "Scheduler Information of IDP", body);

	}
*/
	/*@Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForOrderStatus() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for OrderStatus BulkUpload :" + dtf.format(startTime));

		responseDto = salesOrderServicesLocal.updateTrackOrderInHanaFromRfc();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for OrderStatus BulkUpload :" + dtf.format(endTime));

		logger.error("Total time taken By OrderStatus BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus());

		String body = "<p>" + "Total time taken By OrderStatus BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus() + "</p>";

		sendMail.sendMailCloud("P000010", "Scheduler Information of OrderStatus ", body);

	}*/

	/*@Scheduled(cron = "0 0 8,10,12,14,16,18,20 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForFailedOrders() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for FailedOrders BulkUpload :" + dtf.format(startTime));

		responseDto = salesOrderServicesLocal.postFailedOrdersListToEcc();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for FailedOrders BulkUpload :" + dtf.format(endTime));

		logger.error("Total time taken By FailedOrders BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus());

		String body = "<p>" + "Total time taken By FailedOrders BulkUpload(Sec)="
				+ Duration.between(startTime, endTime).getSeconds() + ":::status=" + responseDto.isStatus() + "</p>";

		sendMail.sendMailCloud("P000003", "Scheduler Information of FailedOrders ", body);

	}

	@Scheduled(cron = "0 0/7 5-23 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForReleaseOrderBlock() {

		hciPostSalesOrderLocal.releaseOrderBlockBulk();

	}

	@Scheduled(cron = "0 1 2 * * ?", zone = "Asia/Kuala_Lumpur")
	public void cronJobForDeleteTempInServer() {

		Boolean flag = deleteTempFilesInServerUtil.deleteFilesInServer();

		sendMail.sendMailCloud("P000003", "Scheduler Information of delete temp files in server ", ("status:" + flag));

	}
*/
}
