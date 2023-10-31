package org.panda.support.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * 通过服务名进行负载均衡调用
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webBuilder(){
        return WebClient.builder();
    }

}
