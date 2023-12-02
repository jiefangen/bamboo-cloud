package org.panda.support.cloud.example.message;

import org.panda.support.cloud.rocketmq.producer.transaction.MessageTransactionMQProducerSupport;
import org.springframework.stereotype.Service;

/**
 * 事务消息生产者服务
 *
 * @author fangen
 **/
@Service
public class RocketTransactionMQProducer extends MessageTransactionMQProducerSupport<Object> {

}
