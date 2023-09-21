package org.panda.support.discovery.example;

import org.panda.tech.core.CoreModule;
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
@Import({CoreModule.class})
@SpringBootApplication
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class,args);
    }
}
