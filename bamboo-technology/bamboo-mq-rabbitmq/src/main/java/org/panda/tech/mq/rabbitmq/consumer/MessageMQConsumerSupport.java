package org.panda.tech.mq.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;

import java.io.IOException;

/**
 * MQ消费者消息抽象支持
 **/
public abstract class MessageMQConsumerSupport extends MessageActionSupport implements MessageConsumerAction {

    private Channel channel = null;

    @Override
    public void subscribe(String exchangeName, String exchangeType, String queueName, String routingKey,
                          String consumerTag, boolean autoAck) {
        channel = channelDeclare(exchangeName, exchangeType, queueName, routingKey);
        try {
            channel.basicConsume(queueName, autoAck, consumerTag,
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                                   byte[] body) throws IOException {
                            consumeMessage(body);
                            if (!autoAck) {
                                long deliveryTag = envelope.getDeliveryTag();
                                channel.basicAck(deliveryTag, false);
                            }
                        }
                    });
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
    }

    protected abstract void consumeMessage(Object message);

    @Override
    public void unsubscribe(String consumerTag) {
        if (channel != null) {
            try {
                channel.basicCancel(consumerTag);
            } catch (IOException e) {
                // do nothing
            }
        }
    }

}
