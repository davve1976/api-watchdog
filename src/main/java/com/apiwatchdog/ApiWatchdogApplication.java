package com.apiwatchdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
})
@EnableScheduling
public class ApiWatchdogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiWatchdogApplication.class, args);
    }
}