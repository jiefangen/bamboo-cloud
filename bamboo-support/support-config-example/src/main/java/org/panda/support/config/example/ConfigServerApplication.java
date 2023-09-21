package org.panda.support.config.example;

import org.panda.support.cloud.core.CloudCoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 微服务配置中心
 *
 * @author fangen
 */
@Import({CloudCoreModule.class})
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
