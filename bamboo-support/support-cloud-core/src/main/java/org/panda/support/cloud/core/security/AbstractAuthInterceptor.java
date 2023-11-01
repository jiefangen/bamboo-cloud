package org.panda.support.cloud.core.security;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.bamboo.common.util.lang.StringUtil;
import org.panda.tech.core.exception.ExceptionEnum;
import org.panda.tech.core.web.config.WebConstants;
import org.panda.tech.core.web.config.security.WebSecurityProperties;
import org.panda.tech.core.web.restful.RestfulResult;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 抽象认证鉴权拦截器
 *
 * @author fangen
 **/
public abstract class AbstractAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private WebSecurityProperties securityProperties;

    @Autowired(required = false)
    private AuthManagerStrategy authManagerStrategy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return true;
        }
        // 忽略资源直接放行
        if (this.securityProperties != null) {
            List<String> ignoringPatterns = this.securityProperties.getIgnoringPatterns();
            if (ignoringPatterns != null) {
                String url = WebHttpUtil.getRelativeRequestUrl(request);
                for (String ignoringPattern : ignoringPatterns) {
                    if (StringUtil.antPathMatchOneOf(url, ignoringPattern)) {
                        return true;
                    }
                }
            }
        }
        // 验证策略未配置则通过所有请求
        if (authManagerStrategy == null) {
            return true;
        }

        // 令牌权限校验
        String token = request.getHeader(WebConstants.HEADER_AUTH_JWT);
        if (StringUtils.isNotBlank(token)) {
            // Auth服务器验证令牌
            String api = WebHttpUtil.getRelativeRequestUrl(request);
            try {
                boolean verifyTokenResult = authManagerStrategy.verification(token, api);
                if (!verifyTokenResult) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
                return true;
            } catch (Exception e) {
                LogUtil.error(getClass(), e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                Object obj = RestfulResult.getFailure(ExceptionEnum.BUSINESS);
                WebHttpUtil.buildJsonResponse(response, obj);
                return false;
            }

        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
