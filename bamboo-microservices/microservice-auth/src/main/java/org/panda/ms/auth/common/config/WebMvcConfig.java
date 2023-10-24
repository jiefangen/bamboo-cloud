package org.panda.ms.auth.common.config;

import org.panda.tech.core.web.mvc.servlet.filter.RequestLogFilter;
import org.panda.tech.core.web.mvc.support.WebMvcConfigurerSupport;
import org.panda.tech.core.webmvc.jwt.JwtGenerator;
import org.panda.tech.core.webmvc.jwt.JwtGeneratorImpl;
import org.panda.tech.core.webmvc.jwt.JwtParser;
import org.panda.tech.core.webmvc.jwt.JwtParserImpl;
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
        return new RequestLogFilter("/auth/**");
    }

    /**
     * 声明JWT解析器
     */
    @Bean
    @ConditionalOnMissingBean(JwtParser.class)
    public JwtParser jwtParser() {
        return new JwtParserImpl();
    }

    /**
     * 声明JWT生成器
     */
    @Bean
    @ConditionalOnMissingBean(JwtGenerator.class)
    public JwtGenerator jwtGenerator() {
        return new JwtGeneratorImpl();
    }

}
