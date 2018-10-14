package com.mituo.logservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LogserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogserviceApplication.class, args);
    }
}
