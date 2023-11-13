package org.panda.support.cloud.example.service.impl;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.cache.ResultHolder;
import org.panda.support.cloud.example.service.TccActionTwo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TccActionTwoImpl implements TccActionTwo {

    @Override
    public boolean prepare(BusinessActionContext actionContext, String b, List list) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),"TccActionTwo prepare, xid:" + xid + ", b:" + b + ", c:" + list.get(1));
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),
                "TccActionTwo commit, xid:" + xid + ", b:" + actionContext.getActionContext("b") + ", c:" + actionContext
                        .getActionContext("c"));
        ResultHolder.setActionTwoResult(xid, "T");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        LogUtil.info(getClass(),
                "TccActionTwo rollback, xid:" + xid + ", b:" + actionContext.getActionContext("b") + ", c:" + actionContext
                        .getActionContext("c"));
        ResultHolder.setActionTwoResult(xid, "R");
        return true;
    }
}
