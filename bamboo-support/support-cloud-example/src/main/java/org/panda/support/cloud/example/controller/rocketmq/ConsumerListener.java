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
    public Consumer<Object> generalConsumer() {
        return message -> {
            LogUtil.info(getClass(),"generalConsumer接到消息：{}", message);
            // do something
        };
    }

    @Bean
    public Consumer<Object> seqConsumer() {
        return message -> {
            LogUtil.info(getClass(),"seqConsumer接到消息：{}", message);
            // do something
        };
    }

    @Bean
    public Consumer<Object> delayConsumer() {
        return message -> {
            LogUtil.info(getClass(),"delayConsumer接到消息：{}", message);
            // do something
        };
    }

    @Bean
    public Consumer<Object> transactionConsumer() {
        return message -> {
            LogUtil.info(getClass(),"transactionConsumer接到消息：{}", message);
            // do something
        };
    }

}
