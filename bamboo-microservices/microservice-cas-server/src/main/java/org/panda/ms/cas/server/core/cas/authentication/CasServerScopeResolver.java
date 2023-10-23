package org.panda.ms.cas.server.core.cas.authentication;

import org.panda.tech.security.user.UserSpecificDetails;

/**
 * Cas服务端业务范围解决器
 */
public interface CasServerScopeResolver {

    String resolveScope(UserSpecificDetails<?> userDetails);

    /**
     * 将指定业务范围应用到指定用户特性细节中
     *
     * @param userDetails 用户特性细节
     * @param scope       业务范围
     * @return 业务范围是否变更
     */
    boolean applyScope(UserSpecificDetails<?> userDetails, String scope);

}
