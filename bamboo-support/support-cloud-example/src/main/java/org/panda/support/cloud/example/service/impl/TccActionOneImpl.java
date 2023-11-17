package org.panda.support.cloud.example.service.impl;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.cache.ResultHolder;
import org.panda.support.cloud.example.service.TccActionOne;
import org.panda.support.cloud.seata.action.message.TccMessageProducerSupport;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class TccActionOneImpl extends TccMessageProducerSupport<Object> implements TccActionOne {

    @Override
    public void prepare(BusinessActionContext actionContext, String payload) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne prepare, xid:" + xid + ", payload:" + payload);
        super.prepare(actionContext, payload);
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne commit, xid:" + xid + ", payload:" + actionContext.getActionContext("payload"));
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException  e) {
        }
        ResultHolder.setActionOneResult(xid, "T");
//        super.commit(actionContext);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne rollback, xid:" + xid + ", payload:" + actionContext.getActionContext("payload"));
        ResultHolder.setActionOneResult(xid, "R");
        return super.rollback(actionContext);
    }

    @Override
    protected MessageChannel getOutputChannel() {
        return null;
    }

}
