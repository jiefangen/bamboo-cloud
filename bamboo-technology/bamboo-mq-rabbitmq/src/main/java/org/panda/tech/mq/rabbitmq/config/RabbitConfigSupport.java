package org.panda.tech.mq.rabbitmq.config;

import org.panda.tech.mq.rabbitmq.MessageMQProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Rabbit抽象配置支持
 **/
public abstract class RabbitConfigSupport {

    @Autowired
    private MessageMQProperties messageMQProperties;

    @Bean
    public List<Queue> queues() {
        List<String> queueNames = messageMQProperties.getQueueNames();
        List<Queue> queues = new ArrayList<>();
        for (String queueName : queueNames) {
            queues.add(new Queue(queueName));
        }
        return queues;
    }

}
