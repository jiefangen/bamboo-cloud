package org.panda.support.cloud.rocketmq.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.panda.support.cloud.rocketmq.action.MessageAction;

/**
 * MQ消息生产者
 **/
public interface MessageMQProducer<T> extends MessageAction {

    /**
     * 普通消息同步发送
     *
     * @param topic 消息主题【必】
     * @param payload 消息数据【必】
     * @param tags 消息标签【选】
     * @param keys 消息业务关键词【选】
     */
    SendResult sendGeneralSync(String topic, T payload, String tags, String keys);

    /**
     * 普通消息异步发送
     *
     * @param topic 消息主题【必】
     * @param payload 消息数据【必】
     * @param tags 消息标签【选】
     * @param keys 消息业务关键词【选】
     * @param retryTimes 失败重试次数【选】
     */
    void sendGeneralAsync(String topic, T payload, String tags, String keys, int retryTimes);

    /**
     * 普通消息单向发送
     *
     * @param topic 消息主题【必】
     * @param payload 消息数据【必】
     * @param tags 消息标签【选】
     * @param keys 消息业务关键词【选】
     */
    void sendGeneralOneway(String topic, T payload, String tags, String keys);

    /**
     * 顺序消息发送
     *
     * @param name 消息渠道名称
     * @param payload 消息数据
     */
    void sendSeq(String name, T payload);

    /**
     * 延迟消息发送
     *
     * @param name 消息渠道名称
     * @param payload 消息数据
     */
    void sendDelay(String name, T payload);

    /**
     * 批量消息发送
     *
     * @param name 消息渠道名称
     * @param payload 消息数据
     */
    default void sendBatch(String name, T payload) {
    }

    /**
     * 事务消息发送
     *
     * @param name 消息渠道名称
     * @param payload 消息数据
     */
    default void sendTransaction(String name, T payload) {
    }

}
