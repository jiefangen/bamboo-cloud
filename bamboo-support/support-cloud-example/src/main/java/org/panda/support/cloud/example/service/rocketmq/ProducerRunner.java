package org.panda.support.cloud.example.service.rocketmq;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用MessageChannel进行消息发送
 *
 * @author fangen
 **/
public class ProducerRunner implements CommandLineRunner {

    @Autowired
    private MessageChannel output; // 获取name为output的binding

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_TAGS, "tag-example");
        String payload = "Hello RocketMQ Message";
        Message<String> message = MessageBuilder.createMessage(payload, new MessageHeaders(headers));
        output.send(message);
    }
}
