package org.panda.support.cloud.rocketmq.action;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Map;

/**
 * 消息操作支持
 */
public abstract class MessageActionSupport implements MessageAction {

    @Autowired
    private StreamBridge streamBridge;

    @Override
    public DefaultMQProducer getMQProducer(String nameServer, String producerGroup) {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServer);
        return producer;
    }

    @Override
    public void sendBridge(String bindingName, Map<String, Object> headers, Object body) {
        Message<Object> msg = new GenericMessage<>(body, headers);
        streamBridge.send(bindingName, msg);
    }

}
