package org.panda.tech.mq.rabbitmq.consumer;

/**
 * 消费者动作
 **/
public interface MessageConsumerAction {

    void subscribe(String exchangeName, String exchangeType, String queueName, String routingKey, String consumerTag,
                   boolean autoAck);

    void unsubscribe(String consumerTag);

}
