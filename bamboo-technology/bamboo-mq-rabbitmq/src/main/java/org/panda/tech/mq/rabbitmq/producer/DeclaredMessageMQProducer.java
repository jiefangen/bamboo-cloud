package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.core.beans.ContextInitializedBean;
import org.panda.tech.core.spec.Named;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.mq.rabbitmq.config.QueueDefinition;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 可预先声明队列和绑定关系的消息生产者
 **/
public abstract class DeclaredMessageMQProducer<T> extends MessageMQProducerSupport<T> implements Named,
        ContextInitializedBean {

    @Override
    public void afterInitialized(ApplicationContext context) throws Exception {
        Channel channel = super.channelDeclare(getChannelDefinition(), getQueueDefinition(),true);
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                // do noting
            }
        }
    }

    public void send(String exchangeName, String routingKey, AMQP.BasicProperties properties, T payload,
                     boolean channelReuse) {
        String channelKey = buildChannelKey(exchangeName, Strings.EMPTY, routingKey, Strings.EMPTY, getName());
        Channel channel;
        if (channelReuse) {
            if (rabbitMQContext.existChannel(channelKey)) {
                channel = rabbitMQContext.getChannelContainer().get(channelKey);
            } else {
                channel = super.getChannel();
                // 存入消息上下文连接通道容器
                rabbitMQContext.put(channelKey, channel);
            }
        } else { // 通道不复用，每次都需创建新的通道
            channel = super.getChannel();
        }
        super.send(channel, exchangeName, routingKey, properties, payload, channelReuse);
    }

    public void send(String exchangeName, String routingKey, T payload) {
        send(exchangeName, routingKey, null, payload, true);
    }

    protected abstract ChannelDefinition getChannelDefinition();

    protected List<QueueDefinition> getQueueDefinition() {
        return null;
    }

}
