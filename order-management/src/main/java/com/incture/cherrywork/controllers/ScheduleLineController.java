package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ScheduleLineDto;
import com.incture.cherrywork.services.ScheduleLineService;


@RestController
@EnableWebMvc
@CrossOrigin("*")
@RequestMapping("/scheduleLine")
public class ScheduleLineController {

	public ScheduleLineController() {
		System.err.println("inside schedule line controller");
	}

	@Autowired
	private ScheduleLineService services;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity createScheduleLine(@RequestBody ScheduleLineDto scheduleLineDto) {
		System.err.println("Schedule Line Created Successfully");
		return services.saveOrUpdateScheduleLine(scheduleLineDto);
	}

	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateScheduleLine(@RequestBody ScheduleLineDto scheduleLineDto) {
		System.err.println("Schedule Line Updated Successfully");
		return services.saveOrUpdateScheduleLine(scheduleLineDto);
	}

	@GetMapping("/list")
	public ResponseEntity listAllScheduleLines() {
		System.err.println("Inside Schedule Lines List");
		return services.listAllScheduleLines();
	}

	// Not working
	@DeleteMapping("/deleteById/{scheduleLineId}&{soItemNum}&{soHeadNum}")
	public ResponseEntity deleteScheduleLineById(@PathVariable("scheduleLineId") String scheduleLineId,
			@PathVariable("soItemNum") String soItemNum, @PathVariable("soHeadNum") String soHeadNum) {
		System.err.println("Inside Delete Schedule Line by Id");
		return services.deleteScheduleLineById(scheduleLineId, soHeadNum, soItemNum);
	}

	@GetMapping("/findScheduleLineById/{scheduleLineId}&{soItemNum}&{soHeadNum}")
	public ResponseEntity findScheduleLineById(@PathVariable("scheduleLineId") String scheduleLineId,
			@PathVariable("soItemNum") String soItemNum, @PathVariable("soHeadNum") String soHeadNum) {
		System.err.println("Inside Schedule Line find by Id method");
		return services.getScheduleLineById(scheduleLineId, soHeadNum, soItemNum);
	}

}

