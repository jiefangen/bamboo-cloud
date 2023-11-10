package org.panda.support.cloud.example;

import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 服务注册与发现
 *
 * @author fangen
 **/
@EnableFeignClients
@EnableDiscoveryClient
@Import({CloudCoreModule.class})
@SpringBootApplication
public class ExampleCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleCloudApplication.class, args);
    }
}
