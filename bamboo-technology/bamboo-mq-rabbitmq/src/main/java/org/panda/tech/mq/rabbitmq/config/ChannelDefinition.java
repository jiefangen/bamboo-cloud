package org.panda.tech.mq.rabbitmq.config;

import lombok.Data;
import org.panda.bamboo.common.model.DomainModel;

import java.util.Map;

/**
 * 消息通道定义模型
 *
 * @author fangen
 **/
@Data
public class ChannelDefinition implements DomainModel {
    /**
     * 交换机名称
     */
    private String exchangeName;
    /**
     * 交换机类型
     * {@link ExchangeEnum}
     */
    private String exchangeType;
    /**
     * 路由键
     */
    private String routingKey;
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 头信息
     */
    private Map<String, Object> headers;

    /**
     * 自定义通道标签
     * 用于区分缓存不同通道
     */
    private String channelTag;
}
