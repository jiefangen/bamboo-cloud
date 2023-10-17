package org.panda.tech.cas.client.userdetails;

import org.jasig.cas.client.validation.Assertion;
import org.panda.tech.cas.core.validation.SimpleAssertion;
import org.panda.tech.security.config.exception.BusinessAuthenticationException;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 简单的根据CasAssertion获取用户细节的服务
 */
@Component
public class SimpleCasAssertionUserDetailsService extends AbstractCasAssertionUserDetailsService {

    @Override
    protected UserDetails loadUserDetails(Assertion assertion) {
        if (assertion instanceof SimpleAssertion) {
            return ((SimpleAssertion) assertion).getUserDetails();
        }
        throw new BusinessAuthenticationException("error.service.security.authentication_failure");
    }

}
