package org.panda.ms.auth.core.cas.authentication;

import org.panda.tech.security.authentication.UserSpecificDetailsAuthenticationToken;
import org.panda.tech.security.user.UserSpecificDetails;

/**
 * CAS用户特性细节鉴权令牌
 */
public class CasUserSpecificDetailsAuthenticationToken extends UserSpecificDetailsAuthenticationToken {

    private static final long serialVersionUID = 955845071338145039L;

    private String service;
    private String scope;

    public CasUserSpecificDetailsAuthenticationToken(UserSpecificDetails<?> details, String service, String scope,
                                                     String ip) {
        super(details);
        this.service = service;
        this.scope = scope;
        setIp(ip);
    }

    public String getService() {
        return this.service;
    }

    public String getScope() {
        return this.scope;
    }
}
