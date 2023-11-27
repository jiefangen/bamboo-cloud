package org.panda.support.cloud.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.bamboo.common.util.jackson.JsonUtil;
import org.panda.support.cloud.rocketmq.action.MessageActionSupport;

/**
 * MQ生产消息抽象支持
 **/
public abstract class MessageMQProducerSupport<T> extends MessageActionSupport implements MessageMQProducer<T> {

    @Override
    public SendResult sendGeneralSync(String topic, T payload, String tags, String keys) {
        DefaultMQProducer producer = super.getMQProducer("", "");
        try {
            producer.start();

            String payloadStr = "";
            if (payload instanceof String) {
                payloadStr = String.valueOf(payload);
            } else {
                payloadStr = JsonUtil.toJson(payload);
            }
            byte[] body = payloadStr.getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message msg = new Message(topic, body);
            SendResult sendResult = producer.send(msg);

            return sendResult;
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        } finally {
            producer.shutdown();
        }
        return null;
    }

    @Override
    public void sendSeq(String name, T payload) {

    }

    @Override
    public void sendDelay(String name, T payload) {

    }

}
