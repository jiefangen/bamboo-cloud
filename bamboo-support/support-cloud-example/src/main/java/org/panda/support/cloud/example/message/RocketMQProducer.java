package org.panda.support.cloud.example.message;

import org.apache.rocketmq.client.producer.SendResult;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.rocketmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

/**
 * 消息队列生产者服务
 *
 * @author fangen
 **/
@Service
public class RocketMQProducer extends MessageMQProducerSupport<Object> {

    @Override
    protected void sendResultCallback(SendResult sendResult) {
        LogUtil.info(getClass(), "Send result callback result: {}", sendResult.getMsgId());
    }

}
