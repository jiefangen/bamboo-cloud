package org.panda.ms.cas.server.core.cas.controller;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.ms.cas.server.core.cas.authentication.CasServerScopeResolver;
import org.panda.ms.cas.server.core.cas.authentication.logout.CasServerLogoutHandler;
import org.panda.tech.core.web.util.NetUtil;
import org.panda.tech.security.config.annotation.ConfigAuthority;
import org.panda.tech.security.user.UserSpecificDetails;
import org.panda.tech.security.util.SecurityUtil;
import org.panda.tech.security.web.AjaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CAS服务端业务范围控制器支持。由实现子类指定访问路径，以更符合具体业务场景
 */
public abstract class CasServerScopeControllerSupport {

    @Autowired
    private CasServerLogoutHandler logoutHandler;
    @Autowired
    private CasServerScopeResolver scopeResolver;
    @Autowired
    private AjaxRedirectStrategy redirectStrategy;

    @GetMapping("/scope")
    @ConfigAuthority
    @ResponseBody
    public String getScope() {
        UserSpecificDetails<?> userDetails = SecurityUtil.getAuthorizedUserDetails();
        return this.scopeResolver.resolveScope(userDetails);
    }

    @RequestMapping("/scope/put")
    @ConfigAuthority
    public void putScope(@RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "next", required = false) String next, // 与登录时的_next不同，以避免冲突
            HttpServletRequest request, HttpServletResponse response) {
        UserSpecificDetails<?> userDetails = SecurityUtil.getAuthorizedUserDetails();
        if (this.scopeResolver.applyScope(userDetails, scope)) {
            this.logoutHandler.logoutClients(request);
        }
        if (StringUtils.isNotBlank(next) && (NetUtil.isHttpUrl(next, true) || next.startsWith(Strings.SLASH))) {
            try {
                this.redirectStrategy.sendRedirect(request, response, next);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
