package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Rex Joush
 * @time 2021.06.06 17:22
 */
@EnableDiscoveryClient // 注册 服务中心
@SpringBootApplication
public class ConsulOrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsulOrderMain80.class);
    }

}
