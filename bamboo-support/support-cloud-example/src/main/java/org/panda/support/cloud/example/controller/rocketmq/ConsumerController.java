package org.panda.support.cloud.example.controller.rocketmq;

import io.swagger.annotations.Api;
import org.panda.support.cloud.example.service.rocketmq.BatchMessageMQPullConsumer;
import org.panda.support.cloud.example.service.rocketmq.MessageMQPullConsumer;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消费者主动拉取数据消费
 *
 * @author fangen
 **/
@Api(tags = "消费者主动拉取数据消费")
@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

    @Autowired
    private MessageMQPullConsumer messageMQPullConsumer;

    @Autowired
    private BatchMessageMQPullConsumer batchMessageMQPullConsumer;

    @GetMapping("/pull")
    public RestfulResult pull() {
        List<Object> result = messageMQPullConsumer.pull("customize-pull-general-topic");
        return RestfulResult.success(result);
    }

    @GetMapping("/pullSeq")
    public RestfulResult pullBatch() {
        List<Object> result = batchMessageMQPullConsumer.pull("customize-pull-general-topic");
        return RestfulResult.success(result);
    }

}
