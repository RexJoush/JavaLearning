package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Rex Joush
 * @time 2021.07.30 16:15
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableHystrix  // 添加服务降级支持
public class OrderHystrixMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain80.class);
    }

}
