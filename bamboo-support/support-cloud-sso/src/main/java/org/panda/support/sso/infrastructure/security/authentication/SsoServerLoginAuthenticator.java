package org.panda.support.sso.infrastructure.security.authentication;

import org.panda.tech.cas.server.authentication.CasServerLoginAuthenticator;
import org.panda.tech.security.user.SimpleUserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * SSO服务端登录认证器
 */
@Component
public class SsoServerLoginAuthenticator implements CasServerLoginAuthenticator {

    @Override
    public UserSpecificDetails<?> authenticate(String appName, String scope, AbstractAuthenticationToken token) {
        UserSpecificDetails userSpecificDetails = new SimpleUserSpecificDetails();
        return userSpecificDetails;
    }
}
