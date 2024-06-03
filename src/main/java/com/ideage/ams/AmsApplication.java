package com.ideage.ams;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ideage.ams.dao")
public class AmsApplication {

    public static void main(String[] args) {
        System.out.println("ams starting...");
        SpringApplication.run(AmsApplication.class, args);
    }
}