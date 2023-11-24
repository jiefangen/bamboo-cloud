package org.panda.support.cloud.rocketmq;

/**
 * MQ消息生产者
 **/
public interface MqMessageProducer<T> {

    /**
     * 消息发送
     *
     * @param name 消息渠道名称
     * @param payload 消息数据
     */
    void send(String name, T payload);

//    /**
//     * 普通消息发送
//     *
//     * @param name 消息渠道名称
//     * @param data 消息数据
//     */
//    void send(String name, Object data);

}
