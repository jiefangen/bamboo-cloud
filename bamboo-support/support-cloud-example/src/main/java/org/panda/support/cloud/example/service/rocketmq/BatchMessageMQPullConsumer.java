package org.panda.support.cloud.example.service.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.rocketmq.consumer.pull.MessageMQPullConsumerSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主动拉取数据消费
 *
 * @author fangen
 **/
@Service
public class BatchMessageMQPullConsumer extends MessageMQPullConsumerSupport {

    @Override
    protected void consumeMessage(List<MessageExt> messages) {
        LogUtil.info(getClass(),"Batch pull consumer size: {} messages：{}", messages.size(), messages);
        // do somethings
    }

    @Override
    protected Integer getPullMaxSize() {
        return 7;
    }

}
