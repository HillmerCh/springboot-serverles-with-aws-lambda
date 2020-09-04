package org.hillmerch.library.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class NotificationsControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String restUrl;

	@BeforeEach
	public void setup()  {
		restUrl = "http://localhost:" + port +"/api/notifications/";
	}

	private final String name = "HillmerCh";
	private final String phone = "555-00-123";
	private final String message = "Welcome to my Library in 51st century";

	@Test
	void sendNotification() {

		ResponseEntity<String> response = restTemplate.getForEntity( restUrl + "/"+name+"/"+phone+"/"+message+"", String.class);
		System.out.println(response);
		System.out.println(response.getBody());
		assertEquals( HttpStatus.OK, response.getStatusCode());

	}
}