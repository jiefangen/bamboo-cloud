package org.panda.ms.payment.config.interceptor;

import org.panda.support.cloud.core.security.AbstractAuthInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * 认证授权拦截器
 *
 * @author fangen
 **/
@Configuration
public class AuthHandlerInterceptor extends AbstractAuthInterceptor {

    @Override
    protected Class<?> getStrategyType() {
        return IndependentAuthStrategy.class;
    }

}
