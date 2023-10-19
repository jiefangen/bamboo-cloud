package org.panda.support.sso.infrastructure.security;

import org.panda.tech.cas.core.security.WebMvcSecurityConfigurerSupport;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.core.webmvc.jwt.JwtGeneratorImpl;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.core.webmvc.jwt.JwtParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web安全配置器
 *
 * @author fangen
 **/
@Configuration
@Order(101) // 必须指定该配置顺序，否则会与安全框架中的配置冲突
public class WebSecurityConfig extends WebMvcSecurityConfigurerSupport {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
