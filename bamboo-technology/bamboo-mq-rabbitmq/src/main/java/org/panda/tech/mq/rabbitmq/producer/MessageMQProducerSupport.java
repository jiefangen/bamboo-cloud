package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.panda.bamboo.common.annotation.helper.EnumValueHelper;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;
import org.panda.tech.mq.rabbitmq.config.ExchangeEnum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * MQ生产消息抽象支持
 * <T> 消息类型
 **/
public abstract class MessageMQProducerSupport<T> extends MessageActionSupport implements MessageMQProducer<T> {

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
        } else {
            LogUtil.warn(getClass(), "Failed to obtain send channel");
        }
    }

    /**
     * 删除实体和清除消息
     *
     * @param exchangeName 交换机名称
     * @param exchangeType 交换机类型
     * @param queueName 队列名称
     * @param routingKey 绑定路由键
     * @param type 删除类型
     */
    protected void queueDelete(String exchangeName, String exchangeType, String queueName, String routingKey, int type) {
        if (queueName == null) { // 队列名称为空时忽略处理
            return;
        }
        Channel channel = channelDeclare(exchangeName, exchangeType, queueName, routingKey);
        if (channel != null) {
            try {
                switch (type) {
                    case 1: // 显示的将队列和交换机删除
                        channel.queueDelete(queueName);
                        break;
                    case 2: // 当队列为空时对其进行删除
                        channel.queueDelete(queueName, false, true);
                        break;
                    case 3: // 当它不再被使用的时候（没有任何消费者对其进行消费）进行删除
                        channel.queueDelete(queueName, true, false);
                        break;
                    case 4: // 清空队列里的消息，保留队列本身
                        channel.queuePurge(queueName);
                        break;
                    default: // 默认忽略不处理
                }
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    @Override
    public void sendDirect(T payload, String exchangeName, String queueName, String routingKey) {
        send(payload, exchangeName, EnumValueHelper.getValue(ExchangeEnum.DIRECT), queueName, routingKey);
    }

}
