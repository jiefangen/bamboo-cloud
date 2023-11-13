package org.panda.support.cloud.example.service.impl;

import com.google.common.cache.Cache;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.cache.LocalGuavaCache;
import org.panda.support.cloud.example.service.TmService;
import org.panda.support.cloud.example.service.feign.RmFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * tm业务逻辑方法
 */
@Service
public class TmServiceImpl implements TmService {

    private final Cache<String, Object> tmCache = LocalGuavaCache.getLocalGuavaCache();

    @Autowired
    private RmFeignService rmFeignService;

    /**
     * 往本地插入记录
     * 再请求tcc服务插入一条记录
     *
     * @param params - name
     * @return String
     */
    @Override
    @GlobalTransactional(timeoutMills = 60000 * 2)
    @Transactional
    public String insertTcc(Map<String, String> params) {
        LogUtil.info(getClass(), "------------------> xid = " + RootContext.getXID());
        tmCache.put(RootContext.getXID(), params);
        rmFeignService.insertTCC(params);
        // throw new RuntimeException("TCC服务测试回滚");
        return "success";
    }

    /**
     * 请求本地事务插入一条记录
     * 再请求at服务插入一条记录
     *
     * @param params - name
     * @return String
     */
    @Override
    @GlobalTransactional(timeoutMills = 60000 * 2)
    @Transactional
    public String insertAt(Map<String, String> params) {
        LogUtil.info(getClass(), "------------------> xid = " + RootContext.getXID());
        tmCache.put(RootContext.getXID(), params);
        rmFeignService.insertAT(params);
        // throw new RuntimeException("AT服务测试回滚");
        return "success";
    }

}
