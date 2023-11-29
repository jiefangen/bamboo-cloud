package org.panda.support.cloud.example.controller.rocketmq;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.panda.bamboo.common.util.lang.UUIDUtil;
import org.panda.support.cloud.example.message.RocketMQProducer;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列-生产者
 *
 * @author fangen
 **/
@Api(tags = "消息队列-生产者")
@RestController
@RequestMapping(value = "/produce")
public class ProducerController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @GetMapping("/sendBridge")
    public void sendBridge(@RequestParam String message) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_KEYS, 1);
        headers.put(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID, 123);
        Message<String> msg = new GenericMessage<>(message, headers);
        rocketMQProducer.sendBridge("demoChannel-out-0", headers, msg);
    }

    @GetMapping("/sendGeneralSync")
    public RestfulResult sendGeneralSync() {
        String topic = "example-stream-topic";
        String message = "Hello RocketMQ!";
        JSONObject msgJson = new JSONObject();
        msgJson.put("message", message);
        String tags = "sync-msg";
        String keys = UUIDUtil.randomUUID32();
        SendResult sendResult = rocketMQProducer.sendGeneralSync(topic, msgJson, tags, keys);
        if (sendResult == null) {
            return RestfulResult.failure();
        }
        return RestfulResult.success(sendResult);
    }

}
