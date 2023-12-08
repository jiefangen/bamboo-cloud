package org.panda.business.official.modules.home;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import org.apache.rocketmq.client.producer.SendResult;
import org.panda.bamboo.common.util.lang.UUIDUtil;
import org.panda.business.official.infrastructure.message.MessageMQConstants;
import org.panda.business.official.infrastructure.message.MessageMQProducer;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.security.config.annotation.ConfigAnonymous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private MessageMQProducer messageMQProducer;

    @GetMapping("/sendGeneralSync")
    @ConfigAnonymous
    public RestfulResult sendGeneralSync() {
        JSONObject msgJson = new JSONObject();
        msgJson.put("message", "Official say: Hello RocketMQ!");
        SendResult sendResult = messageMQProducer.sendGeneralSync(MessageMQConstants.OFFICIAL_MQ_TOPIC, msgJson,
                "sync-msg", UUIDUtil.randomUUID32());
        if (sendResult == null) {
            return RestfulResult.failure();
        }
        return RestfulResult.success(sendResult);
    }
}
