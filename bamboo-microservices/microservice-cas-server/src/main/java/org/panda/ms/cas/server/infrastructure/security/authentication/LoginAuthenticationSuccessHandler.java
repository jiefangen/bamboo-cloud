package org.panda.ms.cas.server.infrastructure.security.authentication;

import org.panda.tech.core.config.app.AppConstants;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.security.user.DefaultUserSpecificDetails;
import org.panda.tech.security.util.SecurityUtil;
import org.panda.tech.security.web.authentication.AjaxAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置登录认证成功的处理器
 *
 * @author fangen
 **/
//@Component
public class LoginAuthenticationSuccessHandler extends AjaxAuthenticationSuccessHandler {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Value(AppConstants.EL_SPRING_APP_NAME)
    private String appName;

    @Override
    protected Object getAjaxLoginResult(HttpServletRequest request, Authentication authentication) {
        // 安全认证登录成功后的业务处理
        if (SecurityUtil.isAuthorized() && jwtGenerator.isAvailable()) {
            DefaultUserSpecificDetails userSpecificDetails = SecurityUtil.getAuthorizedUserDetails();
            // 登录成功，生成用户toke返回，用于前后端交互凭证
            String token = jwtGenerator.generate(appName, userSpecificDetails);
            return RestfulResult.success(token);
        }
        return RestfulResult.failure();
    }
}
