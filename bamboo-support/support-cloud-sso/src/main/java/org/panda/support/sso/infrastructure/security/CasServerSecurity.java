package org.panda.support.sso.infrastructure.security;

import org.panda.tech.cas.server.config.CasServerSecurityConfigurerSupport;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.core.webmvc.jwt.JwtGeneratorImpl;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.core.webmvc.jwt.JwtParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * CAS服务端安全配置器
 *
 * @author fangen
 **/
@Configuration
@EnableWebSecurity
public class CasServerSecurity extends CasServerSecurityConfigurerSupport {
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
