package org.panda.business.official.infrastructure.message.rabbitmq;

import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;
import org.panda.tech.mq.rabbitmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息队列生产者服务
 *
 * @author fangen
 **/
@Service
public class RabbitMQProducer extends MessageMQProducerSupport<Object> {

    public void sendDirect(String routingKey, Object payload) {
        super.sendDirect(getDirectChannelDefinition(), routingKey, payload);
    }

    private ChannelDefinition getDirectChannelDefinition() {
        ChannelDefinition definition = new ChannelDefinition();
        definition.setExchangeName(RabbitMQConstants.EXCHANGE_NAME);
        definition.setQueueName(RabbitMQConstants.QUEUE_NAME);
        definition.setBindKey(RabbitMQConstants.ROUTING_KEY);
        definition.setChannelTag(RabbitMQConstants.PRODUCER_CHANNEL);
        return definition;
    }

    public void sendTopic(String routingKey, Object payload) {
        List<QueueDefinition> queues = new ArrayList<>();
        queues.add(new QueueDefinition().addQueueName("topic-queue-one").addBindKey("*.orange.*"));
        queues.add(new QueueDefinition().addQueueName("topic-queue-two").addBindKey("*.*.rabbit"));
        queues.add(new QueueDefinition().addQueueName("topic-queue-three").addBindKey("lazy.#"));
        super.sendTopic(getTopicChannelDefinition(), queues, routingKey, null, payload);
    }

    private ChannelDefinition getTopicChannelDefinition() {
        ChannelDefinition definition = new ChannelDefinition();
        definition.setExchangeName("topic-exchange");
        definition.setChannelTag(RabbitMQConstants.PRODUCER_CHANNEL);
        return definition;
    }

    public void sendHeaders(Map<String, Object> sendHeaders, Object payload) {
        List<QueueDefinition> queues = new ArrayList<>();
        Map<String, Object> headers = new HashMap<>();
        headers.put("format", "pdf");
        headers.put("type", "report");
        headers.put("x-match", "all"); // 头交换机必须匹配类型
        queues.add(new QueueDefinition().addQueueName("headers-queue-B").addHeaders(headers));

        Map<String, Object> headersC = new HashMap<>();
        headersC.put("format", "zip");
        headersC.put("type", "report");
        headersC.put("x-match", "all");
        queues.add(new QueueDefinition().addQueueName("headers-queue-C").addHeaders(headersC));
        super.sendHeaders(getHeadersChannelDefinition(), sendHeaders, queues, null, payload);
    }

    private ChannelDefinition getHeadersChannelDefinition() {
        ChannelDefinition definition = new ChannelDefinition();
        definition.setExchangeName("headers-exchange");
        definition.setQueueName("headers-queue-A");
        Map<String, Object> headers = new HashMap<>();
        headers.put("format", "pdf");
        headers.put("type", "log");
        headers.put("x-match", "any"); // 头交换机必须匹配类型
        definition.setHeaders(headers);
        definition.setChannelTag(RabbitMQConstants.PRODUCER_CHANNEL);
        return definition;
    }

}
