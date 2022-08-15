package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.example.config","com.example.service","com.example.model","com.example.writer"})
@EnableBatchProcessing
@EnableScheduling
//@ComponentScan({"com.example.config","com.example.service","com.example.model","com.example.reader","com.example.writer"})
public class TestmoduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestmoduleApplication.class, args);
    }

}
