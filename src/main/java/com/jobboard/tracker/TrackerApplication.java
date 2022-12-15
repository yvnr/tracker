package com.jobboard.tracker;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrackerApplication {
	
	@PostConstruct
	void setDefaultTimeZone() {
	    TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	}
	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}

}
