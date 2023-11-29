package org.panda.support.cloud.rocketmq.action;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.Map;

/**
 * 消息生产者动作
 */
public interface MessageProducerAction {

    /**
     * 简单消息发送
     *
     * @param name 消息渠道名称
     *             - 绑定消息主题Topic
     * @param headers 消息头
     * @param body 消息体
     */
    void sendBridge(String name, Map<String, Object> headers, Object body);

    /**
     * 构建普通消息MQ生成者
     */
    DefaultMQProducer buildCommonMQProducer();

}
