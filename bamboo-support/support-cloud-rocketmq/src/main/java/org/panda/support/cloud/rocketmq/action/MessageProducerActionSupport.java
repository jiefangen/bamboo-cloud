package org.panda.support.cloud.rocketmq.action;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.panda.support.cloud.rocketmq.MessageMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Map;

/**
 * 消息生产者操作支持
 */
public abstract class MessageProducerActionSupport implements MessageProducerAction {

    @Autowired
    private MessageMQProperties messageMQProperties;

    @Autowired
    private StreamBridge streamBridge;

    @Override
    public void sendBridge(String bindingName, Map<String, Object> headers, Object body) {
        Message<Object> msg = new GenericMessage<>(body, headers);
        streamBridge.send(bindingName, msg);
    }

    @Override
    public DefaultMQProducer buildCommonMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(messageMQProperties.getProducerGroup());
        producer.setNamesrvAddr(messageMQProperties.getNameServer());
        return producer;
    }

}
