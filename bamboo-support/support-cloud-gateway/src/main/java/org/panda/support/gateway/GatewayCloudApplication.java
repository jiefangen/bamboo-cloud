package org.panda.support.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 微服务网关
 *
 * @author fangen
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayCloudApplication.class, args);
    }
}
