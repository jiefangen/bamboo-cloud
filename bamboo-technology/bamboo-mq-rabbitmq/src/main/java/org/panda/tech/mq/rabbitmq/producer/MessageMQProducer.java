package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;

import java.util.List;

/**
 * MQ消息生产者
 **/
public interface MessageMQProducer<T> {

    /**
     * 消息发送
     *
     * @param definition 通道定义
     * @param properties 消息参数
     * @param payload 消息
     */
    void send(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties, T payload);

    /**
     * 直连模式发送
     *
     * @param definition 通道定义
     * @param properties 消息参数
     * @param payload 消息
     */
    void sendDirect(ChannelDefinition definition, AMQP.BasicProperties properties, T payload);

    /**
     * 直连模式发送
     *
     * @param payload 消息
     * @param definition 通道定义
     */
    void sendDirect(ChannelDefinition definition, T payload);

    /**
     * 主题模式发送
     *
     * @param payload 消息
     * @param properties 消息参数
     * @param definition 通道定义
     */
    void sendTopic(ChannelDefinition definition, AMQP.BasicProperties properties, T payload);

    /**
     * 消息头模式发送
     *
     * @param payload 消息
     * @param properties 消息参数
     * @param definition 通道定义
     */
    void sendHeaders(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties, T payload);

    /**
     * 广播模式发送
     *
     * @param payload 消息
     * @param properties 消息参数
     * @param definition 通道定义
     */
    void sendFanout(ChannelDefinition definition, AMQP.BasicProperties properties, T payload);

}
