package org.example.demo.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackendDemoApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BackendDemoApplication.class, args);
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendDemoApplication.class);
    }
}
