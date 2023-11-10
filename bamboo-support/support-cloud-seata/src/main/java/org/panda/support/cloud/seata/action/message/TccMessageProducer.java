package org.panda.support.cloud.seata.action.message;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.panda.support.cloud.seata.action.TccAction;

/**
 * TCC消息生产者
 */
public interface TccMessageProducer<T> extends TccAction {

    void prepare(BusinessActionContext actionContext, T payload);

}
