package org.panda.ms.auth.infrastructure.security.authorization;

import org.panda.ms.auth.infrastructure.security.authentication.AuthServerLoginAuthenticator;
import org.panda.tech.security.authentication.DefaultAuthenticationToken;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Auth登录验证器
 *
 * @author fangen
 **/
@Component
public class AuthLoginAuthenticator implements AuthServerLoginAuthenticator<DefaultAuthenticationToken> {

    @Autowired
    private UserSpecificDetailsService userDetailsService;

    @Override
    public UserSpecificDetails<?> authenticate(String appName, String scope, DefaultAuthenticationToken token) {
        // TODO 认证参数校验
        DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) userDetailsService.loadUserByUsername("admin");
        return userSpecificDetails;
    }
}
