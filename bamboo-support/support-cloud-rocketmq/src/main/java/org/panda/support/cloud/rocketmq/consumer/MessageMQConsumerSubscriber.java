package org.panda.support.cloud.rocketmq.consumer;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消费者订阅器
 **/
public class MessageMQConsumerSubscriber {

    @Autowired
    private MessageMQConsumerSupport messageMQConsumer;

    public MessageMQConsumerSubscriber(String topic, String tags, String group, boolean isBroadcast) {
        messageMQConsumer.subscribe(topic, tags, group, isBroadcast);
    }

    public MessageMQConsumerSubscriber(String topic, String tags, boolean isBroadcast) {
        messageMQConsumer.subscribe(topic, tags, null, isBroadcast);
    }

    public MessageMQConsumerSubscriber(String topic, String tags, String group) {
        messageMQConsumer.subscribe(topic, tags, group);
    }

    public MessageMQConsumerSubscriber(String topic, String tags) {
        messageMQConsumer.subscribe(topic, tags);
    }

    public MessageMQConsumerSubscriber(String topic) {
        messageMQConsumer.subscribe(topic);
    }

}
