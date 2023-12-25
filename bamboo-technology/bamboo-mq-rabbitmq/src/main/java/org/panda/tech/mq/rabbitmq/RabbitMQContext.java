package org.panda.tech.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.Hashtable;
import java.util.Map;

/**
 * 消息MQ上下文
 **/
public class RabbitMQContext {

    private Connection connection = null;

    private Map<String, Channel> channelContext = new Hashtable<>();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Map<String, Channel> getChannelContext() {
        return channelContext;
    }

    public void put(String key, Channel channel) {
        if (key != null) {
            this.channelContext.put(key, channel);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T remove(String key) {
        if (key != null) {
            return (T) this.channelContext.remove(key);
        }
        return null;
    }

    public void close() {
        try {
            // 关闭该连接下的所有通道
            for (Map.Entry<String, Channel> entry : this.channelContext.entrySet()) {
                Channel channel = entry.getValue();
                channel.close();
            }
            // 关闭连接
            this.connection.close();
        } catch (Exception e) {
            // do nothing
        }
    }
}
