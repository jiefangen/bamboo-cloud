package org.panda.tech.mq.rabbitmq.action;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.mq.rabbitmq.MessageMQProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 消息生产者操作支持
 */
public abstract class MessageActionSupport implements MessageAction, InitializingBean {

    // 消息客户端连接
    private Connection connection = null;
    // 消息通道容器
    protected final Map<String, Channel> channelContainer = new HashMap<>();

    @Autowired
    private MessageMQProperties messageMQProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.connection == null) {
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
            this.connection = factory.newConnection(messageMQProperties.getConnectionName());
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    protected void close() {
        try {
            // 关闭该连接下的所有通道
            for (Map.Entry<String, Channel> entry : this.channelContainer.entrySet()) {
                Channel channel = entry.getValue();
                channel.close();
            }
            // 关闭连接
            this.connection.close();
        } catch (Exception e) {
            // do nothing
        }
    }

    protected Channel channelDeclare(String exchangeName, String exchangeType, String queueName, String routingKey) {
        StringBuffer channelKeyBuffer = new StringBuffer(exchangeName).append(Strings.VERTICAL_BAR)
                .append(exchangeType).append(Strings.VERTICAL_BAR).append(queueName)
                .append(Strings.VERTICAL_BAR).append(routingKey);
        String channelKey = channelKeyBuffer.toString();
        if (this.channelContainer.get(channelKey) != null) {
            return this.channelContainer.get(channelKey);
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
                // 加入到连接通道容器，实现复用
                this.channelContainer.put(channelKey, channel);
                return channel;
            }
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
        return null;
    }

}
