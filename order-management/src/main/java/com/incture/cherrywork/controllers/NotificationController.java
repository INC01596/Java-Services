package com.incture.cherrywork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.NotificationConfigDto;
import com.incture.cherrywork.dtos.NotificationListDto;
import com.incture.cherrywork.services.NotificationConfigService;
import com.incture.cherrywork.services.NotificationDetailService;
//import com.notification.NotificationListDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Notification", tags = { "Notification" })
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationDetailService notificationDetailService;

	@Autowired
	private NotificationConfigService notificationConfigService;

	@GetMapping("/getNotification/{userId}")
	@ApiOperation(value = "getNotification")
	public ResponseEntity<Object> getNotification(@PathVariable("userId") String userId) {
		NotificationListDto notificationListDto = null;
		try {
			//notificationListDto = notificationDetailService.getNotification(userId);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Error Fetching Notification").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notification Fetched Successfully")
				.body(notificationListDto);
	}

	@GetMapping("/markSeen/{notificationId}")
	@ApiOperation(value = "markseen the notification")
	public ResponseEntity<Object> markSeen(@PathVariable("notificationId") String notificationId) {
		try {
			notificationDetailService.markSeen(notificationId);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Error Marking Seen Notification").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notification Marked Seen Successfully")
				.body(null);
	}

	@GetMapping("/markAllSeen/{userId}")
	@ApiOperation(value = "mark all as seen")
	public ResponseEntity<Object> markAllSeen(@PathVariable("userId") String userId) {
		try {
			notificationDetailService.markAllSeen(userId);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Error Marking Seen Notifications").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notifications Marked Seen Successfully")
				.body(null);
	}

	@GetMapping("/clearNotification/{userId}")
	@ApiOperation(value = "Clear Notification")
	public ResponseEntity<Object> clearNotification(@PathVariable("userId") String userId) {

		try {
			notificationDetailService.clearNotification(userId);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Error Clearing Notifications").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notifications Cleared Successfully").body(null);
	}

	@GetMapping("/getNofiticationAlert/{userId}")
	@ApiOperation(value = "get Notification alert")
	public ResponseEntity<Object> getNofiticationAlert(@PathVariable("userId") String userId) {
		return notificationConfigService.getNofiticationAlert(userId);
	}

	@PostMapping("/setNotificationAlert")
	@ApiOperation(value = "set Notification alert")
	public ResponseEntity<Object> setNotificationAlert(@RequestBody NotificationConfigDto configDto) {
		return notificationConfigService.setNotificationAlert(configDto);
	}

	@GetMapping("/createNotificationAlert/{userId}")
	@ApiOperation(value = "create Notification Alert")
	public ResponseEntity<Object> createNotificationAlert(@PathVariable("userId") String userId) {
		return notificationConfigService.createNotificationAlert(userId);
	}

}
