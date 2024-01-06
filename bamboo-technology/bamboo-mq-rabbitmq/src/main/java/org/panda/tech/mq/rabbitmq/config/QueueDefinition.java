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
     * 队列路由绑定键（分隔符是'.'）
     * '*'用来表示一个单词；'#'用来表示任意数量（零个或多个）单词
     */
    private String bindKey;
    /**
     * 头信息
     */
    private Map<String, Object> headers;

    public QueueDefinition addQueueName(String queueName) {
        this.setQueueName(queueName);
        return this;
    }

    public QueueDefinition addBindKey(String bindKey) {
        this.setBindKey(bindKey);
        return this;
    }
}
