package org.panda.business.official.infrastructure.message.rabbitmq;

/**
 * 消息队列规范常量
 */
public class RabbitMQConstants {
    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "direct-exchange";
    /**
     * 交换机名称
     */
    public static final String QUEUE_NAME = "direct-queue";

    /**
     * 默认生产者通道标签
     */
    public static final String PRODUCER_CHANNEL = "producer-channel";
}
