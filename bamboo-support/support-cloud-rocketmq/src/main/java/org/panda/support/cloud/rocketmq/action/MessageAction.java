package org.panda.support.cloud.rocketmq.action;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.Map;

/**
 * 消息动作
 */
public interface MessageAction {
    /**
     * 构建MQ生成者
     *
     * @param nameServer 生产者地址
     * @param producerGroup 生产者分组
     */
    DefaultMQProducer getMQProducer(String nameServer, String producerGroup);

    /**
     * Bridge消息发送
     *
     * @param name 消息渠道名称
     *             - 绑定消息主题Topic
     * @param headers 消息头
     * @param body 消息体
     */
    void sendBridge(String name, Map<String, Object> headers, Object body);

}
