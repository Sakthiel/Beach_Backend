package com.thoughtworks.pulpticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PulpticketApplication {

	public static void main(String[] args) {
		SpringApplication.run(PulpticketApplication.class, args);
	}

}
