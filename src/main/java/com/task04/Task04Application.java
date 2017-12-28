package com.task04;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Task04Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Task04Application.class);
	    builder.headless(true).run(args);
	    
//		SpringApplication.run(Task04Application.class, args);
	}
}
