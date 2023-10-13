package org.panda.support.sso;

import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 单点登录微服务支撑服务
 *
 * @author fangen
 **/
@EnableDiscoveryClient
@Import({CloudCoreModule.class})
@SpringBootApplication
public class SsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
