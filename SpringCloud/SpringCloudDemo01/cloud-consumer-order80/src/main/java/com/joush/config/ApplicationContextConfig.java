package com.joush.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Rex Joush
 * @time 2021.05.24 20:29
 */

@Configuration
public class ApplicationContextConfig {

    @Bean
<<<<<<< Updated upstream
    // @LoadBalanced
=======
    @LoadBalanced
>>>>>>> Stashed changes
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
