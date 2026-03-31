package com.fourman.publicinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fourman.publicinfo", "com.fourman.common"})
public class PublicInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicInfoApplication.class, args);
	}
}
