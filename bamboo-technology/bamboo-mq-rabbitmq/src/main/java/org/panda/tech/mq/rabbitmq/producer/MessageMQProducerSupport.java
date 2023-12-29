package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.annotation.helper.EnumValueHelper;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.core.util.CommonUtil;
import org.panda.tech.mq.rabbitmq.action.MessageActionSupport;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.ExchangeEnum;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MQ生产消息抽象支持
 * <T> 消息类型
 **/
public abstract class MessageMQProducerSupport<T> extends MessageActionSupport implements MessageMQProducer<T> {

    @Autowired(required = false)
    private ConfirmListener confirmListener;
    @Autowired(required = false)
    private ReturnListener returnListener;

    @Override
    public void send(Channel channel, String exchangeName, String routingKey, AMQP.BasicProperties properties, T payload,
                     boolean channelReuse) {
        if (channel != null) {
            List<Object> payloads = CommonUtil.getPayloads(payload);
            boolean isConfirm = confirmListener != null; // 是否异步消息确认
            boolean isReturn = returnListener != null; // 是否处理分发失败消息返还
            try {
                if (isConfirm) {
                    channel.confirmSelect();
                }
                if (isReturn) {
                    channel.addReturnListener(returnListener);
                }
                for (Object message : payloads) {
                    byte[] body = String.valueOf(message).getBytes(StandardCharsets.UTF_8);
                    if (isReturn) { // 监控交换机是否将消息分发到队列，未分发返还给生产者
                        channel.basicPublish(exchangeName, routingKey, true, properties, body);
                    } else {
                        channel.basicPublish(exchangeName, routingKey, properties, body);
                    }
                }
                if (isConfirm) {
                    channel.addConfirmListener(confirmListener);
                }
            } catch (IOException e) {
                LogUtil.error(getClass(), e);
            } finally {
                if (!channelReuse) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        // do noting
                    }
                }
            }
        }
    }

    @Override
    public void sendDirect(ChannelDefinition definition, String routingKey, AMQP.BasicProperties properties, T payload,
                           boolean channelReuse) {
        definition.setExchangeType(BuiltinExchangeType.DIRECT.getType());
        if (properties == null && StringUtils.isNotEmpty(definition.getQueueName())) {
            properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
        }
        Channel channel = channelDeclare(definition, channelReuse);
        send(channel, definition.getExchangeName(), routingKey, properties, payload, channelReuse);
    }

    @Override
    public void sendDirect(ChannelDefinition definition, String routingKey, T payload) {
        sendDirect(definition, routingKey,null, payload, true);
    }

    @Override
    public void sendDelayed(String delayKey, long expiration, String routingKey, T payload) {
        ChannelDefinition definition = new ChannelDefinition();
        definition.setExchangeName(delayKey + "-ttl-exchange");
        definition.setQueueName(delayKey + "-ttl-queue");
        definition.setRoutingKey(routingKey);
        definition.setChannelTag("ttl-delayed");
        Map<String, Object> headers = new HashMap<>(3);
        headers.put("x-dead-letter-exchange", delayKey + DLX_EXCHANGE_SUFFIX); // 绑定死信交换机
        headers.put("x-dead-letter-routing-key", delayKey + DLX_QUEUE_SUFFIX); // 绑定当前队列的死信路由key
        headers.put("x-message-ttl", expiration); // 声明队列的TTL
        definition.setHeaders(headers);
        // 延时通道
        Channel channel = channelDeclare(definition, true);
        Map<String, Object> propertiesHeaders = new HashMap<>();
        propertiesHeaders.put("x-delay", expiration);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .headers(propertiesHeaders)
                .expiration(String.valueOf(expiration))
                .build();

        // 声明死信交换机和队列
        ChannelDefinition dlxDefinition = new ChannelDefinition();
        String channelKey = buildChannelKey(dlxDefinition);
        if (!rabbitMQContext.existChannel(channelKey)) {
            dlxDefinition.setChannelTag("dlx-delayed");
            dlxDefinition.setExchangeName(delayKey + DLX_EXCHANGE_SUFFIX);
            dlxDefinition.setQueueName(delayKey + DLX_QUEUE_SUFFIX);
            dlxDefinition.setRoutingKey(delayKey + DLX_QUEUE_SUFFIX);
            channelDeclare(dlxDefinition, true);
        }
        send(channel, definition.getExchangeName(), routingKey, properties, payload, true);
    }

    @Override
    public void sendTopic(ChannelDefinition definition, List<QueueDefinition> queues, String routingKey,
                          AMQP.BasicProperties properties,  T payload) {
        definition.setExchangeType(BuiltinExchangeType.TOPIC.getType());
        boolean channelReuse = true; // 默认使用通道复用，以提供良好的性能
        Channel channel = channelDeclare(definition, queues, channelReuse);
        send(channel, definition.getExchangeName(), routingKey, properties, payload, channelReuse);
    }

    @Override
    public void sendHeaders(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties,
                            T payload) {
        definition.setExchangeType(EnumValueHelper.getValue(ExchangeEnum.HEADERS));
        boolean channelReuse = true;
        Channel channel = channelDeclare(definition, queues, channelReuse);
        if (properties == null) {
            properties = new AMQP.BasicProperties.Builder().headers(definition.getHeaders()).build();
        } else {
            properties.builder().headers(definition.getHeaders());
        }
        send(channel, definition.getExchangeName(), Strings.EMPTY, properties, payload, channelReuse);
    }

    @Override
    public void sendFanout(ChannelDefinition definition, List<QueueDefinition> queues, AMQP.BasicProperties properties,
                           T payload) {
        definition.setExchangeType(EnumValueHelper.getValue(ExchangeEnum.FANOUT));
        boolean channelReuse = true;
        Channel channel = channelDeclare(definition, queues, channelReuse);
        send(channel, definition.getExchangeName(), Strings.EMPTY, properties, payload, channelReuse);
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
        Channel channel = channelDeclare(definition, true);
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
