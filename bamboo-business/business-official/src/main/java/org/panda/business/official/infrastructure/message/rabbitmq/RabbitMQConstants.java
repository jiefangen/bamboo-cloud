package org.panda.business.official.infrastructure.message.rabbitmq;

/**
 * 消息队列规范常量
 */
public class RabbitMQConstants {
    /**
     * 直连交换机名称
     */
    public static final String EXCHANGE_NAME = "official-direct-exchange";
    /**
     * 直连队列名
     */
    public static final String QUEUE_NAME = "official-direct-queue";
    /**
     * 直连绑定路由键
     */
    public static final String ROUTING_KEY = "official-direct-key";

    /**
     * 默认生产者通道标签
     */
    public static final String PRODUCER_CHANNEL = "official-producer-channel";
}
