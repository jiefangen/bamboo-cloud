package org.panda.tech.data.redis.support;

import org.panda.bamboo.common.model.entity.Unity;
import org.panda.tech.data.cache.UnityCacheRepo;

import java.io.Serializable;

/**
 * ForValue方式存储单体的Redis缓存仓库支持
 *
 * @param <T> 单体类型
 * @param <K> 单体标识类型
 */
public abstract class RedisValueUnityCacheRepoSupport<T extends Unity<K>, K extends Serializable>
        extends RedisValueCacheRepoSupport<T, K> implements UnityCacheRepo<T, K> {

    @Override
    protected final K getKeyValue(T object) {
        return object.getId();
    }

}
