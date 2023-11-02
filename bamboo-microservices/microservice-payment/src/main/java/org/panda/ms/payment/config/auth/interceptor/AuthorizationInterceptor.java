package org.panda.ms.payment.config.auth.interceptor;

import org.panda.ms.payment.config.auth.IndependentAuthStrategy;
import org.panda.support.cloud.core.security.AbstractAuthInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * 服务授权校验拦截器
 *
 * @author fangen
 **/
@Configuration
public class AuthorizationInterceptor extends AbstractAuthInterceptor {

    @Override
    protected Class<IndependentAuthStrategy> getStrategyType() {
        return IndependentAuthStrategy.class;
    }

}
