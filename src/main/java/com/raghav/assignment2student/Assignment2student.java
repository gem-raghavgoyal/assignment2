package com.raghav.assignment2student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Assignment2student {

    public static void main(String[] args) {
        SpringApplication.run(Assignment2student.class, args);
    }

}
