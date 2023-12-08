package org.panda.business.official.infrastructure.message;

import org.panda.tech.mq.rocketmq.consumer.MessageMQConsumerSupport;
import org.springframework.stereotype.Component;

/**
 * 消息队列消费者
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
