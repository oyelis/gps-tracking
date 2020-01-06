package com.oieliseiev.gpstracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class GpsTrackingApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(GpsTrackingApplication.class, args);
	}

}
