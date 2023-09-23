package org.panda.support.cloud.core.common;

import org.panda.support.cloud.core.loadbalancer.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 默认负载均衡客户端配置
 *
 * @author fangen
 **/
@Configuration
@LoadBalancerClients(defaultConfiguration = LoadBalancerConfiguration.class)
public class LoadBalancerClientsConfig {
}
