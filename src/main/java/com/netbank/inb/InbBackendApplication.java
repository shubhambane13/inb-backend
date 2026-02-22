package com.netbank.inb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // <--- Adds background processing capabilities
@EnableCaching
public class InbBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InbBackendApplication.class, args);
	}

}
