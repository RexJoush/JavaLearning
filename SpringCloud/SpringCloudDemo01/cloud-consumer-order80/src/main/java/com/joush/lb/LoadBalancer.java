package com.joush.lb;

/**
 * @author Rex Joush
 * @time 2021.07.22 14:13
 */

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自实现轮询负载均衡算法
 */
public interface LoadBalancer {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
