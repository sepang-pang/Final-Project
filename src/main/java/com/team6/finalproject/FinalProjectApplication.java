package com.team6.finalproject;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

    @PostConstruct
    public void init() {
        //timezone 설정
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
