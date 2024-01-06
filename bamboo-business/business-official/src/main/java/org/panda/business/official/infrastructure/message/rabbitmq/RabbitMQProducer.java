package org.panda.business.official.infrastructure.message.rabbitmq;

import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;
import org.panda.tech.mq.rabbitmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        definition.setRoutingKey(RabbitMQConstants.ROUTING_KEY);
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

}
