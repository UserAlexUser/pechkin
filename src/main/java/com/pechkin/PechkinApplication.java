package com.pechkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class PechkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PechkinApplication.class, args);
	}

}
