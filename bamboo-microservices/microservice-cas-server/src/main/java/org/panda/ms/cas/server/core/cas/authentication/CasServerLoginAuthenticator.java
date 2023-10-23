package org.panda.ms.cas.server.core.cas.authentication;

import org.panda.bamboo.common.util.clazz.ClassUtil;
import org.panda.tech.security.user.UserSpecificDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Cas服务端登录认证器
 *
 * @param <T> 令牌类型
 */
public interface CasServerLoginAuthenticator<T extends AbstractAuthenticationToken> {

    default Class<T> getTokenType() {
        return ClassUtil.getActualGenericType(getClass(), CasServerLoginAuthenticator.class, 0);
    }

    UserSpecificDetails<?> authenticate(String appName, String scope, T token);

}
