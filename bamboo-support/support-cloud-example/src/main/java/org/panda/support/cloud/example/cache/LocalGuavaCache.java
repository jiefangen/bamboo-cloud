package org.panda.support.cloud.example.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.panda.bamboo.common.util.LogUtil;

/**
 * 本地Guava缓存
 *
 * @author fangen
 **/
public class LocalGuavaCache {

    private Cache<String, Object> cache;

    public static Cache<String, Object> getLocalGuavaCache() {
        // 初始化缓存
        return CacheBuilder.newBuilder()
                .maximumSize(100)  // 最大缓存条目数
                .build();
    }

    // 添加缓存项
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    // 获取缓存项
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    // 移除缓存项
    public void remove(String key) {
        cache.invalidate(key);
    }

    // 更新缓存项
    public void update(String key, Object value) {
        if (cache.getIfPresent(key) != null) {
            cache.put(key, value);
        } else {
            // 如果缓存中不存在该项，可以选择添加或忽略
            LogUtil.warn(getClass(), "Key not found in the cache.");
        }
    }

}
