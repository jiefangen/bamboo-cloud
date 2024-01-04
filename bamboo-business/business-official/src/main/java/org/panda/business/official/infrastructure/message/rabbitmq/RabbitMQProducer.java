package org.panda.business.official.infrastructure.message.rabbitmq;

import org.panda.tech.mq.rabbitmq.producer.MessageMQProducerSupport;
import org.springframework.stereotype.Service;

/**
 * 消息队列生产者服务
 *
 * @author fangen
 **/
@Service
public class RabbitMQProducer extends MessageMQProducerSupport<Object> {

}
