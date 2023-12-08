package org.panda.business.official.infrastructure.message;

import org.apache.rocketmq.client.producer.SendResult;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rocketmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

/**
 * 消息队列生产者服务
 *
 * @author fangen
 **/
@Service
public class MessageMQProducer extends MessageMQProducerSupport<Object> {

    @Override
    protected void sendResultCallback(SendResult sendResult) {
        LogUtil.info(getClass(), "Send result callback result: {}", sendResult.getMsgId());
    }

}
