package org.panda.support.cas.server.authentication.logout;

import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * CAS服务端登出处理器
 */
public interface CasServerLogoutHandler extends LogoutHandler {

    /**
     * 登出当前所有已登录客户端应用
     *
     * @param request HTTP请求
     */
    void logoutClients(HttpServletRequest request);

}
