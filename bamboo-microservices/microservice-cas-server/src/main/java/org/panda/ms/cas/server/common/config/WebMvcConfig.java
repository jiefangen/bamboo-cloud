package org.panda.ms.cas.server.common.config;

import org.panda.tech.core.web.jwt.DefaultInternalJwtResolver;
import org.panda.tech.core.web.jwt.InternalJwtResolver;
import org.panda.tech.core.web.mvc.servlet.filter.RequestLogFilter;
import org.panda.tech.core.web.mvc.support.WebMvcConfigurerSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebMvc配置器
 *
 * @author fangen
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerSupport {
    /**
     * web请求日志过滤器
     */
    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter("/cas/**");
    }

    /**
     * 声明JWT解决器，视具体应用而定
     */
    @Bean
    @ConditionalOnMissingBean(InternalJwtResolver.class)
    public InternalJwtResolver internalJwtResolver() {
        return new DefaultInternalJwtResolver();
    }
}
