package org.panda.service.auth;

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
@MapperScan("org.panda.ms.auth.repository")
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class,args);
    }
}
