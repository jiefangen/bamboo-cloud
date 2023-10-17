package org.panda.tech.cas.client.config;

import org.panda.tech.cas.client.web.CasClientLoginProcessingFilter;
import org.panda.tech.core.web.config.meta.ApiMetaProperties;
import org.panda.tech.security.config.LoginSecurityConfigurerSupport;
import org.panda.tech.security.web.authentication.ResolvableExceptionAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

/**
 * CAS客户端登录安全配置器
 */
@Component
public class CasClientLoginSecurityConfigurer
        extends LoginSecurityConfigurerSupport<CasClientLoginProcessingFilter, CasAuthenticationProvider> {

    @Autowired
    private ApiMetaProperties apiMetaProperties;
    @Autowired
    private RedirectStrategy redirectStrategy;
    @Autowired
    private ResolvableExceptionAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected CasClientLoginProcessingFilter createProcessingFilter() {
        return new CasClientLoginProcessingFilter();
    }

    @Override
    protected void configure(HttpSecurity http, CasClientLoginProcessingFilter filter) {
        filter.setApplicationEventPublisher(getApplicationContext());
        filter.setRedirectStrategy(this.redirectStrategy);
        filter.acceptSuccessHandler(handler -> {
            handler.setTargetUrlParameter(this.apiMetaProperties.getRedirectTargetUrlParameter());
        });
        filter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        http.addFilterAt(filter, CasAuthenticationFilter.class);
    }

}
