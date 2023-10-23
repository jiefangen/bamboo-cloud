package org.panda.ms.cas.server.core.cas.authentication;

import org.panda.tech.security.config.LoginSecurityConfigurerSupport;
import org.panda.tech.security.web.authentication.LoginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象的CAS服务端登录安全配置器
 *
 * @param <AP> 认证提供器实现类型
 */
public abstract class AbstractCasServerLoginSecurityConfigurer<PF extends AbstractAuthenticationProcessingFilter, AP extends AuthenticationProvider>
        extends LoginSecurityConfigurerSupport<PF, AP> {

    @Autowired
    private CasAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http, PF filter) {
        if (filter instanceof LoginAuthenticationFilter) {
            LoginAuthenticationFilter loginFilter = (LoginAuthenticationFilter) filter;
            loginFilter.setAuthenticationDetailsSource(authenticationDetailsSource());
            loginFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler); // 指定登录成功时的处理器
        }
        super.configure(http, filter);
    }

    protected AuthenticationDetailsSource<HttpServletRequest, CasServiceAuthenticationDetails> authenticationDetailsSource() {
        return new CasServiceAuthenticationDetailsSource();
    }

}
