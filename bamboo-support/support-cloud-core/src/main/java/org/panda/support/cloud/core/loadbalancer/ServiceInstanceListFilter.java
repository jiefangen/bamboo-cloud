package org.panda.support.cloud.core.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 微服务实例清单过滤器
 */
public interface ServiceInstanceListFilter {

    List<ServiceInstance> filter(List<ServiceInstance> serviceInstances);

}
