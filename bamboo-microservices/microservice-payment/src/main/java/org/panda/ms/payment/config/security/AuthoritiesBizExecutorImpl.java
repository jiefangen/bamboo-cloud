package org.panda.ms.payment.config.security;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.util.LogUtil;
import org.panda.support.cloud.core.security.authority.AppConfigAuthority;
import org.panda.support.cloud.core.security.authority.AuthoritiesBizExecutor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc
 *
 * @author fangen
 **/
@Component
public class AuthoritiesBizExecutorImpl implements AuthoritiesBizExecutor {
    /**
     * 映射集：api路径-所需权限清单
     */
    private final Map<String, Collection<AppConfigAuthority>> apiConfigAuthoritiesMapping = new HashMap<>();

    @Override
    public void execute() {
        LogUtil.info(getClass(), "execute...");
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
