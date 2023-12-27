package org.panda.tech.mq.rabbitmq.consumer;

import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;

/**
 * 消费者动作
 **/
public interface MessageMQConsumer {

    /**
     * 消费者订阅
     * 广播模式建议自动确认
     *
     * @param definition 通道定义模型
     * @param consumerTag 消费者标签
     * @param autoAck 消费自动ack确认
     */
    void subscribe(ChannelDefinition definition, String consumerTag, boolean autoAck);

    /**
     * 消费者解除订阅
     *
     * @param consumerTag 消费者标签
     */
    void unsubscribe(String consumerTag);

}
