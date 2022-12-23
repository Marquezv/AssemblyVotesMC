package com.vmarquezv.dev.sessionApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SessionApiApplication {
	
	static final Logger log = 
	        LoggerFactory.getLogger(SessionApiApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SessionApiApplication.class, args);
	}

}
