package org.panda.support.cloud.example.controller.rocketmq;

import io.swagger.annotations.Api;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
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
    private StreamBridge streamBridge;

    @GetMapping("/demo")
    public void demoProducer(@RequestParam String message) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MessageConst.PROPERTY_KEYS, 1);
        headers.put(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID, 123);
        Message<String> msg = new GenericMessage<>(message, headers);
        streamBridge.send("demoChannel-out-0", msg);
    }

}
