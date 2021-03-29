package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class SpringBootSecurityDemo03Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemo03Application.class, args);
    }

}
