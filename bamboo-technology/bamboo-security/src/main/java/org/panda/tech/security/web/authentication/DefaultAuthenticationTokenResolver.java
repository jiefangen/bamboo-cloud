package org.panda.tech.security.web.authentication;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.tech.security.authentication.DefaultAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认用户名密码登录认证令牌解决器
 */
public class DefaultAuthenticationTokenResolver
        extends AbstractAuthenticationTokenResolver<DefaultAuthenticationToken> {

    public static final String DEFAULT_PARAMETER_USERNAME = "username";
    public static final String DEFAULT_PARAMETER_PASSWORD = "password";

    public DefaultAuthenticationTokenResolver(String loginMode) {
        super(loginMode);
    }

    @Override
    public DefaultAuthenticationToken resolveAuthenticationToken(HttpServletRequest request) {
        // 优先从请求的Parameter中获取
        String username = request.getParameter(DEFAULT_PARAMETER_USERNAME);
        String password = request.getParameter(DEFAULT_PARAMETER_PASSWORD);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {


        }

        //
        if (username == null) {
            username = Strings.EMPTY;
        }
        if (password == null) {
            password = Strings.EMPTY;
        }
        return new DefaultAuthenticationToken(username.trim(), password.trim());
    }

}
