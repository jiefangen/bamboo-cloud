package org.panda.support.cas.server.config;

import org.panda.support.cas.server.authentication.CasAuthenticationSuccessHandler;
import org.panda.support.cas.server.authentication.logout.CasServerLogoutHandler;
import org.panda.support.cas.server.authentication.logout.CasServerLogoutSuccessHandler;
import org.panda.tech.security.config.WebHttpSecurityConfigurer;
import org.panda.tech.security.config.WebViewSecurityConfigurerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * CAS服务端安全配置器支持
 */
public class CasServerMvcSecurityConfigurerSupport extends WebViewSecurityConfigurerSupport {

    @Autowired
    private CasServerLogoutHandler logoutHandler;

    @Bean
    public CasAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CasAuthenticationSuccessHandler();
    }

    @Bean
    public WebHttpSecurityConfigurer logoutConfigurer() {
        return http -> {
            http.logout().addLogoutHandler(CasServerMvcSecurityConfigurerSupport.this.logoutHandler);
        };
    }

    @Bean
    @Override
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CasServerLogoutSuccessHandler(this.urlProvider);
    }

}
