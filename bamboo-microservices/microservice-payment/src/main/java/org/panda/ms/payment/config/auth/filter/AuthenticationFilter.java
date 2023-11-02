package org.panda.ms.payment.config.auth.filter;

import org.panda.ms.payment.config.auth.IndependentAuthStrategy;
import org.panda.support.cloud.core.security.AbstractAuthFilter;
import org.springframework.context.annotation.Configuration;

/**
 * 服务认证过滤器
 *
 * @author fangen
 **/
@Configuration
public class AuthenticationFilter extends AbstractAuthFilter {

    @Override
    protected Class<IndependentAuthStrategy> getStrategyType() {
        return IndependentAuthStrategy.class;
    }

}
