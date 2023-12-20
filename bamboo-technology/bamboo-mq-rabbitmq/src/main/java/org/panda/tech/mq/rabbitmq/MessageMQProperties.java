package org.panda.tech.mq.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("bamboo.message.rabbitmq")
public class MessageMQProperties {
    /**
     * 队列名称集
     */
    private List<String> queueNames;

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }
}
