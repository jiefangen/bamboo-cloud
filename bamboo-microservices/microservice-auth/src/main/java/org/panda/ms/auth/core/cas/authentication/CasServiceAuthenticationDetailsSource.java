package org.panda.ms.auth.core.cas.authentication;

import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.cas.CasConstants;
import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * CasServiceAuthenticationDetails源
 */
public class CasServiceAuthenticationDetailsSource implements
        AuthenticationDetailsSource<HttpServletRequest, CasServiceAuthenticationDetails> {

    @Override
    public CasServiceAuthenticationDetails buildDetails(HttpServletRequest request) {
        String service = WebHttpUtil.getParameterOrAttribute(request, CasConstants.PARAMETER_SERVICE);
        String scope = WebHttpUtil.getParameterOrAttribute(request, CasConstants.PARAMETER_SCOPE);
        String ip = WebHttpUtil.getRemoteAddress(request);
        return new CasServiceAuthenticationDetails(service, scope, ip);
    }

}
