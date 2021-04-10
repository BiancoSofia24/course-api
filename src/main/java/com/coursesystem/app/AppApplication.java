package com.coursesystem.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	private static final Logger log = LoggerFactory.getLogger(AppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public CommandLineRunner logs() {
		return(args) -> {
			log.info("COURSE APP");
			log.info("=====================");
			log.info("This is a Java project. Using Spring Boot");
			log.info("--------------------------------");
			log.info("Made it by: Sofia Bianco with <3 - 03/2021"); 
			log.info("--------------------------------");
		};
	}

}
