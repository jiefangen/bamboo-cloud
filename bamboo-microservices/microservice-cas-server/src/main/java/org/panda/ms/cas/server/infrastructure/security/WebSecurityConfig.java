package org.panda.ms.cas.server.infrastructure.security;

import org.panda.support.cas.core.security.WebMvcSecurityConfigurerSupport;
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
@Order(106) // 必须指定该配置顺序，否则会与安全框架中的配置冲突
public class WebSecurityConfig extends WebMvcSecurityConfigurerSupport {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
