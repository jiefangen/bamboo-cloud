package org.panda.support.gateway.config.common;

import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
public class WebClientConfig {

//    @Bean
//    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
