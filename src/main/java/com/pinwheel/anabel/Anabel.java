package com.pinwheel.anabel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Anabel {
    public static void main(String[] args) {
        SpringApplication.run(Anabel.class, args);
    }
}
