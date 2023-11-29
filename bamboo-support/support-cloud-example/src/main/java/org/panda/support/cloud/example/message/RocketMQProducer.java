package org.panda.support.cloud.example.message;

import com.alibaba.fastjson2.JSONObject;
import org.apache.rocketmq.client.producer.SendResult;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.bamboo.common.util.jackson.JsonUtil;
import org.panda.support.cloud.rocketmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

/**
 * 消息队列生产者服务
 *
 * @author fangen
 **/
@Service
public class RocketMQProducer extends MessageMQProducerSupport<JSONObject> {

    @Override
    protected void sendSuccessCallback(SendResult sendResult) {
        LogUtil.info(getClass(), JsonUtil.toJson(sendResult));
    }

}
