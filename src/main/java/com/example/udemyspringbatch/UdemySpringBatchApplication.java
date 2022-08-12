package com.example.udemyspringbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.example.config","com.example.service"})
public class UdemySpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemySpringBatchApplication.class, args);
    }

}
