package org.panda.support.cloud.example.common;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.rocketmq.consumer.AbstractMessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class GeneralTopicMessageListener extends AbstractMessageListener {

    @Override
    public String getTopic() {
        return "customize-general-topic";
    }

    @Override
    protected ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages) {
        for (MessageExt message : messages) {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            LogUtil.info(getClass(),"general-consumer接到消息：{}", msg);
            // do something
        }
        // 返回RECONSUME_LATER表示消费失败，一段时间后再重新消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
