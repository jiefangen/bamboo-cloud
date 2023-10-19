package org.panda.ms.cas.server.infrastructure.security.business;

import org.panda.support.cas.server.authentication.CasUserSpecificDetailsAuthenticationToken;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 加载用户特性服务
 *
 * @author fangen
 **/
@Component
public class UserDetailsServiceImpl implements AuthenticationUserDetailsService<CasUserSpecificDetailsAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(CasUserSpecificDetailsAuthenticationToken token) throws UsernameNotFoundException {
        String account = token.getName();
        DefaultUserSpecificDetails userSpecificDetails = new DefaultUserSpecificDetails();
        userSpecificDetails.setUsername(account);
        return userSpecificDetails;
    }
}
