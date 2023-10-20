package org.panda.ms.cas.server.common.config;

import org.panda.support.cas.server.config.CasServerMvcConfigurerSupport;
import org.panda.tech.core.web.mvc.servlet.filter.RequestLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CasServerMvc配置器
 *
 * @author fangen
 **/
@Configuration
public class CasServerMvcConfig extends CasServerMvcConfigurerSupport {
    /**
     * web请求日志过滤器
     */
    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter("/cas-server/**");
    }

}
