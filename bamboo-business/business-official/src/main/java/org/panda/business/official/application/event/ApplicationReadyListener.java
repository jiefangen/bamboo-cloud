package org.panda.business.official.application.event;

import org.panda.business.official.infrastructure.message.MessageMQConstants;
import org.panda.business.official.infrastructure.message.MessageMQConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听事件，应用程序启动完成后执行初始化任务
 *
 * @author fangen
 **/
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MessageMQConsumer messageMQConsumer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 开启消费者主题订阅
        messageMQConsumer.subscribe(MessageMQConstants.OFFICIAL_MQ_TOPIC, MessageMQConstants.CONSUMER_GROUP);
    }

}
