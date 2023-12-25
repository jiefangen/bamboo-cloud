package org.panda.tech.mq.rabbitmq.action;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.MessageMQProperties;
import org.panda.tech.mq.rabbitmq.RabbitMQContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

/**
 * 消息生产者操作支持
 */
public abstract class MessageActionSupport implements MessageAction, InitializingBean {

    protected final RabbitMQContext rabbitMQContext = new RabbitMQContext();

    @Autowired
    private MessageMQProperties messageMQProperties;

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
            rabbitMQContext.setConnection(factory.newConnection(messageMQProperties.getConnectionName()));
            // 初始化连接通道
        }
    }

    @Override
    public Connection getConnection() {
        return rabbitMQContext.getConnection();
    }

    protected Channel channelDeclare(String exchangeName, String exchangeType, String queueName, String routingKey) {
        String channelKey = exchangeName + Strings.VERTICAL_BAR + exchangeType + Strings.VERTICAL_BAR + queueName +
                Strings.VERTICAL_BAR + routingKey;
        if (rabbitMQContext.getChannelContext().get(channelKey) != null) {
            return rabbitMQContext.getChannelContext().get(channelKey);
        }
        try {
            Optional<Channel> channelOptional = getConnection().openChannel();
            if (channelOptional.isPresent()) {
                Channel channel = channelOptional.get();
                channel.exchangeDeclare(exchangeName, exchangeType, true);
                if (StringUtils.isEmpty(queueName)) { // 单客户端消费，可以使用默认队列名称
                    queueName = channel.queueDeclare().getQueue();
                } else { // 多客户端消费建议指定名称队列
                    channel.queueDeclare(queueName, true, false, false, null);
                }
                channel.queueBind(queueName, exchangeName, routingKey);
                // 存入消息上下文连接通道容器，实现复用
                rabbitMQContext.put(channelKey, channel);
                return channel;
            }
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
        return null;
    }

}
