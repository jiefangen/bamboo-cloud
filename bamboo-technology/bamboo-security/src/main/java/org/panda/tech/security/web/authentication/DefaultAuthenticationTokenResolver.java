package org.panda.tech.security.web.authentication;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.jackson.JsonUtil;
import org.panda.tech.core.web.util.WebHttpUtil;
import org.panda.tech.security.authentication.DefaultAuthenticationToken;
import org.panda.tech.security.user.UsernamePassword;

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
            // 其次从请求的Body中提取
            String bodyString = WebHttpUtil.getRequestBodyString(request);
            if (StringUtils.isNotBlank(bodyString)) {
                UsernamePassword usernamePassword = JsonUtil.json2Bean(bodyString, UsernamePassword.class);
                username = usernamePassword.getUsername();
                password = usernamePassword.getPassword();
            }
        }

        // 规避trim空指针异常
        if (StringUtils.isEmpty(username)) {
            username = Strings.EMPTY;
        }
        if (StringUtils.isEmpty(password)) {
            password = Strings.EMPTY;
        }
        return new DefaultAuthenticationToken(username.trim(), password.trim());
    }

}
