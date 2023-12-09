package org.panda.tech.data.redis.template;

public class RedisCacheTemplate extends CacheTemplate {

    @Override
    protected String getRedisBeanName() {
        return "redisTemplate";
    }
}
