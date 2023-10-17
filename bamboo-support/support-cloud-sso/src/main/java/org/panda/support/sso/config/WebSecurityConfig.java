package org.panda.support.sso.config;

import org.panda.tech.cas.core.security.WebMvcSecurityConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web安全配置器
 *
 * @author fangen
 **/
@Configuration
public class WebSecurityConfig extends WebMvcSecurityConfigurerSupport {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
