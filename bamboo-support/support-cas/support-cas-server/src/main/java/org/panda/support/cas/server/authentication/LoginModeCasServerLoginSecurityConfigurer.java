package org.panda.support.cas.server.authentication;

import org.panda.tech.security.web.authentication.LoginModeAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 支持多种登录方式的Cas服务端登录安全配置器
 */
@Component
public class LoginModeCasServerLoginSecurityConfigurer extends
        AbstractCasServerLoginSecurityConfigurer<LoginModeAuthenticationFilter, LoginModeCasServerAuthenticationProvider> {

    @Override
    protected LoginModeAuthenticationFilter createProcessingFilter() {
        return new LoginModeAuthenticationFilter(getApplicationContext());
    }

}
