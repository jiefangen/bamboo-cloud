package org.panda.support.discovery.example;

import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 服务注册与发现
 *
 * @author fangen
 **/
@EnableDiscoveryClient
@Import({CloudCoreModule.class})
@SpringBootApplication
public class DiscoveryExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryExampleApplication.class, args);
    }
}
