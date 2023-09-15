package org.panda.support.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 微服务配置中心
 *
 * @author fangen
 */
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(ConfigServerApplication.class,args);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigServerApplication.class, args);
        while(true) {
            // 当动态配置刷新时，会更新到 Enviroment中，因此这里每隔一秒中从Enviroment中获取配置
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            //获取当前部署的环境
            String currentEnv = applicationContext.getEnvironment().getProperty("current.env");
            System.err.println("in "+currentEnv+" enviroment; "+"user name :" + userName + "; age: " + userAge);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
