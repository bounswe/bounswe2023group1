package com.a1.disasterresponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DisasterresponseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisasterresponseApplication.class, args);
	}

}
