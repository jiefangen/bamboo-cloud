package org.panda.ms.cas.server;

import org.mybatis.spring.annotation.MapperScan;
import org.panda.support.cloud.core.CloudCoreModule;
import org.panda.tech.security.SecurityModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import({CloudCoreModule.class, SecurityModule.class})
@EnableTransactionManagement
@MapperScan("org.panda.ms.cas.server.repository")
public class CasServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CasServiceApplication.class,args);
    }
}
