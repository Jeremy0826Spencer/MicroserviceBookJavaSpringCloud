package com.south;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(DiscoveryServerApplication.class);

	public static void main(String[] args) {
		// Log server starting
		logger.info("Starting Discovery Server Application");

		SpringApplication.run(DiscoveryServerApplication.class, args);

		// Log server started successfully
		logger.info("Discovery Server Application Started Successfully");
	}
}