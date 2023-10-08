package org.panda.business.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.panda.support.cloud.core.CloudCoreModule;
import org.panda.tech.security.SecurityModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application启动类
 *
 * @author fangen
 */
@EnableDiscoveryClient
@Import({CloudCoreModule.class, SecurityModule.class})
@EnableScheduling
@EnableTransactionManagement
@MapperScan("org.panda.business.admin.modules.*.service.repository")
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
