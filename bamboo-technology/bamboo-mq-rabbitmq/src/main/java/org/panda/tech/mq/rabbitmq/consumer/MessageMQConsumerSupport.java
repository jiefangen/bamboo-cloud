package org.panda.tech.mq.rabbitmq.consumer;

import com.rabbitmq.client.*;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;

import java.io.IOException;

/**
 * MQ消费者消息抽象支持
 **/
public abstract class MessageMQConsumerSupport extends MessageActionSupport implements MessageMQConsumer {

    private Channel channel = null;

    @Override
    public void subscribe(String queueName, boolean autoAck, String consumerTag) {
        this.channel = getChannel();
        if (this.channel != null) {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    boolean consumeResult = consumeMessage(queueName, body);
                    if (!autoAck && consumeResult) {
                        // 手动消息确认，发送确认消息给RabbitMQ服务器
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            try {
                this.channel.basicConsume(queueName, autoAck, consumerTag, consumer);
            } catch (IOException e) {
                LogUtil.error(getClass(), e);
            }
        } else {
            LogUtil.warn(getClass(), "Failed to open consumption channel");
        }
    }

    protected abstract boolean consumeMessage(String queueName, byte[] message);

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
