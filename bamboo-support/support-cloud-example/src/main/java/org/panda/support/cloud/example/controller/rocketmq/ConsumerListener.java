package org.panda.support.cloud.example.controller.rocketmq;

import org.panda.bamboo.common.util.LogUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * 消息队列-消费监听
 *
 * @author fangen
 **/
@Configuration
public class ConsumerListener {

    @Bean
    public Consumer<String> demoConsumer() {
        return message -> {
            LogUtil.info(getClass(),"demoConsumer接到消息：{}", message);
            // do something

        };
    }

}
