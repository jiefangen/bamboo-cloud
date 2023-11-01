package org.panda.support.cloud.core.security;

/**
 * 认证授权管理策略
 *
 * @author fangen
 **/
public interface AuthManagerStrategy {

    /**
     * 服务授权验证
     *
     * @param token jwt
     * @param service 服务api
     * @return 验证结果
     */
    boolean verification(String token, String service);

}
