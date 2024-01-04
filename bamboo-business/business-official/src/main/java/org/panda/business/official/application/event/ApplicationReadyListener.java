package org.panda.business.official.application.event;

import org.panda.business.official.infrastructure.message.rabbitmq.RabbitMQConstants;
import org.panda.business.official.infrastructure.message.rabbitmq.RabbitMQConsumer;
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

//    @Autowired
//    private RocketMQConsumer rocketMQConsumer;
    @Autowired
    private RabbitMQConsumer rabbitMQConsumer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 开启消费者主题订阅
//        rocketMQConsumer.subscribe(RocketMQConstants.OFFICIAL_MQ_TOPIC, RocketMQConstants.CONSUMER_GROUP);

        rabbitMQConsumer.subscribe(RabbitMQConstants.QUEUE_NAME, false, "official-consumer");
    }

}
