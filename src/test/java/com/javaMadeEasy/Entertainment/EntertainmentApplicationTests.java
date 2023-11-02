package com.javaMadeEasy.Entertainment;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntertainmentApplicationTests {
	@LocalServerPort
	private int port;

	private String baseUrl ="http://localhost";

	private static RestTemplate restTemplate;


	@Test
	void contextLoads() {
	}

	@BeforeAll
	static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	void setUp(){
		final String userBaseUrl = baseUrl.concat(":").concat(port + "").concat("/api");
		final String defaultBaseUrl = baseUrl.concat(":").concat(port + "").concat("/posts");


	}





}