package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Rex Joush
 * @time 2021.07.22 20:22
 */
@SpringBootApplication
@EnableFeignClients // 激活并开启 openfeign
public class OrderMainOpenFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainOpenFeignApplication.class);
    }

}
