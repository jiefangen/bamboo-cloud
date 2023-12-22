package org.panda.tech.mq.rabbitmq.producer;

/**
 * MQ消息生产者
 **/
public interface MessageMQProducer<T> {

    /**
     * 消息发送
     */
    void send(T payload, String exchangeName, String exchangeType, String queueName, String routingKey);

    /**
     * 发送直连交换机
     *
     * @param exchangeName 交换机名称
     * @param queueName 队列名称
     * @param routingKey 绑定路由键
     */
    void sendDirect(T payload, String exchangeName, String queueName, String routingKey);

}
