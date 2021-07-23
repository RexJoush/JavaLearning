package com.joush;

import com.joush.config.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Rex Joush
 * @time 2021.05.24 20:21
 */
@SpringBootApplication
@EnableEurekaClient
// @LoadBalancerClients(defaultConfiguration = MySelfRule.class)
public class OrderMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class);
    }

}
