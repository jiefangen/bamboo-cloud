package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.annotation.helper.EnumValueHelper;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.core.util.CommonUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.ExchangeEnum;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * MQ生产消息抽象支持
 * <T> 消息类型
 **/
public abstract class MessageMQProducerSupport<T> extends MessageActionSupport implements MessageMQProducer<T> {

    @Override
    public void send(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties, T payload) {
        Channel channel = channelDeclare(definition, queues);
        if (channel != null) {
            List<Object> payloads = CommonUtil.getPayloads(payload);
            try {
                for (Object message : payloads) {
                    byte[] body = String.valueOf(message).getBytes(StandardCharsets.UTF_8);
                    if (StringUtils.isEmpty(definition.getQueueName())) { // 默认临时队列
                        channel.basicPublish(definition.getExchangeName(), definition.getRoutingKey(),
                                Objects.requireNonNullElse(properties, null), body);
                    } else {
                        channel.basicPublish(definition.getExchangeName(), definition.getRoutingKey(),
                                Objects.requireNonNullElse(properties, MessageProperties.PERSISTENT_TEXT_PLAIN), body);
                    }
                }
            } catch (IOException e) {
                LogUtil.error(getClass(), e);
            }
        } else {
            LogUtil.warn(getClass(), "Failed to obtain send channel");
        }
    }

    @Override
    public void sendDirect(ChannelDefinition definition, AMQP.BasicProperties properties, T payload) {
        definition.setExchangeType(BuiltinExchangeType.DIRECT.getType());
        send(definition, null, properties, payload);
    }

    @Override
    public void sendDirect(ChannelDefinition definition, T payload) {
        sendDirect(definition, null, payload);
    }

    @Override
    public void sendTopic(ChannelDefinition definition, AMQP.BasicProperties properties, T payload) {
        definition.setExchangeType(BuiltinExchangeType.TOPIC.getType());
        send(definition, null, properties, payload);
    }

    @Override
    public void sendHeaders(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties,
                            T payload) {
        definition.setExchangeType(EnumValueHelper.getValue(ExchangeEnum.HEADERS));
        properties.builder().headers(definition.getHeaders());
        send(definition, queues, properties, payload);
    }

    @Override
    public void sendFanout(ChannelDefinition definition, AMQP.BasicProperties properties, T payload) {
        definition.setExchangeType(EnumValueHelper.getValue(ExchangeEnum.FANOUT));
        send(definition, null, properties, payload);
    }

    /**
     * 删除实体和清除消息
     *
     * @param definition 通道定义
     * @param type 删除类型
     */
    protected void queueDelete(ChannelDefinition definition, int type) {
        if (definition == null || definition.getQueueName() == null) { // 队列名称为空时忽略处理
            return;
        }
        Channel channel = channelDeclare(definition);
        if (channel != null) {
            String queueName = definition.getQueueName();
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

}
