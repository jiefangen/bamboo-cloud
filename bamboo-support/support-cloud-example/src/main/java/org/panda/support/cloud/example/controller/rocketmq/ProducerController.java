package org.panda.support.cloud.example.controller.rocketmq;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.panda.bamboo.common.util.lang.UUIDUtil;
import org.panda.support.cloud.example.message.RocketMQProducer;
import org.panda.support.cloud.example.message.RocketTransactionMQProducer;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private RocketTransactionMQProducer rocketTransactionMQProducer;

    @GetMapping("/sendBridge")
    public void sendBridge(@RequestParam String message) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_KEYS, 1);
        headers.put(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID, 123);
        Message<String> msg = new GenericMessage<>(message, headers);
        rocketMQProducer.sendBridge("exampleProducer-out-0", headers, msg);
    }

    @GetMapping("/sendGeneralSync")
    public RestfulResult sendGeneralSync() {
        String topic = "customize-general-topic";
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

    @GetMapping("/sendGeneralAsync")
    public RestfulResult sendGeneralAsync() {
        String topic = "customize-pull-general-topic";
        String message = "Hello RocketMQ!";
        List<JSONObject> msgList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject msgJson = new JSONObject();
            msgJson.put("message", message + " index: " + i);
            msgList.add(msgJson);
        }
        String tags = "async-msg";
        String keys = UUIDUtil.randomUUID32();
        rocketMQProducer.sendGeneralAsync(topic, msgList, tags, keys, 0);
        return RestfulResult.success();
    }

    @GetMapping("/sendGeneralOneway")
    public RestfulResult sendGeneralOneway() {
        String topic = "example-general-topic";
        String message = "Hello RocketMQ!";
        List<JSONObject> msgList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject msgJson = new JSONObject();
            msgJson.put("message", message + " index: " + i);
            msgList.add(msgJson);
        }
        String tags = "oneway-msg";
        String keys = UUIDUtil.randomUUID32();
        rocketMQProducer.sendGeneralOneway(topic, msgList, tags, keys);
        return RestfulResult.success();
    }

    @GetMapping("/sendSeq")
    public RestfulResult sendSeq() {
        String topic = "example-seq-topic";
//        String topic = "customize-pull-seq-topic";
        String message = "Hello RocketMQ!";
        List<JSONObject> msgList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JSONObject msgJson = new JSONObject();
            msgJson.put("message", message + " index: " + i);
            msgList.add(msgJson);
        }
        String tags = "seq-msg";
        String keys = UUIDUtil.randomUUID32();
        rocketMQProducer.sendSeq(topic, msgList, tags, keys, 1000);
        return RestfulResult.success();
    }

    @GetMapping("/sendDelay")
    public RestfulResult sendDelay() {
        String topic = "example-delay-topic";
        String message = "Hello RocketMQ!";
        List<JSONObject> msgList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            JSONObject msgJson = new JSONObject();
            msgJson.put("message", message + " index: " + i);
            msgList.add(msgJson);
        }
        String tags = "delay-msg";
        String keys = UUIDUtil.randomUUID32();
        rocketMQProducer.sendDelay(topic, msgList, tags, keys, 2);
        return RestfulResult.success();
    }

    @GetMapping("/sendTransaction")
    public RestfulResult sendTransaction() {
        String topic = "example-transaction-topic";
        String message = "Hello RocketMQ!";
        JSONObject msgJson = new JSONObject();
        msgJson.put("message", message);
        String tags = "transaction-msg";
        String keys = UUIDUtil.randomUUID32();
        TransactionSendResult sendResult = rocketTransactionMQProducer.send(topic, msgJson, tags, keys, "mock");
        if (sendResult == null) {
            return RestfulResult.failure();
        }
        return RestfulResult.success(sendResult);
    }

    @GetMapping("/sendTransaction1")
    public RestfulResult sendTransaction1() {
        String topic = "example-transaction-topic";
        String message = "Hello RocketMQ!";
        JSONObject msgJson = new JSONObject();
        msgJson.put("message", message);
        String tags = "transaction-msg";
        String keys = UUIDUtil.randomUUID32();
        TransactionSendResult sendResult = rocketTransactionMQProducer.send(topic, msgJson, tags, keys, "mock",
                "transaction-producer-1");
        if (sendResult == null) {
            return RestfulResult.failure();
        }
        return RestfulResult.success(sendResult);
    }

}
