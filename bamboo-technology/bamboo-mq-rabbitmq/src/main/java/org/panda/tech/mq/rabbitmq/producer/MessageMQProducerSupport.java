package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * MQ生产消息抽象支持
 * <T> 消息类型
 **/
public abstract class MessageMQProducerSupport<T> extends MessageActionSupport implements MessageMQProducer<T> {

    protected static final String EXCHANGE_DIRECT = "direct";

    @Override
    public void send(T payload, String exchangeName, String exchangeType, String queueName, String routingKey) {
        Channel channel = channelDeclare(exchangeName, exchangeType, queueName, routingKey);
        if (channel != null) {
            try {
                byte[] body = String.valueOf(payload).getBytes(StandardCharsets.UTF_8);
                channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, body);
            } catch (IOException e) {
                LogUtil.error(getClass(), e);
            }
        }
    }

    @Override
    public void sendDirect(T payload, String exchangeName, String queueName, String routingKey) {
        send(payload, exchangeName, EXCHANGE_DIRECT, queueName, routingKey);
    }

}
