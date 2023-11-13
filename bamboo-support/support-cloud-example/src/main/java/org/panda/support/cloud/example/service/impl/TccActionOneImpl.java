package org.panda.support.cloud.example.service.impl;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.cache.ResultHolder;
import org.panda.support.cloud.example.service.TccActionOne;
import org.springframework.stereotype.Service;

@Service
public class TccActionOneImpl implements TccActionOne {

    @Override
    public boolean prepare(BusinessActionContext actionContext, int a) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne prepare, xid:" + xid + ", a:" + a);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne commit, xid:" + xid + ", a:" + actionContext.getActionContext("a"));
        ResultHolder.setActionOneResult(xid, "T");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionOne rollback, xid:" + xid + ", a:" + actionContext.getActionContext("a"));
        ResultHolder.setActionOneResult(xid, "R");
        return true;
    }
}
