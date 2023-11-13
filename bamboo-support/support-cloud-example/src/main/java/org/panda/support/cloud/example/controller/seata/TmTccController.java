package org.panda.support.cloud.example.controller.seata;

import cn.hutool.core.lang.Assert;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.service.TccTransactionService;
import org.panda.support.cloud.seata.action.TccActionControllerSupport;
import org.panda.tech.core.web.restful.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 事务管理器-全局事务
 *
 * @author fangen
 **/
@Api(tags = "全局事务管理案例")
@RestController
@RequestMapping(value = "/tm/tcc")
public class TmTccController extends TccActionControllerSupport {

    @Autowired
    private TccTransactionService tccTransactionService;

    /**
     * 调用TCC手工分布式事务-TM（事务管理器）
     *
     * @return String
     */
    @GetMapping("/transaction/commit")
    public RestfulResult transactionCommit() throws InterruptedException {
        // 分布式事务提交
        boolean commitBool = commit(null);
        if (commitBool) {
            return RestfulResult.success();
        }
        return RestfulResult.failure();
    }

    /**
     * 调用TCC手工分布式事务-TM（事务管理器）
     *
     * @return String
     */
    @GetMapping("/transaction/rollback")
    public RestfulResult transactionRollback() throws InterruptedException {
        // 分布式事务回滚
        boolean rollbackBool = rollback(null);
        if (rollbackBool) {
            return RestfulResult.success();
        }
        return RestfulResult.failure();
    }


    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String xid = tccTransactionService.doTransactionCommit();
        if (StringUtils.isBlank(xid)) {
            LogUtil.warn(getClass(), "Transaction opening failed");
            return false;
        }
        LogUtil.info(getClass(), "transaction commit finish. xid: {}", xid);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        try {
            tccTransactionService.doTransactionRollback(new HashMap(16));
        } catch (Throwable t) {
            LogUtil.error(getClass(), "Transaction opening failed");
            Assert.isTrue(true, "Distributed transaction rollback exception");
            return false;
        }
        String xid = actionContext.getXid();
        LogUtil.info(getClass(), "transaction rollback finish. xid: {}", xid);
        return true;
    }
}
