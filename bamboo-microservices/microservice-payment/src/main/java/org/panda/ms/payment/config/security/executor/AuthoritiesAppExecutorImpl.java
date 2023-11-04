package org.panda.ms.payment.config.security.executor;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.core.security.authority.AppConfigAuthority;
import org.panda.support.cloud.core.security.authority.AuthoritiesAppExecutor;
import org.panda.tech.core.config.app.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用权限集业务扩展执行器实现
 *
 * @author fangen
 **/
@Component
public class AuthoritiesAppExecutorImpl implements AuthoritiesAppExecutor {
    /**
     * 映射集：api路径-所需权限清单
     */
    private final Map<String, Collection<AppConfigAuthority>> apiConfigAuthoritiesMapping = new HashMap<>();

    @Value(AppConstants.EL_SPRING_APP_NAME)
    private String appName;

    @Override
    public void execute() {
        if (this.apiConfigAuthoritiesMapping.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Collection<AppConfigAuthority>> authorityEntry : this.apiConfigAuthoritiesMapping.entrySet()) {
            Collection<AppConfigAuthority> appConfigAuthorities = authorityEntry.getValue();

        }
        LogUtil.info(getClass(), "{} service permissions loading completed", appName);
    }

    @Override
    public void setApiConfigAuthoritiesMapping(String api, Collection<AppConfigAuthority> authorities) {
        if (StringUtils.isNotBlank(api)) {
            this.apiConfigAuthoritiesMapping.put(api, authorities);
        }
    }

    @Override
    public String[] getUrlPatterns() {
        return new String[]{"/payment/**"};
    }
}
