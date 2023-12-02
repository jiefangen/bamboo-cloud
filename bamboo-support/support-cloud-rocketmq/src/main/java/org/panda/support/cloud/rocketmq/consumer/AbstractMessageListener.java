package org.panda.support.cloud.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 抽象消费者消息监听器
 **/
public abstract class AbstractMessageListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeContext) {
        return consumeMessage(list);
    }

    public abstract String getTopic();

    protected abstract ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages);

}
