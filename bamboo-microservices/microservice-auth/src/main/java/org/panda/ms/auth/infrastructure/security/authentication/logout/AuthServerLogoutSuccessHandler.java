package org.panda.ms.auth.infrastructure.security.authentication.logout;

import org.panda.bamboo.common.util.LogUtil;
import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.jwt.InternalJwtResolver;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Auth服务端登出成功处理器
 */
public class AuthServerLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private InternalJwtResolver jwtResolver;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String jwt = request.getHeader(WebConstants.HEADER_AUTH_JWT);
        boolean jwtVerify = false;
        try {
            jwtVerify = jwtResolver.verify(jwt);
        } catch (Exception e) {
            LogUtil.error(getClass(), e);
        }
        if (jwtVerify) {
            // 登出成功token失效处理
            // 处理完成返回登出成功
            WebHttpUtil.buildJsonResponse(response, RestfulResult.success());
            return;
        }
        super.handle(request, response, authentication);
    }

}
