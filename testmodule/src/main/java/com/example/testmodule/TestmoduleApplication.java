package com.example.testmodule;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.example.config","com.example.service"})
public class TestmoduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestmoduleApplication.class, args);
    }

}
