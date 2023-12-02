package org.panda.support.cloud.example.config;

import org.panda.support.cloud.rocketmq.consumer.MessageMQConsumerSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * 消费者主题订阅
 *
 * @author fangen
 **/
//@Configuration
public class MessageMQConsumerConfig {

    @Bean
    @DependsOn("messageMQConsumerSupport")
    public MessageMQConsumerSubscriber exampleGeneralConsumer() {
        return new MessageMQConsumerSubscriber("example-general-topic");
    }

}
