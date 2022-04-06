package com.incture.cherrywork.controllers;




import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.incture.cherrywork.services.AbbyOcrService;
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



	@Autowired
	private InvoiceServicesLocal invoiceServicesLocal;
	
	@SuppressWarnings("unused")
	@Autowired
	private AbbyOcrService abbyOcrService;


	

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


	@Scheduled(cron = "0 0/5 * * * ?")
	public void cronJobForCustomers() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for Customers BulkUpload :" + dtf.format(startTime));

		customerServiceLocal.saveAllCustomerFromRfcToHanaDb();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for Customers BulkUpload :" + dtf.format(endTime));

		
	}
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void cronJobForInvoices() {

		LocalDateTime startTime = LocalDateTime.now();

		logger.error("Schedling---Starting for Invoices BulkUpload :" + dtf.format(startTime));

	invoiceServicesLocal.savePendingInvoices();

		LocalDateTime endTime = LocalDateTime.now();

		logger.error("Schedling---Completed for Invoices BulkUpload :" + dtf.format(endTime));

		


	}


}
