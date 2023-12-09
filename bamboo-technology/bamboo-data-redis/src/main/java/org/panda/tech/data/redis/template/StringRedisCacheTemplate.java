package org.panda.tech.data.redis.template;

public class StringRedisCacheTemplate extends CacheTemplate {

    @Override
    protected String getRedisBeanName() {
        return "stringRedisTemplate";
    }
}
