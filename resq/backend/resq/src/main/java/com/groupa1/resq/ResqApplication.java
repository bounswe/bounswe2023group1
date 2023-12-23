package com.groupa1.resq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResqApplication.class, args);
	}

}
