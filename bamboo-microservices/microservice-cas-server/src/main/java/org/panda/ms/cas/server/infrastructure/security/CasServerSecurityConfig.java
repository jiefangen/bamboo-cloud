package org.panda.ms.cas.server.infrastructure.security;

import org.panda.support.cas.server.config.CasServerMvcSecurityConfigurerSupport;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.core.webmvc.jwt.JwtGeneratorImpl;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.core.webmvc.jwt.JwtParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CAS服务端安全配置器
 *
 * @author fangen
 **/
@Configuration
public class CasServerSecurityConfig extends CasServerMvcSecurityConfigurerSupport {
    /**
     * 声明JWT生成器
     */
    @Bean
    @ConditionalOnMissingBean(JwtGenerator.class)
    public JwtGenerator jwtGenerator() {
        return new JwtGeneratorImpl();
    }

    /**
     * 声明JWT解析器
     */
    @Bean
    @ConditionalOnMissingBean(JwtParser.class)
    public JwtParser jwtParser() {
        return new JwtParserImpl();
    }
}
