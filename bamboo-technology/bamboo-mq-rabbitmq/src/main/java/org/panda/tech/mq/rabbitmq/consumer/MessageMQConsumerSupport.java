package org.panda.tech.mq.rabbitmq.consumer;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;

import java.io.IOException;

/**
 * MQ消费者消息抽象支持
 **/
public abstract class MessageMQConsumerSupport extends MessageActionSupport implements MessageMQConsumer {

    private Channel channel = null;

    @Override
    public void subscribe(ChannelDefinition definition, String consumerTag, boolean autoAck) {
        if (StringUtils.isEmpty(definition.getChannelTag())) {
            definition.setChannelTag(consumerTag);
        }
        this.channel = channelDeclare(definition);
        try {
            this.channel.basicConsume(definition.getQueueName(), autoAck, consumerTag,
                    new DefaultConsumer(this.channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope,
                                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
                            boolean consumeResult = consumeMessage(body);
                            if (!autoAck && consumeResult) {
                                // 手动消息确认，发送确认消息给RabbitMQ服务器
                                channel.basicAck(envelope.getDeliveryTag(), false);
                            }
                        }
                    });
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
    }

    protected abstract boolean consumeMessage(Object message);

    @Override
    public void unsubscribe(String consumerTag) {
        if (this.channel != null) {
            try {
                this.channel.basicCancel(consumerTag);
            } catch (IOException e) {
                // do nothing
            }
        }
    }

}
