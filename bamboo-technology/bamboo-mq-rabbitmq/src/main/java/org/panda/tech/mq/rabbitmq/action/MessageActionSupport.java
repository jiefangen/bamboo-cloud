package org.panda.tech.mq.rabbitmq.action;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ExceptionHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.MessageMQProperties;
import org.panda.tech.mq.rabbitmq.RabbitMQContext;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 消息操作支持
 */
public abstract class MessageActionSupport implements MessageAction, InitializingBean {

    public static final String DLX_EXCHANGE_SUFFIX = "-dlx-exchange";
    public static final String DLX_QUEUE_SUFFIX = "-dlx-queue";

    protected final RabbitMQContext rabbitMQContext = new RabbitMQContext();

    @Autowired
    private MessageMQProperties messageMQProperties;
    @Autowired(required = false)
    private ExceptionHandler exceptionHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (rabbitMQContext.getConnection() == null) {
            ConnectionFactory factory = new ConnectionFactory();
            if (StringUtils.isEmpty(messageMQProperties.getUri())) {
                factory.setUsername(messageMQProperties.getUsername());
                factory.setPassword(messageMQProperties.getPassword());
                factory.setVirtualHost(messageMQProperties.getVhost());
                factory.setHost(messageMQProperties.getHost());
                factory.setPort(messageMQProperties.getPort());
            } else { // URI方式连接
                factory.setUri(messageMQProperties.getUri());
            }
            if (exceptionHandler != null) {
                factory.setExceptionHandler(exceptionHandler);
            }
            rabbitMQContext.setConnection(factory.newConnection(messageMQProperties.getConnectionName()));
            // 初始化连接通道
        }
    }

    @Override
    public Connection getConnection() {
        return rabbitMQContext.getConnection();
    }

    @Override
    public Channel getChannel() {
        try {
            Optional<Channel> channelOptional = getConnection().openChannel();
            if (channelOptional.isPresent()) {
                return channelOptional.get();
            }
        } catch (IOException e) {
            // do nothing
        }
        return null;
    }

    /**
     * 生产者通道声明
     *
     * @param definition 通道定义
     * @param queues 队列
     * @param channelReuse 通道复用
     * @return 通道
     */
    protected Channel channelDeclare(ChannelDefinition definition, List<QueueDefinition> queues, boolean channelReuse) {
        String channelKey = buildChannelKey(definition);
        if (channelReuse) {
            if (rabbitMQContext.existChannel(channelKey)) {
                return rabbitMQContext.getChannelContainer().get(channelKey);
            }
        }
        try {
            Optional<Channel> channelOptional = getConnection().openChannel();
            if (channelOptional.isPresent()) {
                Channel channel = channelOptional.get();
                String exchangeName = definition.getExchangeName();
                String exchangeType = definition.getExchangeType();
                String routingKey = definition.getRoutingKey();
                String queueName = definition.getQueueName();
                if (StringUtils.isEmpty(queueName)) { // 单客户端消费，可以使用默认队列名称
                    channel.exchangeDeclare(exchangeName, exchangeType);
                    // 具有系统生成的名称的，非持久化、独占、自动删除的队列
                    queueName = channel.queueDeclare().getQueue();
                    channel.queueBind(queueName, exchangeName, routingKey);
                } else { // 多客户端消费建议指定名称队列
                    // 持久化、非自动删除的交换机
                    channel.exchangeDeclare(exchangeName, exchangeType, true);
                    if (CollectionUtils.isEmpty(queues)) {
                        // 拥有既定名称的，持久化、非独占、非自动删除的队列
                        channel.queueDeclare(queueName, true, false, false, definition.getHeaders());
                        channel.queueBind(queueName, exchangeName, routingKey, definition.getHeaders());
                    } else {
                        for (QueueDefinition queue : queues) {
                            channel.queueDeclare(queue.getQueueName(), true, false, false, queue.getHeaders());
                            channel.queueBind(queue.getQueueName(), exchangeName, routingKey, queue.getHeaders());
                        }
                    }
                }
                if (channelReuse) {
                    // 存入消息上下文连接通道容器
                    rabbitMQContext.put(channelKey, channel);
                }
                return channel;
            }
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
        return null;
    }

    /**
     * 生产者通道声明
     *
     * @param definition 通道定义
     * @param channelReuse 通道复用
     * @return 通道
     */
    protected Channel channelDeclare(ChannelDefinition definition, boolean channelReuse) {
        return channelDeclare(definition, null, channelReuse);
    }

    protected String buildChannelKey(ChannelDefinition definition) {
        String channelKey = definition.getExchangeName() + Strings.VERTICAL_BAR + definition.getExchangeType();
        if (StringUtils.isNotEmpty(definition.getRoutingKey())) {
            channelKey += Strings.VERTICAL_BAR + definition.getRoutingKey();
        }
        if (StringUtils.isNotEmpty(definition.getQueueName())) {
            channelKey += Strings.VERTICAL_BAR + definition.getRoutingKey();
        }
        if (StringUtils.isNotEmpty(definition.getChannelTag())) {
            channelKey += Strings.VERTICAL_BAR + definition.getChannelTag();
        }
        return channelKey;
    }

}
