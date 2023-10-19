package org.panda.ms.cas.server;

import org.panda.support.cas.server.CasServerModule;
import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * CAS服务端服务
 *
 * @author fangen
 **/
@EnableDiscoveryClient
@Import({CloudCoreModule.class, CasServerModule.class})
@SpringBootApplication
public class CasServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CasServiceApplication.class, args);
    }
}
