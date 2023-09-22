package org.panda.support.cloud.core.common;

import org.panda.support.cloud.core.loadbalancer.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端自定义负载
 *
 * @author fangen
 **/
@Configuration
@LoadBalancerClients(defaultConfiguration = LoadBalancerConfiguration.class)
public class LoadBalancerClientsConfig {
}
