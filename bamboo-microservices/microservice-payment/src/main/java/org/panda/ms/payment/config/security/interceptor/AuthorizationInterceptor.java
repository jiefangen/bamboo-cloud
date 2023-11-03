package org.panda.ms.payment.config.security.interceptor;

import org.panda.ms.payment.config.security.IndependentAuthStrategy;
import org.panda.support.cloud.core.security.AbstractAuthInterceptor;

/**
 * 服务授权校验拦截器
 *
 * @author fangen
 **/
public class AuthorizationInterceptor extends AbstractAuthInterceptor {

    @Override
    protected Class<IndependentAuthStrategy> getStrategyType() {
        return IndependentAuthStrategy.class;
    }

}
