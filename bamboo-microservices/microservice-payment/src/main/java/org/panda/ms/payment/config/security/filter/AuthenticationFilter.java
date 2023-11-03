package org.panda.ms.payment.config.security.filter;

import org.panda.ms.payment.config.security.IndependentAuthStrategy;
import org.panda.support.cloud.core.security.AbstractAuthFilter;

/**
 * 服务认证过滤器
 *
 * @author fangen
 **/
public class AuthenticationFilter extends AbstractAuthFilter {

    @Override
    protected Class<IndependentAuthStrategy> getStrategyType() {
        return IndependentAuthStrategy.class;
    }

}
