package org.panda.ms.cas.server.core.cas.authentication;

import org.panda.bamboo.core.beans.ContextInitializedBean;
import org.panda.tech.security.user.UserSpecificDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持多登录方式的Cas服务端认证提供者
 */
@Component
public class LoginModeCasServerAuthenticationProvider
        extends AbstractCasServerAuthenticationProvider<AbstractAuthenticationToken> implements ContextInitializedBean {

    private Map<Class<?>, CasServerLoginAuthenticator<AbstractAuthenticationToken>> authenticators = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public void afterInitialized(ApplicationContext context) throws Exception {
        context.getBeansOfType(CasServerLoginAuthenticator.class).forEach((id, authenticator) -> {
            Class<?> tokenType = authenticator.getTokenType();
            assert tokenType != null;
            this.authenticators.put(tokenType, authenticator);
        });
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
                || this.authenticators.containsKey(authentication);
    }

    @Override
    protected UserSpecificDetails<?> authenticate(String appName, String scope, AbstractAuthenticationToken token) {
        CasServerLoginAuthenticator<AbstractAuthenticationToken> authenticator = this.authenticators
                .get(token.getClass());
        if (authenticator != null) {
            return authenticator.authenticate(appName, scope, token);
        }
        return null;
    }

}
