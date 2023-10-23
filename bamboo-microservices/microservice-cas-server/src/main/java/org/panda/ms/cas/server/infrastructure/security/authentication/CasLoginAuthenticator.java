package org.panda.ms.cas.server.infrastructure.security.authentication;

import org.panda.ms.cas.server.core.cas.authentication.CasServerLoginAuthenticator;
import org.panda.tech.security.authentication.DefaultAuthenticationToken;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CAS登录验证器
 *
 * @author fangen
 **/
@Component
public class CasLoginAuthenticator implements CasServerLoginAuthenticator<DefaultAuthenticationToken> {

    @Autowired
    private UserSpecificDetailsService userDetailsService;

    @Override
    public UserSpecificDetails<?> authenticate(String appName, String scope, DefaultAuthenticationToken token) {
        DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) userDetailsService.loadUserByUsername("admin");
        return userSpecificDetails;
    }
}
