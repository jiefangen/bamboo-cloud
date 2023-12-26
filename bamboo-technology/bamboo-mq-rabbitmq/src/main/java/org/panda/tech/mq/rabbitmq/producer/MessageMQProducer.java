package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.AMQP;

/**
 * MQ消息生产者
 **/
public interface MessageMQProducer<T> {

    /**
     * 消息发送
     */
    void send(T payload, String exchangeName, String exchangeType, String queueName, String routingKey,
              AMQP.BasicProperties properties);


    /**
     * 直连模式发送
     *
     * @param payload 消息
     * @param exchangeName 交换机名称
     * @param queueName 队列名称
     * @param routingKey 绑定路由键
     * @param properties 消息参数配置
     */
    void sendDirect(T payload, String exchangeName, String queueName, String routingKey,
                    AMQP.BasicProperties properties);

    /**
     * 直连模式发送
     *
     * @param payload 消息
     * @param exchangeName 交换机名称
     * @param queueName 队列名称
     * @param routingKey 绑定路由键
     */
    void sendDirect(T payload, String exchangeName, String queueName, String routingKey);

}
