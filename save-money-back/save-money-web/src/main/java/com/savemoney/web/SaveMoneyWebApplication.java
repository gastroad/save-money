package com.savemoney.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.savemoney")
public class SaveMoneyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveMoneyWebApplication.class, args);
    }

}
