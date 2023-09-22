package org.panda.support.cloud.core;

import org.panda.support.cloud.core.loadbalancer.LoadBalancerConfiguration;
import org.panda.tech.core.CoreModule;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注册微服务核心模块组件自动扫描机制
 *
 * @author fangen
 **/
@ComponentScan(basePackageClasses = CoreModule.class)
@LoadBalancerClients(defaultConfiguration = LoadBalancerConfiguration.class)
public class CloudCoreModule {
}
