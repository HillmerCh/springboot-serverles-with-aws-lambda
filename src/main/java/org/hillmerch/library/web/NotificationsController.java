package org.hillmerch.library.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hillmerch.library.service.NotificationsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationsController {

	private static final Logger logger = LoggerFactory.getLogger( NotificationsController.class);

	@Autowired
	NotificationsClient notificationsClient;


	@GetMapping("/notifications/{name}/{phone}/{message}")
	public String sendNotification(@PathVariable String name, @PathVariable String phone, @PathVariable String message) {
		logger.info("Sending notification to " + name + ", Message: " + message);
		return notificationsClient.sendNotification( name, phone, message);
	}
}
