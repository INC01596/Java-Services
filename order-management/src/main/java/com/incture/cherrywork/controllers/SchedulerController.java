package com.incture.cherrywork.controllers;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.Odat.Dto.WorkflowTriggerInputDto;
import com.incture.cherrywork.OdataSe.ODataConsumingService;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTimeDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.SchedulerTableService;



/**
 * @author Mohit.Basak
 *
 */

@Configuration
@EnableScheduling
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

	private final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

	static boolean schedulerSwitch = false;

	static LocalDateTime schedulerFutureTime = LocalDateTime.now(ZoneId.of("GMT+08:00")).plusMinutes(30);

	@Autowired
	private ODataConsumingService oDataConsumingService;

	@Autowired
	private SchedulerTableService schedulerTableService;

	//@Scheduled(cron = "*/5 * * * * ?" )
	  @Scheduled(cron = "0 0/5 * * * ?")
	public void schedulerTrigger() {
		System.err.println("STEP 1 SCHEDULER ENTER time " + "=" + LocalDateTime.now(ZoneId.of("GMT+08:00"))
				+ "  according to Malasiya " + "com.incture.controllers.SchedulerController*****************");

		/*
		 * schedulerTableService.saveInDB( new
		 * SchedulerTableDto("STEP 1 SCHEDULER ENTER time ", new
		 * Date().toString(),LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		 * 
		 */ schedulerFutureTime = LocalDateTime.now(ZoneId.of("GMT+08:00")).plusMinutes(30);
		// if scheduler is on then only data will come using below method
		if (schedulerSwitch) {
			oDataConsumingService.mainScheduler();
		} else {
			System.err.println("scheduler is switched OFF now ");
		}

		System.err.println("SCHEDULER EXIT time " + "=" + LocalDateTime.now(ZoneId.of("GMT+08:00"))
				+ "  according to Malasiya " + "com.incture.controllers.SchedulerController*****************");
		/*
		 * schedulerTableService.saveInDB( new
		 * SchedulerTableDto("Last STEP SCHEDULER EXIT time ", new
		 * Date().toString(),LocalDateTime.now(ZoneId.of("GMT+05:30"))));
		 */
	}

	@GetMapping("/schedulerSwitch/{parameter}")
	public static ResponseEntity turnOffOnScheduler(@PathVariable boolean parameter) {
		if (!parameter) {
			schedulerSwitch = parameter;
			System.err.println("scheduler is switched OFF now ");
			return new ResponseEntity("OFF ", HttpStatus.OK, "Scheduler is switched OFF now ", ResponseStatus.SUCCESS);
			// return "NO" +" scheduler is switched OFF now ";
		} else {

			Duration between = Duration.between(LocalDateTime.now(ZoneId.of("GMT+08:00")), schedulerFutureTime);
			schedulerSwitch = parameter;
			System.err.println("scheduler is switched ON now and will start in =  " + between);
			return new ResponseEntity("ON ", HttpStatus.OK, "Scheduler is switched ON now and will start in =   "
					+ between + "   ,on Malaysian time =  " + schedulerFutureTime, ResponseStatus.SUCCESS);
			// return "YES " + " ,scheduler is switched ON now and will start in
			// = " + between + " ,on Malaysian time =
			// "+LocalDateTime.now(ZoneId.of("GMT+08:00"));
		}
	}

	@GetMapping("/schedulerRunningStatus")
	public boolean schedulerRunningStatus() {
		return schedulerSwitch;
	}

	@GetMapping("/getAllLogsOfScheduler")
	public ResponseEntity listAllSchedulerLogs() {
		return schedulerTableService.listAllLog();
	}

	@GetMapping("/getAllLogsOfSchedulerWithDateRange/{startDate}&{endDate}")
	public ResponseEntity listAllSchedulerLogs(@PathVariable String startDate, @PathVariable String endDate) {
		return schedulerTableService.listAllLogsInIst(
				Instant.ofEpochMilli(Long.parseLong(startDate)).atZone(ZoneId.of(ZoneId.SHORT_IDS.get("IST")))
						.toLocalDateTime(),
				Instant.ofEpochMilli(Long.parseLong(endDate)).atZone(ZoneId.of(ZoneId.SHORT_IDS.get("IST")))
						.toLocalDateTime());
	}

	@PostMapping("/identifySalesOrdersToRetriggerWorkflow")
	public ResponseEntity identifySalesOrdersToRetriggerWorkflow(
			@RequestBody WorkflowTriggerInputDto workflowTriggerDto) {
		return oDataConsumingService.identifySalesOrdersToRetriggerWorkflow(workflowTriggerDto);
	}

	@PostMapping("/reTriggerSalesOrders")
	public ResponseEntity reTriggerSalesOrders(@RequestBody List<String> salesOrderNumList) {
		return oDataConsumingService.reTriggerSalesOrders(salesOrderNumList);
	}

	@PostMapping("/schedulerTriggerManual")
	public ResponseEntity schedulerTriggerManual(@RequestBody SchedulerTimeDto dto) {
			return oDataConsumingService.manualScheduler(dto.getStartDate(), dto.getEndDate(), dto.getStartTime(),
					dto.getEndTime());
		
	}

}

