package org.panda.support.cloud.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.rocketmq.action.MessageConsumerActionSupport;
import org.panda.tech.core.exception.business.BusinessException;

/**
 * MQ消费者消息抽象支持
 * 交由业务使用方配置初始化，避免消息生产方初始化不需要的该配置
 **/
public abstract class MessageMQConsumerSupport extends MessageConsumerActionSupport {

    @Override
    protected void subscribe(DefaultMQPushConsumer consumer, String topic, String tags) {
        try {
            // 订阅主题，指定tag过滤条件
            consumer.subscribe(topic, tags);
            MessageListenerConcurrently messageListener = messageListenerContainer.get(topic);
            if (messageListener == null) {
                throw new BusinessException(topic + " consumer topic listener is not configured");
            }
            // 注册回调接口来处理从Broker中收到的消息
            consumer.registerMessageListener(messageListener);
            consumer.start();
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
    }

}
