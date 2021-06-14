package com.mangh.taskrit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class}
)
public class TaskritApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskritApplication.class, args);
	}

}
