package org.panda.ms.auth.infrastructure.security.authentication;

import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.core.config.app.AppConstants;
import org.panda.tech.core.web.jwt.InternalJwtResolver;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Auth鉴权成功处理器
 */
public class AuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private InternalJwtResolver jwtResolver;

    @Value(AppConstants.EL_SPRING_APP_NAME)
    private String appName;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            Object result = null;
            if (authentication instanceof AuthAccountSpecificDetailsAuthenticationToken) {
                AuthAccountSpecificDetailsAuthenticationToken authAccountToken =
                        (AuthAccountSpecificDetailsAuthenticationToken) authentication;
                String service = authAccountToken.getService();
                // TODO 安全认证登录成功后的业务处理
                if (SecurityUtil.isAuthorized() && jwtResolver.isGenerable(appName) ) {
                    DefaultUserSpecificDetails userSpecificDetails = SecurityUtil.getAuthorizedUserDetails();
                    // 登录成功，生成用户toke返回，用于前后端交互凭证
                    String token = jwtResolver.generate(appName, userSpecificDetails);
                    result = RestfulResult.success(token);
                }
            }

            WebHttpUtil.buildJsonResponse(response, result);
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
    }

}
