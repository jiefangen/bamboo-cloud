package org.panda.support.cloud.example.config;

import org.panda.support.cloud.rocketmq.consumer.MessageMQConsumerSupport;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author fangen
 **/
@Component
public class MessageMQConsumer extends MessageMQConsumerSupport {

    @Override
    protected int getMaxReconsumeTimes() {
        return super.getMaxReconsumeTimes();
    }

    @Override
    protected long getRetryInterval() {
        return super.getRetryInterval();
    }

}
