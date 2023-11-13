package org.panda.support.cloud.example.service.impl;

import com.google.common.cache.Cache;
import io.seata.core.context.RootContext;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.example.cache.LocalGuavaCache;
import org.panda.support.cloud.example.service.AtService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AtServiceImpl implements AtService {

    private final Cache<String, Object> atCache = LocalGuavaCache.getLocalGuavaCache();

    @Override
    public String insert(Map<String, String> params) {
        LogUtil.info(getClass(), "------------------> xid = " + RootContext.getXID());
        atCache.put(RootContext.getXID(), params);
        return "success";
    }
}
