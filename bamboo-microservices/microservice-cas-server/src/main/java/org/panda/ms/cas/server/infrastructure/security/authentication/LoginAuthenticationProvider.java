package org.panda.ms.cas.server.infrastructure.security.authentication;

import org.panda.support.sso.common.constant.SsoConstants;
import org.panda.tech.cas.server.authentication.CasUserSpecificDetailsAuthenticationToken;
import org.panda.tech.security.authentication.AbstractAuthenticationProvider;
import org.panda.tech.security.authentication.DefaultAuthenticationToken;
import org.panda.tech.security.authentication.SmsVerifyCodeAuthenticationToken;
import org.panda.tech.security.authentication.UserSpecificDetailsAuthenticationToken;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.user.UserSpecificDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 支持登录服务端认证提供者
 */
@Component
public class LoginAuthenticationProvider extends AbstractAuthenticationProvider<CasUserSpecificDetailsAuthenticationToken> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSpecificDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        DefaultUserSpecificDetails userSpecificDetails = (DefaultUserSpecificDetails) userDetailsService.loadUserByUsername(username);
        if (authentication instanceof DefaultAuthenticationToken) { // 用户名密码令牌方式
            if (!userSpecificDetails.isEnabled()) { // 账户禁用状态拦截
                throw new DisabledException(SsoConstants.USER_DISABLED);
            }
            if (!userSpecificDetails.isAccountNonLocked()) {
                throw new LockedException(SsoConstants.USER_LOCKED);
            }
            if (!passwordEncoder.matches(password, userSpecificDetails.getPassword())) {
                throw new BadCredentialsException(SsoConstants.PWD_WRONG);
            }
        }
        if (authentication instanceof SmsVerifyCodeAuthenticationToken) { // 短信令牌登录方式
        }
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = webAuthenticationDetails.getRemoteAddress();
        UserSpecificDetailsAuthenticationToken authenticationToken = new UserSpecificDetailsAuthenticationToken(userSpecificDetails);
        authenticationToken.setIp(remoteAddress);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CasUserSpecificDetailsAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
