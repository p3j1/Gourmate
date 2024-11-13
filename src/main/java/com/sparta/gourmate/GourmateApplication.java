package com.sparta.gourmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GourmateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GourmateApplication.class, args);
    }

}
