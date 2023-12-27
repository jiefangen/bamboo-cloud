package org.panda.tech.mq.rabbitmq.producer;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * 抽象消息发送确认监听器
 **/
public class AbstractConfirmListener implements ConfirmListener {

    /**
     * 消息投递正常
     */
    @Override
    public void handleAck(long seq, boolean multiple) throws IOException {
    }

    /**
     * 消息投递异常
     */
    @Override
    public void handleNack(long seq, boolean multiple) throws IOException {
    }

}
