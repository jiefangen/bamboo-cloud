package org.panda.support.sso.common.config;

import org.panda.tech.cas.server.config.CasServerMvcConfigurerSupport;
import org.panda.tech.core.web.mvc.servlet.filter.RequestLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebMvc配置器
 *
 * @author fangen
 **/
@Configuration
public class CasMvcConfig extends CasServerMvcConfigurerSupport {
    /**
     * web请求日志过滤器
     */
    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter("/spt-cas/**");
    }
}
