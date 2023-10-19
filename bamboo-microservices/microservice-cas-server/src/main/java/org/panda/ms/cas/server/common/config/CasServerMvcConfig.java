package org.panda.ms.cas.server.common.config;

import org.panda.support.cas.server.config.CasServerMvcConfigurerSupport;
import org.panda.tech.core.web.mvc.servlet.filter.RequestLogFilter;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.core.webmvc.jwt.JwtGeneratorImpl;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.core.webmvc.jwt.JwtParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    /**
     * 声明JWT生成器
     */
    @Bean
    @ConditionalOnMissingBean(JwtGenerator.class)
    public JwtGenerator jwtGenerator() {
        return new JwtGeneratorImpl();
    }

    /**
     * 声明JWT解析器
     */
    @Bean
    @ConditionalOnMissingBean(JwtParser.class)
    public JwtParser jwtParser() {
        return new JwtParserImpl();
    }
}
