package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * 抽象消息发送返回监听器
 * 监控交换机是否将消息分发到队列
 **/
public class AbstractReturnListener implements ReturnListener {

    /**
     * 处理交换机分发消息到队列失败
     *
     * @param replyCode 回复标识
     * @param replyText 回复内容
     * @param exchangeName 交换机名
     * @param routingKey 路由键
     * @param basicProperties 消息参数
     * @param bytes 消息
     * @throws IOException 异常
     */
    @Override
    public void handleReturn(int replyCode, String replyText, String exchangeName, String routingKey,
                             AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {

    }

}
