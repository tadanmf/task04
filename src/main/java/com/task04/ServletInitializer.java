package com.task04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServletInitializer extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
          SpringApplication.run(ServletInitializer.class, args);
          
//          System.out.println("*** " + java.awt.GraphicsEnvironment.isHeadless());
      }    

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Task04Application.class);
	}

}
