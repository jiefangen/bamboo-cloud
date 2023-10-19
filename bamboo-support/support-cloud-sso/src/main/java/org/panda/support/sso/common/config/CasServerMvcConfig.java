package org.panda.support.sso.common.config;

import org.panda.tech.cas.server.config.CasServerMvcConfigurerSupport;
import org.springframework.context.annotation.Configuration;

/**
 * CasServerMvc配置器
 **/
@Configuration
public class CasServerMvcConfig extends CasServerMvcConfigurerSupport {
    /**
     * web请求日志过滤器
     */
//    @Bean
//    public RequestLogFilter requestLogFilter() {
//        return new RequestLogFilter("/spt-cas/**");
//    }
}
