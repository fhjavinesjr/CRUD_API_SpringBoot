package com.CRUD_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class CRUD_APIApp {

    public static void main(String[] args) {
        SpringApplication.run(CRUD_APIApp.class, args);
    }

}
