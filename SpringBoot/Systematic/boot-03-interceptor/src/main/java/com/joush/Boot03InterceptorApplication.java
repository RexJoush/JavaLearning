package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.joush.servlet")
@SpringBootApplication
public class Boot03InterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot03InterceptorApplication.class, args);
    }

}
