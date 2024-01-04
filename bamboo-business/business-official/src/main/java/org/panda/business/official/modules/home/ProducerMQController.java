package org.panda.business.official.modules.home;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import org.apache.rocketmq.client.producer.SendResult;
import org.panda.bamboo.common.util.lang.UUIDUtil;
import org.panda.business.official.infrastructure.message.rabbitmq.RabbitMQConstants;
import org.panda.business.official.infrastructure.message.rabbitmq.RabbitMQProducer;
import org.panda.business.official.infrastructure.message.rocketmq.RocketMQConstants;
import org.panda.business.official.infrastructure.message.rocketmq.RocketMQProducer;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.mq.rabbitmq.config.ChannelDefinition;
import org.panda.tech.security.config.annotation.ConfigAnonymous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息队列消息生产器
 *
 * @author fangen
 **/
@Api(tags = "消息队列消息生产器")
@RestController
@RequestMapping(value = "/produce")
public class ProducerMQController {

    @Autowired
    private RocketMQProducer rocketMQProducer;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @GetMapping("/sendGeneralSync")
    @ConfigAnonymous
    public RestfulResult<?> sendGeneralSync() {
        JSONObject msgJson = new JSONObject();
        msgJson.put("message", "Official say: Hello RocketMQ!");
        SendResult sendResult = rocketMQProducer.sendGeneralSync(RocketMQConstants.OFFICIAL_MQ_TOPIC, msgJson,
                "sync-msg", UUIDUtil.randomUUID32());
        if (sendResult == null) {
            return RestfulResult.failure();
        }
        return RestfulResult.success(sendResult);
    }

    @GetMapping("/sendDirect")
    @ConfigAnonymous
    public RestfulResult<?> sendDirect() {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String message = "Official say: Hello RabbitMQ! Seq: ";
            messages.add(message + i);
        }
        ChannelDefinition definition = new ChannelDefinition();
        String routingKey = "direct-key";
        definition.setRoutingKey(routingKey);
        definition.setExchangeName(RabbitMQConstants.EXCHANGE_NAME);
        definition.setQueueName(RabbitMQConstants.QUEUE_NAME);
        definition.setChannelTag(RabbitMQConstants.PRODUCER_CHANNEL);
        rabbitMQProducer.sendDirect(definition, routingKey, messages);
        return RestfulResult.success();
    }

}
