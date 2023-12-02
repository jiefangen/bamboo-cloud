package org.panda.support.cloud.example.config.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.rocketmq.producer.transaction.AbstractTransactionListener;
import org.springframework.stereotype.Component;

/**
 * 事务消息二次确认监听
 *
 * @author fangen
 **/
@Component
public class DefaultTransactionListener extends AbstractTransactionListener {

    @Override
    protected LocalTransactionState executeTransaction(Message message, Object arg) {
        LogUtil.info(getClass(), "Execute local transaction, transactionId: {}, arg: {}",
                message.getTransactionId(), arg);
        // do something

        return LocalTransactionState.UNKNOW;
    }

}
