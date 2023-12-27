package org.panda.tech.mq.rabbitmq.config;

import lombok.Data;

import java.util.Map;

/**
 * 队列定义
 *
 * @author fangen
 **/
@Data
public class QueueDefinition {
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 头信息
     */
    private Map<String, Object> headers;
}
