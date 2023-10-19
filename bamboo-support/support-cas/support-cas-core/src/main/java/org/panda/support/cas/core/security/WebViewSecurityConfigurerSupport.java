package org.panda.support.cas.core.security;

import org.apache.commons.lang3.StringUtils;
import org.panda.bamboo.common.constant.basic.Strings;
import org.panda.bamboo.common.util.lang.StringUtil;
import org.panda.support.cas.core.security.controller.RedirectControllerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.util.Collection;
import java.util.Collections;

/**
 * WEB视图层安全配置支持
 */
public abstract class WebViewSecurityConfigurerSupport extends WebMvcSecurityConfigurerSupport {

    @Autowired
    private WebMvcProperties mvcProperties;

    @Bean
    @Override
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = (AccessDeniedHandlerImpl) super.accessDeniedHandler();
        return accessDeniedHandler;
    }

    /**
     * 获取安全框架忽略的URL ANT样式集合
     *
     * @return 安全框架忽略的URL ANT样式集合
     */
    @Override
    protected Collection<String> getIgnoringAntPatterns() {
        Collection<String> patterns = super.getIgnoringAntPatterns();
        patterns.add(getIgnoringAntPatternFromController(RedirectControllerSupport.class));
        // 静态资源全部忽略
        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (StringUtils.isNotBlank(staticPathPattern)) {
            String[] staticPathPatterns = StringUtil.splitAndTrim(staticPathPattern, Strings.COMMA);
            Collections.addAll(patterns, staticPathPatterns);
        }
        return patterns;
    }

}
