package org.panda.support.config;

import org.panda.tech.core.CoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * 微服务配置中心
 *
 * @author fangen
 */
@Import({CoreModule.class})
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}
