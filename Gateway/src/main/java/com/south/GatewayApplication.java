package com.south;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		// Log server starting
		logger.info("Starting Gateway Service Application");

		SpringApplication.run(GatewayApplication.class, args);

		// Log server started successfully
		logger.info("Gateway Service Application Started Successfully");
	}
}

