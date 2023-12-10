package org.panda.business.official.infrastructure.cache;

import org.panda.tech.data.redis.template.config.RedisTemplateConfigSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisTemplateConfig extends RedisTemplateConfigSupport {
    @Override
    protected RedisSerializer<Object> getValueSerializer() {
        return super.getValueSerializer();
    }
}
