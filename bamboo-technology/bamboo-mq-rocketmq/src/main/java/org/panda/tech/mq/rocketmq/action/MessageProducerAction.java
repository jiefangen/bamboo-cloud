package org.panda.tech.mq.rocketmq.action;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * 消息生产者动作
 */
public interface MessageProducerAction {

    /**
     * 构建普通消息MQ生成者
     */
    DefaultMQProducer buildDefaultMQProducer();

}
