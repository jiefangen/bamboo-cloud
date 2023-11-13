package org.panda.support.cloud.example.service.impl;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.panda.support.cloud.example.service.TccActionOne;
import org.panda.support.cloud.example.service.TccActionTwo;
import org.panda.support.cloud.example.service.TccTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * tm业务逻辑方法
 */
@Service
public class TccTransactionServiceImpl implements TccTransactionService {

    @Autowired
    private TccActionOne tccActionOne;
    @Autowired
    private TccActionTwo tccActionTwo;

    /**
     * 发起分布式事务
     */
    @Override
    @GlobalTransactional
    public String doTransactionCommit() {
        // 第一个TCC事务参与者
        boolean result = tccActionOne.prepare(null, 1);
        if (!result) {
            throw new RuntimeException("TccActionOne failed.");
        }
        List list = new ArrayList();
        list.add("c1");
        list.add("c2");
        // 第二个TCC事务参与者
        result = tccActionTwo.prepare(null, "two", list);
        if (!result) {
            throw new RuntimeException("TccActionTwo failed.");
        }
        // throw new RuntimeException("TCC服务测试回滚");
        return RootContext.getXID();
    }

    /**
     * Do transaction rollback string.
     *
     * @param map the map
     * @return the string
     */
    @Override
    @GlobalTransactional
    public String doTransactionRollback(Map map) {
        //第一个TCC 事务参与者
        boolean result = tccActionOne.prepare(null, 1);
        if (!result) {
            throw new RuntimeException("TccActionOne failed.");
        }
        List list = new ArrayList();
        list.add("c1");
        list.add("c2");
        // 第二个TCC事务参与者
        result = tccActionTwo.prepare(null, "two", list);
        if (!result) {
            throw new RuntimeException("TccActionTwo failed.");
        }
        map.put("xid", RootContext.getXID());
        throw new RuntimeException("transacton rollback");
    }

}
