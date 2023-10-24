package org.panda.ms.auth.infrastructure.security.authentication.login;

import org.panda.bamboo.common.util.LogUtil;
import org.panda.ms.auth.infrastructure.security.authentication.AuthAccountSpecificDetailsAuthenticationToken;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Auth鉴权成功处理器
 */
public class AuthLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            if (authentication instanceof AuthAccountSpecificDetailsAuthenticationToken) {
                AuthAccountSpecificDetailsAuthenticationToken authAccountToken =
                        (AuthAccountSpecificDetailsAuthenticationToken) authentication;
                String service = authAccountToken.getService();
                // 安全认证登录成功后的业务处理
                if (SecurityUtil.isAuthorized() && jwtGenerator.isAvailable()) {
                    DefaultUserSpecificDetails userSpecificDetails = SecurityUtil.getAuthorizedUserDetails();
                    // 登录成功，生成账户token返回，用于应用交互凭证
                    String token = jwtGenerator.generate(service, userSpecificDetails);
                    WebHttpUtil.buildJsonResponse(response, RestfulResult.success(token));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                WebHttpUtil.buildJsonResponse(response, RestfulResult.failure());
            }

        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
    }

}
