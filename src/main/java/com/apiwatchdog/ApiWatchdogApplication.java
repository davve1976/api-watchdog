package com.apiwatchdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiWatchdogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiWatchdogApplication.class, args);
    }
}
